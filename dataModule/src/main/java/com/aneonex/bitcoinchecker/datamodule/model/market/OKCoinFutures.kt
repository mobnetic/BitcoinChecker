package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Futures
import com.aneonex.bitcoinchecker.datamodule.model.FuturesMarket
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import org.json.JSONObject

class OKCoinFutures : FuturesMarket(NAME, TTS_NAME, CURRENCY_PAIRS, CONTRACT_TYPES) {
    companion object {
        private const val NAME = "OKCoin Futures"
        private const val TTS_NAME = "OK Coin Futures"
        private const val URL = "https://www.okex.com/api/v1/future_ticker.do?symbol=%1\$s_%2\$s&contract_type=%3\$s"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()
        private val CONTRACT_TYPES = intArrayOf(
                Futures.CONTRACT_TYPE_WEEKLY,
                Futures.CONTRACT_TYPE_BIWEEKLY,
                Futures.CONTRACT_TYPE_QUARTERLY
        )

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf( //			Currency.CNY, //At this time (2015-09-17) no API exist for www.okcoin.cn. Check https://www.okcoin.cn/about/rest_api.do
                    Currency.USD
            )
            CURRENCY_PAIRS[VirtualCurrency.LTC] = arrayOf( //			Currency.CNY, //At this time (2015-09-17) no API exist for www.okcoin.cn. Check https://www.okcoin.cn/about/rest_api.do
                    Currency.USD
            )
        }
    }

    public override fun getUrl(requestId: Int, checkerInfo: CheckerInfo, contractType: Int): String {
        return String.format(URL, checkerInfo.currencyBaseLowerCase, checkerInfo.currencyCounterLowerCase, getContractTypeString(contractType))
    }

    private fun getContractTypeString(contractType: Int): String {
        return when (contractType) {
            Futures.CONTRACT_TYPE_WEEKLY -> "this_week"
            Futures.CONTRACT_TYPE_BIWEEKLY -> "next_week"
            Futures.CONTRACT_TYPE_QUARTERLY -> "quarter"
            else -> "this_week"
        }
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val tickerJsonObject = jsonObject.getJSONObject("ticker")
        ticker.bid = tickerJsonObject.getDouble("buy")
        ticker.ask = tickerJsonObject.getDouble("sell")
        ticker.vol = tickerJsonObject.getDouble("vol")
        ticker.high = tickerJsonObject.getDouble("high")
        ticker.low = tickerJsonObject.getDouble("low")
        ticker.last = tickerJsonObject.getDouble("last")
    }
}