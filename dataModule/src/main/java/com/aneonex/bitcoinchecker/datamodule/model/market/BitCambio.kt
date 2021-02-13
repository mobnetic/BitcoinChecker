/**
 * Author: Jorge Pereira <jpereiran@gmail.com>
 * Date: Fri Feb 12 20:33:40 -03 2021
 * Desc: BitCambio market
 */
 package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import org.json.JSONObject

class BitCambio : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "BitCambio"
        private const val TTS_NAME = "BitCambio Trade"
        private const val URL = "https://nova.bitcambio.com.br/api/v3/public/getmarketsummary?market=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://nova.bitcambio.com.br/api/v3/public/getmarkets"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            // Predefined most used currencies
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                Currency.BRL
            )
        }
    }

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return URL_CURRENCY_PAIRS
    }

    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val markets = jsonObject.getJSONArray("result")
        for(i in 0 until markets.length()){
            val market = markets.getJSONObject(i)

            if(market.getBoolean("IsActive")){
                pairs.add( CurrencyPairInfo(
                    market.getString("MarketAsset"),
                    market.getString("BaseAsset"),
                    market.getString("MarketName")
                ))
            }
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        val pairId: String = checkerInfo.currencyPairId ?: "${checkerInfo.currencyBase}_${checkerInfo.currencyCounter}"
        return String.format(URL, pairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val resultJsonObject = jsonObject.getJSONObject("result")
        ticker.ask  = resultJsonObject.getDouble("Ask")
        ticker.bid  = resultJsonObject.getDouble("Bid")
        ticker.last = resultJsonObject.getDouble("Last")
        ticker.vol  = resultJsonObject.getDouble("Volume")
        ticker.high = resultJsonObject.getDouble("High")
        ticker.low  = resultJsonObject.getDouble("Low")
    }
}
