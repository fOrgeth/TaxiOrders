package com.th.forge.taxiorders;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.th.forge.taxiorders.entity.Order;

import java.util.List;

public class OrdersRVAdapter extends RecyclerView.Adapter<OrdersRVAdapter.OrdersViewHolder> {

    private List<Order> orders;

    public OrdersRVAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class OrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Order order;
        private TextView startAddress;
        private TextView endAddress;
        private TextView orderDate;
        private TextView orderPrice;


        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            startAddress = itemView.findViewById(R.id.start_address);
            endAddress = itemView.findViewById(R.id.end_address);
            orderPrice = itemView.findViewById(R.id.order_price);
            orderDate = itemView.findViewById(R.id.order_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = OrderDetailActivity.newIntent(this.itemView.getContext(), order);
            this.itemView.getContext().startActivity(intent);
        }

        public void bindOrderItem(Order order) {
            this.order = order;
            startAddress.setText(order.getStartAddress().getAddress());
            endAddress.setText(order.getEndAddress().getAddress());
            orderPrice.setText(String.valueOf(order.getPrice().getAmount()));
            orderDate.setText(order.getOrderTime());
        }

    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_item, viewGroup, false);
        OrdersViewHolder ovh = new OrdersViewHolder(view);
        return ovh;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder ordersViewHolder, int i) {
        ordersViewHolder.bindOrderItem(orders.get(i));
    }

    @Override
    public int getItemCount() {
        if(orders==null){
            return 0;
        }
        return orders.size();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }
}
