package com.app.farmacia_fameza.business;

import android.content.Context;

import com.app.farmacia_fameza.controller.cProduct;
import com.app.farmacia_fameza.dto.ProductAddDTO;
import com.app.farmacia_fameza.dto.ProductInventoryDTO;
import com.app.farmacia_fameza.dto.ProductKardexDTO;
import com.app.farmacia_fameza.dto.ProductListDTO;
import com.app.farmacia_fameza.dto.ProductUpdateDTO;
import com.app.farmacia_fameza.models.Product;

import java.math.BigDecimal;
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

    public boolean updateProduct(ProductUpdateDTO product){return CProduct.updateProduct(product);}

    public boolean existsProductById(Integer id){return CProduct.existsProductById(id);}

    //public String searchSKU(Integer id){return  CProduct.searchSKU(id);}

    public String searchProductName(String sku){return CProduct.searchProductName(sku);}

    public Integer searchIdProduct(String sku){return CProduct.searchIdProduct(sku);}

    public Double searchPriceProduct(Integer productId){return CProduct.searchPriceProduct(productId);}

    public boolean validateRegisterKardex(Integer idProduct){return CProduct.validateRegisterKardex(idProduct);}

    /*public List<ProductKardexDTO> getProductData(Integer id){
        return CProduct.getProductData(id);
    }*/

    /*public List<ProductInventoryDTO> completeTableKardex() {
        return CProduct.completeTableKardex();
    }*/

    public List<ProductInventoryDTO> completeTableKardexFilterMonthSKU(String mes,Integer idProduct){
        return  CProduct.completeTableKardexFilterMonthSKU(mes,idProduct);
    }
}
