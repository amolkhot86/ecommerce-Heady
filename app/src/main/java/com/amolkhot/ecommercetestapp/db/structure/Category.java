package com.amolkhot.ecommercetestapp.db.structure;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by amol.khot on 22-Aug-18.
 */
@Entity
public class Category {
    @PrimaryKey
    public int cat_id;

    @ColumnInfo(name = "cat_name")
    public String catName;

    @ColumnInfo(name = "parent_cat_id")
    public int parentCatId;

    @ColumnInfo(name = "has_child_cat")
    public int hasChildCat;

    public Category(int cat_id,String catName,int parentCatId,int hasChildCat){
        this.cat_id=cat_id;
        this.catName=catName;
        this.parentCatId=parentCatId;
        this.hasChildCat=hasChildCat;
    }
}
