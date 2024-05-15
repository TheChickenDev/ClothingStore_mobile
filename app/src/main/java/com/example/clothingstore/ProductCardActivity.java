package com.example.clothingstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import adapters.SizeAdapter;
import models.Product;
import models.SuccessResponse;

import utils.ApiRetrofit;
import utils.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCardActivity extends AppCompatActivity {

    ImageView imageProduct;
    TextView tvSold , tvNameProduct, tvPrice, tvDescription, tvPriceBottomSheet, tvQuantity;
    private Button btnExpandSheet, btnPlus, btnMinus;
    Product product;
    int nQuantity = 0;
    private RecyclerView recyclerView;
    private SizeAdapter adapter;
    String sPrice;
    private LinearLayout layoutBottomSheet;
    private BottomSheetBehavior bottomSheetBehavior;


    public static String removeHtmlTags(String htmlDescription) {
        // Sử dụng Html.fromHtml() với mode legacy để loại bỏ các thẻ HTML
        return Html.fromHtml(htmlDescription, Html.FROM_HTML_MODE_LEGACY).toString();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_card);

        imageProduct = findViewById(R.id.iv_product);
        tvSold = findViewById(R.id.tv_sold);
        tvNameProduct = findViewById(R.id.tv_nameProduct);
        tvPrice = findViewById(R.id.tv_price);
        tvDescription = findViewById(R.id.tv_description);
        btnExpandSheet = findViewById(R.id.btn_Add_Cart);



        String productId = "66116fe4f1bf7f8c4986bd5a";
        ApiService apiService = ApiRetrofit.getRetrofitClient().create(ApiService.class);
        apiService.getProduct(productId).enqueue(new Callback<SuccessResponse<Product>>() {
            @Override
            public void onResponse(@NonNull Call<SuccessResponse<Product>> call, @NonNull Response<SuccessResponse<Product>> response) {
                if (response.isSuccessful()) {
                    SuccessResponse<Product> successResponse = response.body();

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



                    } else {
                        Toast.makeText(ProductCardActivity.this, "User not found!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(ProductCardActivity.this, "Failed to fetch user details!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponse<Product>> call, @NonNull Throwable t) {
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

                ArrayList<String> sizes = product.getSizes();
                System.out.println(sizes);
                recyclerView = viewDialog.findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(ProductCardActivity.this, LinearLayoutManager.HORIZONTAL, false));
                adapter = new SizeAdapter(sizes, new SizeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String size) {
                        // Xử lý sự kiện click cho từng size ở đây
                        Toast.makeText(ProductCardActivity.this, "Selected size: " + size, Toast.LENGTH_SHORT).show();
                    }
                });
                recyclerView.setAdapter(adapter);

                nQuantity = Integer.parseInt(tvQuantity.getText().toString());

                tvPriceBottomSheet.setText(product.getPrice());
                btnPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nQuantity = nQuantity + 1;
                        tvQuantity.setText(String.valueOf(nQuantity));
                        String sPrice = String.valueOf(Integer.parseInt(product.getPrice()) * nQuantity);
                        tvPriceBottomSheet.setText(sPrice);
                        abdc(nQuantity, sPrice);
                    }
                });

                btnMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nQuantity = nQuantity - 1;
                        tvQuantity.setText(String.valueOf(nQuantity));
                        String sPrice = String.valueOf(Integer.parseInt(product.getPrice()) * nQuantity);
                        tvPriceBottomSheet.setText(sPrice);
                        abdc(nQuantity, sPrice);
                    }
                });



                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ProductCardActivity.this);
                bottomSheetDialog.setContentView(viewDialog);
                bottomSheetDialog.show();
            }



            public void abdc(int nQuantity,String sPrice){
                System.out.println("-----------------"+product.getId());
                System.out.println( "So luong: "+nQuantity + "Gia: "+sPrice);
            }
        });

    }

}