package com.amolkhot.ecommercetestapp;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.util.Log;

import com.amolkhot.ecommercetestapp.db.DatabasePopulateTask;
import com.amolkhot.ecommercetestapp.db.ShoppingDatabase;
import com.amolkhot.ecommercetestapp.util.Callback;
import com.amolkhot.ecommercetestapp.util.UrlDataLoader;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by amol.khot on 23-Aug-18.
 */

public class MyApplication extends Application {
    private ShoppingDatabase shoppingDb;
    private static MyApplication instance;
    private final String TAG="MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        initializeShoppingDatabase();
    }
    public static MyApplication getAppInstance(){return instance;}
    public ShoppingDatabase getShoppingDatabase(){return shoppingDb;}
    private void initializeShoppingDatabase(){
        shoppingDb=Room.databaseBuilder(getApplicationContext(),ShoppingDatabase.class,"shopping.db").build();
    }


}
