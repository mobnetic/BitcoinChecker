package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.R
import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency

class UnknownMarket : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "UNKNOWN"
        private const val TTS_NAME = NAME
        private const val URL = ""
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(VirtualCurrency.BTC)
        }
    }

    override val cautionResId: Int
        get() = R.string.market_caution_unknown

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return URL
    }
}