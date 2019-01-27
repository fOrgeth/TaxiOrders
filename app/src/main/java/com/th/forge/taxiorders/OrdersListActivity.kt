package com.th.forge.taxiorders

import android.support.v4.app.Fragment

class OrdersListActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        return OrdersListFragment.newInstance()
    }
}
