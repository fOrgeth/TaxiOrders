package com.th.forge.taxiorders;

import android.support.v4.app.Fragment;

public class OrderDetailActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return OrderDetailFragment.newInstance();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
