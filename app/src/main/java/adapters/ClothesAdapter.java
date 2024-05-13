package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import models.ClothesModel;

import com.bumptech.glide.Glide;
import com.example.clothingstore.HomeActivity;
import com.example.clothingstore.R;

public class ClothesAdapter extends RecyclerView.Adapter<ClothesAdapter.MyViewHolder> {
    Context context;
    List<ClothesModel> clothesList;


    public ClothesAdapter(Context context, List<ClothesModel> clothesList) {
        this.context = context;
        this.clothesList = clothesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clothes, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ClothesModel cate = clothesList.get(position);
        holder.txtClothesName.setText(cate.getName());
        holder.txtClothesPrice.setText(cate.getPrice());
        Glide.with(context)
                .load(cate.getImg())
                .into(holder.imgClothes);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(v.getContext(), HomeActivity.class);
                    intent.putExtra("CATEGORY_ID", cate.getId());
                    intent.putExtra("CATEGORY_IMAGE", cate.getImg());
                    intent.putExtra("CATEGORY_NAME", cate.getName());
                    intent.putExtra("CATEGORY_PRICE", cate.getPrice());
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgClothes;
        public TextView txtClothesName;
        public TextView txtClothesPrice;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgClothes = itemView.findViewById(R.id.cate_image);
            txtClothesName = itemView.findViewById(R.id.cate_name);
            txtClothesPrice = itemView.findViewById(R.id.cate_price);
        }
    }

    @Override
    public int getItemCount() {
        return clothesList == null ? 0 : clothesList.size();
    }
}
