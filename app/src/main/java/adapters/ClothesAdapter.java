package adapters;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.clothingstore.ProductCardActivity;
import com.example.clothingstore.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import models.ProductModel;

public class ClothesAdapter extends RecyclerView.Adapter<ClothesAdapter.MyViewHolder> {
    Context context;
    List<ProductModel> array;
    public ClothesAdapter(Context context, List<ProductModel> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clothes, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductModel clothes = array.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProductCardActivity.class);
                intent.putExtra("productId", clothes.getId());
                v.getContext().startActivity(intent);
            }
        });
        holder.nameSP.setText(clothes.getName());
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY);
        String formattedPrice = numberFormat.format(Integer.parseInt(clothes.getPrice())) + "vnd";
        holder.priceSP.setText(formattedPrice);
        Glide.with(context)
                .load(clothes.getImg())
                .into(holder.images);
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView images;
        public TextView nameSP, priceSP;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            images = (ImageView) itemView.findViewById(R.id.item_image);
            nameSP = (TextView) itemView.findViewById(R.id.item_name);
            nameSP.setEllipsize(TextUtils.TruncateAt.END);
            nameSP.setMaxLines(1);
            priceSP = (TextView) itemView.findViewById(R.id.item_price);
        }
    }
}