package com.amolkhot.ecommercetestapp.db.structure;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by amol.khot on 22-Aug-18.
 */
@Entity
public class Variants {
    @PrimaryKey
    public int var_id;

    @ColumnInfo(name = "prod_id")
    public int prodId;

    @ColumnInfo(name = "prod_color")
    public String prodColor;

    @ColumnInfo(name = "prod_size")
    public int prodSize;

    @ColumnInfo(name = "prod_price")
    public int prodPrice;

    public Variants(int var_id,int prodId,String prodColor,int prodSize,int prodPrice){
        this.var_id=var_id;
        this.prodId=prodId;
        this.prodColor=prodColor;
        this.prodSize=prodSize;
        this.prodPrice=prodPrice;
    }
}
