package com.mobnetic.coinguardian.model.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

public class BitTrex extends Market {

	private final static String NAME = "BitTrex";
	private final static String TTS_NAME = "Bit Trex";
	private final static String URL = "https://bittrex.com/api/v1.1/public/getticker?market=%1$s-%2$s";
	private final static String URL_CURRENCY_PAIRS = "https://bittrex.com/api/v1.1/public/getmarkets";
	
	public BitTrex() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyCounter(), checkerInfo.getCurrencyBase());		// reversed
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject resultJsonObject = jsonObject.getJSONObject("result");
		ticker.bid = resultJsonObject.getDouble("Bid");
		ticker.ask = resultJsonObject.getDouble("Ask");
		ticker.last = resultJsonObject.getDouble("Last");
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
		final JSONArray resultJsonArray = jsonObject.getJSONArray("result");
		
		for(int i=0; i<resultJsonArray.length(); ++i) {
			final JSONObject marketJsonObject = resultJsonArray.getJSONObject(i);
			pairs.add(new CurrencyPairInfo(
					marketJsonObject.getString("MarketCurrency"),		// reversed
					marketJsonObject.getString("BaseCurrency"),			// reversed
					marketJsonObject.getString("MarketName")));
		}
	}
}
