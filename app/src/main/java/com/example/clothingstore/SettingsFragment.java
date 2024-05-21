package com.example.clothingstore;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import classes.PreferencesManager;

public class SettingsFragment extends Fragment {
    Button btn_profile, btn_orders, btn_logout;
    PreferencesManager preferencesManager;
    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        preferencesManager = new PreferencesManager(view.getContext());
        Mapping(view);

        return view;
    }

    private void Mapping(View view) {
        btn_profile = view.findViewById(R.id.setting_btn_profile);
        btn_orders = view.findViewById(R.id.setting_btn_orders);
        btn_logout = view.findViewById(R.id.setting_btn_logout);

        btn_profile.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(), ProfileActivity.class);
            startActivity(intent);
        });

        btn_orders.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(), OrdersActivity.class);
            startActivity(intent);
        });

        btn_logout.setOnClickListener(v -> {
            preferencesManager.removeLoginDetails();
            Intent intent = new Intent(view.getContext(), LoginActivity.class);
            startActivity(intent);
            Toast.makeText(view.getContext(), "Log out successful!", Toast.LENGTH_SHORT).show();
        });
    }
}