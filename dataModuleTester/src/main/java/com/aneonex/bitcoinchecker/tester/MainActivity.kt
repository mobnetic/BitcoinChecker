package com.aneonex.bitcoinchecker.tester

import android.app.Activity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.aneonex.bitcoinchecker.datamodule.config.MarketsConfig
import com.aneonex.bitcoinchecker.datamodule.model.*
import com.aneonex.bitcoinchecker.datamodule.model.Futures.getContractTypeShortName
import com.aneonex.bitcoinchecker.datamodule.util.CurrencyPairsMapHelper
import com.aneonex.bitcoinchecker.datamodule.util.FormatUtilsBase.formatPriceWithCurrency
import com.aneonex.bitcoinchecker.datamodule.util.FormatUtilsBase.formatSameDayTimeOrDate
import com.aneonex.bitcoinchecker.datamodule.util.MarketsConfigUtils.getMarketByKey
import com.aneonex.bitcoinchecker.tester.dialog.DynamicCurrencyPairsDialog
import com.aneonex.bitcoinchecker.tester.util.CheckErrorsUtils
import com.aneonex.bitcoinchecker.tester.util.HttpsHelper
import com.aneonex.bitcoinchecker.tester.util.MarketCurrencyPairsStore
import com.aneonex.bitcoinchecker.tester.volley.CheckerErrorParsedError
import com.aneonex.bitcoinchecker.tester.volley.CheckerVolleyMainRequest
import com.aneonex.bitcoinchecker.tester.volley.CheckerVolleyMainRequest.TickerWrapper
import com.aneonex.bitcoinchecker.tester.volley.generic.ResponseErrorListener
import com.aneonex.bitcoinchecker.tester.volley.generic.ResponseListener
import java.util.*

class MainActivity : Activity() {
    private inner class MarketEntry(var key: String, var name: String) : Comparable<MarketEntry> {
        override fun toString(): String {
            return name
        }

        override fun compareTo(other: MarketEntry): Int {
            return name.compareTo(other.name)
        }
    }

    private lateinit var requestQueue: RequestQueue
    private lateinit var marketSpinner: Spinner
    private lateinit var currencySpinnersWrapper: View
    private lateinit var dynamicCurrencyPairsWarningView: View
    private lateinit var dynamicCurrencyPairsInfoView: View
    private lateinit var currencyBaseSpinner: Spinner
    private lateinit var currencyCounterSpinner: Spinner
    private lateinit var futuresContractTypeSpinner: Spinner
    private lateinit var getResultButton: View
    private lateinit var progressBar: ProgressBar
    private lateinit var resultView: TextView

