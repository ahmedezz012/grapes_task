package com.ezz.grapes_task.Fragments;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ezz.grapes_task.Adpaters.RecyclerViewAdapter;
import com.ezz.grapes_task.Constants;
import com.ezz.grapes_task.Listeners.ProductClickListener;
import com.ezz.grapes_task.Listeners.RecyclerViewListener;
import com.ezz.grapes_task.Models.ImageObject;
import com.ezz.grapes_task.Models.Product;
import com.ezz.grapes_task.Models.ProductOffline;
import com.ezz.grapes_task.R;
import com.google.gson.Gson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Products_Fragment extends Fragment {


    private RequestQueue requestQueue;
    private Gson gson;
    public static ArrayList<Product> products;
    private RecyclerViewAdapter recyclerViewAdapter;
    private int startid = -10;
    private RecyclerView recyclerView;
    private RelativeLayout progress;
    private ProductClickListener productClickListener;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    RecyclerViewListener recyclerViewListener;
    public Products_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.productClickListener = (ProductClickListener)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.products_recyclerView);

        progress = (RelativeLayout) view.findViewById(R.id.load_more_layout);

        requestQueue = Volley.newRequestQueue(getActivity());

        gson = new Gson();

        products = new ArrayList<>();

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(),products,productClickListener);

        recyclerView.setAdapter(recyclerViewAdapter);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(staggeredGridLayoutManager);

         recyclerViewListener = new RecyclerViewListener(staggeredGridLayoutManager) {
            @Override
            public void LoadMoreProducts() {
                getProducts();
            }
        };
        recyclerView.setOnScrollListener(recyclerViewListener);

        //get from cache
        getFromCache();

        //get from API
        getProducts();

        return view;
    }

    public void getProducts()
    {
        progress.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constants.url+(startid+10), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                  startid+=10;
                  if(startid == 0)
                  {
                      if(products.size()!=0) {
                          products.clear();
                          recyclerViewListener.reset();
                          recyclerViewAdapter.notifyDataSetChanged();
                      }
                  }
                   final Product []productsArray = gson.fromJson(response,Product[].class);

                   converter(productsArray,products);

                   recyclerViewAdapter.notifyDataSetChanged();

                   progress.setVisibility(View.GONE);

                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            SaveToCache(productsArray);
                        }
                    };
                    thread.start();
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),getString(R.string.networkerror),Toast.LENGTH_LONG).show();
                progress.setVisibility(View.GONE);
            }
        }
        );
        requestQueue.add(stringRequest);
    }
    private void converter(Product []p,ArrayList<Product> prod)
    {
        for(int i=0;i<p.length;i++)
        {
             prod.add(p[i]);
        }
    }
    public void SaveToCache(Product []p)
    {
        if(startid==0) {
            new Delete().from(ProductOffline.class).execute();
            putintoCache(p);
        }
        else
            putintoCache(p);
    }
    public void putintoCache(Product []p)
    {
        ActiveAndroid.beginTransaction();
        try {
            for (int i = 0; i < p.length; i++) {
                Product prod = p[i];
                ProductOffline productOffline = new ProductOffline(prod.getId()
                        , prod.getPrice()
                        , prod.getProductDescription()
                        , prod.getImage().getWidth()
                        , prod.getImage().getHeight()
                        , convert_URL_to_bytes(prod.getImage().getUrl()));
                Long l = productOffline.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        }catch (Exception e)
        {
            Log.d("Cache Exception",e.getMessage().toString());
        }
        finally {
            ActiveAndroid.endTransaction();
        }
    }
    public void getFromCache()
    {
        List<ProductOffline> list = new Select().from(ProductOffline.class).execute();
        if(list.size()>0) {
            for (int i = 0; i < list.size(); i++) {
                ProductOffline pf = list.get(i);
                ImageObject imageObject = new ImageObject(pf.getWidth(), pf.getHeight(), pf.getImage());
                Product product = new Product(pf.getProductid(), imageObject, pf.getPrice(), pf.getDescription());
                products.add(product);
            }
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }
    public byte[] convert_URL_to_bytes(String url)
    {
        AsyncTask<String, Void, byte[]> image = new DownloadImageTask().execute(url);
        try {
            return image.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    private class DownloadImageTask extends AsyncTask<String, Void, byte[]> {

        protected byte[] doInBackground(String... urls) {
            String url = urls[0];
            try {
                InputStream in = new java.net.URL(url).openStream();
                ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];

                int len = 0;
                while ((len = in.read(buffer)) != -1) {
                    byteBuffer.write(buffer, 0, len);
                }
                return byteBuffer.toByteArray();
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(byte[] result) {

        }
    }
}
