package com.amolkhot.ecommercetestapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.amolkhot.ecommercetestapp.Constants;

/**
 * Created by amol.khot on 27-Aug-18.
 */

public class Utils {

    public static boolean isDataLoaded(Context context){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return (sharedPreferences.getBoolean(Constants.dataLoadedKey,false));
    }
    public static void setIsDataLoaded(Context context,Boolean status){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(Constants.dataLoadedKey,status);
        editor.commit();
    }
}
