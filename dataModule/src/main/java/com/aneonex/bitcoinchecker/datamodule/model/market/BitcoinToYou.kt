package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.exceptions.MarketParseException
import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.market.generic.SimpleMarket
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONArray
import org.json.JSONObject

class BitcoinToYou : SimpleMarket(
    "BitcoinToYou",
    "https://back.bitcointoyou.com/api/v2/pair/getPairList",
    "https://back.bitcointoyou.com/api/v2/ticker?pair=%1\$s",
    "Bitcoin To You"
) {

    override fun getPairId(checkerInfo: CheckerInfo): String? {
        return if (checkerInfo.currencyPairId == null)
                    with(checkerInfo) { "${currencyBase}_${currencyCounter}C" }
                else
                    super.getPairId(checkerInfo)
    }

    override fun parseCurrencyPairs(
        requestId: Int,
        responseString: String,
        pairs: MutableList<CurrencyPairInfo>
    ) {
        fun getDisplaySymbol(symbolString: String): String {
            @Suppress("SpellCheckingInspection")
            return when(symbolString){
                "CBRL" -> "BRL"
                "BRLC" -> "BRL"
                else -> symbolString
            }
        }

        val data = JSONArray(responseString)
        data.forEachJSONObject {
            val symbol = it.getString("symbol")
            val assets = symbol.split('_')
            if(assets.size == 2) {
                pairs.add(
                    CurrencyPairInfo(
                        getDisplaySymbol(assets[0]),
                        getDisplaySymbol(assets[1]),
                        symbol
                    )
                )
            }
        }
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.getJSONObject("summary").also { data ->
            ticker.vol = data.getDouble("amount")
            if(ticker.vol <= 0)
                throw MarketParseException("No trading volume")

            ticker.volQuote = data.getDouble("quote_volume")
            ticker.high = data.getDouble("high")
            ticker.low = data.getDouble("low")
            ticker.last = data.getDouble("last")
        }
    }
}