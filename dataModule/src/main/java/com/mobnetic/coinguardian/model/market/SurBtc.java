package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Implements SurBTC connection to the API
 * @author Eduardo Laguna
 */
public class SurBtc extends Market {

	private final static String NAME = "SurBtc";
	private final static String TTS_NAME = "Sur BTC";
	private final static String URL = "https://www.surbtc.com/api/v2/markets/%1$s/ticker.json";
	private final static String URL_CURRENCY_PAIRS = "https://www.surbtc.com/api/v2/markets.json";

	public SurBtc() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyPairId());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject tickerJsonObject = jsonObject.getJSONObject("ticker");
		ticker.bid = tickerJsonObject.getJSONArray("max_bid").getDouble(0);
		ticker.ask = tickerJsonObject.getJSONArray("min_ask").getDouble(0);
		ticker.vol = tickerJsonObject.getJSONArray("volume").getDouble(0);
		ticker.last = tickerJsonObject.getJSONArray("last_price").getDouble(0);
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
					marketJsonObject.getString("base_currency"),
					marketJsonObject.getString("quote_currency"),
					marketJsonObject.getString("id")));
		}
	}
}
