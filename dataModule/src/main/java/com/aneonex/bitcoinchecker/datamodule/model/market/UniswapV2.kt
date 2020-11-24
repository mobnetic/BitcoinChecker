package com.aneonex.bitcoinchecker.datamodule.model.market

import android.util.Log
import com.aneonex.bitcoinchecker.datamodule.R
import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject
import java.time.ZonedDateTime
import java.util.*

class UniswapV2 : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Uniswap (v2)"
        private const val TTS_NAME = "Uniswap version 2"
        private const val URL = "https://api.coingecko.com/api/v3/exchanges/uniswap/tickers?coin_ids=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://api.coingecko.com/api/v3/exchanges/uniswap/tickers?page=%1\$s"
    }

    override val cautionResId: Int get() = R.string.market_caution_dex_coingecko

    override val currencyPairsNumOfRequests: Int get() = 15

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return String.format(URL_CURRENCY_PAIRS, requestId)
    }

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val markets = jsonObject.getJSONArray("tickers")
        for(i in 0 until markets.length()){
            val market = markets.getJSONObject(i)

            val trustScore = market.getString("trust_score")
            if(trustScore != "green" && trustScore != "yellow")
                continue

            pairs.add( CurrencyPairInfo(
                    market.getString("coin_id").toUpperCase(Locale.ROOT),
                    market.getString("target").toUpperCase(Locale.ROOT),
                    market.getString("coin_id")
            ))
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyPairId)
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val tickersJson = jsonObject.getJSONArray("tickers")
        val tickerJson = tickersJson.getJSONObject(0)

        if(tickerJson.getBoolean("is_anomaly"))
            throw Exception("Price is anomaly")

        val timestamp = ZonedDateTime.parse(tickerJson.getString("timestamp"))

        ticker.last = tickerJson.getDouble("last")
        ticker.vol = tickerJson.getDouble("volume")
        ticker.timestamp = timestamp.toEpochSecond()
    }
}