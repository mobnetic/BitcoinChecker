package com.aneonex.bitcoinchecker.datamodule.model

import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap

abstract class FuturesMarket(name: String, ttsName: String, currencyPairs: CurrencyPairsMap?, val contractTypes: IntArray)
    : Market(name, ttsName, currencyPairs) {

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return getUrl(requestId, checkerInfo, checkerInfo.contractType)
    }

    protected abstract fun getUrl(requestId: Int, checkerInfo: CheckerInfo, contractType: Int): String
}