package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONObject

class AscendEX : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "AscendEX (Bitmax)"
        private const val TTS_NAME = "AscendEx"
        private const val URL = "https://ascendex.com/api/pro/v1/ticker?symbol=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://ascendex.com/api/pro/v1/products"
    }

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        jsonObject.getJSONArray("data").forEachJSONObject { market ->
            if(market.getString("status") == "Normal") {

                val symbol = market.getString("symbol")
                val baseAsset = if(symbol.endsWith("-PERP")) symbol else market.getString("baseAsset")

                pairs.add(CurrencyPairInfo(
                    baseAsset,
                    market.getString("quoteAsset"),
                    symbol
                ))
            }
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.getJSONObject("data").let {
            ticker.high = it.getDouble("high")
            ticker.low = it.getDouble("low")
            ticker.last = it.getDouble("close")
            ticker.vol = it.getDouble("volume")

            it.getJSONArray("bid").let { bids ->
                if(bids.length() == 2) ticker.bid = bids.getDouble(0)
            }
            it.getJSONArray("ask").let { asks ->
                if(asks.length() == 2) ticker.ask = asks.getDouble(0)
            }
        }
    }
}