package com.example.clothingstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import apis.APIService;
import classes.PreferencesManager;
import interfaces.Callbacks;
import models.SuccessResponseModel;
import models.AuthResponseModel;
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
    CircularProgressIndicator progressIndicator;
    APIService apiService;
    PreferencesManager preferencesManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Mapping();
        AutoLogin();
    }

    private void Mapping() {
        preferencesManager = new PreferencesManager(this);
        progressIndicator = findViewById(R.id.login_progress);
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
                String email = s.toString();
                if (email.isEmpty()) {
                    layout_email.setError("Email is required");
                } else {
                    layout_email.setError(null);
                }
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
                String password = s.toString();
                if (password.isEmpty()) {
                    layout_password.setError("Password is required");
                } else {
                    layout_password.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_login.setOnClickListener(v -> {
            String email = Objects.requireNonNull(input_email.getText()).toString().trim();
            String password = Objects.requireNonNull(input_password.getText()).toString().trim();
            boolean isValidForm = true;
            if (email.isEmpty()) {
                layout_email.setError("Email is required");
                isValidForm = false;
            } else {
                layout_email.setError(null);
            }
            if (password.isEmpty()) {
                layout_password.setError("Password is required");
                isValidForm = false;
            } else {
                layout_password.setError(null);
            }
            if (isValidForm) {
                progressIndicator.setVisibility(View.VISIBLE);
                btn_login.setVisibility(View.GONE);
                Login(email, password, () -> {
                    progressIndicator.setVisibility(View.GONE);
                    btn_login.setVisibility(View.VISIBLE);
                });
            }
        });
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        btn_register.setOnClickListener(v -> {
            startActivity(registerIntent);
            finish();
        });
        Intent getPasswordIntent = new Intent(this, ForgotPasswordActivity.class);
        btn_get_password.setOnClickListener(v -> {
            startActivity(getPasswordIntent);
            finish();
        });
    }

    private void AutoLogin() {
        if (preferencesManager.isUserLoggedOut()) {
            return;
        }
        String email = preferencesManager.getEmail();
        String password = preferencesManager.getPassword();
        progressIndicator.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);
        Login(email, password, () -> {
            progressIndicator.setVisibility(View.GONE);
            btn_login.setVisibility(View.VISIBLE);
        });
    }

    private void Login(String email, String password, Callbacks loginCallback) {
        Intent intent = new Intent(this, MainActivity.class);
        apiService = RetrofitClient.getRetrofit(this).create(APIService.class);
        apiService.login(email, password).enqueue(new Callback<SuccessResponseModel<AuthResponseModel>>() {
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<AuthResponseModel>> call, @NonNull Response<SuccessResponseModel<AuthResponseModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<AuthResponseModel> successResponse = response.body();
                    if (successResponse != null) {
                        if (successResponse.getData() != null) {
                            UserModel user = successResponse.getData().getUser();
                            String access_token = successResponse.getData().getAccess_token();
                            String refresh_token = successResponse.getData().getRefresh_token();
                            String id = user.getId();
                            preferencesManager.saveLoginDetails(access_token, refresh_token, email, password, id);
                            startActivity(intent);
                            finish();
                        }
                        Toast.makeText(LoginActivity.this, successResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode = response.code();
                    Toast.makeText(LoginActivity.this, "Error! Status code: " + statusCode, Toast.LENGTH_SHORT).show();
                }
                loginCallback.onFunctionCompleted();
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<AuthResponseModel>> call, @NonNull Throwable t) {
                Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
                loginCallback.onFunctionCompleted();
            }
        });
    }
}