package com.aneonex.bitcoinchecker.tester.dialog

import android.content.Context
import android.content.DialogInterface
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.android.volley.NetworkResponse
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.util.CurrencyPairsMapHelper
import com.aneonex.bitcoinchecker.datamodule.util.FormatUtilsBase.formatSameDayTimeOrDate
import com.aneonex.bitcoinchecker.tester.R
import com.aneonex.bitcoinchecker.tester.databinding.DynamicCurrencyPairsDialogBinding
import com.aneonex.bitcoinchecker.tester.util.CheckErrorsUtils
import com.aneonex.bitcoinchecker.tester.util.HttpsHelper
import com.aneonex.bitcoinchecker.tester.volley.DynamicCurrencyPairsVolleyMainRequest
import com.aneonex.bitcoinchecker.tester.volley.generic.ResponseErrorListener
import com.aneonex.bitcoinchecker.tester.volley.generic.ResponseListener

abstract class DynamicCurrencyPairsDialog protected constructor(context: Context, val market: Market, currencyPairsMapHelper: CurrencyPairsMapHelper?) : AlertDialog(context), DialogInterface.OnDismissListener {
    private val binding = DynamicCurrencyPairsDialogBinding.inflate(LayoutInflater.from(context))
    private val requestQueue: RequestQueue = HttpsHelper.newRequestQueue(context)
    private var currencyPairsMapHelper: CurrencyPairsMapHelper?

    override fun onDismiss(dialog: DialogInterface) {
        requestQueue.cancelAll(this)
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

        requestQueue.add(request)
    }

    private fun refreshStatusView(url: String?, requestHeaders: Map<String, String>?, networkResponse: NetworkResponse?, responseString: String?, errorMsg: String?, error: VolleyError?) {
        val dateString = if (currencyPairsMapHelper != null && currencyPairsMapHelper!!.date > 0) formatSameDayTimeOrDate(context, currencyPairsMapHelper!!.date) else context.getString(R.string.checker_add_dynamic_currency_pairs_dialog_last_sync_never)
        binding.statusView.text = context.getString(R.string.checker_add_dynamic_currency_pairs_dialog_last_sync, dateString)
        if (currencyPairsMapHelper != null && currencyPairsMapHelper!!.size > 0) binding.statusView.append("""

    ${context.getString(R.string.checker_add_dynamic_currency_pairs_dialog_pairs, currencyPairsMapHelper!!.size)}
    """.trimIndent())
        val ssb = SpannableStringBuilder()
        if (errorMsg != null) {
            ssb.append("\n")
            ssb.append(context.getString(R.string.check_error_generic_prefix, errorMsg))
        }
        CheckErrorsUtils.formatResponseDebug(context, ssb, url, requestHeaders, networkResponse, responseString, error)
        binding.errorView.text = ssb
    }

    private fun startRefreshingAnim() {
        setCancelable(false)
        binding.refreshImageView.isEnabled = false
    }

    fun stopRefreshingAnim() {
        setCancelable(true)
        binding.refreshImageView.isEnabled = true
    }

    abstract fun onPairsUpdated(market: Market, currencyPairsMapHelper: CurrencyPairsMapHelper?)

    init {
        // setInverseBackgroundForced(true)
        this.currencyPairsMapHelper = currencyPairsMapHelper
        setTitle(R.string.checker_add_dynamic_currency_pairs_dialog_title)
        setOnDismissListener(this)
        setButton(BUTTON_NEUTRAL, context.getString(android.R.string.ok), null as DialogInterface.OnClickListener?)

        binding.refreshImageView.setOnClickListener { startRefreshing() }
        refreshStatusView(null, null, null, null, null, null)

        setView(binding.root)
    }
}