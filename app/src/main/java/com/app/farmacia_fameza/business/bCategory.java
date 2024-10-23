package com.app.farmacia_fameza.business;

import android.content.Context;

import com.app.farmacia_fameza.controller.cCategory;
import com.app.farmacia_fameza.dto.ItemListDTO;
import com.app.farmacia_fameza.dto.ProductListDTO;
import com.app.farmacia_fameza.models.Product;

import java.util.List;


public class bCategory {

    private cCategory CCategory;

    public bCategory(Context context) {
        CCategory=new cCategory(context);
    }

    public List<ItemListDTO> getCategories(){
        return CCategory.getCategories();
    }

    public List<ProductListDTO> getProductsByCategory(int idCategory){
        return CCategory.getProductsByCategory(idCategory);
    }

}