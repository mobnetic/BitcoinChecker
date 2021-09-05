package com.aneonex.bitcoinchecker.datamodule.model

class CurrencyPairsListWithDate(
    var date: Long = 0,
    var pairs: List<CurrencyPairInfo>? = null
)