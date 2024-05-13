package com.example.clothingstore;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.List;

import adapters.ClothesAdapter;
import apis.APIService;
import models.ClothesModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RetrofitClient;

public class HomeActivity extends AppCompatActivity {

    RecyclerView rcClothes;
    ClothesAdapter clothesAdapter;
    APIService apiService;
    List<ClothesModel> clothesModelList;
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
        apiService.getCategoryAll().enqueue(new Callback<List<ClothesModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<ClothesModel>> call, @NonNull Response<List<ClothesModel>> response) {
                if (response.isSuccessful()) {
                    clothesModelList = response.body();
                    clothesAdapter = new ClothesAdapter( HomeActivity.this, clothesModelList);
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
            public void onFailure(@NonNull Call<List<ClothesModel>> call, @NonNull Throwable t) {
                String message = t.getMessage() != null ? t.getMessage() : "Lỗi rồi";
                Log.d("log", message);
            }
        });
    }
}