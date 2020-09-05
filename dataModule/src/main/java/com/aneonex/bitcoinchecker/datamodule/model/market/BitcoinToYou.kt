package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import org.json.JSONObject

class BitcoinToYou : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "BitcoinToYou"
        private const val TTS_NAME = "Bitcoin To You"
        private const val URL = "https://back.bitcointoyou.com/api/ticker?pair=%1\$s_%2\$sC"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.BRL
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
/*
		String pairId = checkerInfo.getCurrencyPairId();
		if (pairId == null) {
			pairId = String.format("%1$s_%2$s", checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
		}
 */
        return String.format(URL, checkerInfo.currencyBase, checkerInfo.currencyCounter)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val tickerJsonObject = jsonObject.getJSONObject("summary")
        //		ticker.bid = tickerJsonObject.getDouble("buy");
//		ticker.ask = tickerJsonObject.getDouble("sell");
        ticker.vol = tickerJsonObject.getDouble("amount")
        ticker.high = tickerJsonObject.getDouble("high")
        ticker.low = tickerJsonObject.getDouble("low")
        ticker.last = tickerJsonObject.getDouble("last")
    }
}