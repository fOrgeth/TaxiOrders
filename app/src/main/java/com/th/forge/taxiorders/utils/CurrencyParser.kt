package com.th.forge.taxiorders.utils


import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Currency

object CurrencyParser {
    fun getFormattedPrice(amount: Int?, currency: String, symbol: String): String {
        val cur = Currency.getInstance(currency)
        val formatter = NumberFormat.getInstance()
        val bd = BigDecimal(amount!! / 100.0)
        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 2
        formatter.currency = cur
        return String.format("%s %s", formatter.format(bd), symbol)
    }
}
