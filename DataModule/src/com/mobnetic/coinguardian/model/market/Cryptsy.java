package com.mobnetic.coinguardian.model.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

public class Cryptsy extends Market {

	private final static String NAME = "Cryptsy";
	private final static String TTS_NAME = NAME;
	private final static String URL = "http://pubapi.cryptsy.com/api.php?method=singlemarketdata&marketid=%1$s";
	private final static String URL_CURRENCY_PAIRS = "http://pubapi.cryptsy.com/api.php?method=orderdatav2";
	
	public Cryptsy() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyPairId());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject returnObject = jsonObject.getJSONObject("return");
		final JSONObject marketsObject = returnObject.getJSONObject("markets");
		final JSONObject pairObject = marketsObject.getJSONObject(marketsObject.names().getString(0));
		
		ticker.bid = getFirstPriceFromOrdersArray(pairObject.optJSONArray("buyorders"));
		ticker.ask = getFirstPriceFromOrdersArray(pairObject.optJSONArray("sellorders"));
		ticker.vol = pairObject.getDouble("volume");
		ticker.last = pairObject.getDouble("lasttradeprice");
	}
	
	private double getFirstPriceFromOrdersArray(JSONArray ordersArray) {
		try {
			if(ordersArray!=null && ordersArray.length()>0) {
				final JSONObject jsonObject = ordersArray.optJSONObject(0);
				if(jsonObject!=null)
					return jsonObject.getDouble("price");
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		final JSONObject returnObject = jsonObject.getJSONObject("return");
		final JSONArray marketNames = returnObject.names();
		
		for(int i=0; i<marketNames.length(); ++i) {
			JSONObject marketObject = returnObject.getJSONObject(marketNames.getString(i));
			pairs.add(new CurrencyPairInfo(
					marketObject.getString("primarycode"),
					marketObject.getString("secondarycode"),
					marketObject.getString("marketid")
				));
		}
	}
}
