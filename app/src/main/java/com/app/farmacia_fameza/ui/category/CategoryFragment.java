package com.app.farmacia_fameza.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.app.farmacia_fameza.CategoryListFragment;
import com.app.farmacia_fameza.R;

import com.app.farmacia_fameza.databinding.FragmentCategoryBinding;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;
    public static final String ARG_CATEGORY_LIST = "list_category";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadCategoryListFragment();

        return root;
    }

    private void loadCategoryListFragment() {
        CategoryListFragment categoryListFragment = new CategoryListFragment();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.categoryFrameLayout, categoryListFragment);  // ID de FragmentContainerView
        transaction.commit();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}