package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class CoinMateIO extends Market {

	private final static String NAME = "CoinMate.io";
	private final static String TTS_NAME = "Coin Mate";
	private final static String URL = "https://coinmate.io/api/ticker?currencyPair=%1$s_%2$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.EUR,
				Currency.CZK
			});
	}
	
	public CoinMateIO() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
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
