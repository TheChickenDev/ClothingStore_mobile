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

import models.ClothInfomationModel;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    List<ClothInfomationModel> clothInfomationModelList;

    public CartAdapter(Context context, List<ClothInfomationModel> foodDetailStoreList) {
        this.context = context;
        this.clothInfomationModelList = foodDetailStoreList;
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        ClothInfomationModel clothInfomationModel = clothInfomationModelList.get(position);
        holder.clothName.setText(clothInfomationModel.getName());
        holder.clothPrice.setText(String.format("$%s", clothInfomationModel.getPrice()));
        holder.clothSize.setText(String.format("$%s", clothInfomationModel.getSize()));
        holder.clothQuantity.setText(String.valueOf(clothInfomationModel.getQuantity()));
        Glide.with(context).load(clothInfomationModel.getImg()).into(holder.clothImage);
    }

    @Override
    public int getItemCount() {
        return clothInfomationModelList.isEmpty()? 0 : clothInfomationModelList.size();
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

//    private void updateClothQuantityInDatabase(ClothInfomationModel cloth) {
//        SQLiteDatabase db = new DatabaseHelper(context).getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("quantity", cloth.getQuantity());
//        db.update("Clothing Store", values, "_id = ?", new String[]{cloth.getId()});
//        db.close();
//    }
}
