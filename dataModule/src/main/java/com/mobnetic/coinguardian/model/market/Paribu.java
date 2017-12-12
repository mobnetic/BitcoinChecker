package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;
import com.mobnetic.coinguardian.util.ParseUtils;

public class Paribu extends Market {

	public final static String NAME = "Paribu";
	public final static String TTS_NAME = NAME;
	public final static String URL = "https://www.paribu.com/ticker";
	public final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
			Currency.TRY
		});
	}
	
	public Paribu() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject dataJsonObject = jsonObject.getJSONObject("BTC_TL");
		
		ticker.bid = ParseUtils.getDoubleFromString(dataJsonObject, "highestBid");
		ticker.ask = ParseUtils.getDoubleFromString(dataJsonObject, "lowestAsk");
		ticker.vol = ParseUtils.getDoubleFromString(dataJsonObject, "volume");
		ticker.high = ParseUtils.getDoubleFromString(dataJsonObject, "high24hr");
		ticker.low = ParseUtils.getDoubleFromString(dataJsonObject, "low24hr");
		ticker.last = ParseUtils.getDoubleFromString(dataJsonObject, "last");
	}
}
