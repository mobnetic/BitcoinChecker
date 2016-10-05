package com.mobnetic.coinguardian.model.market;

import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class Btc38 extends Market {

	private final static String NAME = "Btc38";
	private final static String TTS_NAME = "BTC 38";
	private final static String URL = "http://api.btc38.com/v1/ticker.php?c=%1$s&mk_type=%2$s";
	private final static String URL_CURRENCY_PAIRS = "http://api.btc38.com/v1/ticker.php?c=all&mk_type=%1$s";

	public Btc38() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject tickerJsonObject = jsonObject.getJSONObject("ticker");
		
		ticker.bid = tickerJsonObject.getDouble("buy");
		ticker.ask = tickerJsonObject.getDouble("sell");
		ticker.vol = tickerJsonObject.getDouble("vol");
		ticker.high = tickerJsonObject.getDouble("high");
		ticker.low = tickerJsonObject.getDouble("low");
		ticker.last = tickerJsonObject.getDouble("last");
	}
	
	// ====================
	// Get currency pairs
	// ====================
	@Override
	public int getCurrencyPairsNumOfRequests() {
		return 2;
	}
	
	public String getCurrencyCounter(int requestId) {
		return requestId == 0 ? Currency.CNY : VirtualCurrency.BTC;
	}
	
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return String.format(URL_CURRENCY_PAIRS, getCurrencyCounter(requestId).toLowerCase(Locale.ENGLISH));
	}
	
	@Override
	protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		final String currencyCounter = getCurrencyCounter(requestId);
		final JSONArray currencyBaseList = jsonObject.names();
		for(int i=0; i<currencyBaseList.length(); ++i) {
			pairs.add(new CurrencyPairInfo(
					currencyBaseList.getString(i).toUpperCase(Locale.ENGLISH),
					currencyCounter,
					null
				));
		}
	}
}
