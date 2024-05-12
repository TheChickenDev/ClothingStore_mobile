package com.example.clothingstore;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import apis.APIService;
import utils.Validater;

public class RegisterActivity extends AppCompatActivity {
    TextInputLayout layout_name;
    TextInputEditText input_name;
    TextInputLayout layout_email;
    TextInputEditText input_email;
    TextInputLayout layout_password;
    TextInputEditText input_password;
    TextInputLayout layout_address;
    TextInputEditText input_address;
    TextInputLayout layout_phone;
    TextInputEditText input_phone;
    Button btn_login;
    Button btn_register;
    APIService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
    }

    private void Mapping() {
        input_email = findViewById(R.id.register_input_email);
        layout_email = findViewById(R.id.register_layout_email);
        input_password = findViewById(R.id.register_input_password);
        layout_password = findViewById(R.id.register_layout_password);;
        input_password = findViewById(R.id.register_input_name);
        layout_password = findViewById(R.id.register_layout_name);;
        input_password = findViewById(R.id.register_input_address);
        layout_password = findViewById(R.id.register_layout_address);;
        input_password = findViewById(R.id.register_input_phone);
        layout_password = findViewById(R.id.register_layout_phone);
        btn_login = findViewById(R.id.register_btn_login);
        btn_register = findViewById(R.id.register_btn_register);
        input_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = s.toString();
                Validater validate = new Validater();
                if (validate.validateEmail(email)) {
                    layout_email.setError("Email không hợp lệ");
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
                Validater validate = new Validater();
                if (validate.validatePassword(password)) {
                    layout_password.setError("Mật khẩu không hợp lệ");
                } else {
                    layout_password.setError(null);
                }
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
//                Login(email, password);
            }
        });
    }
}