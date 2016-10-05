package com.mobnetic.coinguardian.model.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

public class MintPal extends Market {

	private final static String NAME = "MintPal";
	private final static String TTS_NAME = "Mint Pal";
	private final static String URL = "https://api.mintpal.com/market/stats/%1$s/%2$s/";
	private final static String URL_CURRENCY_PAIRS = "https://api.mintpal.com/market/summary/";
	
	public MintPal() {
		super(NAME, TTS_NAME, null);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTicker(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		parseTickerFromJsonObject(requestId, new JSONArray(responseString).getJSONObject(0), ticker, checkerInfo);
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.vol = jsonObject.getDouble("24hvol");
		ticker.high = jsonObject.getDouble("24hhigh");
		ticker.low = jsonObject.getDouble("24hlow");
		ticker.last = jsonObject.getDouble("last_price");
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
		final JSONArray jsonArray = new JSONArray(responseString);
		for(int i=0; i<jsonArray.length(); ++i) {
			JSONObject marketObject = jsonArray.getJSONObject(i);
			pairs.add(new CurrencyPairInfo(
					marketObject.getString("code"),
					marketObject.getString("exchange"),
					null
				));
		}
	}
}
