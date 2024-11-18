package com.app.farmacia_fameza;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KardexFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KardexFragment extends Fragment {


    // TODO: Rename and change types and number of parameters
    public static KardexFragment newInstance(String param1, String param2) {
        KardexFragment fragment = new KardexFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kardex, container, false);
        return view;
    }
}