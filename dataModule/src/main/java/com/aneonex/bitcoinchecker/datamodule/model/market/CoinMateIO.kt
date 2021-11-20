package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONObject

class CoinMateIO : SimpleMarket(
    "CoinMate.io",
    "https://coinmate.io/api/tradingPairs",
    "https://coinmate.io/api/ticker?currencyPair=%1\$s",
    "Coin Mate"
) {
    override fun getPairId(checkerInfo: CheckerInfo): String =
        checkerInfo.currencyPairId ?: "${checkerInfo.currencyBase}_${checkerInfo.currencyCounter}"

    override fun parseCurrencyPairsFromJsonObject(
        requestId: Int,
        jsonObject: JSONObject,
        pairs: MutableList<CurrencyPairInfo>
    ) {
        jsonObject.getJSONArray("data").forEachJSONObject { market ->
            pairs.add(CurrencyPairInfo(
                market.getString("firstCurrency"),
                market.getString("secondCurrency"),
                market.getString("name")
            ))
        }
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.getJSONObject("data").also {
            ticker.bid = it.getDouble("bid")
            ticker.ask = it.getDouble("ask")

            ticker.high = it.getDouble("high")
            ticker.low = it.getDouble("low")

            ticker.vol = it.getDouble("amount")
            ticker.last = it.getDouble("last")

            ticker.timestamp = it.getLong("timestamp")
        }
    }

    @Throws(Exception::class)
    override fun parseErrorFromJsonObject(requestId: Int, jsonObject: JSONObject, checkerInfo: CheckerInfo?): String? {
        return jsonObject.getString("errorMessage")
    }
}