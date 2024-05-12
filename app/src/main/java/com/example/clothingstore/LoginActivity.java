package com.example.clothingstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import apis.APIService;
import classes.PreferencesManager;
import models.SuccessResponseModel;
import models.LoginResponseModel;
import models.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RetrofitClient;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout layout_email;
    TextInputEditText input_email;
    TextInputLayout layout_password;
    TextInputEditText input_password;
    Button btn_login;
    Button btn_register;
    Button btn_get_password;
    APIService apiService;
    PreferencesManager preferencesManager = new PreferencesManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Mapping();
        AutoLogin();
    }

    private void Mapping() {
        layout_email = findViewById(R.id.login_layout_email);
        input_email = findViewById(R.id.login_input_email);
        input_password = findViewById(R.id.login_input_password);
        layout_password = findViewById(R.id.login_layout_password);
        btn_login = findViewById(R.id.login_btn_login);
        btn_register = findViewById(R.id.login_btn_register);
        btn_get_password = findViewById(R.id.login_btn_get_password);
        input_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String email = s.toString();
//                Validater validate = new Validater();
//                if (validate.validateEmail(email)) {
//                    layout_email.setError("Email không hợp lệ");
//                } else {
//                    layout_email.setError(null);
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String password = s.toString();
//                Validater validate = new Validater();
//                if (validate.validatePassword(password)) {
//                    layout_password.setError("Mật khẩu không hợp lệ");
//                } else {
//                    layout_password.setError(null);
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                String email = Objects.requireNonNull(input_email.getText()).toString().trim();
                String password = Objects.requireNonNull(input_password.getText()).toString().trim();
                Login(email, password);
            }
        });
    }

    private void AutoLogin() {
        if (preferencesManager.isUserLoggedOut()) {
            return;
        }
        String email = preferencesManager.getEmail();
        String password = preferencesManager.getPassword();
        Login(email, password);
    }

    private void Login(String email, String password) {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        Intent intent = new Intent(this, MainActivity.class);
        apiService.login(email, password).enqueue(new Callback<SuccessResponseModel<LoginResponseModel>>() {
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<LoginResponseModel>> call, @NonNull Response<SuccessResponseModel<LoginResponseModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<LoginResponseModel> successResponse = response.body();
                    if (successResponse != null) {
                        if (successResponse.getData() != null) {
                            UserModel user = successResponse.getData().getUser();
                            String access_token = successResponse.getData().getAccess_token();
                            String refresh_token = successResponse.getData().getRefresh_token();
                            String id = user.getId();
                            preferencesManager.saveLoginDetails(access_token, refresh_token, email, password, id);
                            System.out.println("-----" + successResponse.getMessage());
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, successResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    // Handle the LoginResponseData
                } else {
                    int statusCode = response.code();
                    Toast.makeText(LoginActivity.this, "Lỗi rồi kìa! Mã lỗi: " + statusCode, Toast.LENGTH_SHORT).show();
                    // Handle the error
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<LoginResponseModel>> call, @NonNull Throwable t) {
                // Handle the failure
                Toast.makeText(LoginActivity.this, "Lỗi rồi kìa! (" + t.getMessage() + ")", Toast.LENGTH_SHORT).show();
            }
        });
    }
}