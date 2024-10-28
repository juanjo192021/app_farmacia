package com.app.farmacia_fameza.business;

import android.content.Context;

import com.app.farmacia_fameza.controller.cProduct;
import com.app.farmacia_fameza.dto.ProductAddDTO;
import com.app.farmacia_fameza.dto.ProductListDTO;
import com.app.farmacia_fameza.models.Product;

import java.util.List;

public class bProduct {
    private cProduct CProduct;

    public bProduct(Context context) {
        CProduct=new cProduct(context);
    }

    public List<ProductListDTO> getProducts(){
        return CProduct.getProducts();
    }

    public Product getDetailProduct(int idProduct){
        return CProduct.getDetailProduct(idProduct);
    }

    public boolean insertProduct(ProductAddDTO product){
        return CProduct.insertProduct(product);
    }

    public boolean updateProduct(ProductAddDTO product){
        return CProduct.updateProduct(product);
    }

    public Integer getIDProduct(String nameSKU){return CProduct.getIDProduct(nameSKU);}
    /*public boolean insertProduct(Product product){
        return CProduct.insertProduct(product);
    }

    public boolean updateProduct(Product product){
        return CProduct.updateProduct(product);
    }

    public boolean changeStockProduct(int productId, int quantity, boolean isIncrease){
        return CProduct.changeStock(productId,quantity,isIncrease);
    }

    public boolean deleteProduct(int idProduct){
        return CProduct.deleteProduct(idProduct);
    }*/
}
