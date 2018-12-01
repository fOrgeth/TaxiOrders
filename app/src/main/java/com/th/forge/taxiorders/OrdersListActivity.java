package com.th.forge.taxiorders;

import android.support.v4.app.Fragment;

public class OrdersListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return OrdersListFragment.newInstance();
    }
}
