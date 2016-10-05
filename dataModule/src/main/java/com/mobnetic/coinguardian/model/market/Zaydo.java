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

public class Zaydo extends Market {

	private final static String NAME = "Zaydo";
	private final static String TTS_NAME = NAME;
	private final static String URL = "http://chart.zyado.com/ticker.json";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.EUR
			});
	}
	
	public Zaydo() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = ParseUtils.getDoubleFromString(jsonObject, "bid");
		ticker.ask = ParseUtils.getDoubleFromString(jsonObject, "ask");
		ticker.vol = ParseUtils.getDoubleFromString(jsonObject, "volume");
		ticker.high = ParseUtils.getDoubleFromString(jsonObject, "high");
		ticker.low = ParseUtils.getDoubleFromString(jsonObject, "low");
		ticker.last = ParseUtils.getDoubleFromString(jsonObject, "last");
	}
}
