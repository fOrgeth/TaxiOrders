package com.th.forge.taxiorders.temp;

import com.th.forge.taxiorders.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SampleOrders {

    private  List<Order> sampleOrders;

    public SampleOrders() {
        Random random=new Random();
        this.sampleOrders = new ArrayList<>();
        for(int i=0; i<10;i++){
            Order order = new Order();
            order.setId(i+1);
            order.setStartAddress("Start Address "+i+1);
            order.setEndAddress("End Address "+i+1);
            order.setPriceAmount((i+1)*random.nextInt(1000));
            order.setOrderTime("201"+random.nextInt(8)+"-08-27T16:36:56+03:00");
            order.setPriceCurrency("RUB");
            sampleOrders.add(order);
        }
    }

    public List<Order> getSampleOrders() {
        return sampleOrders;
    }
}
