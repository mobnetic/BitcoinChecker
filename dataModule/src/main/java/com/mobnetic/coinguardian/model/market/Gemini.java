package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class Gemini extends Market {

	private final static String NAME = "Gemini";
	private final static String TTS_NAME = "Gemini";
	private final static String URL = "https://api.gemini.com/v1/book/%1$s%2$s?limit_asks=1&limit_bids=1";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();

    // Gemini allows dynamic symbol retrieval but returns it in format: [ "btcusd" ]
    // This doesn't provide an easy way to programmatically split the currency
    // So just define them
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.ETH, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
	}

	public Gemini() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}

	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		//Gemini isn't a traditional tracker, rather it seems to return the X last bids and asks
		//We could do something like take the average of the last Y prices
		//But I will just take the average of the last bid and asking price
		
		final JSONArray bidsArray = jsonObject.getJSONArray("bids");
		if (bidsArray != null && bidsArray.length() > 0) {
			ticker.bid = bidsArray.getJSONObject(0).getDouble("price");
		}
		
		final JSONArray asksArray = jsonObject.getJSONArray("asks");
		if (asksArray != null && asksArray.length() > 0) {
			ticker.ask = asksArray.getJSONObject(0).getDouble("price");
		}
		
		if (ticker.bid != Ticker.NO_DATA && ticker.ask != Ticker.NO_DATA) {
			ticker.last = (ticker.bid + ticker.ask) / 2.0;
		} else if (ticker.bid != Ticker.NO_DATA) {
			ticker.last = ticker.bid;
		} else if (ticker.ask != Ticker.NO_DATA) {
			ticker.last = ticker.ask;
		} else {
			ticker.last = 0;
		}
	}
}
