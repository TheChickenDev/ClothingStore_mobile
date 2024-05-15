package com.example.clothingstore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BaseActivity extends AppCompatActivity {
    public BottomNavigationView btmNavigationView;


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base);
        FrameLayout container = findViewById(R.id.frame_layout);
        getLayoutInflater().inflate(layoutResID, container, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Log.d("Activity", "BaseActivity: onCreate: started.");

    }

    protected void initializeBottomBar() {
        btmNavigationView = findViewById(R.id.bottomNavigationView1);
        btmNavigationView.setClickable(true);
        btmNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == btmNavigationView.getSelectedItemId()) {
                    return true;
                }
                Log.d("Navbar", "onNavigationItemSelected: ID = " + id);
                if (id == R.id.home) {
                    Intent intent = new Intent(BaseActivity.this, HomeActivity.class);
                    intent.putExtra("SELECTED_MENU_ITEM_ID", id);
                    startActivity(intent);
                } else if (id == R.id.window) {
                    // Handle profile click
                } else if (id == R.id.alert) {
                    Intent intent = new Intent(BaseActivity.this, CartActivity.class);
                    intent.putExtra("SELECTED_MENU_ITEM_ID", id);
                    startActivity(intent);
                } else if (id == R.id.person) {
                    // Handle help click
                }
                return true;
            }
        });
    }
}
