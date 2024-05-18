package com.example.clothingstore;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;

import apis.APIService;
import models.SuccessResponseModel;
import models.UserModel;
import models.Users;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RealPathUtil;
import utils.RetrofitClient;

public class UpdateProfileActivity extends AppCompatActivity {

    // Đối tượng User chứa thông tin người dùng hiện tại
    EditText nameEdt, phoneEdt, addressEdt;
    TextView  emailTv;
    Button btnBack, btnUpload;
    Users currentUser;
    ImageView updateAvatarImageView;
    MultipartBody.Part imagePart;
    Uri mUri;
    ProgressDialog mProgressDialog;
    public static final int MY_REQUEST_CODE =100;
    public static final String TAG = UpdateProfileActivity.class.getName();
    private ActivityResultLauncher<String> galleryLauncher;

    public static String[] storge_permissions = {
            android. Manifest.permission. WRITE_EXTERNAL_STORAGE,
            android.Manifest. permission. READ_EXTERNAL_STORAGE
    };
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storge_permissions_33 = {
            android. Manifest. permission. READ_MEDIA_IMAGES,
            android. Manifest. permission. READ_MEDIA_AUDIO,
            android. Manifest.permission. READ_MEDIA_VIDEO

    };


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

        // Lấy dữ liệu người dùng từ Intent
        Intent intent = getIntent();
        currentUser = (Users) intent.getSerializableExtra("user");
        assert currentUser != null;
        String userId = currentUser.getid();
        if (currentUser != null) {
            // Hiển thị thông tin người dùng trong giao diện chỉnh sửa
            nameEdt.setText(currentUser.getName());
            phoneEdt.setText(currentUser.getPhone());
            emailTv.setText(currentUser.getEmail());
            addressEdt.setText(currentUser.getAddress());

            Glide.with(UpdateProfileActivity.this).load(currentUser.getAvatar()).into(updateAvatarImageView);
//
        }
        updateAvatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức để mở Gallery
                CheckPermission();
            }
        });


        // Xử lý sự kiện nhấn nút Lưu
        Button btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin mới từ các EditText
                String nameValue = nameEdt.getText().toString();
                String phoneValue = phoneEdt.getText().toString();
                String addressValue = addressEdt.getText().toString();

                // Tạo các RequestBody cho name, phone, address
                RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), nameValue);
                RequestBody phone = RequestBody.create(MediaType.parse("multipart/form-data"), phoneValue);
                RequestBody address = RequestBody.create(MediaType.parse("multipart/form-data"), addressValue);

                String imagePath = RealPathUtil.getRealPath(UpdateProfileActivity.this, mUri);

                // Tạo RequestBody cho hình ảnh

                File file = new File(imagePath);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part avatarUpdate = MultipartBody.Part.createFormData("image", file.getName(), requestFile);


                // Gọi API để cập nhật thông tin người dùng
                APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
                apiService.updateUser(userId, name, phone, address, avatarUpdate)
                        .enqueue(new Callback<SuccessResponseModel<UserModel>>() {
                            @Override
                            public void onResponse(@NonNull Call<SuccessResponseModel<UserModel>> call, @NonNull Response<SuccessResponseModel<UserModel>> response) {
                                if (response.isSuccessful()) {
                                    // Hiển thị thông báo cập nhật thành công
                                    Intent intent = new Intent(UpdateProfileActivity.this, ProfileActivity.class);
                                    startActivity(intent);

                                    Toast.makeText(UpdateProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    // Hiển thị thông báo cập nhật thất bại
                                    Toast.makeText(UpdateProfileActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<SuccessResponseModel<UserModel>> call, @NonNull Throwable t) {
                                // Hiển thị thông báo lỗi
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

    //Hàm check quyền truy cập vào file ảnh

    public static String[] permissions() {
        String[] p;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            p = storge_permissions_33;
        } else {
            p = storge_permissions;
        }
        return p;
    }

    private void CheckPermission() {
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        } else {
            //ActivityCompat.requestPermissions(UploadImageActivity.this,permissions(), MY_REQUEST_CODE);
            //hoặc
            requestPermissions(permissions(),MY_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super. onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if(grantResults.length>0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }
    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select pickture"));
    }

    //Hàm kết quả trả về từ gallery
    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts. StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
//request code
                        Intent data = result.getData();

                        if(data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        assert uri != null;
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            updateAvatarImageView.setImageBitmap(bitmap);
                        }catch (IOException e) {
                            e.printStackTrace();

                        }


                    }

                }

            }

    );

}
