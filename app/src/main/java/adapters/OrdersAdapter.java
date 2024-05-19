package adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothingstore.OrderDetailActivity;
import com.example.clothingstore.R;

import java.io.Serializable;
import java.util.List;
import models.OrderModel;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {
    private List<OrderModel> orders;
    private Context context;

    public OrdersAdapter(Context context, List<OrderModel> orders) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, null);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        OrderModel order = orders.get(position);
        holder.tv_orderId.setText(order.getId());
        holder.tv_orderDate.setText(order.getOrderDate());
        holder.tv_deliveryDate.setText(order.getDeliveryDate());
        holder.tv_status.setText(order.isCompleted() ? "Completed" : "Uncompleted");
        holder.btn_details.setOnClickListener(v -> {
            Intent intent = new Intent(this.context, OrderDetailActivity.class);
            intent.putExtra("id", order.getId());
            intent.putExtra("products", (Serializable) order.getProducts());
            intent.putExtra("orderDate", order.getOrderDate());
            intent.putExtra("deliveryDate", order.getDeliveryDate());
            intent.putExtra("status", order.isCompleted());
            intent.putExtra("price", order.getPrice());
            intent.putExtra("shippingFee", order.getShippingFee());
            intent.putExtra("total", order.getTotalAmount());
            this.context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_orderId, tv_orderDate, tv_deliveryDate, tv_status;
        Button btn_details;

        public OrderViewHolder(View view) {
            super(view);
            tv_orderId = view.findViewById(R.id.order_item_id);
            tv_orderDate = view.findViewById(R.id.order_item_order_date);
            tv_deliveryDate = view.findViewById(R.id.order_item_delivery_date);
            tv_status = view.findViewById(R.id.order_item_status);
            btn_details = view.findViewById(R.id.order_item_btn_details);
        }
    }
}