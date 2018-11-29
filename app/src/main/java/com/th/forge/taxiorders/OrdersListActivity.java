package com.th.forge.taxiorders;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OrdersListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return OrderDetailFragment.newInstance();
    }
}
