/**
 * Author: Jorge Pereira <jpereiran@gmail.com>
 * Date: Fri Feb 12 20:33:40 -03 2021
 * Desc: BitCambio market
 */
package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
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
        private const val URL = "https://nova.bitcambio.com.br/api/v3/public/getmarketsummary?market=%1\$s_%2\$s"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                Currency.BRL
            )
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBase, checkerInfo.currencyCounter)
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
