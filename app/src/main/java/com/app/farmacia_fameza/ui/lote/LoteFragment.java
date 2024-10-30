package com.app.farmacia_fameza.ui.lote;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.farmacia_fameza.R;
import com.app.farmacia_fameza.databinding.FragmentLoteBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class LoteFragment extends Fragment {


    private FragmentLoteBinding binding;
    Button btnAddProduct;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnAddProduct = root.findViewById(R.id.btn_AgregarLote);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getActivity(), R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getActivity().getApplicationContext())
                        .inflate(
                                R.layout.modal_detailproduct,
                                (LinearLayout) getView().findViewById(R.id.bottomDetailProductModal)
                        );


                bottomSheetView.findViewById(R.id.btn_AgregarProdLote).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}