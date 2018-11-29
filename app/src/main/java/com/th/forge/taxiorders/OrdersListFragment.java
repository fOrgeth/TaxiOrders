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

import com.th.forge.taxiorders.temp.SampleOrders;

import java.util.List;

public class OrdersListFragment extends Fragment {

    private RecyclerView recyclerViewOrders;

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
        recyclerViewOrders = view.findViewById(R.id.recycler_orders);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewOrders.setLayoutManager(layoutManager);
        SampleOrders sampleOrders = new SampleOrders();
        OrdersRVAdapter adapter = new OrdersRVAdapter(sampleOrders.getSampleOrders());
        recyclerViewOrders.setAdapter(adapter);
        return view;
    }
}
