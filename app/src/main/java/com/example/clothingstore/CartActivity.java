package com.example.clothingstore;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapters.CartAdapter;
import models.ClothInfomationModel;
import utils.DatabaseHelper;

public class CartActivity extends AppCompatActivity {
    CartAdapter cartAdapter;
    List<FoodDetailStore> foodDetailStoreList;
    RecyclerView rcCart;
    TextView txtTotalItems, txtTotalPrice, txtTotalPriceOne;
    ImageView btnDelete, btnPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initializeBottomBar();
        int selectedMenuItemId = getIntent().getIntExtra("SELECTED_MENU_ITEM_ID", R.id.home);
        bottomNavigationView.getMenu().findItem(selectedMenuItemId).setChecked(true);

        MappingView();
        GetCartDetail();

        rcCart.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rcCart.setLayoutManager(layoutManager);
        int spaceInPixels = getResources().getDimensionPixelSize(R.dimen.margin_15);
        rcCart.addItemDecoration(new SpacesItemDecoration(spaceInPixels));
        cartAdapter = new CartAdapter(this, foodDetailStoreList);
        cartAdapter.notifyDataSetChanged();
        rcCart.setAdapter(cartAdapter);

        calculateTotalPrice();

    }


    private void MappingView() {
        rcCart = findViewById(R.id.recyclerViewCartDetail);
        btnDelete = findViewById(R.id.btn_delete);
        btnPayment = findViewById(R.id.btn_payment);
    }

    private void GetCartDetail() {
        foodDetailStoreList = new ArrayList<>();

        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM FoodStore", null);

        ClothInfomationModel clothInfomationModel;

        if (cursor.moveToFirst()) {
            do {
                clothInfomationModel = new ClothInfomationModel();
                clothInfomationModel.setId(cursor.getString(0));
                clothInfomationModel.setImg(cursor.getString(1);
                clothInfomationModel.setName(cursor.getString(2));
                clothInfomationModel.setPrice(cursor.getDouble(3));
                clothInfomationModel.setSize(cursor.getString(4));
                clothInfomationModel.setQuantity(cursor.getInt(5));
                foodDetailStoreList.add(clothInfomationModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }

    private void calculateTotalPrice() {
        double totalPrice = 0;
        int totalItems = 0;
        for (ClothInfomationModel food : foodDetailStoreList) {
            totalPrice += food.getPrice() * food.getQuantity();
            totalItems += food.getQuantity();
        }
        txtTotalPrice.setText(String.format("$%s", totalPrice));
        txtTotalItems.setText(String.valueOf(totalItems));
    }
}
