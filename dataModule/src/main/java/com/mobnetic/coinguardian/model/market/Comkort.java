package com.mobnetic.coinguardian.model.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

public class Comkort extends Market {

	private final static String NAME = "Comkort";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://api.comkort.com/v1/public/market/summary?market_alias=%1$s";
	private final static String URL_CURRENCY_PAIRS = "https://api.comkort.com/v1/public/market/list";
	
	public Comkort() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyPairId());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject marketsJsonObject = jsonObject.getJSONObject("markets");
		final JSONArray marketNames = marketsJsonObject.names();
		final JSONObject marketJsonObject = marketsJsonObject.getJSONObject(marketNames.getString(0));
		
		ticker.bid = getFirstOrderFrom(marketJsonObject, "buy_orders");
		ticker.ask = getFirstOrderFrom(marketJsonObject, "sell_orders");
		ticker.vol = marketJsonObject.getDouble("volume");
		ticker.high = marketJsonObject.getDouble("high");
		ticker.low = marketJsonObject.getDouble("low");
		ticker.last = marketJsonObject.getDouble("last_price");
	}
	
	private double getFirstOrderFrom(JSONObject marketJsonObject, String arrayName) throws Exception {
		final JSONArray ordersJsonArray = marketJsonObject.getJSONArray(arrayName);
		if(ordersJsonArray.length()>0) {
			return ordersJsonArray.getJSONObject(0).getDouble("price");
		}
		return Ticker.NO_DATA;
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
					marketJsonObject.getString("item"),
					marketJsonObject.getString("price_currency"),
					marketJsonObject.getString("alias")));
		}
	}
}