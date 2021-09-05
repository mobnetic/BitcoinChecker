package com.aneonex.bitcoinchecker.datamodule.model

class Ticker {
	var bid: Double = NO_DATA.toDouble()
	var ask: Double = NO_DATA.toDouble()
	var vol: Double = NO_DATA.toDouble()
    var volQuote: Double = NO_DATA.toDouble()
	var high: Double = NO_DATA.toDouble()
	var low: Double = NO_DATA.toDouble()
	var last: Double = NO_DATA.toDouble()
	var timestamp: Long = NO_DATA.toLong()

	companion object {
		const val NO_DATA = -1
	}
}