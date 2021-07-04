package com.aneonex.bitcoinchecker.datamodule.util

import com.aneonex.bitcoinchecker.datamodule.config.MarketsConfig
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.market.UnknownMarket

object MarketsConfigUtils {
    private val UNKNOWN_MARKET: Market = UnknownMarket()

    @Suppress("unused")
    val defaultMarket: Market = MarketsConfig.MARKETS.values.first()

    fun getMarketByKey(key: String?): Market {
        synchronized(MarketsConfig.MARKETS) {
            return MarketsConfig.MARKETS.getOrDefault(key, null) ?: UNKNOWN_MARKET
        }
    }
/*
    fun getMarketIdByKey(key: String): Int {
        for ((i, market) in MarketsConfig.MARKETS.values.withIndex()) {
            if (market.key == key) return i
        }
        return 0
    }
 */
}