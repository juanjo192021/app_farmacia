package com.app.farmacia_fameza.business;
import android.content.Context;

import com.app.farmacia_fameza.controller.cLoteEntry;
import com.app.farmacia_fameza.dto.ProductEntryDetailDTO;

import java.util.List;

public class bLoteEntry {
    private cLoteEntry CLoteEntryl;

    public bLoteEntry(Context context)  {
        CLoteEntryl = new cLoteEntry(context);
    }

    public boolean insertProductEntryWithDetails(String numberEntry, String dateEntry, String supplierName, List<ProductEntryDetailDTO> productDetails){
        return CLoteEntryl.insertProductEntryWithDetails(numberEntry, dateEntry, supplierName, productDetails);
    }
}
