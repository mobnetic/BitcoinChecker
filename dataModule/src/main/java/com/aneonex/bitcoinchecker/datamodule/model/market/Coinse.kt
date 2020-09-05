package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.R
import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import org.json.JSONObject

class Coinse : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "Coins-E"
        private const val TTS_NAME = NAME
        private const val URL = "https://www.coins-e.com/api/v2/markets/data/"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.ALP] = arrayOf(VirtualCurrency.BTC, VirtualCurrency.LTC)
            CURRENCY_PAIRS[VirtualCurrency.AMC] = arrayOf(VirtualCurrency.BTC, VirtualCurrency.LTC)
            CURRENCY_PAIRS[VirtualCurrency.ANC] = arrayOf(VirtualCurrency.BTC, VirtualCurrency.LTC)
            CURRENCY_PAIRS[VirtualCurrency.ARG] = arrayOf(VirtualCurrency.BTC, VirtualCurrency.LTC)
            CURRENCY_PAIRS[VirtualCurrency.BET] = arrayOf(VirtualCurrency.BTC, VirtualCurrency.LTC)
            CURRENCY_PAIRS[VirtualCurrency.BQC] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.BTG] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.CGB] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.CIN] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.CMC] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.COL] = arrayOf(VirtualCurrency.LTC)
            CURRENCY_PAIRS[VirtualCurrency.CRC] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.CSC] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.DEM] = arrayOf(VirtualCurrency.BTC, VirtualCurrency.LTC)
            CURRENCY_PAIRS[VirtualCurrency.DGC] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.DMD] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.DOGE] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.DTC] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.ELC] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.ELP] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.EMD] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.EZC] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.FLO] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.FRK] = arrayOf(VirtualCurrency.BTC, VirtualCurrency.LTC)
            CURRENCY_PAIRS[VirtualCurrency.FTC] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.GDC] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.GLC] = arrayOf(VirtualCurrency.BTC, VirtualCurrency.LTC)
            CURRENCY_PAIRS[VirtualCurrency.GLX] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.HYC] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.IFC] = arrayOf(VirtualCurrency.BTC, VirtualCurrency.LTC, VirtualCurrency.XPM)
            CURRENCY_PAIRS[VirtualCurrency.KGC] = arrayOf(VirtualCurrency.BTC, VirtualCurrency.LTC)
            CURRENCY_PAIRS[VirtualCurrency.LBW] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.LTC] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.MEC] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.NAN] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.NET] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.NIB] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.NRB] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.NUC] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.NVC] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.ORB] = arrayOf(VirtualCurrency.BTC, VirtualCurrency.LTC)
            CURRENCY_PAIRS[VirtualCurrency.PPC] = arrayOf(VirtualCurrency.BTC, VirtualCurrency.XPM)
            CURRENCY_PAIRS[VirtualCurrency.PTS] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.PWC] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.PXC] = arrayOf(VirtualCurrency.BTC, VirtualCurrency.LTC)
            CURRENCY_PAIRS[VirtualCurrency.QRK] = arrayOf(VirtualCurrency.BTC, VirtualCurrency.LTC, VirtualCurrency.XPM)
            CURRENCY_PAIRS[VirtualCurrency.RCH] = arrayOf(VirtualCurrency.BTC, VirtualCurrency.LTC)
            CURRENCY_PAIRS[VirtualCurrency.REC] = arrayOf(VirtualCurrency.BTC, VirtualCurrency.LTC)
            CURRENCY_PAIRS[VirtualCurrency.RED] = arrayOf(VirtualCurrency.BTC, VirtualCurrency.LTC)
            CURRENCY_PAIRS[VirtualCurrency.SBC] = arrayOf(VirtualCurrency.BTC, VirtualCurrency.LTC)
            CURRENCY_PAIRS[VirtualCurrency.SPT] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.TAG] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.TRC] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.UNO] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.VLC] = arrayOf(VirtualCurrency.BTC, VirtualCurrency.LTC)
            CURRENCY_PAIRS[VirtualCurrency.WDC] = arrayOf(VirtualCurrency.BTC)
            CURRENCY_PAIRS[VirtualCurrency.XNC] = arrayOf(VirtualCurrency.BTC, VirtualCurrency.LTC)
            CURRENCY_PAIRS[VirtualCurrency.XPM] = arrayOf(VirtualCurrency.BTC, VirtualCurrency.LTC)
            CURRENCY_PAIRS[VirtualCurrency.ZET] = arrayOf(VirtualCurrency.BTC)
        }
    }

    override val cautionResId: Int
        get() = R.string.market_caution_much_data

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return URL
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val marketsObject = jsonObject.getJSONObject("markets")
        val pairObject = marketsObject.getJSONObject(checkerInfo.currencyBase + "_" + checkerInfo.currencyCounter)
        val marketStatObject = pairObject.getJSONObject("marketstat")
        val inner24hObject = marketStatObject.getJSONObject("24h")
        ticker.bid = marketStatObject.getDouble("bid")
        ticker.ask = marketStatObject.getDouble("ask")
        ticker.vol = inner24hObject.getDouble("volume")
        ticker.high = inner24hObject.getDouble("h")
        ticker.low = inner24hObject.getDouble("l")
        ticker.last = marketStatObject.getDouble("ltp")
    }
}