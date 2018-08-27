package com.amolkhot.ecommercetestapp;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.amolkhot.ecommercetestapp.ui.MainActivity;
import com.amolkhot.ecommercetestapp.ui.ProductList;
import com.amolkhot.ecommercetestapp.ui.SplashScreen;

/**
 * Created by amol.khot on 27-Aug-18.
 */

public class ViewController {
    private static String TAG="ViewController";

    public static void customizeActionBar(AppCompatActivity activity){
        activity.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        activity.getSupportActionBar().setDisplayShowCustomEnabled(true);
        activity.getSupportActionBar().setCustomView(R.layout.custom_action_bar);
    }
    public static void launchProductList(@NonNull Context context, int cat_id){
        Log.i(TAG,"launchProductList("+cat_id+")");
        Intent intent = new Intent(context,ProductList.class);
        intent.putExtra("cat_id",cat_id);
        context.startActivity(intent);
    }
}
