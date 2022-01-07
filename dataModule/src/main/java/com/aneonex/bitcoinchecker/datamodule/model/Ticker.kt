package com.aneonex.bitcoinchecker.datamodule.model

interface Ticker {
	var bid: Double
	var ask: Double
	var vol: Double
    var volQuote: Double
	var high: Double
	var low: Double
	var last: Double
	var timestamp: Long

	companion object {
		const val NO_DATA = -1
	}
}