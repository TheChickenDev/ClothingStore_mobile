package com.example.clothingstore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import adapters.CartAdapter;
import classes.SpacesItemDecoration;
import models.ClothCartModel;

public class OrderDetailActivity extends AppCompatActivity {
    TextView tv_id, tv_orderDate, tv_deliveryDate, tv_status, tv_price, tv_deliveryService, tv_total;
    MaterialButton btn_back;
    RecyclerView rv_order_products;
    CartAdapter clothesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_detail);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        List<ClothCartModel> products = (List<ClothCartModel>) intent.getSerializableExtra("products");
        String orderDate = intent.getStringExtra("orderDate");
        String deliveryDate = intent.getStringExtra("deliveryDate");
        boolean status = intent.getBooleanExtra("status", false);
        String price = intent.getStringExtra("price");
        String shippingFee = intent.getStringExtra("shippingFee");
        String total = intent.getStringExtra("total");

        Mapping();
        try {
            PutData(id, products, orderDate, deliveryDate, status, price, shippingFee, total);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private String formatCurrency(String price) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY);
        double priceAsDouble = Double.parseDouble(price);
        return numberFormat.format(priceAsDouble) + "vnd";
    }

    private String formatISODate(String isoDate) throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = sdf.parse(isoDate);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        return outputFormat.format(date);
    }

    private void Mapping() {
        tv_id = findViewById(R.id.order_detail_id);
        tv_orderDate = findViewById(R.id.order_detail_order_date);
        tv_deliveryDate = findViewById(R.id.order_detail_delta_delivery);
        tv_status = findViewById(R.id.order_detail_status);
        tv_price = findViewById(R.id.order_detail_price);
        tv_deliveryService = findViewById(R.id.order_detail_delivery_service);
        tv_total = findViewById(R.id.order_detail_total_pay);
        rv_order_products = findViewById(R.id.rv_order_detail);
        btn_back = findViewById(R.id.order_detail_btn_back);
        btn_back.setOnClickListener(v -> finish());

        rv_order_products.setHasFixedSize(true);
        LinearLayoutManager layoutManagerNewArrivals = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rv_order_products.setLayoutManager(layoutManagerNewArrivals);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_6dp);
        rv_order_products.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void PutData(String id, List<ClothCartModel> products, String orderDate, String deliveryDate, boolean status, String price, String deliveryService, String total) throws ParseException {
        tv_id.setText(id);
        tv_orderDate.setText(formatISODate(orderDate));
        tv_deliveryDate.setText(formatISODate(deliveryDate));
        tv_status.setText(status ? "Completed" : "Uncompleted");
        tv_price.setText(formatCurrency(price));
        tv_deliveryService.setText(formatCurrency(deliveryService));
        tv_total.setText(formatCurrency(total));

        clothesAdapter = new CartAdapter(OrderDetailActivity.this, products);
        rv_order_products.setAdapter(clothesAdapter);
        clothesAdapter.notifyDataSetChanged();
    }
}