package com.app.farmacia_fameza.business;

import android.content.Context;
import android.widget.Spinner;

import com.app.farmacia_fameza.dto.ItemListDTO;

import com.app.farmacia_fameza.controller.cBrand;
import com.app.farmacia_fameza.dto.ProductListDTO;
import com.app.farmacia_fameza.models.Product;


import java.util.List;

public class bBrand {

    private cBrand CBrand;

    public bBrand(Context context) {
        CBrand=new cBrand(context);
    }

    public List<ItemListDTO> getBrands(){
        return CBrand.getBrands();
    }

    public List<ProductListDTO> getProductsByBrand(int idBrand){
        return CBrand.getProductsByBrand(idBrand);
    }

    public List<String> getBrandsName(){
        return CBrand.getBrandsName();
    }
}
