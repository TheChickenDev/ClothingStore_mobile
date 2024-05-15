package adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.clothingstore.R;

import java.util.List;

import models.ClothDetailStoreModel;
import utils.DatabaseHelper;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    List<ClothDetailStoreModel> clothDetailStoreModelList;

    public CartAdapter(Context context, List<ClothDetailStoreModel> foodDetailStoreList) {
        this.context = context;
        this.clothDetailStoreModelList = foodDetailStoreList;
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        ClothDetailStoreModel clothing = clothDetailStoreModelList.get(position);
        holder.clothName.setText(clothing.getName());
        holder.clothPrice.setText(String.format("$%s", clothing.getPrice()));
        holder.clothSize.setText(String.format("$%s", clothing.getSize()));
        holder.clothQuantity.setText(String.valueOf(clothing.getQuantity()));
        Glide.with(context).load(clothing.getImg()).into(holder.clothImage);
    }

    @Override
    public int getItemCount() {
        return clothDetailStoreModelList.isEmpty()? 0 : clothDetailStoreModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView clothImage;
        TextView clothName, clothPrice, clothQuantity, clothSize;
        Button btnDelete, btnPayment;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            clothImage = itemView.findViewById(R.id.cart_item_image);
            clothName = itemView.findViewById(R.id.cart_item_name);
            clothPrice = itemView.findViewById(R.id.cart_item_price);
            clothSize = itemView.findViewById(R.id.cart_item_size);
            clothQuantity = itemView.findViewById(R.id.cart_item_quantity);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            btnPayment = itemView.findViewById(R.id.btn_payment);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            btnPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }

    private void updateFoodQuantityInDatabase(ClothDetailStoreModel food) {
        SQLiteDatabase db = new DatabaseHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", food.getQuantity());
        db.update("FoodStore", values, "id = ?", new String[]{food.getId()});
        db.close();
    }
}
