package com.aneonex.bitcoinchecker.tester.volley

import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.Ticker.Companion.NO_DATA

internal class TickerImpl: Ticker {
    override var bid: Double = NO_DATA.toDouble()
    override var ask: Double = NO_DATA.toDouble()
    override var vol: Double = NO_DATA.toDouble()
    override var volQuote: Double = NO_DATA.toDouble()
    override var high: Double = NO_DATA.toDouble()
    override var low: Double = NO_DATA.toDouble()
    override var last: Double = NO_DATA.toDouble()
    override var timestamp: Long = NO_DATA.toLong()
}