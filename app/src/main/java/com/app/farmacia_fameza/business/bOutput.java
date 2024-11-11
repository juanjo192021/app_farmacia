package com.app.farmacia_fameza.business;
import android.content.Context;

import com.app.farmacia_fameza.controller.cOutput;
import com.app.farmacia_fameza.controller.cProduct;
import com.app.farmacia_fameza.dto.ProductOutputDetailDTO;

import java.util.List;

public class bOutput {

    private cOutput COputput;

    public bOutput(Context context) {
        COputput=new cOutput(context);
    }

    public boolean insertProductOutputWithDetails(String numberOuput, String dateOutput, String userName, List<ProductOutputDetailDTO> productDetails) {
        return COputput.insertProductOutputWithDetails(numberOuput, dateOutput, userName, productDetails);
    }
}
