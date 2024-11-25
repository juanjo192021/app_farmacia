package com.app.farmacia_fameza.view.ui.brand;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.app.farmacia_fameza.R;
import com.app.farmacia_fameza.databinding.FragmentBrandBinding;


public class BrandFragment extends Fragment{



    private FragmentBrandBinding binding;

    public static final String ARG_BRAND_LIST = "list_brand";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        binding = FragmentBrandBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadCategoryListFragment();

        return root;
    }

    private void loadCategoryListFragment() {
        BrandListFragment brandListFragment = new BrandListFragment();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.brandFrameLayout, brandListFragment);  // ID de FragmentContainerView
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}