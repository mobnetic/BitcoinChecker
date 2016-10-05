package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class Winkdex extends Market {

	private final static String NAME = "Winkdex";
	private final static String TTS_NAME = NAME;
	private final static String URL = "http://winkdex.com/static/js/stats.js";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD
			});
	}
	
	public Winkdex() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.vol = jsonObject.getDouble("volume_btc_24h");
		ticker.high = jsonObject.getDouble("winkdex_high_24h");
		ticker.low = jsonObject.getDouble("winkdex_low_24h");
		ticker.last = jsonObject.getDouble("winkdex_usd");
	}
}
