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

public class ForgotPasswordActivity extends AppCompatActivity {
    TextInputLayout layout_email;
    TextInputEditText input_email;
    Button getOTP_btn;
    APIService apiService;
    Validator validator = new Validator();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        Mapping();
    }

    private void Mapping() {
        layout_email = findViewById(R.id.forgot_password_layout_email);
        input_email = findViewById(R.id.forgot_password_input_email);
        getOTP_btn = findViewById(R.id.forgot_password_btn_receive_mail);
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
        getOTP_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetOTP();
            }
        });
    }

    private void GetOTP() {
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        String email = Objects.requireNonNull(input_email.getText()).toString().trim();
        if (!validator.validateEmail(email)) {
            layout_email.setError("Invalid email");
            return;
        } else {
            layout_email.setError(null);
        }
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
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
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, successResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    int statusCode = response.code();
                    Toast.makeText(ForgotPasswordActivity.this, "Lỗi rồi kìa! Mã lỗi: " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<String>> call, @NonNull Throwable t) {
                Toast.makeText(ForgotPasswordActivity.this, "Lỗi rồi kìa! (" + t.getMessage() + ")", Toast.LENGTH_SHORT).show();
            }
        });
    }
}