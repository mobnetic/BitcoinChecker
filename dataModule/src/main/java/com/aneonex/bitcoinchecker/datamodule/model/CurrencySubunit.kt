package com.aneonex.bitcoinchecker.datamodule.model

class CurrencySubunit(
    val name: String,
    val subunitToUnit: Long,
    val allowDecimal: Boolean = true
)