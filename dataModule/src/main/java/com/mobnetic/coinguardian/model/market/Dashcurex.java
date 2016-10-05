package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class Dashcurex extends Market {

	private final static String NAME = "Dashcurex";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://dashcurex.com/api/%1$s/ticker.json";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.DASH, new String[]{
				Currency.PLN,
				Currency.USD
			});
	}
	
	public Dashcurex() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyCounterLowerCase());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = parsePrice(jsonObject.getDouble("best_ask"));
		ticker.ask = parsePrice(jsonObject.getDouble("best_bid"));
		ticker.vol = parseBTC(jsonObject.getDouble("total_volume"));
		ticker.high = parsePrice(jsonObject.getDouble("highest_tx_price"));
		ticker.low = parsePrice(jsonObject.getDouble("lowest_tx_price"));
		ticker.last = parsePrice(jsonObject.getDouble("last_tx_price"));
	}
	
	private double parsePrice(double price) {
		return price/10000;
	}
	
	private double parseBTC(double satoshi) {
		return satoshi/100000000;
	}
}
