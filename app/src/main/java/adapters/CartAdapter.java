package adapters;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.clothingstore.CartActivity;
import com.example.clothingstore.OrderDetailActivity;
import com.example.clothingstore.PaymentActivity;
import com.example.clothingstore.ProductCardActivity;
import com.example.clothingstore.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import apis.APIService;
import classes.PreferencesManager;
import models.ClothCartModel;
import models.SuccessResponseModel;
import models.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RetrofitClient;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    List<ClothCartModel> array;
    PreferencesManager preferencesManager;
    APIService apiService;
    public CartAdapter(Context context, List<ClothCartModel> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        preferencesManager = new PreferencesManager(parent.getContext());
        return myViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ClothCartModel clothes = array.get(position);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ProductCardActivity.class);
            intent.putExtra("productId", clothes.getId());
            v.getContext().startActivity(intent);
        });
        holder.btn_delete.setOnClickListener(v -> {
            String userId = preferencesManager.getId();
            String productId = clothes.getId();
            String productSize = clothes.getSize();
            apiService = RetrofitClient.getRetrofit(context).create(APIService.class);
            apiService.removeFromCart(userId, productId, productSize).enqueue(new Callback<SuccessResponseModel<UserModel>>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(@NonNull Call<SuccessResponseModel<UserModel>> call, @NonNull Response<SuccessResponseModel<UserModel>> response) {
                    if (response.isSuccessful()) {
                        SuccessResponseModel<UserModel> successResponse = response.body();
                        if (successResponse != null) {
                            Toast.makeText(context, "Remove success!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, CartActivity.class);
                            context.startActivity(intent);
                        }
                    } else {
                        int statusCode = response.code();
                        Toast.makeText(context, "Error! Status code: " + statusCode, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SuccessResponseModel<UserModel>> call, @NonNull Throwable t) {
                    Toast.makeText(context, "Remove failed!", Toast.LENGTH_SHORT).show();
                }
            });
        });
        if (context instanceof OrderDetailActivity || context instanceof PaymentActivity) {
            holder.btn_delete.setVisibility(View.GONE);
        }
        holder.nameSP.setText(clothes.getName());
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY);
        String formattedPrice = numberFormat.format(Integer.parseInt(clothes.getPrice())) + "vnd";
        holder.priceSP.setText(formattedPrice);
        holder.sizeSP.setText("Size: " + clothes.getSize());
        holder.quantitySP.setText("SL: " + clothes.getQuantity());
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
        public TextView nameSP, priceSP, sizeSP, quantitySP;
        ImageButton btn_delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.cart_item_image);
            nameSP = itemView.findViewById(R.id.cart_item_name);
            nameSP.setEllipsize(TextUtils.TruncateAt.END);
            nameSP.setMaxLines(1);
            priceSP = itemView.findViewById(R.id.cart_item_price);
            sizeSP = itemView.findViewById(R.id.cart_item_size);
            quantitySP = itemView.findViewById(R.id.cart_item_quantity);
            btn_delete = itemView.findViewById(R.id.cart_item_btn_delete);
        }
    }
}