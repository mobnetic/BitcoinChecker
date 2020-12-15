package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Futures
import com.aneonex.bitcoinchecker.datamodule.model.FuturesMarket
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import com.aneonex.bitcoinchecker.datamodule.util.TimeUtils
import org.json.JSONObject

class OkexFutures : FuturesMarket(NAME, TTS_NAME, CURRENCY_PAIRS, CONTRACT_TYPES) {
    companion object {
        private const val NAME = "OKEx Futures"
        private const val TTS_NAME = "Okex Futures"
        private const val URL = "https://www.okex.com/api/futures/v3/instruments/%1\$s/ticker"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()
        private val CONTRACT_TYPES = intArrayOf(
                Futures.CONTRACT_TYPE_WEEKLY,
                Futures.CONTRACT_TYPE_BIWEEKLY,
                Futures.CONTRACT_TYPE_QUARTERLY,
                Futures.CONTRACT_TYPE_BIQUARTERLY
        )

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.USD,
                    VirtualCurrency.USDT
            )
            CURRENCY_PAIRS[VirtualCurrency.ETH] = arrayOf(
                    Currency.USD,
                    VirtualCurrency.USDT
            )
            CURRENCY_PAIRS[VirtualCurrency.LTC] = arrayOf(
                    Currency.USD,
                    VirtualCurrency.USDT
            )
            CURRENCY_PAIRS[VirtualCurrency.EOS] = arrayOf(
                    Currency.USD,
                    VirtualCurrency.USDT
            )
            CURRENCY_PAIRS[VirtualCurrency.XRP] = arrayOf(
                    Currency.USD,
                    VirtualCurrency.USDT
            )
            CURRENCY_PAIRS[VirtualCurrency.BCH] = arrayOf(
                    Currency.USD,
                    VirtualCurrency.USDT
            )
            CURRENCY_PAIRS[VirtualCurrency.TRX] = arrayOf(
                    Currency.USD,
                    VirtualCurrency.USDT
            )
            CURRENCY_PAIRS[VirtualCurrency.LINK] = arrayOf(
                    Currency.USD,
                    VirtualCurrency.USDT
            )
        }
    }

    public override fun getUrl(requestId: Int, checkerInfo: CheckerInfo, contractType: Int): String {
        return String.format(URL, getInstrumentId(checkerInfo.currencyBase, checkerInfo.currencyCounter, contractType))
    }

    private fun getInstrumentId(currencyBase: String, currencyCounter: String, contractType: Int): String {
        val suffix = when (contractType) {
            Futures.CONTRACT_TYPE_WEEKLY -> "201009"
            Futures.CONTRACT_TYPE_BIWEEKLY -> "201016"
            Futures.CONTRACT_TYPE_QUARTERLY -> "201225"
            Futures.CONTRACT_TYPE_BIQUARTERLY -> "210326"
            else -> throw ArrayIndexOutOfBoundsException("Unknown contract type: $contractType")
        }

        return "$currencyBase-$currencyCounter-$suffix"
    }

    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.bid = jsonObject.getDouble("best_bid")
        ticker.ask = jsonObject.getDouble("best_ask")
        ticker.vol = jsonObject.getDouble("volume_token_24h")
        ticker.high = jsonObject.getDouble("high_24h")
        ticker.low = jsonObject.getDouble("low_24h")
        ticker.last = jsonObject.getDouble("last")
        ticker.timestamp =  TimeUtils.convertISODateToTimestamp(jsonObject.getString("timestamp"))
    }
}