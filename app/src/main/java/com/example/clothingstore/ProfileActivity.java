package com.example.clothingstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import apis.APIService;
import models.SuccessResponseModel;
import models.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.bumptech.glide.Glide;

import models.Users;
import utils.RetrofitClient;

import android.widget.Toast;

import java.io.Serializable;


public class ProfileActivity extends AppCompatActivity {
    TextView nameTextView, phoneTextView, emailTextView, addressTextView;
    ImageView avatarImageView;
    Button btnUpdateProfile, btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        nameTextView = findViewById(R.id.tv_name_value);
        phoneTextView = findViewById(R.id.tv_phoneNumber_value);
        emailTextView = findViewById(R.id.tv_email_value);
        addressTextView = findViewById(R.id.tv_address_value);
        avatarImageView = findViewById(R.id.imageViewProfile);
        btnBack = findViewById(R.id.btn_back);
        btnUpdateProfile = findViewById(R.id.btn_update);
        // Get user ID from intent
//        String userId = getIntent().getStringExtra("USER_ID");
        String userId = "65f99e462c619f15e778fb21";

        // Call API
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getUser(userId).enqueue(new Callback<SuccessResponseModel<UserModel>>() {
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<UserModel>> call, @NonNull Response<SuccessResponseModel<UserModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<UserModel> successResponse = response.body();
                    UserModel user;
                    if (successResponse !=null){
                        user = successResponse.getData();
                        nameTextView.setText(user.getName());
                        phoneTextView.setText(user.getPhone());
                        emailTextView.setText(user.getEmail());
                        addressTextView.setText(user.getAddress());
                        // Hien avatar ra imageView
                        Glide.with(ProfileActivity.this).load(user.getAvatar()).into(avatarImageView);

                        btnUpdateProfile.setOnClickListener(v -> {
                            // Tạo Intent để chuyển từ ProfileActivity sang UpdateProfileActivity
                            Intent intent = new Intent(ProfileActivity.this, UpdateProfileActivity.class);
                            intent.putExtra("user", (Serializable) user);
                            // Bắt đầu Activity mới
                            startActivity(intent);
                        });

                    } else {
                        Toast.makeText(ProfileActivity.this, "User not found!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(ProfileActivity.this, "Failed to fetch user details!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<UserModel>> call, @NonNull Throwable t) {
                Toast.makeText(ProfileActivity.this, "Network error! Please try again later.", Toast.LENGTH_SHORT).show();
            }


        });
        btnBack.setOnClickListener(v -> {
            finish(); // Kết thúc activity hiện tại và trở về activity trước đó
        });
    }
}