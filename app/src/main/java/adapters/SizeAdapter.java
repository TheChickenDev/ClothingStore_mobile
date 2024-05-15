package adapters;

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
        holder.bind(size, listener);
    }

    @Override
    public int getItemCount() {
        return sizes.length;
    }

    static class SizeViewHolder extends RecyclerView.ViewHolder {
        private Button btnSize;

        public SizeViewHolder(@NonNull View itemView) {
            super(itemView);
            btnSize = itemView.findViewById(R.id.btn_Size);
        }

        public void bind(final String size, final OnItemClickListener listener) {
            btnSize.setText(size);
            btnSize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(size);
                    }
                }
            });
        }
    }
}
