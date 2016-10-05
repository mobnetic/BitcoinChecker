package com.mobnetic.coinguardiandatamodule.tester.dialog;

import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.util.CurrencyPairsMapHelper;
import com.mobnetic.coinguardian.util.FormatUtilsBase;
import com.mobnetic.coinguardiandatamodule.tester.R;
import com.mobnetic.coinguardiandatamodule.tester.util.CheckErrorsUtils;
import com.mobnetic.coinguardiandatamodule.tester.util.HttpsHelper;
import com.mobnetic.coinguardiandatamodule.tester.volley.DynamicCurrencyPairsVolleyMainRequest;
import com.mobnetic.coinguardiandatamodule.tester.volley.generic.ResponseErrorListener;
import com.mobnetic.coinguardiandatamodule.tester.volley.generic.ResponseListener;

public abstract class DynamicCurrencyPairsDialog extends AlertDialog implements OnDismissListener {

	private final RequestQueue requestQueue;
	private final Market market;
	private CurrencyPairsMapHelper currencyPairsMapHelper;
	
	private View refreshImageView;
	private TextView statusView;
	private TextView errorView;
	
	protected DynamicCurrencyPairsDialog(Context context, Market market, CurrencyPairsMapHelper currencyPairsMapHelper) {
		super(context);
		setInverseBackgroundForced(true);
		
		this.requestQueue = HttpsHelper.newRequestQueue(context);
		this.market = market;
		this.currencyPairsMapHelper = currencyPairsMapHelper;
		
		setTitle(R.string.checker_add_dynamic_currency_pairs_dialog_title);
		setOnDismissListener(this);
		setButton(BUTTON_NEUTRAL, context.getString(android.R.string.ok), (OnClickListener)null);
		
		View view = LayoutInflater.from(context).inflate(R.layout.dynamic_currency_pairs_dialog, null);
		refreshImageView = view.findViewById(R.id.refreshImageView);
		statusView = (TextView)view.findViewById(R.id.statusView);
		errorView = (TextView)view.findViewById(R.id.errorView);
		refreshImageView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startRefreshing();
			}
		});
		refreshStatusView(null, null, null, null, null, null);
		
		setView(view);
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		requestQueue.cancelAll(this);
		currencyPairsMapHelper = null;
	}
	
	private void startRefreshing() {
		setCancelable(false);
		startRefreshingAnim();
		DynamicCurrencyPairsVolleyMainRequest request = new DynamicCurrencyPairsVolleyMainRequest(getContext(), market,
			new ResponseListener<CurrencyPairsMapHelper>() {
				@Override
				public void onResponse(String url, Map<String, String> requestHeaders, NetworkResponse networkResponse, String responseString, CurrencyPairsMapHelper currencyPairsMapHelper) {
					DynamicCurrencyPairsDialog.this.currencyPairsMapHelper = currencyPairsMapHelper;
					refreshStatusView(url, requestHeaders, networkResponse, responseString, null, null);
					stopRefreshingAnim();
					onPairsUpdated(market, currencyPairsMapHelper);
//					dismiss();
				}
			}, new ResponseErrorListener() {
				@Override
				public void onErrorResponse(String url, Map<String, String> requestHeaders, NetworkResponse networkResponse, String responseString, VolleyError error) {
					error.printStackTrace();
					refreshStatusView(url, requestHeaders, networkResponse, responseString, CheckErrorsUtils.parseVolleyErrorMsg(getContext(), error), error);
					stopRefreshingAnim();
				}
			});
		request.setTag(this);
		requestQueue.add(request);
	}
	
	private void refreshStatusView(String url, Map<String, String> requestHeaders, NetworkResponse networkResponse, String responseString, String errorMsg, VolleyError error) {
		String dateString;
		if(currencyPairsMapHelper!=null && currencyPairsMapHelper.getDate()>0)
			dateString = FormatUtilsBase.formatSameDayTimeOrDate(getContext(), currencyPairsMapHelper.getDate());
		else
			dateString = getContext().getString(R.string.checker_add_dynamic_currency_pairs_dialog_last_sync_never);
		
		statusView.setText(getContext().getString(R.string.checker_add_dynamic_currency_pairs_dialog_last_sync, dateString));
		if(currencyPairsMapHelper!=null && currencyPairsMapHelper.getPairsCount()>0)
			statusView.append("\n"+getContext().getString(R.string.checker_add_dynamic_currency_pairs_dialog_pairs, currencyPairsMapHelper.getPairsCount()));
		
		SpannableStringBuilder ssb = new SpannableStringBuilder();
		
		if(errorMsg!=null) {
			ssb.append("\n");
			ssb.append(getContext().getString(R.string.check_error_generic_prefix, errorMsg));
		}
		
		CheckErrorsUtils.formatResponseDebug(getContext(), ssb, url, requestHeaders, networkResponse, responseString, error);
		errorView.setText(ssb);	
	}
	
	public void startRefreshingAnim() {
		setCancelable(false);
		refreshImageView.setEnabled(false);
	}
	
	public void stopRefreshingAnim() {
		setCancelable(true);
		refreshImageView.setEnabled(true);
	}
	
	public abstract void onPairsUpdated(Market market, CurrencyPairsMapHelper currencyPairsMapHelper);
}
