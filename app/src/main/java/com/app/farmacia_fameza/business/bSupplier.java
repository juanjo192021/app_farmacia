package com.app.farmacia_fameza.business;

import android.content.Context;

import com.app.farmacia_fameza.controller.cSupplier;

import java.util.List;

public class bSupplier {

    private cSupplier CSupplier;

    public bSupplier(Context context){
        CSupplier = new cSupplier(context);
    }

    public List<String> getAllSupplierNames(){
        return CSupplier.getAllSupplierNames();
    }
}