    private var currencyPairsMapHelper: CurrencyPairsMapHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestQueue = HttpsHelper.newRequestQueue(this)
        setContentView(R.layout.main_activity)
        marketSpinner = findViewById<View>(R.id.marketSpinner) as Spinner
        currencySpinnersWrapper = findViewById(R.id.currencySpinnersWrapper)
        dynamicCurrencyPairsWarningView = findViewById(R.id.dynamicCurrencyPairsWarningView)
        dynamicCurrencyPairsInfoView = findViewById(R.id.dynamicCurrencyPairsInfoView)
        currencyBaseSpinner = findViewById<View>(R.id.currencyBaseSpinner) as Spinner
        currencyCounterSpinner = findViewById<View>(R.id.currencyCounterSpinner) as Spinner
        futuresContractTypeSpinner = findViewById<View>(R.id.futuresContractTypeSpinner) as Spinner
        getResultButton = findViewById(R.id.getResultButton)
        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
        resultView = findViewById<View>(R.id.resultView) as TextView
        refreshMarketSpinner()
        val market = selectedMarket
        currencyPairsMapHelper = CurrencyPairsMapHelper(MarketCurrencyPairsStore.getPairsForMarket(this, selectedMarket.key))
        refreshCurrencySpinners(market)
        refreshFuturesContractTypeSpinner(market)
        showResultView(true)
        marketSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(arg0: AdapterView<*>?, arg1: View, arg2: Int, arg3: Long) {
                val selectedMarket = selectedMarket
                currencyPairsMapHelper = CurrencyPairsMapHelper(MarketCurrencyPairsStore.getPairsForMarket(this@MainActivity, selectedMarket.key))
                refreshCurrencySpinners(selectedMarket)
                refreshFuturesContractTypeSpinner(selectedMarket)
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
                // do nothing
            }
        }
        dynamicCurrencyPairsInfoView.setOnClickListener {
            object : DynamicCurrencyPairsDialog(this@MainActivity, selectedMarket, currencyPairsMapHelper) {
                override fun onPairsUpdated(market: Market, currencyPairsMapHelper: CurrencyPairsMapHelper?) {

                    this@MainActivity.currencyPairsMapHelper = currencyPairsMapHelper
                    refreshCurrencySpinners(market)
                }
            }.show()
        }
        currencyBaseSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(arg0: AdapterView<*>?, arg1: View, arg2: Int, arg3: Long) {
                refreshCurrencyCounterSpinner(selectedMarket)
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
                // do nothing
            }
        }
        getResultButton.setOnClickListener { newResult }
    }

    // ====================
    // Get selected items
    // ====================
    private val selectedMarket: Market
        get() {
            val marketEntry = marketSpinner.selectedItem as MarketEntry
            return getMarketByKey(marketEntry.key)
        }
    private val selectedCurrencyBase: String?
        get() = if (currencyBaseSpinner.adapter == null) null else currencyBaseSpinner.selectedItem.toString()
    private val selectedCurrencyCounter: String?
        get() = if (currencyCounterSpinner.adapter == null) null else currencyCounterSpinner.selectedItem.toString()

    private fun getSelectedContractType(market: Market): Int {
        if (market is FuturesMarket) {
            val selection = futuresContractTypeSpinner.selectedItemPosition
            return market.contractTypes[selection]
        }
        return Futures.CONTRACT_TYPE_WEEKLY
    }

    // ====================
    // Refreshing UI
    // ====================
    private fun refreshMarketSpinner() {
        val entries = arrayOfNulls<MarketEntry>(MarketsConfig.MARKETS.size)
        var i = entries.size - 1
        for (market in MarketsConfig.MARKETS.values) {
            val marketEntry = MarketEntry(market.key, market.name)
            entries[i--] = marketEntry // market.name;
        }
        Arrays.sort(entries)
        marketSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, entries)
    }

    private fun refreshCurrencySpinners(market: Market) {
        refreshCurrencyBaseSpinner(market)
        refreshCurrencyCounterSpinner(market)
        refreshDynamicCurrencyPairsView(market)
        val isCurrencyEmpty = selectedCurrencyBase == null || selectedCurrencyCounter == null
        currencySpinnersWrapper.visibility = if (isCurrencyEmpty) View.GONE else View.VISIBLE
        dynamicCurrencyPairsWarningView.visibility = if (isCurrencyEmpty) View.VISIBLE else View.GONE
        getResultButton.visibility = if (isCurrencyEmpty) View.GONE else View.VISIBLE
    }

    private fun refreshDynamicCurrencyPairsView(market: Market) {
        dynamicCurrencyPairsInfoView.visibility = if (market.getCurrencyPairsUrl(0) != null) View.VISIBLE else View.GONE
    }

    private fun refreshCurrencyBaseSpinner(market: Market) {
        val currencyPairs = getProperCurrencyPairs(market)
        if (currencyPairs != null && currencyPairs.size > 0) {
            val entries = arrayOfNulls<CharSequence>(currencyPairs.size)
            var i = 0
            for (currency in currencyPairs.keys) {
                entries[i++] = currency
            }
            currencyBaseSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, entries)
        } else {
            currencyBaseSpinner.adapter = null
        }
    }

    private fun refreshCurrencyCounterSpinner(market: Market) {
        val currencyPairs = getProperCurrencyPairs(market)
        if (currencyPairs != null && currencyPairs.size > 0) {
            val selectedCurrencyBase = selectedCurrencyBase
            val entries = currencyPairs[selectedCurrencyBase]!!.clone()
            currencyCounterSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, entries)
        } else {
            currencyCounterSpinner.adapter = null
        }
    }

    private fun refreshFuturesContractTypeSpinner(market: Market) {
        var spinnerAdapter: SpinnerAdapter? = null
        if (market is FuturesMarket) {
            val entries = arrayOfNulls<CharSequence>(market.contractTypes.size)
            for (i in market.contractTypes.indices) {
                val contractType = market.contractTypes[i]
                entries[i] = getContractTypeShortName(contractType)
            }
            spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, entries)
        }
        futuresContractTypeSpinner.adapter = spinnerAdapter
        futuresContractTypeSpinner.visibility = if (spinnerAdapter != null) View.VISIBLE else View.GONE
    }

    private fun showResultView(showResultView: Boolean) {
        getResultButton.isEnabled = showResultView
        progressBar.visibility = if (showResultView) View.GONE else View.VISIBLE
        resultView.visibility = if (showResultView) View.VISIBLE else View.GONE
    }

    private fun getProperCurrencyPairs(market: Market): HashMap<String?, Array<CharSequence>>? {
        val currencyPairsMapHelper = currencyPairsMapHelper
        return if (currencyPairsMapHelper?.currencyPairs != null && currencyPairsMapHelper.currencyPairs.size > 0)
                currencyPairsMapHelper.currencyPairs
            else market.currencyPairs
    }

    // ====================
    // Get && display results
    // ====================
    private val newResult: Unit
        get() {
            val market = selectedMarket
            val currencyBase = selectedCurrencyBase
            val currencyCounter = selectedCurrencyCounter
            val pairId = if (currencyPairsMapHelper != null) currencyPairsMapHelper!!.getCurrencyPairId(currencyBase, currencyCounter) else null
            val contractType = getSelectedContractType(market)
            val checkerInfo = CheckerInfo(currencyBase!!, currencyCounter!!, pairId, contractType)
            val request: Request<*> = CheckerVolleyMainRequest(market, checkerInfo,
                    object : ResponseListener<TickerWrapper?>() {
                override fun onResponse(url: String?, requestHeaders: Map<String, String>?, networkResponse: NetworkResponse?, responseString: String?, response: TickerWrapper?) {
                    handleNewResult(checkerInfo, response?.ticker, url, requestHeaders, networkResponse, responseString, null, null)
                }
            }, object : ResponseErrorListener() {
                override fun onErrorResponse(url: String?, requestHeaders: Map<String, String>?, networkResponse: NetworkResponse?, responseString: String?, error: VolleyError) {
                    error.printStackTrace()
                    var errorMsg: String? = null
                    if (error is CheckerErrorParsedError) {
                        errorMsg = error.errorMsg
                    }
                    if (TextUtils.isEmpty(errorMsg)) errorMsg = CheckErrorsUtils.parseVolleyErrorMsg(this@MainActivity, error)
                    handleNewResult(checkerInfo, null, url, requestHeaders, networkResponse, responseString, errorMsg, error)
                }
            })
            requestQueue.add(request)
            showResultView(false)
        }

    private fun handleNewResult(checkerInfo: CheckerInfo, ticker: Ticker?, url: String?, requestHeaders: Map<String, String>?, networkResponse: NetworkResponse?, rawResponse: String?, errorMsg: String?, error: VolleyError?) {
        showResultView(true)
        val ssb = SpannableStringBuilder()
        if (ticker != null) {
            ssb.append(getString(R.string.ticker_last, formatPriceWithCurrency(ticker.last, checkerInfo.currencyCounter)))
            ssb.append(createNewPriceLineIfNeeded(R.string.ticker_high, ticker.high, checkerInfo.currencyCounter))
            ssb.append(createNewPriceLineIfNeeded(R.string.ticker_low, ticker.low, checkerInfo.currencyCounter))
            ssb.append(createNewPriceLineIfNeeded(R.string.ticker_bid, ticker.bid, checkerInfo.currencyCounter))
            ssb.append(createNewPriceLineIfNeeded(R.string.ticker_ask, ticker.ask, checkerInfo.currencyCounter))
            ssb.append(createNewPriceLineIfNeeded(R.string.ticker_vol, ticker.vol, checkerInfo.currencyBase))
            ssb.append("""

    ${getString(R.string.ticker_timestamp, formatSameDayTimeOrDate(this, ticker.timestamp))}
    """.trimIndent())
        } else {
            ssb.append(getString(R.string.check_error_generic_prefix, errorMsg ?: "UNKNOWN"))
        }
        CheckErrorsUtils.formatResponseDebug(this, ssb, url, requestHeaders, networkResponse, rawResponse, error)
        resultView.text = ssb
    }

    private fun createNewPriceLineIfNeeded(textResId: Int, price: Double, currency: String): String {
        return if (price <= Ticker.NO_DATA) "" else """

     ${getString(textResId, formatPriceWithCurrency(price, currency))}
     """.trimIndent()
    }
}