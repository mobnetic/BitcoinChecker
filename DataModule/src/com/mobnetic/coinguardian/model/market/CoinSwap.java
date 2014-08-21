package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

public class CoinSwap extends Market {

	private final static String NAME = "Coin-Swap";
	private final static String TTS_NAME = "Coin Swap";
	private final static String URL = "https://api.coin-swap.net/market/stats/%1$s/%2$s";
	private final static String URL_CURRENCY_PAIRS = "http://api.coin-swap.net/market/summary";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	
	public CoinSwap() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("bid");
		ticker.ask = jsonObject.getDouble("ask");
		ticker.vol = jsonObject.getDouble("dayvolume");
		ticker.high = jsonObject.getDouble("dayhigh");
		ticker.low = jsonObject.getDouble("daylow");
		ticker.last = jsonObject.getDouble("lastprice");
	}
	
	public String getCurrencyPairsUrl(int requestId) {
		return URL_CURRENCY_PAIRS;
	}
	
	protected void parseCurrencyPairs(int requestId, String responseString, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray marketsJsonArray = new JSONArray(responseString);
		
		for(int i=0; i<marketsJsonArray.length(); ++i) {
			final JSONObject marketJsonObject = marketsJsonArray.getJSONObject(i);
			pairs.add(new CurrencyPairInfo(
				marketJsonObject.getString("symbol"),
				marketJsonObject.getString("exchange"),
				marketJsonObject.getString("symbol")));
		}
	}
}
