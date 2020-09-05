package com.aneonex.bitcoinchecker.datamodule.model

import java.util.*

class CheckerInfo(currencyBase: String, currencyCounter: String, currencyPairId: String?, val contractType: Int)
    : CurrencyPairInfo(currencyBase, currencyCounter, currencyPairId) {
    val currencyBaseLowerCase: String
        get() = currencyBase.toLowerCase(Locale.US)
    val currencyCounterLowerCase: String
        get() = currencyCounter.toLowerCase(Locale.US)
}