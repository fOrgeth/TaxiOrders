package com.th.forge.taxiorders.repo;

import android.os.AsyncTask;

import com.th.forge.taxiorders.OrdersRVAdapter;
import com.th.forge.taxiorders.api.RetroClient;
import com.th.forge.taxiorders.api.RoxieApiService;
import com.th.forge.taxiorders.entity.Order;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataProvider {

    public DataProvider() {
        RoxieApiService api = RetroClient.getApiService();
        ArrayList<Order> ordersList = new ArrayList<>();
        Call<ArrayList<Order>> call = api.getOrders();
        call.enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                if (response.isSuccessful()) {
                    ordersList.addAll(response.body());
                    OrdersRVAdapter adapter = new OrdersRVAdapter(ordersList);


                } else {

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {

            }
        });
    }

    private class GetOrdersTask extends AsyncTask<Void, Void, List<Order>>{
        private String query;

        public GetOrdersTask(String query) {
            this.query = query;
        }

        @Override
        protected List<Order> doInBackground(Void... voids) {
            return null;
        }
    }
}
