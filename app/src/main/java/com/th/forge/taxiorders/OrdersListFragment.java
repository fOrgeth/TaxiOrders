package com.th.forge.taxiorders;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.th.forge.taxiorders.entity.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersListFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Order> ordersList;

    public static OrdersListFragment newInstance() {
        return new OrdersListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_list, container, false);
        recyclerView = view.findViewById(R.id.recycler_orders);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        ordersList = new ArrayList<>();
        App.getApiService().getOrders().enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ordersList.addAll(response.body());
                        OrdersRVAdapter adapter = new OrdersRVAdapter(getSortedOrders(ordersList));
                        recyclerView.setAdapter(adapter);
                        Toast.makeText(getActivity(), "WTF " + ordersList.get(1).getOrderTime(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "WAWEDAD", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                Toast.makeText(getActivity(), "SHIT"+" "+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    private List<Order> getSortedOrders(List<Order> orders) {
        List<Order> sortedOrders = new ArrayList<>(orders);
        boolean swapped;
        for (int i = 0; i < sortedOrders.size(); i++) {
            swapped = false;
            for (int j = 0; j < sortedOrders.size() - i - 1; j++) {
                if (sortedOrders.get(j).getOrderTime().getTime() < sortedOrders.get(j + 1).getOrderTime().getTime()) {
                    Order tmp = sortedOrders.get(j);
                    sortedOrders.set(j, sortedOrders.get(j + 1));
                    sortedOrders.set(j + 1, tmp);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
        return sortedOrders;
    }

    private static class OrdersRVAdapter extends RecyclerView.Adapter<OrdersRVAdapter.OrdersViewHolder> {

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
                orderDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(order.getOrderTime().getTime()));
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
}
