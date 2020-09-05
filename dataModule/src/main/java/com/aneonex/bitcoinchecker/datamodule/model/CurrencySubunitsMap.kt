package com.aneonex.bitcoinchecker.datamodule.model

import java.util.*

class CurrencySubunitsMap(vararg currencySubunits: CurrencySubunit) : LinkedHashMap<Long, CurrencySubunit>() {
    companion object {
//        private const val serialVersionUID = -7219011491064245859L
    }

    init {
        for (currencySubunit in currencySubunits) put(currencySubunit.subunitToUnit, currencySubunit)
    }
}