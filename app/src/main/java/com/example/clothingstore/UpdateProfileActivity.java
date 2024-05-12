package com.example.clothingstore;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import models.SuccessResponse;
import models.Users;
import utils.ApiRetrofit;
import utils.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    // Đối tượng User chứa thông tin người dùng hiện tại
    EditText nameEdt, phoneEdt, addressEdt;
    TextView  emailTv;
    Button btnBack, btnUpload;
    Users currentUser;
    ImageView updateAvatarImageView;
    private String UriImage;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        nameEdt = findViewById(R.id.et_name_value);
        phoneEdt = findViewById(R.id.et_phone_value);
        emailTv = findViewById(R.id.tv_email_value);
        addressEdt = findViewById(R.id.et_address_value);
        updateAvatarImageView = findViewById(R.id.updateImageViewProfile);
        btnBack = findViewById(R.id.btn_back);
        btnUpload = findViewById(R.id.btn_upload);


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        // Lấy dữ liệu người dùng từ Intent
        Intent intent = getIntent();
        currentUser = (Users) intent.getSerializableExtra("user");
        String userId = currentUser.getid();
        System.out.println("------------------------"+userId);
        if (currentUser != null) {
            // Hiển thị thông tin người dùng trong giao diện chỉnh sửa
            nameEdt.setText(currentUser.getName());
            phoneEdt.setText(currentUser.getPhone());
            emailTv.setText(currentUser.getEmail());
            addressEdt.setText(currentUser.getAddress());
            // Hiển thị ảnh đại diện của người dùng (nếu có)
            Glide.with(UpdateProfileActivity.this).load(currentUser.getAvatar()).into(updateAvatarImageView);
//            displayUserData(currentUser);
        }

        // Xử lý sự kiện nhấn nút Lưu
        Button btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin mới từ các EditText
                Users updateUser = new Users();
                updateUser.setid(userId);
                System.out.println("--------------------------------------"+userId);
                updateUser.setName(nameEdt.getText().toString());
                updateUser.setPhone(phoneEdt.getText().toString());
                updateUser.setEmail(emailTv.getText().toString());
                updateUser.setAddress(addressEdt.getText().toString());
                updateUser.setAvatar(UriImage);
                System.out.println("-------------------------"+updateUser.getid() + updateUser.getName() + updateUser.getPhone() + updateUser.getEmail() + updateUser.getAddress());
                // Gọi phương thức cập nhật thông tin người dùng
//                updateUserData(user);
                ApiService apiService = ApiRetrofit.getRetrofitClient().create(ApiService.class);
                // Gửi yêu cầu cập nhật thông tin người dùng lên API
                apiService.updateUser(updateUser.getid(), updateUser).enqueue(new Callback<SuccessResponse<Users>>() {
                    @Override
                    public void onResponse(@NonNull Call<SuccessResponse<Users>> call, @NonNull Response<SuccessResponse<Users>> response) {
                        System.out.println("------" + response.body());
                        if (response.isSuccessful()) {
//                                // Hiển thị thông báo cập nhật thành công
                                Toast.makeText(UpdateProfileActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            // Cập nhật thất bại, xử lý lỗi
                            System.out.println(response.message());
                            // Hiển thị thông báo lỗi
                            Toast.makeText(UpdateProfileActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SuccessResponse<Users>> call, @NonNull Throwable t) {
                        Toast.makeText(UpdateProfileActivity.this, "Đã xảy ra lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Kết thúc activity hiện tại và trở về activity trước đó
            }
        });
    }


}
