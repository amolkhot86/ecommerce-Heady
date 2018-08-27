package com.amolkhot.ecommercetestapp.db.datacontroller;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.amolkhot.ecommercetestapp.db.structure.Product;
import com.amolkhot.ecommercetestapp.db.structure.Variants;

import java.util.List;

/**
 * Created by amol.khot on 22-Aug-18.
 */

@Dao
public interface VariantsController {
    @Insert
    void insert(Variants variants);

    @Update
    void update(Variants... variants);

    @Delete
    void delete(Variants... variants);

    @Query("SELECT * FROM Variants where prod_id=:prodId")
    List<Variants> getVariants(int prodId);

    @Query("SELECT * FROM Variants")
    List<Variants> getAllVariants();
}
