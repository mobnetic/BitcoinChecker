package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject

class CryptoAltex : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return URL
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val pairObject = jsonObject.getJSONObject(checkerInfo.currencyPairId)
        ticker.vol = pairObject.getDouble("24_hours_volume")
        ticker.high = pairObject.getDouble("24_hours_price_high")
        ticker.low = pairObject.getDouble("24_hours_price_low")
        ticker.last = pairObject.getDouble("last_trade")
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val pairsNamesArray = jsonObject.names()
        for (i in 0 until pairsNamesArray.length()) {
            val pairName = pairsNamesArray.getString(i)
            val split = pairName.split("/".toRegex()).toTypedArray()
            if (split != null && split.size >= 2) {
                pairs.add(CurrencyPairInfo(split[0], split[1], pairName))
            }
        }
    }

    companion object {
        private const val NAME = "CryptoAltex"
        private const val TTS_NAME = "Crypto Altex"
        private const val URL = "https://www.cryptoaltex.com/api/public_v2.php"
    }
}