package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Coinsph extends Market {

	private final static String NAME = "Coins.ph";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://quote.coins.ph/v1/markets/%1$s";
	private final static String URL_CURRENCY_PAIRS = "https://quote.coins.ph/v1/markets";

	public Coinsph() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyPairId());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		JSONObject marketJsonObject = jsonObject.getJSONObject("market");
		ticker.bid = marketJsonObject.getDouble("bid");
		ticker.ask = marketJsonObject.getDouble("ask");
		ticker.last = ticker.ask;
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
			final JSONObject pairJsonObject = marketsJsonArray.getJSONObject(i);
			if(pairJsonObject != null) {
				pairs.add(new CurrencyPairInfo(
						pairJsonObject.getString("product"),
						pairJsonObject.getString("currency"),
						pairJsonObject.getString("symbol")
				));
			}
		}
	}
}
