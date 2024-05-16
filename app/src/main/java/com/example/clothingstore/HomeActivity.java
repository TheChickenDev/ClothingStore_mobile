package com.example.clothingstore;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import adapters.ClothesAdapter;
import apis.APIService;
import models.ClothModel;
import models.GetProductResponseModel;
import models.SuccessResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RetrofitClient;

public class HomeActivity extends AppCompatActivity {
    RecyclerView rcClothesBestSeller, rcClothesNewArrivals;
    ClothesAdapter clothesAdapter;
    APIService apiService;
    List<ClothModel> clothesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AnhXaBestSeller();
        GetClothesBestSeller();

        AnhXaNewArrivals();
        GetClothesNewArrivals();
    }
    private void AnhXaBestSeller() {
        rcClothesBestSeller = (RecyclerView) findViewById(R.id.rvBestSeller);
    }
    private void GetClothesBestSeller() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getProduct().enqueue(new Callback<SuccessResponseModel<GetProductResponseModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Response<SuccessResponseModel<GetProductResponseModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<GetProductResponseModel> successResponse = response.body();
                    clothesList = successResponse.getData().getProducts();
                    clothesAdapter = new ClothesAdapter(HomeActivity.this, clothesList);
                    rcClothesBestSeller.setHasFixedSize(true);
                    LinearLayoutManager layoutManagerBestSeller = new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.HORIZONTAL, false);
                    rcClothesBestSeller.setLayoutManager(layoutManagerBestSeller);
                    rcClothesBestSeller.setAdapter(clothesAdapter);
                    clothesAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    Toast.makeText(HomeActivity.this, "Lỗi rồi kìa! Mã lỗi: " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Throwable t) {
                String message = t.getMessage() != null ? t.getMessage() : "Lỗi rồi";
                Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnhXaNewArrivals() {
        rcClothesNewArrivals = (RecyclerView) findViewById(R.id.rvNewArrivals);
    }
    private void GetClothesNewArrivals() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getProduct().enqueue(new Callback<SuccessResponseModel<GetProductResponseModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Response<SuccessResponseModel<GetProductResponseModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<GetProductResponseModel> successResponse = response.body();
                    clothesList = successResponse.getData().getProducts();
                    clothesAdapter = new ClothesAdapter(HomeActivity.this, clothesList);
                    rcClothesNewArrivals.setHasFixedSize(true);
                    LinearLayoutManager layoutManagerNewArrivals = new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.HORIZONTAL, false);
                    rcClothesNewArrivals.setLayoutManager(layoutManagerNewArrivals);
                    rcClothesNewArrivals.setAdapter(clothesAdapter);
                    clothesAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    Toast.makeText(HomeActivity.this, "Lỗi rồi kìa! Mã lỗi: " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Throwable t) {
                String message = t.getMessage() != null ? t.getMessage() : "Lỗi rồi";
                Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}