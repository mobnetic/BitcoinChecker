package com.mobnetic.coinguardiandatamodule.tester;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.mobnetic.coinguardian.config.MarketsConfig;
import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.util.CurrencyPairsMapHelper;
import com.mobnetic.coinguardian.util.FormatUtilsBase;
import com.mobnetic.coinguardian.util.MarketsConfigUtils;
import com.mobnetic.coinguardiandatamodule.tester.dialog.DynamicCurrencyPairsDialog;
import com.mobnetic.coinguardiandatamodule.tester.util.CheckErrorsUtils;
import com.mobnetic.coinguardiandatamodule.tester.util.HttpsHelper;
import com.mobnetic.coinguardiandatamodule.tester.util.MarketCurrencyPairsStore;
import com.mobnetic.coinguardiandatamodule.tester.volley.CheckerErrorParsedError;
import com.mobnetic.coinguardiandatamodule.tester.volley.CheckerVolleyMainRequest;
import com.mobnetic.coinguardiandatamodule.tester.volley.CheckerVolleyMainRequest.TickerWithRawResponse;

public class MainActivity extends Activity {

	private RequestQueue requestQueue;
	private Spinner marketSpinner;
	private View currencySpinnersWrapper;
	private View dynamicCurrencyPairsWarningView;
	private View dynamicCurrencyPairsInfoView;
	private Spinner currencyBaseSpinner;
	private Spinner currencyCounterSpinner;
	private View getResultButton;
	private ProgressBar progressBar;
	private TextView resultView;
	
	private CurrencyPairsMapHelper currencyPairsMapHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestQueue = Volley.newRequestQueue(this, new HurlStack(null, HttpsHelper.getMySSLSocketFactory()));
		
		setContentView(R.layout.main_activity);
		
		marketSpinner = (Spinner)findViewById(R.id.marketSpinner);
		currencySpinnersWrapper = findViewById(R.id.currencySpinnersWrapper);
		dynamicCurrencyPairsWarningView = findViewById(R.id.dynamicCurrencyPairsWarningView);
		dynamicCurrencyPairsInfoView = findViewById(R.id.dynamicCurrencyPairsInfoView);
		currencyBaseSpinner = (Spinner)findViewById(R.id.currencyBaseSpinner);
		currencyCounterSpinner = (Spinner)findViewById(R.id.currencyCounterSpinner);
		getResultButton = findViewById(R.id.getResultButton);
		progressBar = (ProgressBar)findViewById(R.id.progressBar);
		resultView = (TextView)findViewById(R.id.resultView);
		
		refreshMarketSpinner();
		currencyPairsMapHelper = new CurrencyPairsMapHelper(MarketCurrencyPairsStore.getPairsForMarket(this, getSelectedMarket().key));
		refreshCurrencySpinners(getSelectedMarket());
		showResultView(true);
		
		marketSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				final Market selectedMarket = getSelectedMarket();
				currencyPairsMapHelper = new CurrencyPairsMapHelper(MarketCurrencyPairsStore.getPairsForMarket(MainActivity.this, selectedMarket.key));
				refreshCurrencySpinners(selectedMarket);
			}
			public void onNothingSelected(AdapterView<?> arg0) {
				// do nothing
			}
		});
		
		dynamicCurrencyPairsInfoView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new DynamicCurrencyPairsDialog(MainActivity.this, getSelectedMarket(), currencyPairsMapHelper) {
					public void onPairsUpdated(Market market, CurrencyPairsMapHelper currencyPairsMapHelper) {
						MainActivity.this.currencyPairsMapHelper = currencyPairsMapHelper;
						refreshCurrencySpinners(market);
					}
				}.show();
			}
		});
		
		currencyBaseSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				refreshCurrencyCounterSpinner(getSelectedMarket());
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
	
	private String getSelectedCurrencyBase() {
		if(currencyBaseSpinner.getAdapter()==null)
			return null;
		return String.valueOf(currencyBaseSpinner.getSelectedItem());
	}
	
	private String getSelectedCurrencyCounter() {
		if(currencyCounterSpinner.getAdapter()==null)
			return null;
		return String.valueOf(currencyCounterSpinner.getSelectedItem());
	}


	// ====================
	// Refreshing UI
	// ====================
	private void refreshMarketSpinner() {
		final CharSequence[] entries = new String[MarketsConfig.MARKETS.size()];
		int i=0;
		for(Market market : MarketsConfig.MARKETS.values()) {
			entries[i++] = market.name;
		}
		
		marketSpinner.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, entries));
	}
	
	private void refreshCurrencySpinners(Market market) {
		refreshCurrencyBaseSpinner(market);
		refreshCurrencyCounterSpinner(market);
		refreshDynamicCurrencyPairsView(market);
		
		final boolean isCurrencyEmpty = getSelectedCurrencyBase()==null || getSelectedCurrencyCounter()==null;
		currencySpinnersWrapper.setVisibility(isCurrencyEmpty ? View.GONE : View.VISIBLE);
		dynamicCurrencyPairsWarningView.setVisibility(isCurrencyEmpty ? View.VISIBLE : View.GONE);
	}
	
	private void refreshDynamicCurrencyPairsView(Market market) {
		dynamicCurrencyPairsInfoView.setVisibility(market.getCurrencyPairsUrl()!=null ? View.VISIBLE : View.GONE);
	}
	
	private void refreshCurrencyBaseSpinner(Market market) {
		final HashMap<String, CharSequence[]> currencyPairs = getProperCurrencyPairs(market);
		if(currencyPairs!=null && currencyPairs.size()>0) {
			final CharSequence[] entries = new CharSequence[currencyPairs.size()];
			int i=0;
			for(String currency : currencyPairs.keySet()) {
				entries[i++] = currency;
			}
			currencyBaseSpinner.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, entries));
		} else {
			currencyBaseSpinner.setAdapter(null);
		}
	}
	
	private void refreshCurrencyCounterSpinner(Market market) {
		final HashMap<String, CharSequence[]> currencyPairs = getProperCurrencyPairs(market);
		if(currencyPairs!=null && currencyPairs.size()>0) {
			final String selectedCurrencyBase = getSelectedCurrencyBase();
			final CharSequence[] entries = currencyPairs.get(selectedCurrencyBase).clone();
			currencyCounterSpinner.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, entries));
		} else {
			currencyCounterSpinner.setAdapter(null);
		}
	}

	private void showResultView(boolean showResultView) {
		getResultButton.setEnabled(showResultView);
		progressBar.setVisibility(showResultView ? View.GONE : View.VISIBLE);
		resultView.setVisibility(showResultView ? View.VISIBLE : View.GONE);
	}
	
	private HashMap<String, CharSequence[]> getProperCurrencyPairs(Market market) {
		if(currencyPairsMapHelper!=null && currencyPairsMapHelper.getCurrencyPairs()!=null && currencyPairsMapHelper.getCurrencyPairs().size()>0)
			return currencyPairsMapHelper.getCurrencyPairs();
		else
			return market.currencyPairs;
	}
	
	// ====================
	// Get && display results
	// ====================
	private void getNewResult() {
		final Market market = getSelectedMarket();
		final String currencyBase = getSelectedCurrencyBase();
		final String currencyCounter = getSelectedCurrencyCounter();
		final String pairId = currencyPairsMapHelper!=null ? currencyPairsMapHelper.getCurrencyPairId(currencyBase, currencyCounter) : null;
		final CheckerInfo checkerInfo = new CheckerInfo(currencyBase, currencyCounter, pairId);
		Request<?> request = new CheckerVolleyMainRequest(market, checkerInfo, new Listener<TickerWithRawResponse>() {
			@Override
			public void onResponse(TickerWithRawResponse tickerWithRawResponse) {
				handleNewResult(checkerInfo, tickerWithRawResponse.ticker, tickerWithRawResponse.rawResponse, null);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				error.printStackTrace();
				
				String rawResponse = null;
				String errorMsg = null;
				if(error instanceof CheckerErrorParsedError) {
					rawResponse = ((CheckerErrorParsedError)error).getRawResponse();
					errorMsg = ((CheckerErrorParsedError)error).getErrorMsg();
				}
				
				if(TextUtils.isEmpty(errorMsg))
					errorMsg = CheckErrorsUtils.parseVolleyErrorMsg(MainActivity.this, error);
					
				handleNewResult(checkerInfo, null, rawResponse, errorMsg);
			}
		});
		requestQueue.add(request);
		showResultView(false);
	}
	
	private void handleNewResult(CheckerInfo checkerInfo, Ticker ticker, String rawResponse, String errorMsg) {
		showResultView(true);
		SpannableStringBuilder ssb = new SpannableStringBuilder();
		
		if (ticker!=null) {
			ssb.append(getString(R.string.ticker_last, FormatUtilsBase.formatPriceWithCurrency(ticker.last, checkerInfo.getCurrencyCounter())));
			ssb.append(createNewPriceLineIfNeeded(R.string.ticker_high, ticker.high, checkerInfo.getCurrencyCounter()));
			ssb.append(createNewPriceLineIfNeeded(R.string.ticker_low, ticker.low, checkerInfo.getCurrencyCounter()));
			ssb.append(createNewPriceLineIfNeeded(R.string.ticker_bid, ticker.bid, checkerInfo.getCurrencyCounter()));
			ssb.append(createNewPriceLineIfNeeded(R.string.ticker_ask, ticker.ask, checkerInfo.getCurrencyCounter()));
			ssb.append(createNewPriceLineIfNeeded(R.string.ticker_vol, ticker.vol, checkerInfo.getCurrencyBase()));
			ssb.append("\n"+getString(R.string.ticker_timestamp, FormatUtilsBase.formatSameDayTimeOrDate(this, ticker.timestamp)));
		} else {
			ssb.append(getString(R.string.check_error_generic_prefix, errorMsg!=null ? errorMsg : "UNKNOWN"));
		}
		if(rawResponse!=null){
			ssb.append("\n\n");
			ssb.append(Html.fromHtml(getString(R.string.ticker_raw_response)+"<br\\><small>"+rawResponse+"</small>"));
		}
		
		resultView.setText(ssb);
	}
	
	private String createNewPriceLineIfNeeded(int textResId, double price, String currency) {
		if(price<=Ticker.NO_DATA)
			return "";
		
		return "\n"+getString(textResId, FormatUtilsBase.formatPriceWithCurrency(price, currency));
	}
}
