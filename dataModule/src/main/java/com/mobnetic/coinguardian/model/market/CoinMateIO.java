package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

import org.json.JSONArray;
import java.util.List;

public class CoinMateIO extends Market {

	private final static String NAME = "CoinMate.io";
	private final static String TTS_NAME = "Coin Mate";
	private final static String URL = "https://coinmate.io/api/ticker?currencyPair=%1$s_%2$s";
	private final static String URL_CURRENCY_PAIRS = "https://coinmate.io/api/tradingPairs";

	public CoinMateIO() {
		super(NAME, TTS_NAME, null);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}

	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL_CURRENCY_PAIRS;
	}

	@Override
	protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray dataJsonArray = jsonObject.getJSONArray("data");
		for(int i=0; i<dataJsonArray.length(); ++i) {
			final JSONObject dataJsonObject = dataJsonArray.getJSONObject(i);
			pairs.add(new CurrencyPairInfo(
					dataJsonObject.getString("firstCurrency"),
					dataJsonObject.getString("secondCurrency"),
					dataJsonObject.getString("name")
			));
		}
	}

	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject dataJsonObject = jsonObject.getJSONObject("data");
		ticker.bid = dataJsonObject.getDouble("bid");
		ticker.ask = dataJsonObject.getDouble("ask");
		ticker.vol = dataJsonObject.getDouble("amount");
		ticker.high = dataJsonObject.getDouble("high");
		ticker.low = dataJsonObject.getDouble("low");
		ticker.last = dataJsonObject.getDouble("last");
	}
	
	@Override
	protected String parseErrorFromJsonObject(int requestId, JSONObject jsonObject, CheckerInfo checkerInfo) throws Exception {
		return jsonObject.getString("errorMessage");
	}
}
