package com.th.forge.taxiorders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.th.forge.taxiorders.Entity.Order;

public class OrderDetailActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context, Order order) {
        Intent i = new Intent(context, OrderDetailActivity.class);

        return i;
    }

    @Override
    protected Fragment createFragment() {
        return OrderDetailFragment.newInstance();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
