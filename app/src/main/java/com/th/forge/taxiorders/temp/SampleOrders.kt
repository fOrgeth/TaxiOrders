package com.th.forge.taxiorders.temp

import com.th.forge.taxiorders.entity.Address
import com.th.forge.taxiorders.entity.Order
import com.th.forge.taxiorders.entity.Price

import java.util.ArrayList
import java.util.Random

class SampleOrders {

    private val sampleOrders: MutableList<Order>

    init {
        val random = Random()
        this.sampleOrders = ArrayList()
        for (i in 0..9) {
            val order = Order()
            order.id = i + 1
            val startAddress = Address()
            startAddress.address = "Start Address " + i + 1
            order.startAddress = startAddress
            val address = Address()
            address.address = "End Address " + i + 1
            order.endAddress = address
            val price = Price()
            price.amount = (i + 1) * random.nextInt(1000)
            price.currency = "RUB"
            order.price = price
            //            order.setOrderTime("201"+random.nextInt(8)+"-08-27T16:36:56+03:00");
            sampleOrders.add(order)
        }
    }

    fun getSampleOrders(): List<Order> {
        return sampleOrders
    }
}
