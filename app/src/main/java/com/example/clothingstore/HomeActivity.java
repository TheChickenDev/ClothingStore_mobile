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
    RecyclerView rcClothesMale, rcClothesFemale, rcClothesUnisex, rcClothesJacket, rcClothesAccessory;
    ClothesAdapter clothesAdapter;
    APIService apiService;
    List<ClothModel> clothesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AnhXaMale();
        GetClothesMale();

        AnhXaFemale();
        GetClothesFemale();

        AnhXaUnisex();
        GetClothesUnisex();

        AnhXaJacket();
        GetClothesJacket();

        AnhXaAccessory();
        GetClothesAccessory();
    }
    private void AnhXaMale() {
        rcClothesMale = (RecyclerView) findViewById(R.id.rvMale);
    }
    private void GetClothesMale() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getProductMale().enqueue(new Callback<SuccessResponseModel<GetProductResponseModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Response<SuccessResponseModel<GetProductResponseModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<GetProductResponseModel> successResponse = response.body();
                    clothesList = successResponse.getData().getProducts();
                    clothesAdapter = new ClothesAdapter(HomeActivity.this, clothesList);
                    rcClothesMale.setHasFixedSize(true);
                    LinearLayoutManager layoutManagerBestSeller = new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.HORIZONTAL, false);
                    rcClothesMale.setLayoutManager(layoutManagerBestSeller);
                    rcClothesMale.setAdapter(clothesAdapter);
                    clothesAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    System.out.println("Mã lỗi: " + statusCode);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Throwable t) {
                String message = t.getMessage() != null ? t.getMessage() : "Lỗi rồi";
                Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnhXaFemale() {
        rcClothesFemale = (RecyclerView) findViewById(R.id.rvFemale);
    }
    private void GetClothesFemale() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getProductFemale().enqueue(new Callback<SuccessResponseModel<GetProductResponseModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Response<SuccessResponseModel<GetProductResponseModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<GetProductResponseModel> successResponse = response.body();
                    clothesList = successResponse.getData().getProducts();
                    clothesAdapter = new ClothesAdapter(HomeActivity.this, clothesList);
                    rcClothesFemale.setHasFixedSize(true);
                    LinearLayoutManager layoutManagerNewArrivals = new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.HORIZONTAL, false);
                    rcClothesFemale.setLayoutManager(layoutManagerNewArrivals);
                    rcClothesFemale.setAdapter(clothesAdapter);
                    clothesAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    System.out.println("Mã lỗi: " + statusCode);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Throwable t) {
                String message = t.getMessage() != null ? t.getMessage() : "Lỗi rồi";
                Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnhXaUnisex() {
        rcClothesUnisex = (RecyclerView) findViewById(R.id.rvUnisex);
    }
    private void GetClothesUnisex() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getProductUnisex().enqueue(new Callback<SuccessResponseModel<GetProductResponseModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Response<SuccessResponseModel<GetProductResponseModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<GetProductResponseModel> successResponse = response.body();
                    clothesList = successResponse.getData().getProducts();
                    clothesAdapter = new ClothesAdapter(HomeActivity.this, clothesList);
                    rcClothesUnisex.setHasFixedSize(true);
                    LinearLayoutManager layoutManagerNewArrivals = new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.HORIZONTAL, false);
                    rcClothesUnisex.setLayoutManager(layoutManagerNewArrivals);
                    rcClothesUnisex.setAdapter(clothesAdapter);
                    clothesAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    System.out.println("Mã lỗi: " + statusCode);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Throwable t) {
                String message = t.getMessage() != null ? t.getMessage() : "Lỗi rồi";
                Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnhXaJacket() {
        rcClothesJacket= (RecyclerView) findViewById(R.id.rvJacket);
    }
    private void GetClothesJacket() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getProductJacket().enqueue(new Callback<SuccessResponseModel<GetProductResponseModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Response<SuccessResponseModel<GetProductResponseModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<GetProductResponseModel> successResponse = response.body();
                    clothesList = successResponse.getData().getProducts();
                    clothesAdapter = new ClothesAdapter(HomeActivity.this, clothesList);
                    rcClothesJacket.setHasFixedSize(true);
                    LinearLayoutManager layoutManagerNewArrivals = new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.HORIZONTAL, false);
                    rcClothesJacket.setLayoutManager(layoutManagerNewArrivals);
                    rcClothesJacket.setAdapter(clothesAdapter);
                    clothesAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    System.out.println("Mã lỗi: " + statusCode);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Throwable t) {
                String message = t.getMessage() != null ? t.getMessage() : "Lỗi rồi";
                Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnhXaAccessory() {
        rcClothesAccessory= (RecyclerView) findViewById(R.id.rvAccessory);
    }
    private void GetClothesAccessory() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getProductAccessory().enqueue(new Callback<SuccessResponseModel<GetProductResponseModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Response<SuccessResponseModel<GetProductResponseModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<GetProductResponseModel> successResponse = response.body();
                    clothesList = successResponse.getData().getProducts();
                    clothesAdapter = new ClothesAdapter(HomeActivity.this, clothesList);
                    rcClothesAccessory.setHasFixedSize(true);
                    LinearLayoutManager layoutManagerNewArrivals = new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.HORIZONTAL, false);
                    rcClothesAccessory.setLayoutManager(layoutManagerNewArrivals);
                    rcClothesAccessory.setAdapter(clothesAdapter);
                    clothesAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    System.out.println("Mã lỗi: " + statusCode);
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