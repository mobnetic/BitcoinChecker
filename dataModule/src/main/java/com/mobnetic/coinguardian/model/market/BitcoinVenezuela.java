package com.mobnetic.coinguardian.model.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class BitcoinVenezuela extends Market {

	private final static String NAME = "BitcoinVenezuela";
	private final static String TTS_NAME = "Bitcoin Venezuela";
	private final static String URL = "http://api.bitcoinvenezuela.com/?html=no&currency=%1$s&amount=1&to=%2$s";
	private final static String URL_CURRENCY_PAIRS = "http://api.bitcoinvenezuela.com/";
	
	public BitcoinVenezuela() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTicker(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.last = Double.parseDouble(responseString.trim());
	}
	
	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL_CURRENCY_PAIRS;
	}
	
	@Override
	protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		parseCurrencyPairsFromCurrencyBase(VirtualCurrency.BTC, jsonObject, pairs);
		parseCurrencyPairsFromCurrencyBase(VirtualCurrency.LTC, jsonObject, pairs);
		parseCurrencyPairsFromCurrencyBase(VirtualCurrency.MSC, jsonObject, pairs);
	}
	
	private void parseCurrencyPairsFromCurrencyBase(String currencyBase, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		if(!jsonObject.has(currencyBase))
			return;
		
		final JSONObject currencyBaseJsonObject = jsonObject.getJSONObject(currencyBase);
		final JSONArray counterCurrencyNames = currencyBaseJsonObject.names();
		for(int i=0; i<counterCurrencyNames.length(); ++i) {
			pairs.add(new CurrencyPairInfo(
					currencyBase,
					counterCurrencyNames.getString(i),
					null));
		}
	}
}
