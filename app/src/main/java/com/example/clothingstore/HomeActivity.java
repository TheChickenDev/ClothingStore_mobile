package com.example.clothingstore;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.List;

import adapters.ClothesAdapter;
import apis.APIService;
import models.ClothesModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RetrofitClient;

public class HomeActivity extends AppCompatActivity {

    RecyclerView rcCate;
    ClothesAdapter clothesAdapter;
    APIService apiService;
    List<ClothesModel> clothesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        initializeBottomBar();

//        int selectedMenuItemId = getIntent().getIntExtra("SELECTED_MENU_ITEM_ID", R.id.home);
//        bottomNavigationView.getMenu().findItem(selectedMenuItemId).setChecked(true);

        Log.d("Activity", "MainActivity: onCreate: started.");
        mappingView();
        clothesList = new ArrayList<>();

        rcCate.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rcCate.setLayoutManager(layoutManager);
//        int spaceInPixels = getResources().getDimensionPixelSize(R.dimen.margin_15);
//        rcCate.addItemDecoration(new SpacesItemDecoration(spaceInPixels));
        clothesAdapter = new ClothesAdapter(HomeActivity.this, clothesList);
        rcCate.setAdapter(clothesAdapter);
        GetCategory();
    }

    private void GetCategory() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getClothesAll().enqueue(new Callback<List<ClothesModel>>() {
            @Override
            public void onResponse(Call<List<ClothesModel>> call, Response<List<ClothesModel>> response) {
                if (response.isSuccessful()) {
                    clothesList = response.body();
                    clothesAdapter = new ClothesAdapter(HomeActivity.this, clothesList);
                    rcCate.setAdapter(clothesAdapter);
                    clothesAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                }
            }
            @Override
            public void onFailure(Call<List<ClothesModel>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    private void mappingView() {
        rcCate = findViewById(R.id.rvBestSeller);
    }
}