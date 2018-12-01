package com.th.forge.taxiorders;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.th.forge.taxiorders.Entity.Order;
import com.th.forge.taxiorders.api.RetroClient;
import com.th.forge.taxiorders.api.RoxieApiService;
import com.th.forge.taxiorders.temp.SampleOrders;

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
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_list, container, false);
        recyclerView = view.findViewById(R.id.recycler_orders);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        SampleOrders sampleOrders = new SampleOrders();
        RoxieApiService api = RetroClient.getApiService();
        ordersList = new ArrayList<>();
        Call<ArrayList<Order>> call = api.getJSON();
        call.enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                if (response.isSuccessful()) {
                    ordersList.addAll(response.body());
                    OrdersRVAdapter adapter = new OrdersRVAdapter(ordersList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.getAdapter().notifyDataSetChanged();
                    Toast.makeText(getActivity(), "WTF " + ordersList.get(1).getOrderTime(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "WAWEDAD", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
                Toast.makeText(getActivity(), "SHIT", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
