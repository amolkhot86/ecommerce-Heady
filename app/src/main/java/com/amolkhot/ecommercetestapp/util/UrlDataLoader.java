package com.amolkhot.ecommercetestapp.util;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by amol.khot on 22-Aug-18.
 */

public class UrlDataLoader extends AsyncTask<URL,Integer,String> {

    private final String TAG="UrlDataLoader";
    HttpURLConnection connection = null;
    BufferedReader reader = null;
    Callback callback;

    public UrlDataLoader(Callback callback){
        this.callback=callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.taskStarted();
    }

    @Override
    protected String doInBackground(@NonNull URL... params) {
        try {
            Log.i(TAG,"Loading URL : "+params[0]);
            connection = (HttpURLConnection) params[0].openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
                Log.d(TAG, "Response : " + line);
            }
            return buffer.toString();
        }catch (Exception e){
            Log.e(TAG,"Error : " + e.getMessage());
            return null;
        }finally {
            if (connection != null) connection.disconnect();
            if(reader != null) try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.i(TAG,"Progress : "+values);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        callback.taskCompleted(result);
    }
}
