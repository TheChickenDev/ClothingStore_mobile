package com.example.clothingstore;

import android.annotation.SuppressLint;
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
import models.AuthResponseModel;
import models.SuccessResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RetrofitClient;
import utils.Validator;

public class RegisterActivity extends AppCompatActivity {
    TextInputLayout layout_name;
    TextInputEditText input_name;
    TextInputLayout layout_email;
    TextInputEditText input_email;
    TextInputLayout layout_password;
    TextInputEditText input_password;
    TextInputLayout layout_confirm_password;
    TextInputEditText input_confirm_password;
    TextInputLayout layout_address;
    TextInputEditText input_address;
    TextInputLayout layout_phone;
    TextInputEditText input_phone;
    Button btn_login;
    Button btn_register;
    CircularProgressIndicator progressIndicator;
    APIService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        Mapping();
    }

    private void Mapping() {
        progressIndicator = findViewById(R.id.register_progress);
        input_name = findViewById(R.id.register_input_name);
        layout_name = findViewById(R.id.register_layout_name);
        input_email = findViewById(R.id.register_input_email);
        layout_email = findViewById(R.id.register_layout_email);
        input_password = findViewById(R.id.register_input_password);
        layout_password = findViewById(R.id.register_layout_password);
        input_confirm_password = findViewById(R.id.register_input_confirm_password);
        layout_confirm_password = findViewById(R.id.register_layout_confirm_password);
        input_address = findViewById(R.id.register_input_address);
        layout_address = findViewById(R.id.register_layout_address);
        input_phone = findViewById(R.id.register_input_phone);
        layout_phone = findViewById(R.id.register_layout_phone);
        btn_login = findViewById(R.id.register_btn_login);
        btn_register = findViewById(R.id.register_btn_register);
        Validator validate = new Validator();
        input_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = s.toString();
                if (!validate.validateName(name)) {
                    layout_name.setError("Invalid name");
                } else {
                    layout_name.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = s.toString();
                if (!validate.validateEmail(email)) {
                    layout_email.setError("Invalid email");
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
                if (!validate.validatePassword(password)) {
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
                String confirm_password = s.toString();
                String password = Objects.requireNonNull(input_password.getText()).toString();
                if (!validate.validateConfirmPassword(password, confirm_password)) {
                    layout_confirm_password.setError("Invalid re-enter password");
                } else {
                    layout_confirm_password.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String address = s.toString();
                if (!validate.validateAddress(address)) {
                    layout_address.setError("Invalid address");
                } else {
                    layout_address.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phoneNo = s.toString();
                if (!validate.validatePhoneNumber(phoneNo)) {
                    layout_phone.setError("Invalid phone number");
                } else {
                    layout_phone.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                String name = Objects.requireNonNull(input_name.getText()).toString();
                String email = Objects.requireNonNull(input_email.getText()).toString();
                String password = Objects.requireNonNull(input_password.getText()).toString();
                String confirm_password = Objects.requireNonNull(input_confirm_password.getText()).toString();
                String address = Objects.requireNonNull(input_address.getText()).toString();
                String phone = Objects.requireNonNull(input_phone.getText()).toString();
                boolean isValidForm = true;
                if (!validate.validateName(name)) {
                    layout_name.setError("Invalid name");
                    isValidForm = false;
                }
                if (!validate.validateEmail(email)) {
                    layout_email.setError("Invalid email");
                    isValidForm = false;
                }
                if (!validate.validatePassword(password)) {
                    layout_password.setError("Invalid password");
                    isValidForm = false;
                }
                if (!validate.validateConfirmPassword(password, confirm_password)) {
                    layout_confirm_password.setError("Invalid re-enter password");
                    isValidForm = false;
                }
                if (!validate.validateAddress(address)) {
                    layout_address.setError("Invalid address");
                    isValidForm = false;
                }
                if (!validate.validatePhoneNumber(phone)) {
                    layout_phone.setError("Invalid phone number");
                }
                if (isValidForm) {
                    progressIndicator.setVisibility(View.VISIBLE);
                    btn_register.setVisibility(View.GONE);
                    Register(name, email, password, confirm_password, address, phone, new Callbacks() {
                        @Override
                        public void onFunctionCompleted() {
                            progressIndicator.setVisibility(View.GONE);
                            btn_register.setVisibility(View.VISIBLE);
                        }
                    });
                }
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
    }

    private void Register(String name, String email, String password, String confirm_password, String address, String phone, Callbacks registerCallback) {
        Intent intent = new Intent(this, LoginActivity.class);
        apiService = RetrofitClient.getRetrofit(this).create(APIService.class);
        apiService.register(name, email, password, confirm_password, address, phone).enqueue(new Callback<SuccessResponseModel<AuthResponseModel>>() {
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<AuthResponseModel>> call, @NonNull Response<SuccessResponseModel<AuthResponseModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<AuthResponseModel> successResponse = response.body();
                    if (successResponse != null) {
                        if (successResponse.getData() != null) {
                            startActivity(intent);
                            finish();
                        }
                        Toast.makeText(RegisterActivity.this, successResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode = response.code();
                    Toast.makeText(RegisterActivity.this, "Error! Status code: " + statusCode, Toast.LENGTH_SHORT).show();
                }
                registerCallback.onFunctionCompleted();
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<AuthResponseModel>> call, @NonNull Throwable t) {
                Toast.makeText(RegisterActivity.this, "Register failed!", Toast.LENGTH_SHORT).show();
                registerCallback.onFunctionCompleted();
            }
        });
    }
}