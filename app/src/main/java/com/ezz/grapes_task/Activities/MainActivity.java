package com.ezz.grapes_task.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ezz.grapes_task.Fragments.Product_detail_Fragment;
import com.ezz.grapes_task.Listeners.ProductClickListener;
import com.ezz.grapes_task.R;

public class MainActivity extends AppCompatActivity implements ProductClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(getString(R.string.products));
    }

    @Override
    public void productClicked(int id) {
        View v=findViewById(R.id.Product_detail_Fragment_container);
        if(v != null)
        {
            Product_detail_Fragment productDetailFragment=new Product_detail_Fragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            productDetailFragment.setId(id);
            fragmentTransaction.replace(R.id.Product_detail_Fragment_container,productDetailFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
        else
        {
            //intent
            Intent intent = new Intent(MainActivity.this,Detail_Activity.class);
            intent.putExtra("id",id);
            startActivity(intent);
        }
    }
}
