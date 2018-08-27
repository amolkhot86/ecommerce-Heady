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
import com.amolkhot.ecommercetestapp.db.datacontroller.CategoryController;
import com.amolkhot.ecommercetestapp.db.structure.Category;
import com.amolkhot.ecommercetestapp.ui.MainActivity;
import com.amolkhot.ecommercetestapp.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amol.khot on 25-Aug-18.
 */

public class CategoryListAdapter extends BaseAdapter{

    final String TAG="CategoryListAdapter";
    Context context;
    List<Category> categoryList= new ArrayList<>();

    public CategoryListAdapter(Context context,List<Category> categories){
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
        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = mInflater.inflate(R.layout.category_listitem, null);
            ((TextView)view.findViewById(R.id.category_title)).setText(categoryList.get(i).catName);
            if((categoryList.get(i).hasChildCat==1)) view.findViewById(R.id.toggle_button).setVisibility(View.VISIBLE);
            else view.findViewById(R.id.toggle_button).setVisibility(View.GONE);
        }
        final int thisIndex=i;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Log.i(TAG,"ITEM CLICKED : " + categoryList.get(thisIndex).catName + "("+categoryList.get(thisIndex).cat_id+") | " + view.findViewById(R.id.toggle_button).isSelected());
                if(categoryList.get(thisIndex).hasChildCat==1) {
                    if(!view.findViewById(R.id.toggle_button).isSelected()) {
                        new LoadChildCategoryData().execute(new LoadCategoryParams(categoryList.get(thisIndex).cat_id,
                                new Callback() {
                                    @Override
                                    public void taskCompleted(Object param) {
                                        Log.i(TAG, "______LOAD COMPLETED : " + ((List<Category>) param).size());
                                        if (((List<Category>) param).size() > 0) {
                                            view.findViewById(R.id.toggle_button).setSelected(true);
                                            ((GridView) view.findViewById(R.id.subCatList)).setVisibility(View.VISIBLE);
                                            ((GridView) view.findViewById(R.id.subCatList)).setAdapter(new SubCategoryListAdapter(context, ((List<Category>) param)));
                                        }
                                    }

                                    @Override
                                    public void taskStarted() {
                                    }
                                }));
                    }else {
                        ((GridView) view.findViewById(R.id.subCatList)).setVisibility(View.INVISIBLE);
                        ((GridView) view.findViewById(R.id.subCatList)).setAdapter(null);
                        view.findViewById(R.id.toggle_button).setSelected(false);
                    }
                }
            }
        });
        return view;
    }

    private class LoadCategoryParams{
        int category_id;
        Callback callback;
        public LoadCategoryParams(int category_id,Callback callback){
            this.category_id=category_id;
            this.callback = callback;
        }
    }
    private class LoadChildCategoryData extends AsyncTask<LoadCategoryParams,String,Callback>{
        List<Category> populatedList;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Callback doInBackground(LoadCategoryParams... params) {
            final CategoryController catOperation= MyApplication.getAppInstance().getShoppingDatabase().getCategoryDataOperationsInstance();
            populatedList = catOperation.getChildCategories(params[0].category_id);
            return params[0].callback;
        }

        @Override
        protected void onPostExecute(Callback callback) {
            super.onPostExecute(callback);
            callback.taskCompleted(populatedList);
        }
    }
}
