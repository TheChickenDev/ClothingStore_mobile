package adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    List<FoodDetailStore> foodDetailStoreList;

    public CartAdapter(Context context, List<FoodDetailStore> foodDetailStoreList) {
        this.context = context;
        this.foodDetailStoreList = foodDetailStoreList;
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_detail, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        FoodDetailStore food = foodDetailStoreList.get(position);
        holder.foodName.setText(food.getName());
        holder.foodPrice.setText(String.format("$%s", food.getPrice()));
        holder.foodQuantity.setText(String.valueOf(food.getQuantity()));
        holder.foodOneTotalPrice.setText(String.format("$%s", food.getPrice() * food.getQuantity()));
        Glide.with(context).load(food.getImageUrl()).into(holder.foodImage);
    }

    @Override
    public int getItemCount() {
        return foodDetailStoreList.isEmpty()? 0 : foodDetailStoreList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView foodName, foodPrice, foodQuantity, foodOneTotalPrice;
        ImageView btnMinus, btnPlus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.food_cart_image);
            foodName = itemView.findViewById(R.id.food_cart_detail_name);
            foodPrice = itemView.findViewById(R.id.food_cart_detail_price);
            foodQuantity = itemView.findViewById(R.id.food_cart_detail_quantity);
            foodOneTotalPrice = itemView.findViewById(R.id.food_one_total_price);
            btnMinus = itemView.findViewById(R.id.btn_cart_minus);
            btnPlus = itemView.findViewById(R.id.btn_cart_plus);

            btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    FoodDetailStore food = foodDetailStoreList.get(position);
                    int currentQuantity = food.getQuantity();
                    if (food.getQuantity() > 1) {
                        food.setQuantity(food.getQuantity() - 1);
                        updateFoodQuantityInDatabase(food);
                        foodOneTotalPrice.setText(String.format("$%s", food.getPrice() * food.getQuantity()));
                    }
                    notifyDataSetChanged();
                }
            });

            btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    FoodDetailStore food = foodDetailStoreList.get(position);
                    food.setQuantity(food.getQuantity() + 1);
                    updateFoodQuantityInDatabase(food);
                    foodOneTotalPrice.setText(String.format("$%s", food.getPrice() * food.getQuantity()));
                    notifyDataSetChanged();
                }
            });

        }
    }

    private void updateFoodQuantityInDatabase(FoodDetailStore food) {
        SQLiteDatabase db = new DatabaseHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", food.getQuantity());
        db.update("FoodStore", values, "id = ?", new String[]{food.getId()});
        db.close();
    }
}
