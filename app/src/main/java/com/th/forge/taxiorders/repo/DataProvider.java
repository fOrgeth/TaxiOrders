package com.th.forge.taxiorders.repo;

import android.os.AsyncTask;
import android.util.Log;

import com.th.forge.taxiorders.OrdersRVAdapter;
import com.th.forge.taxiorders.api.RetroClient;
import com.th.forge.taxiorders.api.RoxieApiService;
import com.th.forge.taxiorders.entity.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataProvider {
    private static RoxieApiService api;
    private final static String MASK = "yyyy-MM-dd'T'HH:mm:ssZ";

    public DataProvider() {
        api = RetroClient.getApiService();
    }

    private static List<Order> fetchOrders() {
        ArrayList<Order> ordersList = new ArrayList<>();
        Call<ArrayList<Order>> call = api.getOrders();
        call.enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        ordersList.addAll(response.body());
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {
            }
        });
        return ordersList;
    }

    public static List<Order> getSortedOrders(List<Order> orders) {
        List<Order> sortedOrders = new ArrayList<>();
        sortedOrders.addAll(orders);
        for (int i = 0; i < sortedOrders.size(); i++) {
            for (int j = 0; j < sortedOrders.size()-i-1; j++) {
                if (parseDateInLong(sortedOrders.get(j).getOrderTime()) > parseDateInLong(sortedOrders.get(j+1).getOrderTime())) {
                    Order tmp = sortedOrders.get(j);
                    sortedOrders.set(j,sortedOrders.get(j+1));
                    sortedOrders.set(j+1,tmp);
//                    sortedOrders.add(orders.get(j));
                }
            }
        }
        for(int i=0; i<sortedOrders.size();i++){
            Log.d("@@@@@@@@@@",sortedOrders.get(i).getOrderTime());
        }
        /*Collections.sort(sortedOrders, new Comparator<Order>() {
            @Override
            public int compare(Order order, Order t1) {
                return order.getParsedDateToLong(MASK)
            }
        });*/
        return sortedOrders;
    }


    private static long parseDateInLong(String date) {
        SimpleDateFormat f = new SimpleDateFormat(MASK);
        long milliseconds = 0L;
        try {
            Date d = f.parse(date);
            milliseconds = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }

}
