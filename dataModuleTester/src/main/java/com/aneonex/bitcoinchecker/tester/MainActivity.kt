package com.aneonex.bitcoinchecker.tester

import android.content.Context
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.android.volley.NetworkResponse
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.aneonex.bitcoinchecker.datamodule.config.MarketsConfig
import com.aneonex.bitcoinchecker.datamodule.model.*
import com.aneonex.bitcoinchecker.datamodule.util.CurrencyPairsMapHelper
import com.aneonex.bitcoinchecker.datamodule.util.FormatUtilsBase
import com.aneonex.bitcoinchecker.datamodule.util.MarketsConfigUtils.getMarketByKey
import com.aneonex.bitcoinchecker.tester.databinding.MainActivityBinding
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

class MainActivity : AppCompatActivity() {
    private lateinit var binding:  MainActivityBinding

    private inner class MarketEntry(var key: String, var name: String)  {
        override fun toString(): String {
            return name
        }
    }

    private lateinit var requestQueue: RequestQueue
    private lateinit var currencyPairsMapHelper: CurrencyPairsMapHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestQueue = HttpsHelper.newRequestQueue(this)

        refreshMarketSpinner()
        currencyPairsMapHelper = createCurrencyPairsMapHelper(this@MainActivity, selectedMarket)
        refreshCurrencySpinners()
        showResultView(true)
        binding.marketSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(arg0: AdapterView<*>?, arg1: View?, arg2: Int, arg3: Long) {
                currencyPairsMapHelper = createCurrencyPairsMapHelper(this@MainActivity, selectedMarket)
                binding.resultView.text = ""
                refreshCurrencySpinners()
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
                // do nothing
            }
        }
        binding.dynamicCurrencyPairsInfoView.setOnClickListener {
            object : DynamicCurrencyPairsDialog(this@MainActivity, selectedMarket, currencyPairsMapHelper) {
                override fun onPairsUpdated(market: Market, currencyPairsMapHelper: CurrencyPairsMapHelper?) {
                    this@MainActivity.currencyPairsMapHelper = currencyPairsMapHelper ?: createCurrencyPairsMapHelper(this@MainActivity, selectedMarket)
                    refreshCurrencySpinners()
                }
            }.show()
        }
        binding.currencyBaseSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(arg0: AdapterView<*>?, arg1: View?, arg2: Int, arg3: Long) {
                refreshCurrencyCounterSpinner()
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
                // do nothing
            }
        }
        binding.currencyCounterSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(arg0: AdapterView<*>?, arg1: View?, arg2: Int, arg3: Long) {
                refreshFuturesContractTypeSpinner()
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {
                // do nothing
            }
        }

        binding.getResultButton.setOnClickListener { newResult }
        // binding.testAllButton.setOnClickListener { testAllExchanges() }
    }

    // ====================
    // Get selected items
    // ====================
    private val selectedMarket: Market
        get() {
            val marketEntry = binding.marketSpinner.selectedItem as MarketEntry
            return getMarketByKey(marketEntry.key)
        }

    private val selectedCurrencyBase: String?
        get() = if (binding.currencyBaseSpinner.adapter == null) null else binding.currencyBaseSpinner.selectedItem.toString()
    private val selectedCurrencyCounter: String?
        get() = if (binding.currencyCounterSpinner.adapter == null) null else binding.currencyCounterSpinner.selectedItem.toString()
    private val selectedFuturesContractType: FuturesContractType
        get() = if (binding.currencyCounterSpinner.adapter == null || !binding.futuresContractTypeSpinner.isVisible) FuturesContractType.NONE
            else binding.futuresContractTypeSpinner.selectedItem as FuturesContractType

    // ====================
    // Refreshing UI
    // ====================
    private fun refreshMarketSpinner() {
/*
        val entries = arrayOfNulls<MarketEntry>(MarketsConfig.MARKETS.size)
        var i = entries.size - 1
        for (market in MarketsConfig.MARKETS.values) {
            val marketEntry = MarketEntry(market.key, market.name)
            entries[i--] = marketEntry // market.name;
        }
*/
        val entries = MarketsConfig.MARKETS.values
            .sortedBy { market -> market.name.uppercase() }
            .map { market ->  MarketEntry(market.key, market.name) }

        binding.marketSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, entries)
    }

    private fun refreshCurrencySpinners() {
        refreshCurrencyBaseSpinner()
        refreshCurrencyCounterSpinner()
        refreshDynamicCurrencyPairsView()
        val isCurrencyEmpty = selectedCurrencyBase == null || selectedCurrencyCounter == null
        binding.currencySpinnersWrapper.isVisible = !isCurrencyEmpty
        binding.dynamicCurrencyPairsWarningView.isVisible = isCurrencyEmpty
        binding.getResultButton.isVisible = !isCurrencyEmpty
    }

    private fun refreshDynamicCurrencyPairsView() {
        binding.dynamicCurrencyPairsInfoView.isEnabled = selectedMarket.getCurrencyPairsUrl(0) != null
    }

    private fun refreshCurrencyBaseSpinner() {
        if (!currencyPairsMapHelper.isEmpty()) {
            binding.currencyBaseSpinner.adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                currencyPairsMapHelper.baseAssets.toList()
            )
        } else {
            binding.currencyBaseSpinner.adapter = null
        }
    }

    private fun refreshCurrencyCounterSpinner() {
        val baseAsset = selectedCurrencyBase
        if (!currencyPairsMapHelper.isEmpty() && !baseAsset.isNullOrEmpty()) {
            binding.currencyCounterSpinner.adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                currencyPairsMapHelper.getQuoteAssets(baseAsset).toList()
            )
        } else {
            binding.currencyCounterSpinner.adapter = null
        }

        refreshFuturesContractTypeSpinner()
    }

    private fun refreshFuturesContractTypeSpinner() {
        val availableContractTypes = currencyPairsMapHelper.getAvailableFuturesContractsTypes(selectedCurrencyBase, selectedCurrencyCounter)
        var spinnerAdapter: SpinnerAdapter? = null
        if (availableContractTypes.any { it != FuturesContractType.NONE }) {
/*
            val entries = arrayOfNulls<CharSequence>(market.contractTypes.size)
            for (i in market.contractTypes.indices) {
                val contractType = market.contractTypes[i]
                entries[i] = getContractTypeShortName(contractType)
            }
 */
            spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, availableContractTypes.toTypedArray())
        }
        binding.futuresContractTypeSpinner.adapter = spinnerAdapter
        binding.futuresContractTypeSpinner.isVisible = spinnerAdapter != null
    }

    private fun showResultView(showResultView: Boolean) {
        binding.getResultButton.isEnabled = showResultView
        binding.progressBar.isVisible = !showResultView
        binding.resultView.isVisible = showResultView
    }
