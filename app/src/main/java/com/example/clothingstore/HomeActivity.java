package com.example.clothingstore;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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

    RecyclerView rcItem;
    ImageView imgItem;
    TextView txtItemName, txtItemPrice;
    ClothesAdapter clothesAdapter;
    APIService apiService;
    List<ClothesModel> clothesList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rcItem = findViewById(R.id.rvBestSeller);
        imgItem = findViewById(R.id.cate_image);
        txtItemName = findViewById(R.id.cate_name);
        txtItemPrice = findViewById(R.id.cate_price);

        Log.d("Activity", "HomeActivity: onCreate: started.");
        mappingView();
        clothesList = new ArrayList<>();

        rcItem.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rcItem.setLayoutManager(layoutManager);
//        int spaceInPixels = getResources().getDimensionPixelSize(R.dimen.margin_15);
//        rcItem.addItemDecoration(new SpacesItemDecoration(spaceInPixels));
        clothesAdapter = new ClothesAdapter(HomeActivity.this, clothesList);
        rcItem.setAdapter(clothesAdapter);
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
                    rcItem.setAdapter(clothesAdapter);
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
        rcItem = findViewById(R.id.rvBestSeller);
    }
}