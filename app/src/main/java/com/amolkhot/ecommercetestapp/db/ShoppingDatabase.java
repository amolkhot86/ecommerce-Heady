package com.amolkhot.ecommercetestapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.amolkhot.ecommercetestapp.db.datacontroller.CategoryController;
import com.amolkhot.ecommercetestapp.db.datacontroller.CategoryProductLinkController;
import com.amolkhot.ecommercetestapp.db.datacontroller.ProductsController;
import com.amolkhot.ecommercetestapp.db.datacontroller.VariantsController;
import com.amolkhot.ecommercetestapp.db.structure.Category;
import com.amolkhot.ecommercetestapp.db.structure.CategoryProductLink;
import com.amolkhot.ecommercetestapp.db.structure.Product;
import com.amolkhot.ecommercetestapp.db.structure.Variants;

/**
 * Created by amol.khot on 22-Aug-18.
 */
@Database(entities = { Product.class, Category.class,CategoryProductLink.class,Variants.class },
        version = 1,exportSchema = false)
    public abstract class ShoppingDatabase extends RoomDatabase{

    public abstract CategoryController getCategoryDataOperationsInstance();
    public abstract ProductsController getProductsDataOperationsInstance();
    public abstract VariantsController getVariantsDataOperationsInstance();
    public abstract CategoryProductLinkController getCategoryProductLinkController();
}
