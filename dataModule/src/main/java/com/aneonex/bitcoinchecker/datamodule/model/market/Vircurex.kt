package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.Currency
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import org.json.JSONObject

class Vircurex : Market(NAME, TTS_NAME, CURRENCY_PAIRS) {
    companion object {
        private const val NAME = "Vircurex"
        private const val TTS_NAME = NAME
        private const val URL = "https://api.vircurex.com/api/get_info_for_1_currency.json?base=%1\$s&alt=%2\$s"
        private val CURRENCY_PAIRS: CurrencyPairsMap = CurrencyPairsMap()

        init {
            CURRENCY_PAIRS[VirtualCurrency.BTC] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    VirtualCurrency.ANC,
                    VirtualCurrency.DGC,
                    VirtualCurrency.DOGE,
                    VirtualCurrency.DVC,
                    VirtualCurrency.FRC,
                    VirtualCurrency.FTC,
                    VirtualCurrency.I0C,
                    VirtualCurrency.IXC,
                    VirtualCurrency.LTC,
                    VirtualCurrency.NMC,
                    VirtualCurrency.NVC,
                    VirtualCurrency.NXT,
                    VirtualCurrency.PPC,
                    VirtualCurrency.QRK,
                    VirtualCurrency.TRC,
                    VirtualCurrency.VTC,
                    VirtualCurrency.WDC,
                    VirtualCurrency.XPM
            )
            CURRENCY_PAIRS[VirtualCurrency.ANC] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    VirtualCurrency.BTC,
                    VirtualCurrency.DGC,
                    VirtualCurrency.DOGE,
                    VirtualCurrency.DVC,
                    VirtualCurrency.FRC,
                    VirtualCurrency.FTC,
                    VirtualCurrency.I0C,
                    VirtualCurrency.IXC,
                    VirtualCurrency.LTC,
                    VirtualCurrency.NMC,
                    VirtualCurrency.NVC,
                    VirtualCurrency.NXT,
                    VirtualCurrency.PPC,
                    VirtualCurrency.QRK,
                    VirtualCurrency.TRC,
                    VirtualCurrency.VTC,
                    VirtualCurrency.WDC,
                    VirtualCurrency.XPM
            )
            CURRENCY_PAIRS[VirtualCurrency.DGC] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    VirtualCurrency.BTC,
                    VirtualCurrency.ANC,
                    VirtualCurrency.DOGE,
                    VirtualCurrency.DVC,
                    VirtualCurrency.FRC,
                    VirtualCurrency.FTC,
                    VirtualCurrency.I0C,
                    VirtualCurrency.IXC,
                    VirtualCurrency.LTC,
                    VirtualCurrency.NMC,
                    VirtualCurrency.NVC,
                    VirtualCurrency.NXT,
                    VirtualCurrency.PPC,
                    VirtualCurrency.QRK,
                    VirtualCurrency.TRC,
                    VirtualCurrency.VTC,
                    VirtualCurrency.WDC,
                    VirtualCurrency.XPM
            )
            CURRENCY_PAIRS[VirtualCurrency.DOGE] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    VirtualCurrency.BTC,
                    VirtualCurrency.ANC,
                    VirtualCurrency.DGC,
                    VirtualCurrency.DVC,
                    VirtualCurrency.FRC,
                    VirtualCurrency.FTC,
                    VirtualCurrency.I0C,
                    VirtualCurrency.IXC,
                    VirtualCurrency.LTC,
                    VirtualCurrency.NMC,
                    VirtualCurrency.NVC,
                    VirtualCurrency.NXT,
                    VirtualCurrency.PPC,
                    VirtualCurrency.QRK,
                    VirtualCurrency.TRC,
                    VirtualCurrency.VTC,
                    VirtualCurrency.WDC,
                    VirtualCurrency.XPM
            )
            CURRENCY_PAIRS[VirtualCurrency.DVC] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    VirtualCurrency.BTC,
                    VirtualCurrency.ANC,
                    VirtualCurrency.DGC,
                    VirtualCurrency.DOGE,
                    VirtualCurrency.FRC,
                    VirtualCurrency.FTC,
                    VirtualCurrency.I0C,
                    VirtualCurrency.IXC,
                    VirtualCurrency.LTC,
                    VirtualCurrency.NMC,
                    VirtualCurrency.NVC,
                    VirtualCurrency.NXT,
                    VirtualCurrency.PPC,
                    VirtualCurrency.QRK,
                    VirtualCurrency.TRC,
                    VirtualCurrency.VTC,
                    VirtualCurrency.WDC,
                    VirtualCurrency.XPM
            )
            CURRENCY_PAIRS[VirtualCurrency.FRC] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    VirtualCurrency.BTC,
                    VirtualCurrency.ANC,
                    VirtualCurrency.DGC,
                    VirtualCurrency.DOGE,
                    VirtualCurrency.DVC,
                    VirtualCurrency.FTC,
                    VirtualCurrency.I0C,
                    VirtualCurrency.IXC,
                    VirtualCurrency.LTC,
                    VirtualCurrency.NMC,
                    VirtualCurrency.NVC,
                    VirtualCurrency.NXT,
                    VirtualCurrency.PPC,
                    VirtualCurrency.QRK,
                    VirtualCurrency.TRC,
                    VirtualCurrency.VTC,
                    VirtualCurrency.WDC,
                    VirtualCurrency.XPM
            )
            CURRENCY_PAIRS[VirtualCurrency.FTC] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    VirtualCurrency.BTC,
                    VirtualCurrency.ANC,
                    VirtualCurrency.DGC,
                    VirtualCurrency.DOGE,
                    VirtualCurrency.DVC,
                    VirtualCurrency.FRC,
                    VirtualCurrency.I0C,
                    VirtualCurrency.IXC,
                    VirtualCurrency.LTC,
                    VirtualCurrency.NMC,
                    VirtualCurrency.NVC,
                    VirtualCurrency.NXT,
                    VirtualCurrency.PPC,
                    VirtualCurrency.QRK,
                    VirtualCurrency.TRC,
                    VirtualCurrency.VTC,
                    VirtualCurrency.WDC,
                    VirtualCurrency.XPM
            )
            CURRENCY_PAIRS[VirtualCurrency.I0C] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    VirtualCurrency.BTC,
                    VirtualCurrency.ANC,
                    VirtualCurrency.DGC,
                    VirtualCurrency.DOGE,
                    VirtualCurrency.DVC,
                    VirtualCurrency.FRC,
                    VirtualCurrency.FTC,
                    VirtualCurrency.IXC,
                    VirtualCurrency.LTC,
                    VirtualCurrency.NMC,
                    VirtualCurrency.NVC,
                    VirtualCurrency.NXT,
                    VirtualCurrency.PPC,
                    VirtualCurrency.QRK,
                    VirtualCurrency.TRC,
                    VirtualCurrency.VTC,
                    VirtualCurrency.WDC,
                    VirtualCurrency.XPM
            )
            CURRENCY_PAIRS[VirtualCurrency.IXC] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    VirtualCurrency.BTC,
                    VirtualCurrency.ANC,
                    VirtualCurrency.DGC,
                    VirtualCurrency.DOGE,
                    VirtualCurrency.DVC,
                    VirtualCurrency.FRC,
                    VirtualCurrency.FTC,
                    VirtualCurrency.I0C,
                    VirtualCurrency.LTC,
                    VirtualCurrency.NMC,
                    VirtualCurrency.NVC,
                    VirtualCurrency.NXT,
                    VirtualCurrency.PPC,
                    VirtualCurrency.QRK,
                    VirtualCurrency.TRC,
                    VirtualCurrency.VTC,
                    VirtualCurrency.WDC,
                    VirtualCurrency.XPM
            )
            CURRENCY_PAIRS[VirtualCurrency.LTC] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    VirtualCurrency.BTC,
                    VirtualCurrency.ANC,
                    VirtualCurrency.DGC,
                    VirtualCurrency.DOGE,
                    VirtualCurrency.DVC,
                    VirtualCurrency.FRC,
                    VirtualCurrency.FTC,
                    VirtualCurrency.I0C,
                    VirtualCurrency.IXC,
                    VirtualCurrency.NMC,
                    VirtualCurrency.NVC,
                    VirtualCurrency.NXT,
                    VirtualCurrency.PPC,
                    VirtualCurrency.QRK,
                    VirtualCurrency.TRC,
                    VirtualCurrency.VTC,
                    VirtualCurrency.WDC,
                    VirtualCurrency.XPM
            )
            CURRENCY_PAIRS[VirtualCurrency.NMC] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    VirtualCurrency.BTC,
                    VirtualCurrency.ANC,
                    VirtualCurrency.DGC,
                    VirtualCurrency.DOGE,
                    VirtualCurrency.DVC,
                    VirtualCurrency.FRC,
                    VirtualCurrency.FTC,
                    VirtualCurrency.I0C,
                    VirtualCurrency.IXC,
                    VirtualCurrency.LTC,
                    VirtualCurrency.NVC,
                    VirtualCurrency.NXT,
                    VirtualCurrency.PPC,
                    VirtualCurrency.QRK,
                    VirtualCurrency.TRC,
                    VirtualCurrency.VTC,
                    VirtualCurrency.WDC,
                    VirtualCurrency.XPM
            )
            CURRENCY_PAIRS[VirtualCurrency.NVC] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    VirtualCurrency.BTC,
                    VirtualCurrency.ANC,
                    VirtualCurrency.DGC,
                    VirtualCurrency.DOGE,
                    VirtualCurrency.DVC,
                    VirtualCurrency.FRC,
                    VirtualCurrency.FTC,
                    VirtualCurrency.I0C,
                    VirtualCurrency.IXC,
                    VirtualCurrency.LTC,
                    VirtualCurrency.NMC,
                    VirtualCurrency.NXT,
                    VirtualCurrency.PPC,
                    VirtualCurrency.QRK,
                    VirtualCurrency.TRC,
                    VirtualCurrency.VTC,
                    VirtualCurrency.WDC,
                    VirtualCurrency.XPM
            )
            CURRENCY_PAIRS[VirtualCurrency.NXT] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    VirtualCurrency.BTC,
                    VirtualCurrency.ANC,
                    VirtualCurrency.DGC,
                    VirtualCurrency.DOGE,
                    VirtualCurrency.DVC,
                    VirtualCurrency.FRC,
                    VirtualCurrency.FTC,
                    VirtualCurrency.I0C,
                    VirtualCurrency.IXC,
                    VirtualCurrency.LTC,
                    VirtualCurrency.NMC,
                    VirtualCurrency.NVC,
                    VirtualCurrency.PPC,
                    VirtualCurrency.QRK,
                    VirtualCurrency.TRC,
                    VirtualCurrency.VTC,
                    VirtualCurrency.WDC,
                    VirtualCurrency.XPM
            )
            CURRENCY_PAIRS[VirtualCurrency.PPC] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    VirtualCurrency.BTC,
                    VirtualCurrency.ANC,
                    VirtualCurrency.DGC,
                    VirtualCurrency.DOGE,
                    VirtualCurrency.DVC,
                    VirtualCurrency.FRC,
                    VirtualCurrency.FTC,
                    VirtualCurrency.I0C,
                    VirtualCurrency.IXC,
                    VirtualCurrency.LTC,
                    VirtualCurrency.NMC,
                    VirtualCurrency.NVC,
                    VirtualCurrency.NXT,
                    VirtualCurrency.QRK,
                    VirtualCurrency.TRC,
                    VirtualCurrency.VTC,
                    VirtualCurrency.WDC,
                    VirtualCurrency.XPM
            )
            CURRENCY_PAIRS[VirtualCurrency.QRK] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    VirtualCurrency.BTC,
                    VirtualCurrency.ANC,
                    VirtualCurrency.DGC,
                    VirtualCurrency.DOGE,
                    VirtualCurrency.DVC,
                    VirtualCurrency.FRC,
                    VirtualCurrency.FTC,
                    VirtualCurrency.I0C,
                    VirtualCurrency.IXC,
                    VirtualCurrency.LTC,
                    VirtualCurrency.NMC,
                    VirtualCurrency.NVC,
                    VirtualCurrency.NXT,
                    VirtualCurrency.PPC,
                    VirtualCurrency.TRC,
                    VirtualCurrency.VTC,
                    VirtualCurrency.WDC,
                    VirtualCurrency.XPM
            )
            CURRENCY_PAIRS[VirtualCurrency.TRC] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    VirtualCurrency.BTC,
                    VirtualCurrency.ANC,
                    VirtualCurrency.DGC,
                    VirtualCurrency.DOGE,
                    VirtualCurrency.DVC,
                    VirtualCurrency.FRC,
                    VirtualCurrency.FTC,
                    VirtualCurrency.I0C,
                    VirtualCurrency.IXC,
                    VirtualCurrency.LTC,
                    VirtualCurrency.NMC,
                    VirtualCurrency.NVC,
                    VirtualCurrency.NXT,
                    VirtualCurrency.PPC,
                    VirtualCurrency.QRK,
                    VirtualCurrency.VTC,
                    VirtualCurrency.WDC,
                    VirtualCurrency.XPM
            )
            CURRENCY_PAIRS[VirtualCurrency.VTC] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    VirtualCurrency.BTC,
                    VirtualCurrency.ANC,
                    VirtualCurrency.DGC,
                    VirtualCurrency.DOGE,
                    VirtualCurrency.DVC,
                    VirtualCurrency.FRC,
                    VirtualCurrency.FTC,
                    VirtualCurrency.I0C,
                    VirtualCurrency.IXC,
                    VirtualCurrency.LTC,
                    VirtualCurrency.NMC,
                    VirtualCurrency.NVC,
                    VirtualCurrency.NXT,
                    VirtualCurrency.PPC,
                    VirtualCurrency.QRK,
                    VirtualCurrency.TRC,
                    VirtualCurrency.WDC,
                    VirtualCurrency.XPM
            )
            CURRENCY_PAIRS[VirtualCurrency.WDC] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    VirtualCurrency.BTC,
                    VirtualCurrency.ANC,
                    VirtualCurrency.DGC,
                    VirtualCurrency.DOGE,
                    VirtualCurrency.DVC,
                    VirtualCurrency.FRC,
                    VirtualCurrency.FTC,
                    VirtualCurrency.I0C,
                    VirtualCurrency.IXC,
                    VirtualCurrency.LTC,
                    VirtualCurrency.NMC,
                    VirtualCurrency.NVC,
                    VirtualCurrency.NXT,
                    VirtualCurrency.PPC,
                    VirtualCurrency.QRK,
                    VirtualCurrency.TRC,
                    VirtualCurrency.VTC,
                    VirtualCurrency.XPM
            )
            CURRENCY_PAIRS[VirtualCurrency.XPM] = arrayOf(
                    Currency.USD,
                    Currency.EUR,
                    VirtualCurrency.BTC,
                    VirtualCurrency.ANC,
                    VirtualCurrency.DGC,
                    VirtualCurrency.DOGE,
                    VirtualCurrency.DVC,
                    VirtualCurrency.FRC,
                    VirtualCurrency.FTC,
                    VirtualCurrency.I0C,
                    VirtualCurrency.IXC,
                    VirtualCurrency.LTC,
                    VirtualCurrency.NMC,
                    VirtualCurrency.NVC,
                    VirtualCurrency.NXT,
                    VirtualCurrency.PPC,
                    VirtualCurrency.QRK,
                    VirtualCurrency.TRC,
                    VirtualCurrency.VTC,
                    VirtualCurrency.WDC)
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBase, checkerInfo.currencyCounter)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        ticker.bid = jsonObject.getDouble("highest_bid")
        ticker.ask = jsonObject.getDouble("lowest_ask")
        ticker.vol = jsonObject.getDouble("volume")
        //		ticker.high;
//		ticker.low;
        ticker.last = jsonObject.getDouble("last_trade")
    }
}