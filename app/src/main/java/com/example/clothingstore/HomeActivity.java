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

import java.util.ArrayList;
import java.util.List;

import adapters.ClothesAdapter;
import apis.APIService;
import models.ClothesModel;
import models.SuccessResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RetrofitClient;

public class HomeActivity extends AppCompatActivity {

    ImageView imageProduct;
    TextView tvNameProduct, tvPrice;

    public static String removeHtmlTags(String htmlDescription) {
        // Sử dụng Html.fromHtml() với mode legacy để loại bỏ các thẻ HTML
        return Html.fromHtml(htmlDescription, Html.FROM_HTML_MODE_LEGACY).toString();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageProduct = findViewById(R.id.cate_image);
        tvNameProduct = findViewById(R.id.cate_name);
        tvPrice = findViewById(R.id.cate_price);

        String productId = "66116fe4f1bf7f8c4986bd5a";
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getClothesAll().enqueue(new Callback<SuccessResponseModel<ClothesModel>>() {
            @Override
            public void onResponse(Call<SuccessResponseModel<ClothesModel>> call, Response<SuccessResponseModel<ClothesModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<ClothesModel> successResponse = response.body();
                    ClothesModel product;
                    if (successResponse !=null){
                        product = successResponse.getData();
//                        tvSold.setText(product.getSold());
                        tvNameProduct.setText(product.getName());
                        tvPrice.setText(product.getPrice());
                        String des = product.getDesc();
                        String processDes = removeHtmlTags(des);
                        // Hien avatar ra imageView
                        Glide.with(HomeActivity.this).load(product.getImg()).into(imageProduct);

                    } else {
                        Toast.makeText(HomeActivity.this, "User not found!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(HomeActivity.this, "Failed to fetch user details!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SuccessResponseModel<ClothesModel>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Network error! Please try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}