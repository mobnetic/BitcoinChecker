package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import org.json.JSONObject
import java.util.*

class LiteBit : SimpleMarket(
        "LiteBit.eu",
        "https://api.litebit.eu/markets",
        "https://api.litebit.eu/market/%1\$s",
        "Lite Bit"
        ) {

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val data = jsonObject.getJSONObject("result")
        for (key in data.keys()) {
            val market = data.getJSONObject(key)

            pairs.add(
                CurrencyPairInfo(
                    market.getString("abbr").uppercase(Locale.ROOT),
                    Currency.EUR,
                    key
                ))
        }
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.getJSONObject("result").let {
            ticker.last = it.getDouble("buy") // last is not available. Using "buy" like at CoinMarketCap.com
            ticker.vol = it.getDouble("volume")

            ticker.bid = it.getDouble("sell")
            ticker.ask = it.getDouble("buy")
        }
    }
}