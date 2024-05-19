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

public class ForgotPasswordActivity extends AppCompatActivity {
    TextInputLayout layout_email;
    TextInputEditText input_email;
    Button btn_getOTP;
    Button btn_login;
    Button btn_register;
    CircularProgressIndicator progressIndicator;
    APIService apiService;
    Validator validator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        Mapping();
    }

    private void Mapping() {
        validator = new Validator();
        progressIndicator = findViewById(R.id.forgot_password_progress);
        layout_email = findViewById(R.id.forgot_password_layout_email);
        input_email = findViewById(R.id.forgot_password_input_email);
        btn_getOTP = findViewById(R.id.forgot_password_btn_receive_mail);
        btn_login = findViewById(R.id.forgot_password_btn_login);
        btn_register = findViewById(R.id.forgot_password_btn_register);
        input_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = s.toString();
                if (!validator.validateEmail(email)) {
                    layout_email.setError("Invalid email");
                } else {
                    layout_email.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_getOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressIndicator.setVisibility(View.VISIBLE);
                btn_getOTP.setVisibility(View.GONE);
                GetOTP(new Callbacks() {
                    @Override
                    public void onFunctionCompleted() {
                        progressIndicator.setVisibility(View.GONE);
                        btn_getOTP.setVisibility(View.VISIBLE);
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

    private void GetOTP(Callbacks getOTPCallback) {
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        String email = Objects.requireNonNull(input_email.getText()).toString().trim();
        if (!validator.validateEmail(email)) {
            layout_email.setError("Invalid email");
            return;
        } else {
            layout_email.setError(null);
        }
        apiService = RetrofitClient.getRetrofit(this).create(APIService.class);
        apiService.getOTP(email).enqueue(new Callback<SuccessResponseModel<String>>() {
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<String>> call, @NonNull Response<SuccessResponseModel<String>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<String> successResponse = response.body();
                    if (successResponse != null) {
                        if (successResponse.getData() != null) {
                            String token = successResponse.getData();
                            intent.putExtra("token", token);
                            startActivity(intent);
                            finish();
                        }
                        Toast.makeText(ForgotPasswordActivity.this, successResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode = response.code();
                    Toast.makeText(ForgotPasswordActivity.this, "Error! Status code: " + statusCode, Toast.LENGTH_SHORT).show();
                }
                getOTPCallback.onFunctionCompleted();
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<String>> call, @NonNull Throwable t) {
                Toast.makeText(ForgotPasswordActivity.this, "Get OTP failed!", Toast.LENGTH_SHORT).show();
                getOTPCallback.onFunctionCompleted();
            }
        });
    }
}