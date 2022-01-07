package com.aneonex.bitcoinchecker.datamodule.model.currency

import com.aneonex.bitcoinchecker.datamodule.model.CurrencySubunit
import com.aneonex.bitcoinchecker.datamodule.model.CurrencySubunitsMap

object CurrenciesSubunits {
    val CURRENCIES_SUBUNITS = mapOf(
        VirtualCurrency.BTC to CurrencySubunitsMap(
            CurrencySubunit(VirtualCurrency.BTC, 1),
            CurrencySubunit(VirtualCurrency.mBTC, 1000),
//                CurrencySubunit(VirtualCurrency.uBTC, 1000000),
            CurrencySubunit(VirtualCurrency.Satoshi, 100000000, false)
        ),
        VirtualCurrency.LTC to CurrencySubunitsMap(
            CurrencySubunit(VirtualCurrency.LTC, 1),
            CurrencySubunit(VirtualCurrency.mLTC, 1000)
        )
    )
}