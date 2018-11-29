package com.th.forge.taxiorders;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class OrdersRVAdapter extends RecyclerView.Adapter<OrdersRVAdapter.OrdersViewHolder> {

    private List<Order> orders;

    public OrdersRVAdapter(List<Order> orders) {
        this.orders = orders;
    }

    public static class OrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Order orderItem;
        CardView cardView;
        TextView startAddress;
        TextView endAddress;
        TextView orderDate;
        TextView orderPrice;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view_order);
            startAddress = itemView.findViewById(R.id.start_address);
            endAddress = itemView.findViewById(R.id.end_address);
            orderPrice = itemView.findViewById(R.id.order_price);
            orderDate = itemView.findViewById(R.id.order_date);

        }

        @Override
        public void onClick(View view) {

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
        ordersViewHolder.startAddress.setText(orders.get(i).getStartAddress());
        ordersViewHolder.endAddress.setText(orders.get(i).getEndAddress());
        ordersViewHolder.orderDate.setText(orders.get(i).getOrderTime());
        ordersViewHolder.orderPrice.setText(String.valueOf(orders.get(i).getPriceAmount()));

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
