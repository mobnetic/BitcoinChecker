package com.mobnetic.coinguardian.model.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

public class ShareXcoin extends Market {

	private final static String NAME = "ShareXcoin";
	private final static String TTS_NAME = "Share X coin";
	private final static String URL = "https://sharexcoin.com/public_api/v1/market/%1$s_%2$s/summary";
	private final static String URL_CURRENCY_PAIRS = "https://sharexcoin.com/public_api/v1/market/summary";
	
	public ShareXcoin() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("bid");
		ticker.ask = jsonObject.getDouble("ask");
		ticker.vol = jsonObject.getDouble("volume");
		ticker.high = jsonObject.getDouble("high");
		ticker.low = jsonObject.getDouble("low");
		ticker.last = jsonObject.getDouble("last");
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
		final JSONArray marketsJsonArray = jsonObject.getJSONArray("markets");
		
		for(int i=0; i<marketsJsonArray.length(); ++i) {
			final JSONObject marketJsonObject = marketsJsonArray.getJSONObject(i);
			pairs.add(new CurrencyPairInfo(
					marketJsonObject.getString("coin1"),
					marketJsonObject.getString("coin2"),
					marketJsonObject.getString("market_id")));
		}
	}
}