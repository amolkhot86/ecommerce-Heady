package com.amolkhot.ecommercetestapp.db;

import android.os.AsyncTask;
import android.util.Log;

import com.amolkhot.ecommercetestapp.Constants;
import com.amolkhot.ecommercetestapp.MyApplication;
import com.amolkhot.ecommercetestapp.db.datacontroller.CategoryController;
import com.amolkhot.ecommercetestapp.db.datacontroller.CategoryProductLinkController;
import com.amolkhot.ecommercetestapp.db.datacontroller.ProductsController;
import com.amolkhot.ecommercetestapp.db.datacontroller.VariantsController;
import com.amolkhot.ecommercetestapp.db.structure.Category;
import com.amolkhot.ecommercetestapp.db.structure.CategoryProductLink;
import com.amolkhot.ecommercetestapp.db.structure.Product;
import com.amolkhot.ecommercetestapp.db.structure.Variants;
import com.amolkhot.ecommercetestapp.util.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by amol.khot on 23-Aug-18.
 */

public class DatabasePopulateTask extends AsyncTask <String,String,String>{
    final String TAG="DatabasePopulateTask";
    Callback callback;

    public DatabasePopulateTask(Callback callback){
        this.callback=callback;
    }
    public DatabasePopulateTask(){}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(callback!=null) callback.taskStarted();
    }

    @Override
    protected String doInBackground(String... data) {
        Log.i(TAG,"START");
        MyApplication.getAppInstance().getShoppingDatabase().clearAllTables();
        try {
            JSONObject rootNode = new JSONObject(data[0]);
            JSONArray categoryNode = rootNode.getJSONArray("categories");
            Log.i(TAG,"Initialize Controllers");
            CategoryController catOperation=MyApplication.getAppInstance().getShoppingDatabase().getCategoryDataOperationsInstance();
            ProductsController prodOperation=MyApplication.getAppInstance().getShoppingDatabase().getProductsDataOperationsInstance();
            VariantsController varOperation=MyApplication.getAppInstance().getShoppingDatabase().getVariantsDataOperationsInstance();
            CategoryProductLinkController catProdLinkOperation=MyApplication.getAppInstance().getShoppingDatabase().getCategoryProductLinkController();

            catOperation.insert(new Category(Constants.BY_CATEGORY_ID,"By Categories",Constants.ROOT_ID,1));
            List<Map<String, Integer>> category_parent_array = new ArrayList<>();
            for(int i=0;i<categoryNode.length();i++){
                JSONObject catItem = ((JSONObject)categoryNode.get(i));
                catOperation.insert(new Category(catItem.getInt("id"),catItem.getString("name"),Constants.BY_CATEGORY_ID,0));
                JSONArray productsArray = catItem.getJSONArray("products");
                for(int j=0;j<productsArray.length();j++){
                    JSONObject prodItem = ((JSONObject) productsArray.get(j));
                    JSONObject taxItem = prodItem.getJSONObject("tax");
                    prodOperation.insert(new Product(prodItem.getInt("id"),prodItem.getString("name"),prodItem.getString("date_added"),taxItem.getString("name"),taxItem.getDouble("value"),0,0,0));
                    catProdLinkOperation.insert(new CategoryProductLink(prodItem.getInt("id"),catItem.getInt("id")));
                    JSONArray variantsArray = prodItem.getJSONArray("variants");
                    for(int k=0;k<variantsArray.length();k++){
                        JSONObject varientItem = ((JSONObject) variantsArray.get(k));
                        int size=0;
                        if(!((varientItem.get("size")).equals(null))) size=varientItem.getInt("size");
                        varOperation.insert(new Variants(varientItem.getInt("id"),prodItem.getInt("id"),varientItem.getString("color"),size,varientItem.getInt("price")));
                    }
                }
                JSONArray childCats = catItem.getJSONArray("child_categories");
                if(childCats.length()>0) {
                    for (int childCatIndex = 0; childCatIndex < childCats.length(); childCatIndex++) {
                        Map<String, Integer> tmpArray = new HashMap<String, Integer>();
                        tmpArray.put("cat_id", childCats.getInt(childCatIndex));
                        tmpArray.put("parent_id", catItem.getInt("id"));
                        category_parent_array.add(tmpArray);
                    }
                }
            }

            for(int i=0;i<category_parent_array.size();i++){
                catOperation.updateParentCategory(category_parent_array.get(i).get("cat_id"),category_parent_array.get(i).get("parent_id"));
            }
            catOperation.updateHasParentCat();
            // Reading Ranking Array
            JSONArray rankingsNode = rootNode.getJSONArray("rankings");
            catOperation.insert(new Category(Constants.BY_RANKING_ID,"By Ranking",Constants.ROOT_ID,1));
            List<Integer> product_array=new ArrayList<>();
            List<Map<String,Integer>> ranking_array=new ArrayList<>();
            for(int i=0;i<rankingsNode.length();i++){
                JSONObject rankingItem = ((JSONObject)rankingsNode.get(i));
                int newCatId=(Constants.BY_RANKING_ID+(i+1));
                catOperation.insert(new Category(newCatId,rankingItem.getString("ranking"),Constants.BY_RANKING_ID,0));
                JSONArray rankingProducts= rankingItem.getJSONArray("products");
                for(int j=0;j<rankingProducts.length();j++) {
                    JSONObject rankProductItem = ((JSONObject)rankingProducts.get(j));
                    catProdLinkOperation.insert(new CategoryProductLink(rankProductItem.getInt("id"),newCatId));
                    int count =0;
                    String value_name="";
                    try {
                        if (!(rankProductItem.get("view_count").equals(null))) {
                            value_name = "view_count";
                            count = rankProductItem.getInt("view_count");
                        }

                    }catch (Exception e){}
                    try {
                        if (!(rankProductItem.get("order_count").equals(null))) {
                            value_name = "order_count";
                            count = rankProductItem.getInt("order_count");
                        }
                    }catch (Exception e){}
                    try{
                        if(!(rankProductItem.get("shares").equals(null))) {
                            value_name="shares";
                            count = rankProductItem.getInt("shares");
                        }
                    }catch (Exception e){}


                    Map<String,Integer> tmpProd;
                    if(product_array.indexOf(rankProductItem.getInt("id")) == -1) {
                        product_array.add(rankProductItem.getInt("id"));
                        tmpProd=new HashMap<>();
                        tmpProd.put(value_name,count);
                        ranking_array.add(tmpProd);
                    }else {
                        tmpProd = ranking_array.get(product_array.indexOf(rankProductItem.getInt("id")));
                        tmpProd.put(value_name,count);
                        ranking_array.set(product_array.indexOf(rankProductItem.getInt("id")),tmpProd);
                    }
                }
            }

            for(int i=0;i<product_array.size();i++){
                int view_count=0,order_count=0,shares=0;
                for (Map.Entry<String, Integer> entry : ranking_array.get(i).entrySet()) {
                    if (entry.getKey().equals("view_count")) view_count = entry.getValue();
                    if (entry.getKey().equals("order_count")) order_count = entry.getValue();
                    if (entry.getKey().equals("shares")) shares = entry.getValue();
                }
                prodOperation.updateRanking(product_array.get(i),view_count,order_count,shares);
            }
        }catch (JSONException e){
            Log.e(TAG,"Error While Parsing JSON : " + e.getMessage());
        }catch (Exception e){
            Log.e(TAG,"Error While Inserting Record : " + e.getMessage());
        }
        Log.i(TAG,"FINISH");
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(callback!=null) callback.taskCompleted("");
    }
}
