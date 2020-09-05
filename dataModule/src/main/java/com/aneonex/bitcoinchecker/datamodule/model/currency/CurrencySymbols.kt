package com.aneonex.bitcoinchecker.datamodule.model.currency

import java.util.*

object CurrencySymbols {
    val CURRENCY_SYMBOLS = HashMap<String, String>()

    init {
        CURRENCY_SYMBOLS[Currency.USD] = "$"
        CURRENCY_SYMBOLS[Currency.PLN] = "zł"
        CURRENCY_SYMBOLS[Currency.CNY] = "¥"
        CURRENCY_SYMBOLS[Currency.EUR] = "€"
        //		CURRENCY_SYMBOLS.put(CAD, "$");
        CURRENCY_SYMBOLS[Currency.GBP] = "£"
        CURRENCY_SYMBOLS[Currency.CHF] = "Fr"
        CURRENCY_SYMBOLS[Currency.RUB] = "р."
        CURRENCY_SYMBOLS[Currency.RUR] = "р."
        CURRENCY_SYMBOLS[Currency.AUD] = "$"
        CURRENCY_SYMBOLS[Currency.SEK] = "kr"
        CURRENCY_SYMBOLS[Currency.DKK] = "kr"
        CURRENCY_SYMBOLS[Currency.HKD] = "$"
        CURRENCY_SYMBOLS[Currency.SGD] = "$"
        CURRENCY_SYMBOLS[Currency.THB] = "฿"
        CURRENCY_SYMBOLS[Currency.NZD] = "$"
        CURRENCY_SYMBOLS[Currency.JPY] = "¥"
        CURRENCY_SYMBOLS[Currency.BRL] = "R$"
        CURRENCY_SYMBOLS[Currency.KRW] = "₩"
        CURRENCY_SYMBOLS[Currency.AFN] = "؋"
        CURRENCY_SYMBOLS[Currency.ALL] = "L"
        CURRENCY_SYMBOLS[Currency.DZD] = "د.ج"
        CURRENCY_SYMBOLS[Currency.AOA] = "Kz"
        CURRENCY_SYMBOLS[Currency.ARS] = "$"
        CURRENCY_SYMBOLS[Currency.AMD] = "դր."
        CURRENCY_SYMBOLS[Currency.AWG] = "ƒ"
        CURRENCY_SYMBOLS[Currency.AZN] = "m"
        CURRENCY_SYMBOLS[Currency.BSD] = "$"
        CURRENCY_SYMBOLS[Currency.BHD] = "ب.د"
        CURRENCY_SYMBOLS[Currency.BDT] = "৳"
        CURRENCY_SYMBOLS[Currency.BBD] = "$"
        CURRENCY_SYMBOLS[Currency.BYR] = "Br"
        CURRENCY_SYMBOLS[Currency.BZD] = "$"
        CURRENCY_SYMBOLS[Currency.BMD] = "$"
        CURRENCY_SYMBOLS[Currency.BTN] = "Nu."
        CURRENCY_SYMBOLS[Currency.BOB] = "Bs."
        CURRENCY_SYMBOLS[Currency.BAM] = "КМ"
        CURRENCY_SYMBOLS[Currency.BWP] = "P"
        CURRENCY_SYMBOLS[Currency.BND] = "$"
        CURRENCY_SYMBOLS[Currency.BGN] = "лв"
        CURRENCY_SYMBOLS[Currency.BIF] = "Fr"
        CURRENCY_SYMBOLS[Currency.TRY] = "TL"
        CURRENCY_SYMBOLS[Currency.ZAR] = "R"
        CURRENCY_SYMBOLS[Currency.IDR] = "Rp"
    }
}