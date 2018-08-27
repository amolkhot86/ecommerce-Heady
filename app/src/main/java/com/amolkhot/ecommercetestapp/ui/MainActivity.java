package com.amolkhot.ecommercetestapp.ui;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.amolkhot.ecommercetestapp.Constants;
import com.amolkhot.ecommercetestapp.MyApplication;
import com.amolkhot.ecommercetestapp.R;
import com.amolkhot.ecommercetestapp.ViewController;
import com.amolkhot.ecommercetestapp.adapters.CategoryListAdapter;
import com.amolkhot.ecommercetestapp.db.datacontroller.CategoryController;
import com.amolkhot.ecommercetestapp.db.structure.Category;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    NavigationView nav_view;
    DrawerLayout drawer;
    ListView categoryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setCustomActionBar();
        initialize();
        setClickListeners();
        loadSidePanel();
    }

    private void setCustomActionBar(){
        ViewController.customizeActionBar(this);
    }

    private void initialize(){
        drawer = findViewById(R.id.drawer_layout);
        nav_view = findViewById(R.id.nav_view);
        categoryList = findViewById(R.id.categoryList);
    }

    private void setClickListeners(){
        getSupportActionBar().getCustomView().findViewById(R.id.action_bar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"_______ButtonClick");
                if (drawer.isDrawerOpen(nav_view)) {
                    if(categories.size()>0) closeNavigationDrawer();
                else
                        Toast.makeText(MainActivity.this,"Please Select Category",Toast.LENGTH_SHORT).show();
                } else {
                    openNavigationDrawer();
                }
            }
        });
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Menu m=nav_view.getMenu();
                for(int i=0;i<rootCategories.size();i++){
                    if(rootCategories.get(i).cat_id == item.getItemId()){
                        if(rootCategories.get(i).hasChildCat==1) {
                            for(int j=0;j<m.size();j++){
                                for(int k=0;k<m.getItem(j).getSubMenu().size();k++)
                                    nav_view.getMenu().getItem(j).getSubMenu().getItem(k).setChecked(false);
                            }
                            item.setChecked(true);
                            closeNavigationDrawer();
                            loadChildCategories(item.getItemId());
                        }
                        else
                            ViewController.launchProductList(MainActivity.this,item.getItemId());
                       break;
                    }
                }
                return true;
            }
        });
    }

    List<Category> rootCategories;
    List<Category> categories=new ArrayList<>();
    private void loadSidePanel(){
        new LoadRootCategoryData().execute();
    }


    private void loadChildCategories(int parent_cat_id){
        new LoadChildCategoryData().execute(parent_cat_id);
    }

    private void loadCategoryList(){
        categoryList.setAdapter(new CategoryListAdapter(this,categories));
    }

    @Override
    public void onBackPressed() {
//        if(drawer.isDrawerOpen(nav_view)) drawer.closeDrawer(nav_view);
    }

    private void openNavigationDrawer(){
        drawer.openDrawer(nav_view);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
    }
    private void closeNavigationDrawer(){
        drawer.closeDrawer(nav_view);
    }

    private static class CategoryLoadParams {
        int category_id;
        int loadID;

        CategoryLoadParams(int category_id,int loadID) {
            this.category_id = category_id;
            this.loadID = loadID;
        }
    }
    private class LoadRootCategoryData extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            final CategoryController catOperation= MyApplication.getAppInstance().getShoppingDatabase().getCategoryDataOperationsInstance();
            rootCategories = catOperation.getRootCategories();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Menu menu = nav_view.getMenu();
            List<SubMenu> sectionCategory=new ArrayList<>();
            List<Integer> sectionCatId=new ArrayList<>();
            for(int i=0;i<rootCategories.size();i++){
                if(rootCategories.get(i).parentCatId== Constants.ROOT_ID){
                    sectionCatId.add(rootCategories.get(i).cat_id);
                    sectionCategory.add(menu.addSubMenu(i,rootCategories.get(i).cat_id,0,rootCategories.get(i).catName));
                }
            }
            for(int i=0;i<rootCategories.size();i++){
                if(rootCategories.get(i).parentCatId != Constants.ROOT_ID){
                    for(int j=0;j<sectionCatId.size();j++){
                        if(rootCategories.get(i).parentCatId == sectionCatId.get(j)){
                            sectionCategory.get(j).add(0, rootCategories.get(i).cat_id, 0, rootCategories.get(i).catName);
                            break;
                        }
                    }
                }
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    openNavigationDrawer();
                }
            }, 500);

        }
    }

    private class LoadChildCategoryData extends AsyncTask<Integer,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... category_id) {
            final CategoryController catOperation= MyApplication.getAppInstance().getShoppingDatabase().getCategoryDataOperationsInstance();
            categories = catOperation.getChildCategories(category_id[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            loadCategoryList();
        }
    }
}
