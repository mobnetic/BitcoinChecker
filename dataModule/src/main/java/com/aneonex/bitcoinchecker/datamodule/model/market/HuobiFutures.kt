package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.exceptions.MarketParseException
import com.aneonex.bitcoinchecker.datamodule.model.*
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import com.aneonex.bitcoinchecker.datamodule.util.forEachJSONObject
import org.json.JSONObject
import java.util.*

class HuobiFutures : Market(
    MARKET_NAME,
    MARKET_NAME,
    null
) {

    override val currencyPairsNumOfRequests: Int
        get() = 3

    override fun getCurrencyPairsUrl(requestId: Int): String {
        return when(requestId) {
            REQUEST_SWAP_COIN -> URL_PAIRS_SWAP_COIN
            REQUEST_SWAP_USDT -> URL_PAIRS_SWAP_USDT

            // REQUEST_FUTURES
            else -> URL_PAIRS_FUTURES
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        val utlTemplate: String =
            if(checkerInfo.contractType <= FuturesContractType.PERPETUAL) {
                if(checkerInfo.currencyCounter == VirtualCurrency.USDT) URL_TICKER_SWAP_USDT
                else
                    URL_TICKER_SWAP_COIN
            }
            else URL_TICKER_FUTURES

        return String.format(utlTemplate, getPairId(checkerInfo))
    }

    private fun getPairId(checkerInfo: CheckerInfo): String? {
        return when(checkerInfo.contractType) {
            FuturesContractType.NONE,
            FuturesContractType.PERPETUAL -> checkerInfo.currencyPairId

            FuturesContractType.WEEKLY -> "${checkerInfo.currencyBase}_CW"
            FuturesContractType.BIWEEKLY -> "${checkerInfo.currencyBase}_NW"

            FuturesContractType.QUARTERLY -> "${checkerInfo.currencyBase}_CQ"
            FuturesContractType.BIQUARTERLY -> "${checkerInfo.currencyBase}_NQ"

            else ->
                throw MarketParseException("Unexpected contract type")
        }
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        jsonObject.getJSONObject("tick").also {
            ticker.bid = it.getJSONArray("bid").getDouble(0)
            ticker.ask = it.getJSONArray("ask").getDouble(0)
            ticker.vol = it.getDouble("amount")
            ticker.volQuote = it.getDouble("vol")
            ticker.high = it.getDouble("high")
            ticker.low = it.getDouble("low")
            ticker.last = it.getDouble("close")
        }
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        fun parseContractType(value: String): FuturesContractType? =
            when(value) {
                "this_week" -> FuturesContractType.WEEKLY
                "next_week" -> FuturesContractType.BIWEEKLY
                "quarter" -> FuturesContractType.QUARTERLY
                "next_quarter" -> FuturesContractType.BIQUARTERLY
                else -> null
            }

        if ("ok".equals(jsonObject.getString("status"), ignoreCase = true)) {

            jsonObject.getJSONArray("data")
                .forEachJSONObject { market ->
                    val contractType: FuturesContractType
                    val quoteAsset: String

                    when(requestId) {
                        REQUEST_SWAP_COIN -> {
                            contractType = FuturesContractType.PERPETUAL
                            quoteAsset = Currency.USD
                        }
                        REQUEST_SWAP_USDT -> {
                            contractType = FuturesContractType.PERPETUAL
                            quoteAsset = VirtualCurrency.USDT
                        }

                        // REQUEST_FUTURES
                        else -> {
                            contractType = parseContractType(market.getString("contract_type"))
                                ?: return@forEachJSONObject
                            quoteAsset = Currency.USD
                        }
                    }

                    pairs.add(CurrencyPairInfo(
                        market.getString("symbol"),
                        quoteAsset,
                        market.getString("contract_code"),
                        contractType
                    ))
                }
        } else {
            throw Exception("Parse currency pairs error.")
        }
    }

    companion object {
        private const val MARKET_NAME = "Huobi Futures"

        // private const val REQUEST_FUTURES = 0
        private const val REQUEST_SWAP_COIN = 1
        private const val REQUEST_SWAP_USDT = 2

        private const val URL_PAIRS_FUTURES = "https://api.hbdm.com/api/v1/contract_contract_info"
        private const val URL_TICKER_FUTURES = "https://api.hbdm.com/market/detail/merged?symbol=%1\$s"

        private const val URL_PAIRS_SWAP_COIN = "https://api.hbdm.com/swap-api/v1/swap_contract_info"
        private const val URL_TICKER_SWAP_COIN = "https://api.hbdm.com/swap-ex/market/detail/merged?contract_code=%1\$s"

        private const val URL_PAIRS_SWAP_USDT = "https://api.hbdm.com/linear-swap-api/v1/swap_contract_info"
        private const val URL_TICKER_SWAP_USDT = "https://api.hbdm.com/linear-swap-ex/market/detail/merged?contract_code=%1\$s"

    }
}