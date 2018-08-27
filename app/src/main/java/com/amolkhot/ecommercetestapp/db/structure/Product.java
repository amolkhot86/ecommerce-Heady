package com.amolkhot.ecommercetestapp.db.structure;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by amol.khot on 22-Aug-18.
 */
@Entity
public class Product {
    @PrimaryKey
    @ColumnInfo(name = "prod_id")
    public int prod_id;

    @ColumnInfo(name = "prod_name")
    public String productName;

    @ColumnInfo(name = "date_added")
    public String dateAdded;

    @ColumnInfo(name = "tax_name")
    public String taxName;

    @ColumnInfo(name = "tax_value")
    public double taxValue;

    @ColumnInfo(name = "view_count")
    public int viewCount;

    @ColumnInfo(name = "order_count")
    public int orderCount;

    @ColumnInfo(name = "share_count")
    public int shareCount;

    public Product(int prod_id,String productName,String dateAdded,String taxName,double taxValue,int viewCount,int orderCount,int shareCount){
        this.prod_id=prod_id;
        this.productName=productName;
        this.dateAdded=dateAdded;
        this.taxName=taxName;
        this.taxValue=taxValue;
        this.viewCount=viewCount;
        this.orderCount=orderCount;
        this.shareCount=shareCount;
    }
}
