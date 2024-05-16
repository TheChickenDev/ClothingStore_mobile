package com.example.clothingstore;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import apis.APIService;
import models.SuccessResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RetrofitClient;
import utils.Validator;

public class ResetPasswordActivity extends AppCompatActivity {
    TextInputLayout layout_otp;
    TextInputEditText input_otp;
    TextInputLayout layout_password;
    TextInputEditText input_password;
    TextInputLayout layout_confirm_password;
    TextInputEditText input_confirm_password;
    Button change_password_btn;
    APIService apiService;
    Validator validator = new Validator();
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);
        token = getIntent().getStringExtra("token");

        Mapping();
    }

    private void Mapping() {
        layout_otp = findViewById(R.id.reset_password_layout_otp);
        input_otp = findViewById(R.id.reset_password_input_otp);
        layout_password = findViewById(R.id.reset_password_layout_password);
        input_password = findViewById(R.id.reset_password_input_password);
        layout_confirm_password = findViewById(R.id.reset_password_layout_confirm_password);
        input_confirm_password = findViewById(R.id.reset_password_input_confirm_password);
        change_password_btn = findViewById(R.id.reset_password_btn_change_password);
        input_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = s.toString();
                if (!validator.validatePassword(password)) {
                    layout_password.setError("Invalid password");
                } else {
                    layout_password.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input_confirm_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = Objects.requireNonNull(input_password.getText()).toString().trim();
                String confirm_password = s.toString();
                if (!validator.validateConfirmPassword(password, confirm_password)) {
                    layout_confirm_password.setError("Invalid re-enter password");
                } else {
                    layout_confirm_password.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        change_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassword();
            }
        });
    }

    private void ChangePassword() {
        Intent intent = new Intent(this, LoginActivity.class);
        String key = Objects.requireNonNull(input_otp.getText()).toString().trim();
        String password = Objects.requireNonNull(input_password.getText()).toString().trim();
        String confirm_password = Objects.requireNonNull(input_confirm_password.getText()).toString().trim();
        if (!validator.validatePassword(password)) {
            layout_password.setError("Invalid password");
            return;
        } else {
            layout_password.setError(null);
        }
        if (!validator.validateConfirmPassword(password, confirm_password)) {
            layout_confirm_password.setError("Invalid re-enter password");
            return;
        } else {
            layout_password.setError(null);
        }
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.resetPassword(key, token, password, confirm_password).enqueue(new Callback<SuccessResponseModel<String>>() {
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<String>> call, @NonNull Response<SuccessResponseModel<String>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<String> successResponse = response.body();
                    if (successResponse != null) {
                        Toast.makeText(ResetPasswordActivity.this, successResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        if (Objects.equals(successResponse.getStatus(), "OK")) {
                            startActivity(intent);
                            finish();
                        }
                    }
                } else {
                    int statusCode = response.code();
                    Toast.makeText(ResetPasswordActivity.this, "Lỗi rồi kìa! Mã lỗi: " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<String>> call, @NonNull Throwable t) {
                Toast.makeText(ResetPasswordActivity.this, "Lỗi rồi kìa! (" + t.getMessage() + ")", Toast.LENGTH_SHORT).show();
            }
        });
    }
}