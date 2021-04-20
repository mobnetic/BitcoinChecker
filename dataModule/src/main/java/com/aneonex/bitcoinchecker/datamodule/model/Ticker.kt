package com.aneonex.bitcoinchecker.datamodule.model

class Ticker {
    companion object {
        const val NO_DATA = -1
    }

    @kotlin.jvm.JvmField
	var bid: Double = NO_DATA.toDouble()

    @kotlin.jvm.JvmField
	var ask: Double = NO_DATA.toDouble()

    @kotlin.jvm.JvmField
	var vol: Double = NO_DATA.toDouble()

    @kotlin.jvm.JvmField
    var volQuote: Double = NO_DATA.toDouble()

    @kotlin.jvm.JvmField
	var high: Double = NO_DATA.toDouble()

    @kotlin.jvm.JvmField
	var low: Double = NO_DATA.toDouble()

    @kotlin.jvm.JvmField
	var last: Double = NO_DATA.toDouble()

    @kotlin.jvm.JvmField
	var timestamp: Long = NO_DATA.toLong()
}