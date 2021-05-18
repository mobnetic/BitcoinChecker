package com.aneonex.bitcoinchecker.datamodule.model

import java.util.*

class CheckerInfo(currencyBase: String, currencyCounter: String, currencyPairId: String?, val contractType: Int)
    : CurrencyPairInfo(currencyBase, currencyCounter, currencyPairId) {
    val currencyBaseLowerCase: String
        get() = currencyBase.lowercase(Locale.US)
    val currencyCounterLowerCase: String
        get() = currencyCounter.lowercase(Locale.US)
}