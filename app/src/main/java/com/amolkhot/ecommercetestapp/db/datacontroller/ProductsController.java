package com.amolkhot.ecommercetestapp.db.datacontroller;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.amolkhot.ecommercetestapp.db.structure.Product;

import java.util.List;

/**
 * Created by amol.khot on 22-Aug-18.
 */

@Dao
public interface ProductsController {
    @Insert
    void insert(Product product);

    @Update
    void update(Product... products);

    @Delete
    void delete(Product... products);

    @Query("SELECT * FROM Product where prod_id IN (Select DISTINCT prod_id from CategoryProductLink where cat_id=:catId group by prod_id) order by prod_id")
    List<Product> getProducts(int catId);

    @Query("SELECT * FROM Product")
    List<Product> getAllProducts();

    @Query("UPDATE Product SET view_count = :viewCount, order_count = :orderCount, share_count = :shareCount where prod_id = :prod_id")
    void updateRanking(int prod_id,int viewCount,int orderCount,int shareCount);
}
