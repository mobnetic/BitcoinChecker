package com.mobnetic.coinguardiandatamodule.tester;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.mobnetic.coinguardian.config.MarketsConfig;
import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.util.CurrencyUtils;
import com.mobnetic.coinguardian.util.MarketsConfigUtils;
import com.mobnetic.coinguardiandatamodule.tester.util.HttpsHelper;
import com.mobnetic.coinguardiandatamodule.tester.volley.CheckerErrorParsedError;
import com.mobnetic.coinguardiandatamodule.tester.volley.CheckerVolleyRequest;

public class MainActivity extends Activity {

	private RequestQueue requestQueue;
	private Spinner marketSpinner;
	private Spinner currencyBaseSpinner;
	private Spinner currencyCounterSpinner;
	private View getResultButton;
	private ProgressBar progressBar;
	private TextView resultView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestQueue = Volley.newRequestQueue(this, new HurlStack(null, HttpsHelper.getMySSLSocketFactory()));
		
		setContentView(R.layout.main_activity);
		
		marketSpinner = (Spinner)findViewById(R.id.marketSpinner);
		currencyBaseSpinner = (Spinner)findViewById(R.id.currencyBaseSpinner);
		currencyCounterSpinner = (Spinner)findViewById(R.id.currencyCounterSpinner);
		getResultButton = findViewById(R.id.getResultButton);
		progressBar = (ProgressBar)findViewById(R.id.progressBar);
		resultView = (TextView)findViewById(R.id.resultView);
		
		refreshMarketSpinner();
		refreshCurrencyBaseSpinner();
		refreshCurrencyCounterSpinner();
		showResultView(true);
		
		marketSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				refreshCurrencyBaseSpinner();
				refreshCurrencyCounterSpinner();
			}
			public void onNothingSelected(AdapterView<?> arg0) {
				// do nothing
			}
		});
		currencyBaseSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				refreshCurrencyCounterSpinner();
			}
			public void onNothingSelected(AdapterView<?> arg0) {
				// do nothing
			}
		});
		
		getResultButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				getNewResult();
			}
		});
	}
	
	
	// ====================
	// Get selected items
	// ====================
	private Market getSelectedMarket() {
		return MarketsConfigUtils.getMarketById(marketSpinner.getSelectedItemPosition());
	}
	
	private String getSelectedCurrencyBase(Market market) {
		return (String)market.currencyPairs.keySet().toArray()[currencyBaseSpinner.getSelectedItemPosition()];
	}
	
	private String getSelectedCurrencyCounter(Market market, String currencyBase) {
		return (String) market.currencyPairs.get(currencyBase)[currencyCounterSpinner.getSelectedItemPosition()];
	}
	
	private void refreshMarketSpinner() {
		final CharSequence[] entries = new String[MarketsConfig.MARKETS.size()];
		int i=0;
		for(Market market : MarketsConfig.MARKETS.values()) {
			entries[i++] = market.name;
		}
		
		marketSpinner.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, entries));
	}
	
	// ====================
	// Refreshing UI
	// ====================
	private void refreshCurrencyBaseSpinner() {
		Market selectedMarket = getSelectedMarket();
		final CharSequence[] entries = new CharSequence[selectedMarket.currencyPairs.size()];
		int i=0;
		for(String currency : selectedMarket.currencyPairs.keySet()) {
			entries[i++] = currency;
		}
		currencyBaseSpinner.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, entries));
	}
	
	private void refreshCurrencyCounterSpinner() {
		Market selectedMarket = getSelectedMarket();
		String selectedCurrencyBase = getSelectedCurrencyBase(selectedMarket);
		final CharSequence[] entries = selectedMarket.currencyPairs.get(selectedCurrencyBase).clone();
		currencyCounterSpinner.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, entries));
	}

	private void showResultView(boolean showResultView) {
		getResultButton.setEnabled(showResultView);
		progressBar.setVisibility(showResultView ? View.GONE : View.VISIBLE);
		resultView.setVisibility(showResultView ? View.VISIBLE : View.GONE);
	}
	
	// ====================
	// Get && display results
	// ====================
	private void getNewResult() {
		final Market market = getSelectedMarket();
		final String currencyBase = getSelectedCurrencyBase(market);
		final String currencyCounter = getSelectedCurrencyCounter(market, currencyBase);
		final CheckerInfo checkerInfo = new CheckerInfo(currencyBase, currencyCounter);
		Request<?> request = new CheckerVolleyRequest(market, checkerInfo, new Listener<Ticker>() {
			@Override
			public void onResponse(Ticker ticker) {
				handleNewResult(checkerInfo, ticker, null);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				error.printStackTrace();
				
				String errorMsg;
				if(error instanceof NetworkError)
					errorMsg = getString(R.string.check_error_network);
				else if(error instanceof TimeoutError)
					errorMsg = getString(R.string.check_error_timeout);
				else if(error instanceof CheckerErrorParsedError && !TextUtils.isEmpty(((CheckerErrorParsedError)error).getErrorMsg()))
					errorMsg = ((CheckerErrorParsedError)error).getErrorMsg();
				else if(error instanceof ServerError)
					errorMsg = getString(R.string.check_error_server);
				else if(error instanceof ParseError)
					errorMsg = getString(R.string.check_error_parse);
				else
					errorMsg = getString(R.string.check_error_unknown);
					
				handleNewResult(checkerInfo, null, errorMsg);
			}
		});
		requestQueue.add(request);
		showResultView(false);
	}
	
	private void handleNewResult(CheckerInfo checkerInfo, Ticker ticker, String errorMsg) {
		showResultView(true);
		String text = "";
		
		if (ticker!=null) {
			text += getString(R.string.ticker_last, formatPriceWithCurrency(ticker.last, checkerInfo.getCurrencyCounter()));
			text += createNewPriceLineIfNeeded(R.string.ticker_high, ticker.high, checkerInfo.getCurrencyCounter());
			text += createNewPriceLineIfNeeded(R.string.ticker_low, ticker.low, checkerInfo.getCurrencyCounter());
			text += createNewPriceLineIfNeeded(R.string.ticker_bid, ticker.bid, checkerInfo.getCurrencyCounter());
			text += createNewPriceLineIfNeeded(R.string.ticker_ask, ticker.ask, checkerInfo.getCurrencyCounter());
			text += createNewPriceLineIfNeeded(R.string.ticker_vol, ticker.vol, checkerInfo.getCurrencyBase());
			text += "\n"+getString(R.string.ticker_timestamp, formatSameDayTimeOrDate(this, ticker.timestamp));
		} else {
			text = getString(R.string.check_error_generic_prefix, errorMsg!=null ? errorMsg : "UNKNOWN");
		}
		
		resultView.setText(text);
	}
	
	private String formatPriceWithCurrency(double price, String currency) {
		return String.valueOf(price)+" "+CurrencyUtils.getCurrencySymbol(currency);
	}
	
	private String createNewPriceLineIfNeeded(int textResId, double price, String currency) {
		if(price<=Ticker.NO_DATA)
			return "";
		
		return "\n"+getString(textResId, formatPriceWithCurrency(price, currency));
	}
	
	public static String formatSameDayTimeOrDate(Context context, long time) {
		if (DateUtils.isToday(time)) {
	        return DateFormat.getTimeFormat(context).format(new Date(time));
	    } else {
	    	return DateFormat.getDateFormat(context).format(new Date(time));
	    }
	}
}
