package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import org.json.JSONObject

class BudaCom : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "Buda.com"
        private const val TTS_NAME = NAME
        private const val URL = "https://www.buda.com/api/v2/markets/%1\$s-%2\$s/ticker"

        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BCH] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.ARS,
                    Currency.CLP,
                    Currency.COP,
                    Currency.PEN,
            )
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.ARS,
                    Currency.CLP,
                    Currency.COP,
                    Currency.PEN,
            )
            CURRENCY_PAIRS[VirtualCurrency.ETH] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.ARS,
                    Currency.CLP,
                    Currency.COP,
                    Currency.PEN,
            )
            CURRENCY_PAIRS[VirtualCurrency.LTC] = arrayOf(
                    VirtualCurrency.BTC,
                    Currency.ARS,
                    Currency.CLP,
                    Currency.COP,
                    Currency.PEN
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBase, checkerInfo.currencyCounter)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val tickerJson = jsonObject.getJSONObject("ticker")

        fun getFieldValue(fieldName: String): Double {
            return tickerJson.getJSONArray(fieldName).getDouble(0)
        }

        ticker.last = getFieldValue("last_price")
        ticker.ask = getFieldValue("min_ask")
        ticker.bid = getFieldValue("max_bid")
        ticker.vol = getFieldValue("volume")
    }
}