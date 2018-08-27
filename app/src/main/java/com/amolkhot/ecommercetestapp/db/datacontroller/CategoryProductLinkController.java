package com.amolkhot.ecommercetestapp.db.datacontroller;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.amolkhot.ecommercetestapp.db.structure.Category;
import com.amolkhot.ecommercetestapp.db.structure.CategoryProductLink;

import java.util.List;

/**
 * Created by amol.khot on 23-Aug-18.
 */

@Dao
public interface CategoryProductLinkController {
    @Insert
    void insert(CategoryProductLink cat_prod_link);

    @Update
    void update(CategoryProductLink... cat_prod_link);

    @Delete
    void delete(CategoryProductLink... cat_prod_link);

    @Query("Select * from CategoryProductLink")
    List<CategoryProductLink> getAllLinks();
}

