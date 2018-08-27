package com.amolkhot.ecommercetestapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.amolkhot.ecommercetestapp.Constants;
import com.amolkhot.ecommercetestapp.MyApplication;
import com.amolkhot.ecommercetestapp.R;
import com.amolkhot.ecommercetestapp.ViewController;
import com.amolkhot.ecommercetestapp.db.datacontroller.CategoryController;
import com.amolkhot.ecommercetestapp.db.structure.Category;
import com.amolkhot.ecommercetestapp.ui.MainActivity;
import com.amolkhot.ecommercetestapp.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amol.khot on 25-Aug-18.
 */

public class SubCategoryListAdapter extends BaseAdapter{

    final String TAG="SubCategoryListAdapter";
    Context context;
    List<Category> categoryList= new ArrayList<>();

    public SubCategoryListAdapter(Context context,List<Category> categories){
        this.context=context;
        categoryList.clear();
        categoryList.addAll(categories);
    }
    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return categoryList.get(i).cat_id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = mInflater.inflate(R.layout.subcategory_listitem, null);
            ((TextView)view.findViewById(R.id.category_title)).setText(categoryList.get(i).catName);
        }
        final int thisIndex=i;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Log.i(TAG,"ITEM CLICKED : " + categoryList.get(thisIndex).catName + "("+categoryList.get(thisIndex).cat_id+")");
                ViewController.launchProductList(context,categoryList.get(thisIndex).cat_id);
            }
        });
        return view;
    }
}
