package com.mobnetic.coinguardian.model.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

public class Basebit extends Market {

	private final static String NAME = "Basebit";
	private final static String TTS_NAME = "Base Bit";
	private final static String URL = "http://www.basebit.com.br/quote-%1$s";
	private final static String URL_CURRENCY_PAIRS = "http://www.basebit.com.br/listpairs";
	
	public Basebit() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyPairId());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject resultJsonObject = jsonObject.getJSONObject("result");
		ticker.vol = resultJsonObject.getDouble("volume24h");
		ticker.high = resultJsonObject.getDouble("high");
		ticker.low = resultJsonObject.getDouble("low");
		ticker.last = resultJsonObject.getDouble("last");
	}
	
	@Override
	protected String parseErrorFromJsonObject(int requestId, JSONObject jsonObject, CheckerInfo checkerInfo) throws Exception {
		return jsonObject.getString("errorMessage");
	}
	
	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL_CURRENCY_PAIRS;
	}
	
	@Override
	protected void parseCurrencyPairs(int requestId, String responseString, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray pairsJsonArray = new JSONArray(responseString);
		
		for(int i=0; i<pairsJsonArray.length(); ++i) {
			final String pairName = pairsJsonArray.getString(i);
			final String[] currencyNames = pairName.split("_");
			if(currencyNames!=null && currencyNames.length>=2) {
				pairs.add(new CurrencyPairInfo(
						currencyNames[0],
						currencyNames[1],
						pairName));
			}
		}
	}
}
