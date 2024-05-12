package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import models.ClothesModel;
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

    }

//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        Clothes cate = clothesList.get(position);
//        holder.txtCategoryName.setText(cate.getName());
//        holder.txtCategoryDescription.setText(cate.getPrice());
//        Glide.with(context)
//                .load(cate.getImg())
//                .into(holder.imgCategory);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                if (position != RecyclerView.NO_POSITION) {
//                    Intent intent = new Intent(v.getContext(), FoodListActivity.class);
//                    intent.putExtra("CATEGORY_ID", cate.getId());
//                    intent.putExtra("CATEGORY_NAME", cate.getName());
//                    v.getContext().startActivity(intent);
//                }
//            }
//        });
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgCategory;
        public TextView txtCategoryName;
        public TextView txtCategoryDescription;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.cate_image);
            txtCategoryName = itemView.findViewById(R.id.cate_name);
            txtCategoryDescription = itemView.findViewById(R.id.cate_desc);
        }
    }

    @Override
    public int getItemCount() {
        return clothesList == null ? 0 : clothesList.size();
    }
}
