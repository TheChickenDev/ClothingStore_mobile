package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.example.clothingstore.ProductCardActivity;
import com.example.clothingstore.ProfileActivity;
import com.example.clothingstore.R;


import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private String[] thumbnails;
    private OnItemClickListener listener;
    private static String imageSelectedItem = null;


    public interface OnItemClickListener {
        void onItemClick(String thumbnail);
    }

    public ImageAdapter(ArrayList<String> thumbnails, OnItemClickListener listener) {
        this.thumbnails = thumbnails.toArray(new String[0]);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_imageview_button, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String thumbnail = thumbnails[position];
        holder.bind(thumbnail, listener);
    }

    @Override
    public int getItemCount() {
        return thumbnails.length;
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgButton;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgButton = itemView.findViewById(R.id.btn_view_list);
        }

        public void bind(final String thumbnail, final OnItemClickListener listener) {
            Glide.with(itemView.getContext()).load(thumbnail).into(imgButton);
            imgButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageSelectedItem =thumbnail;
                    if (listener != null) {
                        listener.onItemClick(thumbnail);
                    }
                }
            });
        }
    }
}

