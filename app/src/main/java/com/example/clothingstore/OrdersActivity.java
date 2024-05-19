package com.example.clothingstore;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import adapters.OrdersAdapter;
import apis.APIService;
import classes.PreferencesManager;
import classes.SpacesItemDecoration;
import models.OrderModel;
import models.SuccessResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RetrofitClient;

public class OrdersActivity extends AppCompatActivity {
    RecyclerView rv_orders;
    TextView tv_empty;
    MaterialButton btn_back;
    APIService apiService;
    PreferencesManager preferencesManager;
    OrdersAdapter ordersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_orders);
        preferencesManager = new PreferencesManager(this);

        Mapping();
        GetOrders();
    }

    private void Mapping() {
        rv_orders = findViewById(R.id.rv_orders);
        tv_empty = findViewById(R.id.order_tv_empty);
        btn_back = findViewById(R.id.order_btn_back);
        btn_back.setOnClickListener(v -> finish());

        rv_orders.setHasFixedSize(true);
        LinearLayoutManager layoutManagerNewArrivals = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rv_orders.setLayoutManager(layoutManagerNewArrivals);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_6dp);
        rv_orders.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
    }

    private void GetOrders() {
        String userId = preferencesManager.getId();
        apiService = RetrofitClient.getRetrofit(this).create(APIService.class);
        apiService.getOrders(userId).enqueue(new Callback<SuccessResponseModel<List<OrderModel>>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<List<OrderModel>>> call, @NonNull Response<SuccessResponseModel<List<OrderModel>>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<List<OrderModel>> successResponse = response.body();
                    if (successResponse != null) {
                        List<OrderModel> orders = successResponse.getData();
                        if (orders == null) {
                            tv_empty.setVisibility(View.VISIBLE);
                        } else {
                            ordersAdapter = new OrdersAdapter(OrdersActivity.this, orders);
                            rv_orders.setAdapter(ordersAdapter);
                            ordersAdapter.notifyDataSetChanged();
                            tv_empty.setVisibility(View.GONE);
                        }
                    }
                } else {
                    int statusCode = response.code();
                    Toast.makeText(OrdersActivity.this, "Error! Status code: " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<List<OrderModel>>> call, @NonNull Throwable t) {
                Toast.makeText(OrdersActivity.this, "Get order history failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}