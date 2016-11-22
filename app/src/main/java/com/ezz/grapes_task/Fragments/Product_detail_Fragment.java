package com.ezz.grapes_task.Fragments;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezz.grapes_task.R;
import com.squareup.picasso.Picasso;


public class Product_detail_Fragment extends Fragment {


    private TextView product_description_detail;
    private ImageView product_image_detail;
    private int id;
    public Product_detail_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        if(savedInstanceState != null)
        {
            id = savedInstanceState.getInt("id");
        }

        product_description_detail = (TextView) view.findViewById(R.id.product_description_detail);

        product_image_detail = (ImageView) view.findViewById(R.id.product_image_detail);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        product_description_detail.setText(Products_Fragment.products.get(id).getProductDescription());

        product_image_detail.getLayoutParams().height = (Products_Fragment.products.get(id).getImage().getHeight())*2;

        if(Products_Fragment.products.get(id).getImage().getUrl()!=null) {
            Picasso.with(getActivity()).
                    load(Products_Fragment.products.get(id).getImage().getUrl()).
                    placeholder(R.drawable.noimage).
                    into(product_image_detail);
        }
        else
        {
            byte[] b = Products_Fragment.products.get(id).getImage().getImage();
            try {
                product_image_detail.setImageBitmap(BitmapFactory.decodeByteArray(b, 0, b.length));
            }catch (Exception ex)
            {

            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("id",id);
    }

    public void setId(int id) {
        this.id = id;
    }
}
