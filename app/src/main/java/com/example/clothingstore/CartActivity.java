package com.example.clothingstore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import adapters.CartAdapter;
import apis.APIService;
import classes.PreferencesManager;
import classes.SpacesItemDecoration;
import models.UserModel;
import models.ClothCartModel;
import models.SuccessResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RetrofitClient;

public class CartActivity extends AppCompatActivity {
    RecyclerView rv_cart;
    TextView tv_empty;
    Button btn_payment, btn_back;
    APIService apiService;
    List<ClothCartModel> clothesList;
    CartAdapter clothesAdapter;
    PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        Mapping();
        GetUserCart();
    }

    private void Mapping() {
        preferencesManager = new PreferencesManager(this);
        btn_payment = findViewById(R.id.cart_btn_payment);
        btn_back = findViewById(R.id.cart_btn_back);
        rv_cart = findViewById(R.id.rv_cart);
        tv_empty = findViewById(R.id.cart_tv_empty);

        rv_cart.setHasFixedSize(true);
        LinearLayoutManager layoutManagerNewArrivals = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rv_cart.setLayoutManager(layoutManagerNewArrivals);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_6dp);
        rv_cart.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        btn_back.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        btn_payment.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
            startActivity(intent);
        });
    }

    private void GetUserCart() {
        String userId = preferencesManager.getId();
        apiService = RetrofitClient.getRetrofit(this).create(APIService.class);
        apiService.getUser(userId).enqueue(new Callback<SuccessResponseModel<UserModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<UserModel>> call, @NonNull Response<SuccessResponseModel<UserModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<UserModel> successResponse = response.body();
                    if (successResponse != null) {
                        if (successResponse.getData() != null) {
                            UserModel user = successResponse.getData();
                            clothesList = user.getCart();
                            if (clothesList.isEmpty()) {
                                btn_payment.setVisibility(View.GONE);
                                tv_empty.setVisibility(View.VISIBLE);
                            } else {
                                clothesAdapter = new CartAdapter(CartActivity.this, clothesList);
                                rv_cart.setAdapter(clothesAdapter);
                                clothesAdapter.notifyDataSetChanged();
                                btn_payment.setVisibility(View.VISIBLE);
                                tv_empty.setVisibility(View.GONE);
                            }
                        }
                    }
                } else {
                    int statusCode = response.code();
                    Toast.makeText(CartActivity.this, "Error! Status code: " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<UserModel>> call, @NonNull Throwable t) {
                Toast.makeText(CartActivity.this, "Get cart failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}