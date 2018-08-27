package com.amolkhot.ecommercetestapp.db.datacontroller;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.amolkhot.ecommercetestapp.Constants;
import com.amolkhot.ecommercetestapp.db.structure.Category;

import java.util.List;

/**
 * Created by amol.khot on 23-Aug-18.
 */

@Dao
public interface CategoryController {
    @Insert
    void insert(Category category);

    @Update
    void update(Category... category);

    @Query("UPDATE Category set parent_cat_id=:parent_cid where cat_id=:cat_id")
    void updateParentCategory(int cat_id,int parent_cid);

    @Delete
    void delete(Category... category);

    @Query("SELECT * FROM Category where parent_cat_id =:parentCategoryId")
    List<Category> getChildCategories(int parentCategoryId);

    @Query("SELECT * FROM Category where parent_cat_id IN("+ Constants.ROOT_ID+","+Constants.BY_CATEGORY_ID+","+Constants.BY_RANKING_ID+")")
    List<Category> getRootCategories();

    @Query("SELECT * FROM Category")
    List<Category> getAllCategories();

    @Query("UPDATE Category set has_child_cat=1 where cat_id in (select parent_cat_id from Category where parent_cat_id <> 0)")
    void updateHasParentCat();
}

