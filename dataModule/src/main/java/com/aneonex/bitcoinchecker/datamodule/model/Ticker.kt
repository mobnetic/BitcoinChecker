package com.aneonex.bitcoinchecker.datamodule.model

class Ticker {
    @kotlin.jvm.JvmField
	var bid: Double
    @kotlin.jvm.JvmField
	var ask: Double
    @kotlin.jvm.JvmField
	var vol: Double
    @kotlin.jvm.JvmField
	var high: Double
    @kotlin.jvm.JvmField
	var low: Double
    @kotlin.jvm.JvmField
	var last: Double
    @kotlin.jvm.JvmField
	var timestamp: Long

    companion object {
        const val NO_DATA = -1
    }

    init {
        bid = NO_DATA.toDouble()
        ask = NO_DATA.toDouble()
        vol = NO_DATA.toDouble()
        high = NO_DATA.toDouble()
        low = NO_DATA.toDouble()
        last = NO_DATA.toDouble()
        timestamp = NO_DATA.toLong()
    }
}