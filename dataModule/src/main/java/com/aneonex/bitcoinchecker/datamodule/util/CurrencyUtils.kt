package com.aneonex.bitcoinchecker.datamodule.util

import com.aneonex.bitcoinchecker.datamodule.model.CurrencySubunit
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrenciesSubunits
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencySymbols

object CurrencyUtils {
    fun getCurrencySymbol(currency: String): String {
        return CurrencySymbols.CURRENCY_SYMBOLS[currency] ?: currency
    }

    fun getCurrencySubunit(currency: String?, subunitToUnit: Long): CurrencySubunit {
        val subunits = CurrenciesSubunits.CURRENCIES_SUBUNITS[currency]
        if (subunits != null) {
            if (subunits.containsKey(subunitToUnit)) return subunits.getValue(subunitToUnit)
        }
        return CurrencySubunit(currency ?: "", 1)
    }
}