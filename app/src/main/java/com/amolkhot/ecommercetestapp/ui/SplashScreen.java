package com.amolkhot.ecommercetestapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amolkhot.ecommercetestapp.R;
import com.amolkhot.ecommercetestapp.db.DatabasePopulateTask;
import com.amolkhot.ecommercetestapp.util.Callback;
import com.amolkhot.ecommercetestapp.util.UrlDataLoader;
import com.amolkhot.ecommercetestapp.util.Utils;

import java.net.MalformedURLException;
import java.net.URL;

public class SplashScreen extends AppCompatActivity {

    private final String TAG="SplashScreen";
    private TextView loadMessageText;
    private Button retryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initialize();
        setClickListeners();
        if(Utils.isDataLoaded(this)) {
            loadMessageText.setText("Data already available in Memory......");
            loadNextScreen();
        } else {
            loadDataFromUrl();
        }
    }
    private void initialize(){
        loadMessageText=findViewById(R.id.loadMessageText);
        retryButton = findViewById(R.id.retryButton);
    }
    private void setClickListeners(){
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDataFromUrl();
            }
        });
    }

    private void loadNextScreen(){
        Intent intent = new Intent(SplashScreen.this,MainActivity.class);
        startActivity(intent);
    }

    private void loadDataFromUrl(){
        Log.i(TAG, "loadData()");
        try { new UrlDataLoader(new Callback() {
            @Override public void taskStarted() {
                retryButton.setVisibility(View.GONE);
                loadMessageText.setText("Loading Data From URL......");
            }
            @Override public void taskCompleted(Object data) {
                loadMessageText.setText("Received Data from URL......");
                if(data!=null) loadDataToDatabase(data.toString());
                else {
                    loadMessageText.setText("Unable to load Data from URL......");
                    retryButton.setVisibility(View.VISIBLE);

                }
            }
        }).execute(new URL("https://stark-spire-93433.herokuapp.com/json"));
        }catch (MalformedURLException e){
            Log.e(TAG,"URL load Exception : " + e.getMessage());
        }
    }

    private void loadDataToDatabase(String data){
        new DatabasePopulateTask(new Callback() {
            @Override
            public void taskCompleted(Object param) {
                loadMessageText.setText("Data saved locally.....");
                Utils.setIsDataLoaded(SplashScreen.this,true);
                loadNextScreen();
            }

            @Override
            public void taskStarted() {
                loadMessageText.setText("Storing Data locally.....");
            }
        }).execute(data);
    }
}
