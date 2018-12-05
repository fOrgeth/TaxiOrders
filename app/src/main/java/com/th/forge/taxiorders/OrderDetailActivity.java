
package com.th.forge.taxiorders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.th.forge.taxiorders.entity.Order;

public class OrderDetailActivity extends SingleFragmentActivity implements OrderDetailFragment.OnFragmentInteractionListener {

    private static final String ORDER_KEY = "order_key";

    public static Intent newIntent(Context context, Order order) {
        Intent i = new Intent(context, OrderDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ORDER_KEY, order);
        i.putExtras(bundle);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        return OrderDetailFragment.newInstance(getIntent().getSerializableExtra(ORDER_KEY));
    }

    @Override
    public void onFragmentInteraction(String title, String subtitle) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setSubtitle(subtitle);
        }
    }
}
