package com.th.forge.taxiorders.temp;

import com.th.forge.taxiorders.entity.EndAddress;
import com.th.forge.taxiorders.entity.Order;
import com.th.forge.taxiorders.entity.Price;
import com.th.forge.taxiorders.entity.StartAddress;

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
            StartAddress startAddress = new StartAddress();
            startAddress.setAddress("Start Address "+i+1);
            order.setStartAddress(startAddress);
            EndAddress endAddress=new EndAddress();
            endAddress.setAddress("End Address "+i+1);
            order.setEndAddress(endAddress);
            Price price = new Price();
            price.setAmount((i+1)*random.nextInt(1000));
            price.setCurrency("RUB");
            order.setPrice(price);
//            order.setOrderTime("201"+random.nextInt(8)+"-08-27T16:36:56+03:00");
            sampleOrders.add(order);
        }
    }

    public List<Order> getSampleOrders() {
        return sampleOrders;
    }
}
