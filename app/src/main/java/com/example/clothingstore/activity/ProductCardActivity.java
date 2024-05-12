package com.example.clothingstore.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.clothingstore.R;
import com.example.clothingstore.model.Product;
import com.example.clothingstore.model.SuccessResponse;
import com.example.clothingstore.model.Users;
import com.example.clothingstore.retrofit.ApiRetrofit;
import com.example.clothingstore.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCardActivity extends AppCompatActivity {

    ImageView imageProduct;
    TextView tvSold , tvNameProduct, tvPrice, tvDescription;

    public static String removeHtmlTags(String htmlDescription) {
        // Sử dụng Html.fromHtml() với mode legacy để loại bỏ các thẻ HTML
        return Html.fromHtml(htmlDescription, Html.FROM_HTML_MODE_LEGACY).toString();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_card);

        imageProduct = findViewById(R.id.iv_product);
        tvSold = findViewById(R.id.tv_sold);
        tvNameProduct = findViewById(R.id.tv_nameProduct);
        tvPrice = findViewById(R.id.tv_price);
        tvDescription = findViewById(R.id.tv_description);

        String productId = "66116fe4f1bf7f8c4986bd5a";
        ApiService apiService = ApiRetrofit.getRetrofitClient().create(ApiService.class);
        apiService.getProduct(productId).enqueue(new Callback<SuccessResponse<Product>>() {
            @Override
            public void onResponse(@NonNull Call<SuccessResponse<Product>> call, @NonNull Response<SuccessResponse<Product>> response) {
                if (response.isSuccessful()) {
                    SuccessResponse<Product> successResponse = response.body();
                    Product product;
                    if (successResponse !=null){
                        product = successResponse.getData();
                        tvSold.setText(product.getSold());
                        tvNameProduct.setText(product.getName());
                        tvPrice.setText(product.getPrice());
                        String des = product.getDesc();
                        String processDes = removeHtmlTags(des);
                        tvDescription.setText(processDes);
                        // Hien avatar ra imageView
                        Glide.with(ProductCardActivity.this).load(product.getImg()).into(imageProduct);

                    } else {
                        Toast.makeText(ProductCardActivity.this, "User not found!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(ProductCardActivity.this, "Failed to fetch user details!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponse<Product>> call, @NonNull Throwable t) {
                Toast.makeText(ProductCardActivity.this, "Network error! Please try again later.", Toast.LENGTH_SHORT).show();
            }


        });

    }
}