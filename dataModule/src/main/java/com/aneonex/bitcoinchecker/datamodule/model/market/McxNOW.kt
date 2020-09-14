package com.aneonex.bitcoinchecker.datamodule.model.market

import android.text.TextUtils
import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import com.aneonex.bitcoinchecker.datamodule.model.currency.VirtualCurrency
import com.aneonex.bitcoinchecker.datamodule.util.XmlParserUtils
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

class McxNOW : Market(NAME, TTS_NAME, null) {
    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        return String.format(URL, checkerInfo.currencyBase)
    }

    @Throws(Exception::class)
    override fun parseTicker(requestId: Int, responseString: String, ticker: Ticker, checkerInfo: CheckerInfo) {
        val db = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val `is` = InputSource()
        `is`.characterStream = StringReader(responseString)
        val doc = db.parse(`is`)
        ticker.bid = getFirstPriceFromOrder(doc, "buy")
        ticker.ask = getFirstPriceFromOrder(doc, "sell")
        ticker.vol = XmlParserUtils.getDoubleNodeValue(XmlParserUtils.getFirstElementByTagName(doc, "curvol"))
        ticker.high = XmlParserUtils.getDoubleNodeValue(XmlParserUtils.getFirstElementByTagName(doc, "priceh"))
        ticker.low = XmlParserUtils.getDoubleNodeValue(XmlParserUtils.getFirstElementByTagName(doc, "pricel"))
        ticker.last = XmlParserUtils.getDoubleNodeValue(XmlParserUtils.getFirstElementByTagName(doc, "lprice"))
    }

    @Throws(Exception::class)
    private fun getFirstPriceFromOrder(doc: Document, arrayName: String): Double {
        val arrayNode = XmlParserUtils.getFirstElementByTagName(doc, arrayName)
        if (arrayNode != null) {
            val orderNodes = (arrayNode as Element).getElementsByTagName("o")
            if (orderNodes != null && orderNodes.length > 1) {
                val orderNode = orderNodes.item(1)
                if (orderNode != null && orderNode is Element) {
                    val priceNodes = orderNode.getElementsByTagName("p")
                    if (priceNodes != null && priceNodes.length > 0) {
                        return XmlParserUtils.getDoubleNodeValue(priceNodes.item(0))
                    }
                }
            }
        }
        return Ticker.NO_DATA.toDouble()
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairs(requestId: Int, responseString: String, pairs: MutableList<CurrencyPairInfo>) {
        val db = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val `is` = InputSource()
        `is`.characterStream = StringReader(responseString)
        val doc = db.parse(`is`)
        val nodes = doc.getElementsByTagName("cur")
        if (nodes != null) {
            for (i in 0 until nodes.length) {
                val node = nodes.item(i) as Element?
                if (node != null) {
                    val currency = node.getAttribute("tla")
                    if (!TextUtils.isEmpty(currency) && VirtualCurrency.BTC != currency) pairs.add(CurrencyPairInfo(currency, VirtualCurrency.BTC, null))
                }
            }
        }
    }

    companion object {
        private const val NAME = "McxNOW"
        private const val TTS_NAME = "MCX now"
        private const val URL = "https://mcxnow.com/orders?cur=%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://mcxnow.com/current"
    }
}