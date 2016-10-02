package com.mobnetic.coinguardian.model.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class CoinDesk extends Market {

	private final static String NAME = "CoinDesk";
	private final static String TTS_NAME = "Coin Desk";
	private final static String URL = "https://api.coindesk.com/v1/bpi/currentprice.json";
	private final static String URL_CURRENCY_PAIRS = URL;
	
	public CoinDesk() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject bpiJsonObject = jsonObject.getJSONObject("bpi");
		final JSONObject pairJsonObject = bpiJsonObject.getJSONObject(checkerInfo.getCurrencyCounter());
		ticker.last = pairJsonObject.getDouble("rate_float");
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
		final JSONObject bpiJsonObject = jsonObject.getJSONObject("bpi");
		final JSONArray currencyCounterNames = bpiJsonObject.names();
		
		for(int i=0; i<currencyCounterNames.length(); ++i) {
			pairs.add(new CurrencyPairInfo(VirtualCurrency.BTC, currencyCounterNames.getString(i), null));
		}
	}
}
