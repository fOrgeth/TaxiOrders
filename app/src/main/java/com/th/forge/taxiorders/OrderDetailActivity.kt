package com.th.forge.taxiorders

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar

import com.th.forge.taxiorders.entity.Order

class OrderDetailActivity : SingleFragmentActivity(), OrderDetailFragment.OnFragmentInteractionListener {

    override fun createFragment(): Fragment {
        return OrderDetailFragment.newInstance(intent.getSerializableExtra(ORDER_KEY))
    }

    override fun onFragmentInteraction(title: String, subtitle: String) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = title
            actionBar.subtitle = subtitle
        }
    }

    companion object {

        private val ORDER_KEY = "order_key"

        fun newIntent(context: Context, order: Order): Intent {
            val i = Intent(context, OrderDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable(ORDER_KEY, order)
            i.putExtras(bundle)
            return i
        }
    }
}
