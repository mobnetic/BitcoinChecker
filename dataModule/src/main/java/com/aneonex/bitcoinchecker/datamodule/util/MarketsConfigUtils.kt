package com.aneonex.bitcoinchecker.datamodule.util

import com.aneonex.bitcoinchecker.datamodule.config.MarketsConfig
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.market.Unknown
import java.util.*

object MarketsConfigUtils {
    private val UNKNOWN: Market = Unknown()

    fun getMarketById(id: Int): Market {
        synchronized(MarketsConfig.MARKETS) {
            if (id >= 0 && id < MarketsConfig.MARKETS.size) {
                return ArrayList(MarketsConfig.MARKETS.values)[id]
            }
        }
        return UNKNOWN
    }

    @kotlin.jvm.JvmStatic
    fun getMarketByKey(key: String?): Market {
        synchronized(MarketsConfig.MARKETS) { if (MarketsConfig.MARKETS.containsKey(key)) return MarketsConfig.MARKETS[key]!! }
        return UNKNOWN
    }

    fun getMarketIdByKey(key: String): Int {
        for ((i, market) in MarketsConfig.MARKETS.values.withIndex()) {
            if (market.key == key) return i
        }
        return 0
    }
}