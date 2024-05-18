package com.example.clothingstore;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.Objects;

public class FilterFragment extends Fragment {
    Spinner arrangementSpinner;
    RadioGroup typeGroup;
    RadioGroup priceGroup;
    RadioGroup ratingGroup;
    Button applyBtn;
    Button cancelBtn;
    String arrangement;
    String type;
    String price;
    String rating;
    int typeSelectedId;
    int priceSelectedId;
    int ratingSelectedId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        Mapping(view);
        return view;
    }

    private void Mapping(View view) {
        arrangement = "";
        type = "";
        price = "";
        rating = "";

        arrangementSpinner = view.findViewById(R.id.spinner_arrangement);
        typeGroup = view.findViewById(R.id.filter_radio_group_type);
        priceGroup = view.findViewById(R.id.filter_radio_group_price);
        ratingGroup = view.findViewById(R.id.filter_radio_group_rating);
        applyBtn = view.findViewById(R.id.apply_filter);
        cancelBtn = view.findViewById(R.id.cancel_filter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.arrangement_filter, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrangementSpinner.setAdapter(adapter);
        arrangementSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                if (Objects.equals(selectedItem, "All")) {
                    arrangement = "";
                } else {
                    arrangement = selectedItem;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where no item is selected if necessary
            }
        });

        applyBtn.setOnClickListener(v -> {
            typeSelectedId = typeGroup.getCheckedRadioButtonId();
            priceSelectedId = priceGroup.getCheckedRadioButtonId();
            ratingSelectedId = ratingGroup.getCheckedRadioButtonId();
            if (typeSelectedId != -1) {
                RadioButton typeRadioBtn = view.findViewById(typeSelectedId);
                type = typeRadioBtn.getText().toString();
            }
            if (priceSelectedId != -1) {
                RadioButton priceRadioBtn = view.findViewById(priceSelectedId);
                price = priceRadioBtn.getText().toString();
            }
            if (ratingSelectedId != -1) {
                RadioButton ratingRadioBtn = view.findViewById(ratingSelectedId);
                rating = ratingRadioBtn.getText().toString();
            }
            ProductsFragment productsFragment = new ProductsFragment();
            Bundle args = new Bundle();
            args.putString("arrangement", arrangement);
            args.putString("type", type);
            args.putString("price", price);
            args.putString("rating", rating);
            productsFragment.setArguments(args);
            Fragment parentFragment = getParentFragment();
            if (parentFragment != null) {
                FragmentTransaction transaction = parentFragment.getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.shop_frame_layout, productsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        cancelBtn.setOnClickListener(v -> {
            Fragment parentFragment = getParentFragment();
            if (parentFragment != null) {
                FragmentTransaction transaction = parentFragment.getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.shop_frame_layout, new ProductsFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}