package com.amolkhot.ecommercetestapp.db.structure;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by amol.khot on 22-Aug-18.
 */
@Entity
public class CategoryProductLink {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "prod_id")
    public int prodId;
    @ColumnInfo(name = "cat_id")
    public int catId;

    public CategoryProductLink(int prodId,int catId){
        this.prodId=prodId;
        this.catId=catId;
    }

}
