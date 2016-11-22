package com.ezz.grapes_task.Adpaters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ezz.grapes_task.Listeners.ItemClickListener;
import com.ezz.grapes_task.Listeners.ProductClickListener;
import com.ezz.grapes_task.Models.Product;
import com.ezz.grapes_task.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.TheViewHolder>{

    private Context context;

    private ArrayList<Product> products;

    private LayoutInflater layoutInflater;

    private ProductClickListener productClickListener;

    public RecyclerViewAdapter() {
    }

    public RecyclerViewAdapter(Context context, ArrayList<Product> products, ProductClickListener productClickListener) {
        this.context = context;
        this.products = products;
        this.productClickListener = productClickListener;
        layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public TheViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.product_item_layout,null);

        TheViewHolder theViewHolder = new TheViewHolder(view);

        return theViewHolder;
    }

    @Override
    public void onBindViewHolder(TheViewHolder holder, int position) {
        String product_price_string = "$"+products.get(position).getPrice();

        String product_description_string  = products.get(position).getProductDescription();

        int height = products.get(position).getImage().getHeight();

        holder.product_price.setText(product_price_string);

        holder.product_description.setText(product_description_string);

        holder.product_Image.getLayoutParams().height = height;

        if(products.get(position).getImage().getUrl()!=null) {
            String product_image_url = products.get(position).getImage().getUrl();
            Picasso.with(context).load(product_image_url).placeholder(R.drawable.noimage).into(holder.product_Image);
        }
        else
        {
            byte[] b  = products.get(position).getImage().getImage();
            if(b!=null) {
                Bitmap bm = BitmapFactory.decodeByteArray(b, 0, b.length);
                try {
                    holder.product_Image.setImageBitmap(bm);
                }catch (Exception ex)
                {

                }
            }
        }
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnItemClick(int pos) {
                if(productClickListener!=null)
                {
                    productClickListener.productClicked(pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class TheViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView product_Image;

        TextView  product_price,product_description;

        ItemClickListener itemClickListener;

        public TheViewHolder(View itemView) {
            super(itemView);

            product_description = (TextView) itemView.findViewById(R.id.product_description);

            product_price = (TextView) itemView.findViewById(R.id.product_price);

            product_Image = (ImageView) itemView.findViewById(R.id.product_image);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
          this.itemClickListener.OnItemClick(this.getLayoutPosition());
        }
    }
}
