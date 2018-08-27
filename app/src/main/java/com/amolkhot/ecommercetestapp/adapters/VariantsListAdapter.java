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
import com.amolkhot.ecommercetestapp.db.structure.Variants;
import com.amolkhot.ecommercetestapp.ui.MainActivity;
import com.amolkhot.ecommercetestapp.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amol.khot on 25-Aug-18.
 */

public class VariantsListAdapter extends BaseAdapter{

    final String TAG="VariantsListAdapter";
    Context context;
    List<Variants> variantsList= new ArrayList<>();

    public VariantsListAdapter(Context context,List<Variants> variants){
        this.context=context;
        variantsList.clear();
        variantsList.addAll(variants);
    }
    @Override
    public int getCount() {
        return variantsList.size();
    }

    @Override
    public Object getItem(int i) {
        return variantsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return variantsList.get(i).prodPrice;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = mInflater.inflate(R.layout.variants_listitem, null);
            String text="";
            if(variantsList.get(i).prodColor != "") text += "Color : " + variantsList.get(i).prodColor;
            if(variantsList.get(i).prodSize > 0) text += ((text!="")?" | Size : ":"Size : ")+variantsList.get(i).prodColor;
            ((TextView)view.findViewById(R.id.variant_title)).setText("Color :"+variantsList.get(i).prodColor + " | Size : " +variantsList.get(i).prodSize);
        }
        return view;
    }
}
