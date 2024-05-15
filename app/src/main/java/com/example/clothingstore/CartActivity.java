package com.example.clothingstore;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapters.CartAdapter;
import models.ClothDetailStoreModel;
import models.SpacesItemDecoration;
import utils.DatabaseHelper;

public class CartActivity extends BaseActivity {
    CartAdapter cartAdapter;
    List<ClothDetailStoreModel> clothDetailStoreModelList;
    RecyclerView rcCart;
    Button btnDelete, btnPayment;

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
        cartAdapter = new CartAdapter(this, clothDetailStoreModelList);
        cartAdapter.notifyDataSetChanged();
        rcCart.setAdapter(cartAdapter);

    }


    private void MappingView() {
        rcCart = findViewById(R.id.recyclerViewCartDetail);
        btnDelete = findViewById(R.id.btn_delete);
        btnPayment = findViewById(R.id.btn_payment);
    }

    private void GetCartDetail() {
        clothDetailStoreModelList = new ArrayList<>();

        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ClothStore", null);

        ClothDetailStoreModel clothDetailStoreModel;

        if (cursor.moveToFirst()) {
            do {
                clothDetailStoreModel = new ClothDetailStoreModel();
                clothDetailStoreModel.setId(cursor.getString(0));
                clothDetailStoreModel.setImg(cursor.getString(1));
                clothDetailStoreModel.setName(cursor.getString(2));
                clothDetailStoreModel.setPrice(cursor.getDouble(3));
                clothDetailStoreModel.setSize(cursor.getString(4));
                clothDetailStoreModel.setQuantity(cursor.getInt(5));
                clothDetailStoreModelList.add(clothDetailStoreModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }
}
