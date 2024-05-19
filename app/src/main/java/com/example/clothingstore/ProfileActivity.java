package com.example.clothingstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import apis.APIService;
import classes.PreferencesManager;
import models.SuccessResponseModel;
import models.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.bumptech.glide.Glide;

import utils.RetrofitClient;

import android.widget.Toast;

import java.io.Serializable;
import java.util.Objects;


public class ProfileActivity extends AppCompatActivity {
    TextView nameTextView, phoneTextView, emailTextView, addressTextView;
    ImageView avatarImageView;
    Button btnUpdateProfile, btnBack;
    PreferencesManager preferencesManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        preferencesManager = new PreferencesManager(this);

        // Initialize views
        nameTextView = findViewById(R.id.tv_name_value);
        phoneTextView = findViewById(R.id.tv_phoneNumber_value);
        emailTextView = findViewById(R.id.tv_email_value);
        addressTextView = findViewById(R.id.tv_address_value);
        avatarImageView = findViewById(R.id.imageViewProfile);
        btnBack = findViewById(R.id.btn_back);
        btnUpdateProfile = findViewById(R.id.btn_update);
        String userId = preferencesManager.getId();

        // Call API
        APIService apiService = RetrofitClient.getRetrofit(this).create(APIService.class);
        apiService.getUser(userId).enqueue(new Callback<SuccessResponseModel<UserModel>>() {
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<UserModel>> call, @NonNull Response<SuccessResponseModel<UserModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<UserModel> successResponse = response.body();
                    UserModel user;
                    if (successResponse != null){
                        user = successResponse.getData();
                        nameTextView.setText(user.getName());
                        phoneTextView.setText(user.getPhone());
                        emailTextView.setText(user.getEmail());
                        addressTextView.setText(user.getAddress());
                        if (!user.getAvatar().isEmpty()) {
                            // Hien avatar ra imageView
                            Glide.with(ProfileActivity.this).load(user.getAvatar()).into(avatarImageView);
                        }

                        btnUpdateProfile.setOnClickListener(v -> {
                            // Tạo Intent để chuyển từ ProfileActivity sang UpdateProfileActivity
                            Intent intent = new Intent(ProfileActivity.this, UpdateProfileActivity.class);
                            intent.putExtra("user", user);
                            // Bắt đầu Activity mới
                            startActivity(intent);
                            finish();
                        });

                    } else {
                        Toast.makeText(ProfileActivity.this, "User not found!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(ProfileActivity.this, "Error! Status code: " + response.code(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<UserModel>> call, @NonNull Throwable t) {
                Toast.makeText(ProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });
        btnBack.setOnClickListener(v -> {
            finish(); // Kết thúc activity hiện tại và trở về activity trước đó
        });
    }
}