package com.example.clothingstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import adapters.ImageAdapter;
import adapters.SizeAdapter;
import apis.APIService;
import classes.PreferencesManager;
import models.AuthResponseModel;
import models.ProductModel;

import models.SuccessResponseModel;
import models.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RetrofitClient;

public class ProductCardActivity extends AppCompatActivity {

    ImageView imageProduct;
    TextView tvSold , tvNameProduct, tvPrice, tvDescription, tvPriceBottomSheet, tvQuantity, tvSize;
    private Button btnExpandSheet, btnPlus, btnMinus, btnConfirm;
    private MaterialButton btnBack;
    ProductModel product;
    int nQuantity = 1;
    private RecyclerView recyclerView, imbRecycleView;
    private SizeAdapter adapter;
    private ImageAdapter imageAdapter;
    String sQuantity, sPrice, isize, iimageProduct;
    private LinearLayout layoutBottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private APIService apiService;
    private PreferencesManager preferencesManager;

    public static String removeHtmlTags(String htmlDescription) {
        // Sử dụng Html.fromHtml() với mode legacy để loại bỏ các thẻ HTML
        return Html.fromHtml(htmlDescription, Html.FROM_HTML_MODE_LEGACY).toString();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_card);

        apiService = RetrofitClient.getRetrofit(this).create(APIService.class);
        preferencesManager = new PreferencesManager(this);
        imageProduct = findViewById(R.id.iv_product);
        tvSold = findViewById(R.id.tv_sold);
        tvNameProduct = findViewById(R.id.tv_nameProduct);
        tvPrice = findViewById(R.id.tv_price);
        tvDescription = findViewById(R.id.tv_description);
        btnExpandSheet = findViewById(R.id.btn_Add_Cart);
        btnBack = findViewById(R.id.product_card_btn_back);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ProductCardActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });


        Intent intent = getIntent();
        String productId = intent.getStringExtra("productId");
        APIService apiService = RetrofitClient.getRetrofit(this).create(APIService.class);
        apiService.getProduct(productId).enqueue(new Callback<SuccessResponseModel<ProductModel>>() {
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<ProductModel>> call, @NonNull Response<SuccessResponseModel<ProductModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<ProductModel> successResponse = response.body();

                    if (successResponse !=null){
                        product = successResponse.getData();
                        tvSold.setText(product.getSold());
                        tvNameProduct.setText(product.getName());
                        tvPrice.setText(product.getPrice());
                        String des = product.getDesc();
                        String processDes = removeHtmlTags(des);
                        tvDescription.setText(processDes);
                        // Hien avatar ra imageView
                        Glide.with(ProductCardActivity.this).load(product.getImg()).into(imageProduct);

                        ArrayList<String> thumbnailUrls = new ArrayList<>();
                        thumbnailUrls.add(0, product.getImg());
                        List<ProductModel.Thumbnail> thumbnails = product.getThumbnails();
                        for (ProductModel.Thumbnail thumbnail : thumbnails) {
                            thumbnailUrls.add(thumbnail.getUrl());
                        }
                        System.out.println(thumbnailUrls);
                        if (!thumbnailUrls.isEmpty()) {
                            // Tiếp tục xử lý
                            imbRecycleView = findViewById(R.id.imagebutton_recycleview);
                            imbRecycleView.setLayoutManager(new LinearLayoutManager(ProductCardActivity.this, LinearLayoutManager.HORIZONTAL, false));
                            imageAdapter = new ImageAdapter(thumbnailUrls, new ImageAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(String thumbnail) {
                                    iimageProduct = thumbnail;
                                    Glide.with(ProductCardActivity.this).load(thumbnail).into(imageProduct);
                                }
                            });
                            imbRecycleView.setAdapter(imageAdapter);
                        } else {
                            // Xử lý khi sizes là null hoặc rỗng
                            Toast.makeText(ProductCardActivity.this, "No sizes available for this product", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(ProductCardActivity.this, "User not found!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(ProductCardActivity.this, "Failed to fetch user details!", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<ProductModel>> call, @NonNull Throwable t) {
                Toast.makeText(ProductCardActivity.this, "Network error! Please try again later.", Toast.LENGTH_SHORT).show();
            }


        });



        btnExpandSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_product_card, null);
                tvPriceBottomSheet = viewDialog.findViewById(R.id.tv_price_bottomSheet);
                tvQuantity = viewDialog.findViewById(R.id.tv_quantity);
                btnPlus = viewDialog.findViewById(R.id.btn_plus);
                btnMinus = viewDialog.findViewById(R.id.btn_minus);
                btnConfirm = viewDialog.findViewById(R.id.btn_Identify_Cart);

                ArrayList<String> sizes = product.getSizes();
                if (sizes != null && !sizes.isEmpty()) {
                    // Tiếp tục xử lý
                    recyclerView = viewDialog.findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ProductCardActivity.this, LinearLayoutManager.HORIZONTAL, false));
                    adapter = new SizeAdapter(sizes, new SizeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(String size) {
                            isize = size;
                        }
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    // Xử lý khi sizes là null hoặc rỗng
                    Toast.makeText(ProductCardActivity.this, "No sizes available for this product", Toast.LENGTH_SHORT).show();
                }

                nQuantity = Integer.parseInt(tvQuantity.getText().toString());

                tvPriceBottomSheet.setText(product.getPrice());
                btnPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nQuantity = nQuantity + 1;
                        tvQuantity.setText(String.valueOf(nQuantity));
                        tvPriceBottomSheet.setText(String.valueOf(Integer.parseInt(product.getPrice()) * nQuantity));
                    }
                });

                btnMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(nQuantity > 1){
                            nQuantity = nQuantity - 1;
                            tvQuantity.setText(String.valueOf(nQuantity));
                            tvPriceBottomSheet.setText(String.valueOf(Integer.parseInt(product.getPrice()) * nQuantity));
                        }

                    }
                });


                btnConfirm.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String userId = preferencesManager.getId();
                        String quantity = tvQuantity.getText().toString();
                        if (isize == null) {
                            Toast.makeText(ProductCardActivity.this, "Please choose product size!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        apiService.addToCart(userId, product.getId(), product.getName(), product.getImg(), isize, quantity, product.getPrice()).enqueue(new Callback<SuccessResponseModel<UserModel>>() {
                            @Override
                            public void onResponse(@NonNull Call<SuccessResponseModel<UserModel>> call, @NonNull Response<SuccessResponseModel<UserModel>> response) {
                                if (response.isSuccessful()) {
                                    SuccessResponseModel<UserModel> successResponse = response.body();
                                    if (successResponse != null) {
                                        Intent intent1 = new Intent(ProductCardActivity.this, CartActivity.class);
                                        startActivity(intent1);
                                        finish();
                                        Toast.makeText(ProductCardActivity.this, successResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    int statusCode = response.code();
                                    Toast.makeText(ProductCardActivity.this, "Error! Status code: " + statusCode, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<SuccessResponseModel<UserModel>> call, @NonNull Throwable t) {
                                Toast.makeText(ProductCardActivity.this, "Add to cart failed!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ProductCardActivity.this);
                bottomSheetDialog.setContentView(viewDialog);
                bottomSheetDialog.show();
            }

        });

    }

}