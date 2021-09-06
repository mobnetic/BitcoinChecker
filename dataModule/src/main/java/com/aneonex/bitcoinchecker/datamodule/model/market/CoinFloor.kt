package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import org.json.JSONObject

class CoinFloor : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    // API info: https://github.com/coinfloor/API/blob/master/BIST_v2.md
    companion object {
        private const val NAME = "Coinfloor"
        private const val TTS_NAME = "Coin Floor"
        private const val URL = "https://webapi.coinfloor.co.uk/v2/bist/%1\$s/%2\$s/ticker/"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.XBT] = arrayOf(
                    Currency.GBP,
                    Currency.EUR,
//                    Currency.USD
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBase, checkerInfo.currencyCounter)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.apply {
            bid = jsonObject.getDouble("bid")
            ask = jsonObject.getDouble("ask")
            vol = jsonObject.getDouble("volume")
            high = jsonObject.getDouble("high")
            low = jsonObject.getDouble("low")
            last = jsonObject.getDouble("last")
        }
    }
}