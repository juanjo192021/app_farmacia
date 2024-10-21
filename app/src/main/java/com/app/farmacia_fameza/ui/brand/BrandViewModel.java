package com.app.farmacia_fameza.ui.brand;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BrandViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BrandViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}