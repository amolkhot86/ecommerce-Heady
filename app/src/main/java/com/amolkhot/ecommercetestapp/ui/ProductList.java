package com.amolkhot.ecommercetestapp.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.amolkhot.ecommercetestapp.MyApplication;
import com.amolkhot.ecommercetestapp.R;
import com.amolkhot.ecommercetestapp.ViewController;
import com.amolkhot.ecommercetestapp.adapters.ProductListAdapter;
import com.amolkhot.ecommercetestapp.db.datacontroller.ProductsController;
import com.amolkhot.ecommercetestapp.db.structure.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductList extends AppCompatActivity {

    private static String TAG="ProductList";
    private GridView productList;
    private List<Product> products=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        setCustomActionBar();
        initialize();
        setClickListeners();
        loadProductList(getIntent().getExtras().getInt("cat_id"));
    }
    private void setCustomActionBar(){
        ViewController.customizeActionBar(this);
    }
    private void initialize(){
        productList = findViewById(R.id.productList);
    }
    private void setClickListeners(){
        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
    private void loadProductList(int cat_id){
        Log.i(TAG,"loadProductList("+cat_id+")");
        new LoadProductList().execute(cat_id);
    }

    private class LoadProductList extends AsyncTask<Integer,String,String>{

        @Override
        protected String doInBackground(Integer... cat_id) {
            final ProductsController prodOperation= MyApplication.getAppInstance().getShoppingDatabase().getProductsDataOperationsInstance();
            products.clear();
            products.addAll(prodOperation.getProducts(cat_id[0]));
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            productList.setAdapter(new ProductListAdapter(ProductList.this,products));
        }
    }
}
