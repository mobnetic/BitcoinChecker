package com.aneonex.bitcoinchecker.datamodule.model.market.generic

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market

abstract class SimpleMarket(
        name: String,
        private val pairsUrl: String,
        private val tickerUrl: String,
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