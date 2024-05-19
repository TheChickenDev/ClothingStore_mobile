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

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import apis.APIService;
import classes.PreferencesManager;
import interfaces.Callbacks;
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
    Button btn_login;
    Button btn_register;
    CircularProgressIndicator progressIndicator;
    APIService apiService;
    Validator validator;
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
        validator = new Validator();
        progressIndicator = findViewById(R.id.reset_password_progress);
        layout_otp = findViewById(R.id.reset_password_layout_otp);
        input_otp = findViewById(R.id.reset_password_input_otp);
        layout_password = findViewById(R.id.reset_password_layout_password);
        input_password = findViewById(R.id.reset_password_input_password);
        layout_confirm_password = findViewById(R.id.reset_password_layout_confirm_password);
        input_confirm_password = findViewById(R.id.reset_password_input_confirm_password);
        change_password_btn = findViewById(R.id.reset_password_btn_change_password);
        btn_login = findViewById(R.id.reset_password_btn_login);
        btn_register = findViewById(R.id.reset_password_btn_register);
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
                progressIndicator.setVisibility(View.VISIBLE);
                change_password_btn.setVisibility(View.GONE);
                ChangePassword(new Callbacks() {
                    @Override
                    public void onFunctionCompleted() {
                        progressIndicator.setVisibility(View.GONE);
                        change_password_btn.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        Intent loginIntent = new Intent(this, LoginActivity.class);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(loginIntent);
                finish();
            }
        });
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(registerIntent);
                finish();
            }
        });
    }

    private void ChangePassword(Callbacks resetPasswordCallback) {
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
        apiService = RetrofitClient.getRetrofit(this).create(APIService.class);
        apiService.resetPassword(key, token, password, confirm_password).enqueue(new Callback<SuccessResponseModel<String>>() {
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<String>> call, @NonNull Response<SuccessResponseModel<String>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<String> successResponse = response.body();
                    if (successResponse != null) {
                        if (Objects.equals(successResponse.getStatus(), "OK")) {
                            startActivity(intent);
                            finish();
                        }
                        Toast.makeText(ResetPasswordActivity.this, successResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode = response.code();
                    Toast.makeText(ResetPasswordActivity.this, "Error! Status code: " + statusCode, Toast.LENGTH_SHORT).show();
                }
                resetPasswordCallback.onFunctionCompleted();
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<String>> call, @NonNull Throwable t) {
                Toast.makeText(ResetPasswordActivity.this, "Change password failed!", Toast.LENGTH_SHORT).show();
                resetPasswordCallback.onFunctionCompleted();
            }
        });
    }
}