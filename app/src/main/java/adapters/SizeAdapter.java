package adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothingstore.R;

import java.util.ArrayList;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.SizeViewHolder> {

    private String[] sizes;
    private OnItemClickListener listener;
    private static int lastSelectedItemPosition = -1;
    private static String lastSelectedItem = null;

    public interface OnItemClickListener {
        void onItemClick(String size);
    }

    public SizeAdapter(ArrayList<String> sizes, OnItemClickListener listener) {
        this.sizes = sizes.toArray(new String[0]);
        this.listener = listener;
    }

    @NonNull
    @Override
    public SizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_thumbnail_item, parent, false);
        return new SizeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SizeViewHolder holder, int position) {
        String size = sizes[position];
        holder.bind(size, listener, position);
    }

    @Override
    public int getItemCount() {
        return sizes.length;
    }

    class SizeViewHolder extends RecyclerView.ViewHolder {
        private Button btnSize;

        public SizeViewHolder(@NonNull View itemView) {
            super(itemView);
            btnSize = itemView.findViewById(R.id.btn_List);
        }

        public void bind(final String size, final OnItemClickListener listener, int position) {
            btnSize.setText(size);
            if (position == lastSelectedItemPosition) {
                btnSize.setBackgroundColor(Color.parseColor("#D3D3D3")); // Đặt màu nền cho button được chọn
            } else {
                btnSize.setBackgroundColor(Color.parseColor("#FFFFFF")); // Đặt màu nền mặc định cho các button khác
            }
            btnSize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedItem = size;
                    lastSelectedItemPosition = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(size);
                    }
                    notifyDataSetChanged(); // Cập nhật lại giao diện sau khi thay đổi vị trí được chọn
                }
            });
        }
    }
}

