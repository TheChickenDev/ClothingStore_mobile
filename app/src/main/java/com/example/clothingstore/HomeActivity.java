package com.example.clothingstore;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import adapters.ClothesAdapter;
import apis.APIService;
import models.Clothes;
import models.SuccessResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RetrofitClient;

public class HomeActivity extends AppCompatActivity {
    RecyclerView rcClothes;
    ClothesAdapter clothesAdapter;
    APIService apiService;
    List<Clothes> clothesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AnhXa();
        GetCategory();
    }
    private void AnhXa() {
        rcClothes = (RecyclerView) findViewById(R.id.rvBestSeller);
    }
    private void GetCategory() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getProduct().enqueue(new Callback<List<Clothes>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<Clothes>> call, @NonNull Response<List<Clothes>> response) {
                if (response.isSuccessful()) {
                    clothesList = response.body();
                    clothesAdapter = new ClothesAdapter(HomeActivity.this, clothesList);
                    rcClothes.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.HORIZONTAL, false);
                    rcClothes.setLayoutManager(layoutManager);
                    rcClothes.setAdapter(clothesAdapter);
                    clothesAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    System.out.println("Mã lỗi: " + statusCode);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Clothes>> call, @NonNull Throwable t) {
                String message = t.getMessage() != null ? t.getMessage() : "Lỗi rồi";
                Log.d("log", message);
            }
        });
    }
}