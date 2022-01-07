package com.aneonex.bitcoinchecker.datamodule.model

import java.util.*

class CheckerInfo(currencyBase: String, currencyCounter: String, currencyPairId: String?, contractType: FuturesContractType)
    : CurrencyPairInfo(currencyBase, currencyCounter, currencyPairId, contractType) {
    val currencyBaseLowerCase: String
        get() = currencyBase.lowercase(Locale.US)
    val currencyCounterLowerCase: String
        get() = currencyCounter.lowercase(Locale.US)
}