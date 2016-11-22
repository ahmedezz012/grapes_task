package com.ezz.grapes_task.Application;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;


public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Configuration c = new Configuration.Builder(this).setDatabaseName("Products.db").create();
        ActiveAndroid.initialize(c);
    }
}
