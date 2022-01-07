package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.*
import com.aneonex.bitcoinchecker.datamodule.util.TimeUtils
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONArray
import org.json.JSONObject
import java.time.format.DateTimeFormatter
import java.util.*

class OkexFutures : Market(
    "OKEx Futures",
    "Okex Futures",
    null
) {

    override val currencyPairsNumOfRequests: Int
        get() = 2

    override fun getCurrencyPairsUrl(requestId: Int): String =
        if(requestId == 0) URL_PAIRS_PERPETUAL else URL_PAIRS_FUTURES

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        val urlTemplate = if(checkerInfo.contractType <= FuturesContractType.PERPETUAL)
            URL_TICKER_PERPETUAL
        else
            URL_TICKER_FUTURES

        return String.format(urlTemplate, calculateFuturesPairId(checkerInfo))
    }

    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        fun parseContractType(value: String): FuturesContractType? =
            when(value) {
                "this_week" -> FuturesContractType.WEEKLY
                "next_week" -> FuturesContractType.BIWEEKLY
                "quarter" -> FuturesContractType.QUARTERLY
                "bi_quarter" -> FuturesContractType.BIQUARTERLY
                else -> null
            }

        JSONArray(responseString)
            .forEachJSONObject {
//                val deliveryType = parseFuturesType(it.getString("alias")) ?: return@forEachJSONObject

                val contractType: FuturesContractType =
                    if(requestId == 0) FuturesContractType.PERPETUAL
                    else parseContractType(it.getString("alias")) ?: return@forEachJSONObject

                pairs.add(
                    CurrencyPairInfo(
                        it.getString("base_currency"),
                        it.getString("quote_currency"),
                        it.getString("instrument_id"),
                        contractType
                        )
                )
            }
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

    companion object {
        private const val URL_PAIRS_PERPETUAL = "https://www.okex.com/api/swap/v3/instruments"
        private const val URL_PAIRS_FUTURES = "https://www.okex.com/api/futures/v3/instruments"

        private const val URL_TICKER_PERPETUAL = "https://www.okex.com/api/swap/v3/instruments/%1\$s/ticker"
        private const val URL_TICKER_FUTURES = "https://www.okex.com/api/futures/v3/instruments/%1\$s/ticker"

        //private val FUTURES_DATE_FORMAT = SimpleDateFormat("yyMMdd", Locale.ROOT)
        private val FUTURES_DATE_FORMAT = DateTimeFormatter.ofPattern("yyMMdd", Locale.ROOT)

        private fun calculateFuturesPairId(checkerInfo: CheckerInfo): String? {
            val targetDate = FuturesContractType.getDeliveryDate(checkerInfo.contractType)
                ?: return checkerInfo.currencyPairId

            return with(checkerInfo){ "$currencyBase-$currencyCounter-${FUTURES_DATE_FORMAT.format(targetDate)}" }
        }
    }
}