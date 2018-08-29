package com.amolkhot.ecommercetestapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.amolkhot.ecommercetestapp.MyApplication;
import com.amolkhot.ecommercetestapp.R;
import com.amolkhot.ecommercetestapp.db.datacontroller.CategoryController;
import com.amolkhot.ecommercetestapp.db.datacontroller.VariantsController;
import com.amolkhot.ecommercetestapp.db.structure.Category;
import com.amolkhot.ecommercetestapp.db.structure.Product;
import com.amolkhot.ecommercetestapp.db.structure.Variants;
import com.amolkhot.ecommercetestapp.util.Callback;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by amol.khot on 25-Aug-18.
 */

public class ProductListAdapter extends BaseAdapter{

    final String TAG="ProductListAdapter";
    Context context;
    List<Product> productList= new ArrayList<>();

    public ProductListAdapter(Context context, List<Product> products){
        this.context=context;
        productList=products;
    }
    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return productList.get(i).prod_id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = mInflater.inflate(R.layout.product_listitem, null);
        ((TextView)view.findViewById(R.id.product_title)).setText(productList.get(i).productName);
        SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000Z'");
        SimpleDateFormat formatOut = new SimpleDateFormat("dd MMM yyyy");
        try{
            Date date = formatIn.parse(productList.get(i).dateAdded);
            ((TextView)view.findViewById(R.id.added_on_text)).setText(formatOut.format(date));
        }catch (ParseException pe){
            Log.e(TAG,"ParseException : " + pe.getMessage());
            ((TextView)view.findViewById(R.id.added_on_text)).setText("-");
        }
        ((TextView)view.findViewById(R.id.view_count_text)).setText(""+productList.get(i).viewCount);
        view.findViewById(R.id.view_count_cont).setVisibility((productList.get(i).viewCount>0)?View.VISIBLE:View.INVISIBLE);
        ((TextView)view.findViewById(R.id.ordered_count_text)).setText(""+productList.get(i).orderCount);
        view.findViewById(R.id.order_count_cont).setVisibility((productList.get(i).orderCount>0)?View.VISIBLE:View.INVISIBLE);
        ((TextView)view.findViewById(R.id.shared_count_text)).setText(""+productList.get(i).shareCount);
        view.findViewById(R.id.share_count_cont).setVisibility((productList.get(i).shareCount>0)?View.VISIBLE:View.INVISIBLE);

        ((TextView)view.findViewById(R.id.final_price_label)).setText("With " + productList.get(i).taxName + "("+ productList.get(i).taxValue +"%)");

        final View tmpView = view;
        final int thisIndex=i;
        ((GridView)view.findViewById(R.id.variant_grid)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int gridIndex, long l) {
                Log.i(TAG,"_____________CLICK VARIANT");
                view.findViewById(R.id.variant_item_cont).setSelected(true);
                ((TextView)tmpView.findViewById(R.id.price_text)).setText("\u20B9 "+l);
                ((TextView)tmpView.findViewById(R.id.final_price_text)).setText("\u20B9 "+ (l+(l*(productList.get(thisIndex).taxValue/100))));
            }
        });
        new LoadProductVariants().execute(new LoadVariantsParams(productList.get(i).prod_id, new Callback() {
            @Override
            public void taskCompleted(Object param) {
                ((GridView)tmpView.findViewById(R.id.variant_grid)).setAdapter(new VariantsListAdapter(context,(List<Variants>)param));
                ViewGroup.LayoutParams lp = ((GridView)tmpView.findViewById(R.id.variant_grid)).getLayoutParams();
                lp.height=((lp.height+5)*((List<Variants>) param).size()/2);
                ((GridView)tmpView.findViewById(R.id.variant_grid)).setLayoutParams(lp);
                tmpView.invalidate();
            }

            @Override
            public void taskStarted() {

            }
        }));
        return view;
    }
    private class LoadVariantsParams{
        int prod_id;
        Callback callback;
        public LoadVariantsParams(int prod_id,Callback callback){
            this.prod_id=prod_id;
            this.callback=callback;
        }
    }
    private class LoadProductVariants extends AsyncTask<LoadVariantsParams,String,Callback>{

        List<Variants> thisList;
        @Override
        protected Callback doInBackground(LoadVariantsParams... params) {
            final VariantsController variantsOperation= MyApplication.getAppInstance().getShoppingDatabase().getVariantsDataOperationsInstance();
            thisList = new ArrayList<>();
            thisList.addAll(variantsOperation.getVariants(params[0].prod_id));
            return params[0].callback;
        }

        @Override
        protected void onPostExecute(Callback callback) {
            super.onPostExecute(callback);
            callback.taskCompleted(thisList);
        }
    }
}
