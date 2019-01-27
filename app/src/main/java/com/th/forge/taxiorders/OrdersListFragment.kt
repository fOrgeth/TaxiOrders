package com.th.forge.taxiorders

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.th.forge.taxiorders.entity.Order
import com.th.forge.taxiorders.utils.CurrencyParser
import com.th.forge.taxiorders.utils.DateTimeParser

import java.io.Serializable
import java.util.ArrayList
import java.util.Collections

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrdersListFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var ordersList: MutableList<Order>? = null

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(SAVE_LIST_STATE, ordersList as Serializable?)
        Log.d(LOG_TAG, "onSavedInstance")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_orders_list, container, false)
        init(view)
        if (savedInstanceState != null) {
            ordersList = savedInstanceState.getSerializable(SAVE_LIST_STATE) as MutableList<Order>
            updateAdapter()
            Log.d(LOG_TAG, "onCreate, saved!=null")
        } else {
            loadList()
        }
        return view
    }

    private fun init(view: View) {
        ordersList = ArrayList()
        symbol = resources.getString(R.string.rubleSymbol)
        recyclerView = view.findViewById(R.id.recycler_orders)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = layoutManager
    }

    private fun loadList() {
        Log.d(LOG_TAG, "LOAD LIST EXECUTED")
        App.apiService!!.orders.enqueue(object : Callback<ArrayList<Order>> {
            override fun onResponse(call: Call<ArrayList<Order>>, response: Response<ArrayList<Order>>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        if (ordersList != null) {
                            ordersList!!.addAll(response.body()!!)
                            updateAdapter()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Order>>, t: Throwable) {}
        })
    }

    private fun updateAdapter() {
        val adapter = OrdersRVAdapter(getSortedOrders(ordersList!!))
        recyclerView!!.adapter = adapter
    }

    private fun getSortedOrders(orders: List<Order>): List<Order> {
        val sortedOrders = ArrayList(orders)
        Collections.sort(sortedOrders) { o1, o2 -> o2.orderTime!!.compareTo(o1.orderTime) }
        return sortedOrders
    }

    private class OrdersRVAdapter(private val orders: List<Order>?) : RecyclerView.Adapter<OrdersRVAdapter.OrdersViewHolder>() {

        class OrdersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

            private var order: Order? = null
            private val startAddress: TextView
            private val endAddress: TextView
            private val orderDate: TextView
            private val orderPrice: TextView

            init {
                startAddress = itemView.findViewById(R.id.start_address)
                endAddress = itemView.findViewById(R.id.end_address)
                orderPrice = itemView.findViewById(R.id.order_price)
                orderDate = itemView.findViewById(R.id.order_date)
                itemView.setOnClickListener(this)
            }

            override fun onClick(view: View) {
                val intent = OrderDetailActivity.newIntent(this.itemView.context, order!!)
                this.itemView.context.startActivity(intent)
            }

            fun bindOrderItem(order: Order) {
                this.order = order
                startAddress.text = order.startAddress!!.address
                endAddress.text = order.endAddress!!.address
                orderPrice.text = CurrencyParser
                        .getFormattedPrice(order.price!!.amount,
                                order.price!!.currency!!, symbol!!)
                orderDate.text = DateTimeParser.getReadableString("dd-MM-yyyy",
                        order.orderTime!!.time)
            }
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): OrdersViewHolder {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.order_item, viewGroup, false)
            val ovh = OrdersViewHolder(view)
            return ovh
        }

        override fun onBindViewHolder(ordersViewHolder: OrdersViewHolder, i: Int) {
            ordersViewHolder.bindOrderItem(orders!![i])
        }

        override fun getItemCount(): Int {
            return orders?.size ?: 0
        }
    }

    companion object {
        private val SAVE_LIST_STATE = "list_orders"
        private val LOG_TAG = "ORDERSLISTFRGMNT"
        private var symbol: String? = null

        fun newInstance(): OrdersListFragment {
            return OrdersListFragment()
        }
    }
}
