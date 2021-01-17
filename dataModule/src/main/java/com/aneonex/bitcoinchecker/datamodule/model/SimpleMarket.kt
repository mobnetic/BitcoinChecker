package com.aneonex.bitcoinchecker.datamodule.model

abstract class SimpleMarket(
        name: String,
        val pairsUrl: String,
        val tickerUrl: String,
        ttsName: String = name
    ): Market(name, ttsName, null) {

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return pairsUrl
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(tickerUrl, getPairId(checkerInfo))
    }

    open fun getPairId(checkerInfo: CheckerInfo): String? {
        return checkerInfo.currencyPairId
    }
}