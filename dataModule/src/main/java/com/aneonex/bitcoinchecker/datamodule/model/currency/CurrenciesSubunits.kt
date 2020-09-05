package com.aneonex.bitcoinchecker.datamodule.model.currency

import com.aneonex.bitcoinchecker.datamodule.model.CurrencySubunit
import com.aneonex.bitcoinchecker.datamodule.model.CurrencySubunitsMap
import java.util.*

object CurrenciesSubunits {
    val CURRENCIES_SUBUNITS = HashMap<String, CurrencySubunitsMap>()

    init {
        CURRENCIES_SUBUNITS[VirtualCurrency.BTC] = CurrencySubunitsMap(
                CurrencySubunit(VirtualCurrency.BTC, 1),
                CurrencySubunit(VirtualCurrency.mBTC, 1000),
                CurrencySubunit(VirtualCurrency.uBTC, 1000000),
                CurrencySubunit(VirtualCurrency.Satoshi, 100000000, false)
        )
        CURRENCIES_SUBUNITS[VirtualCurrency.LTC] = CurrencySubunitsMap(
                CurrencySubunit(VirtualCurrency.LTC, 1),
                CurrencySubunit(VirtualCurrency.mLTC, 1000)
        )
    }
}