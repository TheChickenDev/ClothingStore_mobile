package com.example.clothingstore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import adapters.CartAdapter;
import apis.APIService;
import classes.PreferencesManager;
import classes.SpacesItemDecoration;
import interfaces.Callbacks;
import models.ClothCartModel;
import models.SuccessResponseModel;
import models.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RetrofitClient;

public class PaymentActivity extends AppCompatActivity {
    RecyclerView rv_payment;
    TextView tv_empty, tv_total_pay, tv_delivery_date, tv_delivery, tv_price, tv_name, tv_phone, tv_address;
    ImageView iv_avatar;
    Button btn_payment, btn_back;
    APIService apiService;
    List<ClothCartModel> clothesList;
    CartAdapter clothesAdapter;
    PreferencesManager preferencesManager;
    String DEVIVERY_SERVICE_FEE = "20000", PAYMEN_METHOD = "CASH";
    UserModel user;
    String price, totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);

        Mapping();
        GetUserCart();
    }

    private void Mapping() {
        preferencesManager = new PreferencesManager(this);
        btn_payment = findViewById(R.id.payment_btn_payment);
        btn_back = findViewById(R.id.payment_btn_back);
        rv_payment = findViewById(R.id.rv_payment);
        tv_empty = findViewById(R.id.payment_tv_empty);
        tv_total_pay = findViewById(R.id.payment_total_pay);
        tv_delivery = findViewById(R.id.payment_delivery_service);
        tv_price = findViewById(R.id.payment_price);
        tv_delivery_date = findViewById(R.id.payment_delta_delivery);
        iv_avatar = findViewById(R.id.payment_avatar);
        tv_name = findViewById(R.id.payment_name);
        tv_phone = findViewById(R.id.payment_phone);
        tv_address = findViewById(R.id.payment_address);

        rv_payment.setHasFixedSize(true);
        LinearLayoutManager layoutManagerNewArrivals = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rv_payment.setLayoutManager(layoutManagerNewArrivals);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_6dp);
        rv_payment.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        btn_back.setOnClickListener(v -> finish());
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_payment.setEnabled(false);
                Pay(new Callbacks() {
                    @Override
                    public void onFunctionCompleted() {
                        btn_payment.setEnabled(true);
                    }
                });
            }
        });
    }

    private String formatCurrency(String price) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY);
        double priceAsDouble = Double.parseDouble(price);
        return numberFormat.format(priceAsDouble) + "vnd";
    }

    private void GetUserCart() {
        String userId = preferencesManager.getId();
        apiService = RetrofitClient.getRetrofit(this).create(APIService.class);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Note: months are 0-based in Calendar
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = year + "-" + month + "-" + day;
        tv_delivery_date.setText(date);
        apiService.getUser(userId).enqueue(new Callback<SuccessResponseModel<UserModel>>() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<UserModel>> call, @NonNull Response<SuccessResponseModel<UserModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<UserModel> successResponse = response.body();
                    if (successResponse != null) {
                        if (successResponse.getData() != null) {
                            user = successResponse.getData();
                            clothesList = user.getCart();
                            if (clothesList.isEmpty()) {
                                btn_payment.setVisibility(View.GONE);
                                tv_empty.setVisibility(View.VISIBLE);
                                tv_total_pay.setText("0 vnđ");
                                tv_delivery.setText("0 vnđ");
                                tv_price.setText("0 vnđ");
                            } else {
                                clothesAdapter = new CartAdapter(PaymentActivity.this, clothesList);
                                rv_payment.setAdapter(clothesAdapter);
                                clothesAdapter.notifyDataSetChanged();
                                btn_payment.setVisibility(View.VISIBLE);
                                tv_empty.setVisibility(View.GONE);
                                tv_price.setText(formatCurrency(user.getTotalPay()));
                                tv_delivery.setText(formatCurrency(DEVIVERY_SERVICE_FEE));
                                double total = Double.parseDouble(user.getTotalPay()) + Double.parseDouble(DEVIVERY_SERVICE_FEE);
                                tv_total_pay.setText(formatCurrency(String.valueOf(total)));

                                price = user.getTotalPay();
                                totalAmount = String.valueOf(total);
                            }
                            Glide.with(PaymentActivity.this).load(user.getAvatar()).into(iv_avatar);
                            tv_name.setText(user.getName());
                            tv_phone.setText(user.getPhone());
                            tv_address.setText(user.getAddress());
                        }
                    }
                } else {
                    int statusCode = response.code();
                    Toast.makeText(PaymentActivity.this, "Error! Status code: " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<UserModel>> call, @NonNull Throwable t) {
                Toast.makeText(PaymentActivity.this, "Get payment failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Pay(Callbacks payCallback) {
        String userId = preferencesManager.getId();
        apiService = RetrofitClient.getRetrofit(this).create(APIService.class);

        Calendar calendar = Calendar.getInstance();
        int orderYear = calendar.get(Calendar.YEAR);
        int orderMonth = calendar.get(Calendar.MONTH) + 1; // Note: months are 0-based in Calendar
        int orderDay = calendar.get(Calendar.DAY_OF_MONTH);
        String orderDate = orderYear + "-" + orderMonth + "-" + orderDay;
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        int deliveryYear = calendar.get(Calendar.YEAR);
        int deliveryMonth = calendar.get(Calendar.MONTH) + 1; // Note: months are 0-based in Calendar
        int deliveryDay = calendar.get(Calendar.DAY_OF_MONTH);
        String deliveryDate = deliveryYear + "-" + deliveryMonth + "-" + deliveryDay;

        tv_delivery_date.setText(deliveryDate);
        apiService.payment(userId, orderDate, deliveryDate, price, DEVIVERY_SERVICE_FEE, totalAmount, "", PAYMEN_METHOD).enqueue(new Callback<SuccessResponseModel<UserModel>>() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<UserModel>> call, @NonNull Response<SuccessResponseModel<UserModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<UserModel> successResponse = response.body();
                    if (successResponse != null) {
                        Toast.makeText(PaymentActivity.this, successResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    int statusCode = response.code();
                    Toast.makeText(PaymentActivity.this, "Error! Status code: " + statusCode, Toast.LENGTH_SHORT).show();
                }
                payCallback.onFunctionCompleted();
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<UserModel>> call, @NonNull Throwable t) {
                Toast.makeText(PaymentActivity.this, "Get payment failed!", Toast.LENGTH_SHORT).show();
                payCallback.onFunctionCompleted();
            }
        });
    }
}