package com.ezz.grapes_task.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.ezz.grapes_task.Fragments.Product_detail_Fragment;
import com.ezz.grapes_task.R;

public class Detail_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_);

        getSupportActionBar().setTitle(getString(R.string.productdetail));

        Product_detail_Fragment productDetailFragment = (Product_detail_Fragment) getSupportFragmentManager().findFragmentById(R.id.detailfragment);

        productDetailFragment.setId(getIntent().getExtras().getInt("id"));

    }
}
