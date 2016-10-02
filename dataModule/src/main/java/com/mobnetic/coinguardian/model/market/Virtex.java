package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class Virtex extends Market {

	private final static String NAME = "CaVirtEx";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://cavirtex.com/api2/ticker.json";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.CAD,
				VirtualCurrency.LTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				Currency.CAD
			});
	}
	
	public Virtex() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject tickerJsonObject = jsonObject.getJSONObject("ticker");
		final JSONObject pairJsonObject = tickerJsonObject.getJSONObject(checkerInfo.getCurrencyBase()+checkerInfo.getCurrencyCounter());
		
		if (!pairJsonObject.isNull("buy")) {
			ticker.bid = pairJsonObject.getDouble("buy");
		}
		if (!pairJsonObject.isNull("sell")) {
			ticker.ask = pairJsonObject.getDouble("sell");
		}
		if (!pairJsonObject.isNull("volume")) {
			ticker.vol = pairJsonObject.getDouble("volume");
		}
		if (!pairJsonObject.isNull("high")) {
			ticker.high = pairJsonObject.getDouble("high");
		}
		if (!pairJsonObject.isNull("low")) {
			ticker.low = pairJsonObject.getDouble("low");
		}
		ticker.last = pairJsonObject.getDouble("last");
	}
}
