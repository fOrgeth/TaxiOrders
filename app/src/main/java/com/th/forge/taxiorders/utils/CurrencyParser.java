package com.th.forge.taxiorders.utils;


import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;

public class CurrencyParser {
    public static String getFormattedPrice(Integer amount, String currency, String symbol){
        Currency cur = Currency.getInstance(currency);
        NumberFormat formatter = NumberFormat.getInstance();
        BigDecimal bd = new BigDecimal(amount / 100d);
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        formatter.setCurrency(cur);
        return String.format("%s %s",formatter.format(bd),symbol);
    }
}