/*
    private fun getProperCurrencyPairs(market: Market): HashMap<String?, Array<String>>? {
        val currencyPairsMapHelper = currencyPairsMapHelper

        return if (!currencyPairsMapHelper.isEmpty())
                currencyPairsMapHelper.pa
            else market.currencyPairs
    }
*/

    // ====================
    // Get && display results
    // ====================
    private val newResult: Unit
        get() {
            val market = selectedMarket
            val currencyBase = selectedCurrencyBase
            val currencyCounter = selectedCurrencyCounter
            // val contractType = getSelectedContractType(market)
            val contractType = selectedFuturesContractType
            val pairId = currencyPairsMapHelper.getCurrencyPairId(currencyBase, currencyCounter, contractType)
            val checkerInfo = CheckerInfo(currencyBase!!, currencyCounter!!, pairId, contractType)
            val request = CheckerVolleyMainRequest(market, checkerInfo,
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
                    if (errorMsg.isNullOrEmpty()) errorMsg = CheckErrorsUtils.parseVolleyErrorMsg(this@MainActivity, error)
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
            ssb.append(getString(R.string.ticker_last, FormatUtilsBase.formatPriceWithCurrency(ticker.last, checkerInfo.currencyCounter)))
            ssb.append(createNewPriceLineIfNeeded(R.string.ticker_high, ticker.high, checkerInfo.currencyCounter))
            ssb.append(createNewPriceLineIfNeeded(R.string.ticker_low, ticker.low, checkerInfo.currencyCounter))
            ssb.append(createNewPriceLineIfNeeded(R.string.ticker_bid, ticker.bid, checkerInfo.currencyCounter))
            ssb.append(createNewPriceLineIfNeeded(R.string.ticker_ask, ticker.ask, checkerInfo.currencyCounter))
            ssb.append(createNewPriceLineIfNeeded(R.string.ticker_vol_base, ticker.vol, checkerInfo.currencyBase))
            ssb.append(createNewPriceLineIfNeeded(R.string.ticker_vol_quote, ticker.volQuote, checkerInfo.currencyCounter))
            ssb.append("""

    ${getString(R.string.ticker_timestamp, FormatUtilsBase.formatSameDayTimeOrDate(this, ticker.timestamp))}
    """.trimIndent())
        } else {
            ssb.append(getString(R.string.check_error_generic_prefix, errorMsg ?: "UNKNOWN"))
        }
        CheckErrorsUtils.formatResponseDebug(this, ssb, url, requestHeaders, networkResponse, rawResponse, error)
        binding.resultView.text = ssb
    }

    private fun createNewPriceLineIfNeeded(textResId: Int, price: Double, currency: String): String {
        return if (price <= Ticker.NO_DATA) "" else """

         ${getString(textResId, FormatUtilsBase.formatPriceWithCurrency(price, currency))}
         """.trimIndent()
    }

