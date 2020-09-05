package com.aneonex.bitcoinchecker.datamodule.model

open class CurrencyPairInfo(val currencyBase: String, val currencyCounter: String, val currencyPairId: String?) : Comparable<CurrencyPairInfo> {
    @Throws(NullPointerException::class)
    override fun compareTo(other: CurrencyPairInfo): Int {
        val compBase = currencyBase.compareTo(other.currencyBase, ignoreCase = true)
        return if (compBase != 0) compBase else currencyCounter.compareTo(other.currencyCounter, ignoreCase = true)
    }
}