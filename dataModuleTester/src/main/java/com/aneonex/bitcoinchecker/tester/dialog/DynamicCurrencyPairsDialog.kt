package com.aneonex.bitcoinchecker.tester.dialog

import androidx.appcompat.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.android.volley.NetworkResponse
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.util.CurrencyPairsMapHelper
import com.aneonex.bitcoinchecker.datamodule.util.FormatUtilsBase.formatSameDayTimeOrDate
import com.aneonex.bitcoinchecker.tester.R
import com.aneonex.bitcoinchecker.tester.util.CheckErrorsUtils
import com.aneonex.bitcoinchecker.tester.util.HttpsHelper
import com.aneonex.bitcoinchecker.tester.volley.DynamicCurrencyPairsVolleyMainRequest
import com.aneonex.bitcoinchecker.tester.volley.generic.ResponseErrorListener
import com.aneonex.bitcoinchecker.tester.volley.generic.ResponseListener

abstract class DynamicCurrencyPairsDialog protected constructor(context: Context, market: Market, currencyPairsMapHelper: CurrencyPairsMapHelper?) : AlertDialog(context), DialogInterface.OnDismissListener {
    private val requestQueue: RequestQueue?
    private val market: Market
    private var currencyPairsMapHelper: CurrencyPairsMapHelper?
    private val refreshImageView: View
    private val statusView: TextView
    private val errorView: TextView
    override fun onDismiss(dialog: DialogInterface) {
        requestQueue!!.cancelAll(this)
        currencyPairsMapHelper = null
    }

    private fun startRefreshing() {
        setCancelable(false)
        startRefreshingAnim()
        val request = DynamicCurrencyPairsVolleyMainRequest(context, market,
                object : ResponseListener<CurrencyPairsMapHelper?>() {
                    override fun onResponse(url: String?, requestHeaders: Map<String, String>?, networkResponse: NetworkResponse?, responseString: String?, response: CurrencyPairsMapHelper?) {
                        this@DynamicCurrencyPairsDialog.currencyPairsMapHelper = response
                        refreshStatusView(url, requestHeaders, networkResponse, responseString, null, null)
                        stopRefreshingAnim()
                        onPairsUpdated(market, currencyPairsMapHelper)
                        //					dismiss();
                    }
                }, object : ResponseErrorListener() {
            override fun onErrorResponse(url: String?, requestHeaders: Map<String, String>?, networkResponse: NetworkResponse?, responseString: String?, error: VolleyError) {
                error.printStackTrace()
                refreshStatusView(url, requestHeaders, networkResponse, responseString, CheckErrorsUtils.parseVolleyErrorMsg(context, error), error)
                stopRefreshingAnim()
            }
        })
        request.tag = this
        requestQueue!!.add(request)
    }

    private fun refreshStatusView(url: String?, requestHeaders: Map<String, String>?, networkResponse: NetworkResponse?, responseString: String?, errorMsg: String?, error: VolleyError?) {
        val dateString = if (currencyPairsMapHelper != null && currencyPairsMapHelper!!.date > 0) formatSameDayTimeOrDate(context, currencyPairsMapHelper!!.date) else context.getString(R.string.checker_add_dynamic_currency_pairs_dialog_last_sync_never)
        statusView.text = context.getString(R.string.checker_add_dynamic_currency_pairs_dialog_last_sync, dateString)
        if (currencyPairsMapHelper != null && currencyPairsMapHelper!!.pairsCount > 0) statusView.append("""

    ${context.getString(R.string.checker_add_dynamic_currency_pairs_dialog_pairs, currencyPairsMapHelper!!.pairsCount)}
    """.trimIndent())
        val ssb = SpannableStringBuilder()
        if (errorMsg != null) {
            ssb.append("\n")
            ssb.append(context.getString(R.string.check_error_generic_prefix, errorMsg))
        }
        CheckErrorsUtils.formatResponseDebug(context, ssb, url, requestHeaders, networkResponse, responseString, error)
        errorView.text = ssb
    }

    private fun startRefreshingAnim() {
        setCancelable(false)
        refreshImageView.isEnabled = false
    }

    fun stopRefreshingAnim() {
        setCancelable(true)
        refreshImageView.isEnabled = true
    }

    abstract fun onPairsUpdated(market: Market, currencyPairsMapHelper: CurrencyPairsMapHelper?)

    init {
        // setInverseBackgroundForced(true)
        requestQueue = HttpsHelper.newRequestQueue(context)
        this.market = market
        this.currencyPairsMapHelper = currencyPairsMapHelper
        setTitle(R.string.checker_add_dynamic_currency_pairs_dialog_title)
        setOnDismissListener(this)
        setButton(BUTTON_NEUTRAL, context.getString(android.R.string.ok), null as DialogInterface.OnClickListener?)
        val view = LayoutInflater.from(context).inflate(R.layout.dynamic_currency_pairs_dialog, null)
        refreshImageView = view.findViewById(R.id.refreshImageView)
        statusView = view.findViewById<View>(R.id.statusView) as TextView
        errorView = view.findViewById<View>(R.id.errorView) as TextView
        refreshImageView.setOnClickListener { startRefreshing() }
        refreshStatusView(null, null, null, null, null, null)
        setView(view)
    }
}