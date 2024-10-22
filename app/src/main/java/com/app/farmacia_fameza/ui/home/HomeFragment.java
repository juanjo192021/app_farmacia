package com.app.farmacia_fameza.ui.home;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.app.farmacia_fameza.R;
import com.app.farmacia_fameza.databinding.FragmentHomeBinding;

import com.app.farmacia_fameza.view.ProductListFragment;

public class HomeFragment extends Fragment{

    private FragmentHomeBinding binding;
    public static final String ARG_PRODUCT_LIST = "list_product";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadProductListFragment();

        return root;
    }

    private void loadProductListFragment() {
        ProductListFragment productListFragment = new ProductListFragment();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.productFrameLayout, productListFragment);  // ID de FragmentContainerView
        transaction.commit();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}