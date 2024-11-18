package com.app.farmacia_fameza.business;

import android.content.Context;

import com.app.farmacia_fameza.dto.ItemListDTO;

import com.app.farmacia_fameza.controller.cBrand;
import com.app.farmacia_fameza.dto.ProductListDTO;


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

    public Integer getIDBrand(String nameBrand){return CBrand.getIDBrand(nameBrand);}

    public boolean addBrand(String name, int status){
        return CBrand.addBrand(name, status);
    }

    public boolean editBrand(int id, String name, int status){
        return CBrand.editBrand(id, name, status);
    }
}
