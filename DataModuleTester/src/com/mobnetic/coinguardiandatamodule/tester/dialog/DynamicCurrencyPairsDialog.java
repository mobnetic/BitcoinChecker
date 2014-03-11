package com.mobnetic.coinguardiandatamodule.tester.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.util.CurrencyPairsMapHelper;
import com.mobnetic.coinguardian.util.FormatUtilsBase;
import com.mobnetic.coinguardiandatamodule.tester.R;
import com.mobnetic.coinguardiandatamodule.tester.util.CheckErrorsUtils;
import com.mobnetic.coinguardiandatamodule.tester.volley.DynamicCurrencyPairsVolleyRequest;

public abstract class DynamicCurrencyPairsDialog extends AlertDialog implements OnDismissListener {

	private final RequestQueue requestQueue;
	private final Market market;
	private CurrencyPairsMapHelper currencyPairsMapHelper;
	
	private View refreshImageView;
	private TextView statusView;
	
	protected DynamicCurrencyPairsDialog(Context context, Market market, CurrencyPairsMapHelper currencyPairsMapHelper) {
		super(context);
		this.requestQueue = Volley.newRequestQueue(context);
		this.market = market;
		this.currencyPairsMapHelper = currencyPairsMapHelper;
		
		setTitle(R.string.checker_add_dynamic_currency_pairs_dialog_title);
		setOnDismissListener(this);
		setButton(BUTTON_NEUTRAL, context.getString(android.R.string.ok), (OnClickListener)null);
		
		View view = LayoutInflater.from(context).inflate(R.layout.dynamic_currency_pairs_dialog, null);
		refreshImageView = view.findViewById(R.id.refreshImageView);
		statusView = (TextView)view.findViewById(R.id.statusView);
		refreshImageView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startRefreshing();
			}
		});
		refreshStatusView(null);
		
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
		DynamicCurrencyPairsVolleyRequest request = new DynamicCurrencyPairsVolleyRequest(getContext(), market,
			new Listener<CurrencyPairsMapHelper>() {
				@Override
				public void onResponse(CurrencyPairsMapHelper currencyPairsMapHelper) {
					DynamicCurrencyPairsDialog.this.currencyPairsMapHelper = currencyPairsMapHelper;
					refreshStatusView(null);
					stopRefreshingAnim();
					onPairsUpdated(market, currencyPairsMapHelper);
//					dismiss();
				}
			}, new ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					error.printStackTrace();
					refreshStatusView(CheckErrorsUtils.parseVolleyErrorMsg(getContext(), error));
					stopRefreshingAnim();
				}
			});
		requestQueue.add(request);
	}
	
	private void refreshStatusView(String errorMsg) {
		String dateString;
		if(currencyPairsMapHelper!=null && currencyPairsMapHelper.getDate()>0)
			dateString = FormatUtilsBase.formatSameDayTimeOrDate(getContext(), currencyPairsMapHelper.getDate());
		else
			dateString = getContext().getString(R.string.checker_add_dynamic_currency_pairs_dialog_last_sync_never);
		
		statusView.setText(getContext().getString(R.string.checker_add_dynamic_currency_pairs_dialog_last_sync, dateString));
		if(currencyPairsMapHelper!=null && currencyPairsMapHelper.getPairsCount()>0)
			statusView.append("\n"+getContext().getString(R.string.checker_add_dynamic_currency_pairs_dialog_pairs, currencyPairsMapHelper.getPairsCount()));
		if(errorMsg!=null)
			statusView.append("\n"+CheckErrorsUtils.formatError(getContext(), errorMsg));
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