/*
    private fun testAllExchanges(){
        Toast.makeText(this, "Test all", Toast.LENGTH_SHORT).show()

        for (market in MarketsConfig.MARKETS.values) {
            Log.d(tag, "*** Checking: ${market.name} (${market.key}) ***")

            if(market.currencyPairs.isNullOrEmpty()){
                Log.d(tag, "No pairs, queue updating...")
                requestQueue.add(createRequestDynamicCurrencyPairs(this, market))

                continue
            }

            val map = market.currencyPairs!!
            val currencyBase = map.keys.first()
            val currencyCounter = map[currencyBase]!!.first()

            Log.d(tag, "First pair: $currencyBase/$currencyCounter")

            val currencyPairsMapHelper = CurrencyPairsMapHelper(MarketCurrencyPairsStore.getPairsForMarket(this, market.key))
            val pairId = currencyPairsMapHelper.getCurrencyPairId(currencyBase, currencyCounter)

            val contractType = if (market is FuturesMarket)
                market.contractTypes[0]
                else Futures.CONTRACT_TYPE_WEEKLY

            val checkerInfo = CheckerInfo(currencyBase, currencyCounter, pairId, contractType)
            val request = CheckerVolleyMainRequest(market, checkerInfo,
                    object : ResponseListener<TickerWrapper?>() {
                        override fun onResponse(url: String?, requestHeaders: Map<String, String>?, networkResponse: NetworkResponse?, responseString: String?, response: TickerWrapper?) {
                            handleTestExchange(market.name, url, null)
                        }
                    }, object : ResponseErrorListener() {
                override fun onErrorResponse(url: String?, requestHeaders: Map<String, String>?, networkResponse: NetworkResponse?, responseString: String?, error: VolleyError) {
//                    error.printStackTrace()
                    handleTestExchange(market.name, url, error)
                }
            })
            requestQueue.add(request)
        }
    }

    private fun handleTestExchange(marketName: String, url: String?, error: VolleyError?) {
//        showResultView(true)

        val sb = StringBuilder()

        sb.append("TEST_RESULT [$marketName]: ")

        if(error == null)
            sb.append("Success")
        else
            sb.append("FAILED")

        sb.append(": ")
        sb.append(url ?: "Unknown uri")

        if(error?.cause != null)
            sb.append("\nDetails: ${error.cause}")

        Log.d(tag, sb.toString())
    }

    private fun createRequestDynamicCurrencyPairs(context: Context, market: Market): DynamicCurrencyPairsVolleyMainRequest {
        return DynamicCurrencyPairsVolleyMainRequest(context, market,
                object : ResponseListener<CurrencyPairsMapHelper?>() {
                    override fun onResponse(url: String?, requestHeaders: Map<String, String>?, networkResponse: NetworkResponse?, responseString: String?, response: CurrencyPairsMapHelper?) {
                        handleTestExchange(market.name, url, null)
                    }
                }, object : ResponseErrorListener() {
            override fun onErrorResponse(url: String?, requestHeaders: Map<String, String>?, networkResponse: NetworkResponse?, responseString: String?, error: VolleyError) {
                handleTestExchange(market.name, url, error)
            }
        })
    }
 */

    companion object {
        private fun createCurrencyPairsMapHelper(context: Context, market: Market): CurrencyPairsMapHelper {
            val pairsFromStore = MarketCurrencyPairsStore.getPairsForMarket(context, market.key)
            return if(pairsFromStore != null)
                CurrencyPairsMapHelper(pairsFromStore)
            else
                CurrencyPairsMapHelper(market.currencyPairs)
        }
    }
}