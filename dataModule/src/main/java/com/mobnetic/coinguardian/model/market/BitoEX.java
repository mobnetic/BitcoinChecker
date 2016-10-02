package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONArray;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class BitoEX extends Market {

	private final static String NAME = "BitoEX";
	private final static String TTS_NAME = NAME; 
	private final static String URL = "https://www.bitoex.com/sync/dashboard/%1$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.TWD
			});
	}
	
	public BitoEX() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, System.currentTimeMillis());
	}

	@Override
	protected void parseTicker(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		JSONArray jsonArray = new JSONArray(responseString);
		ticker.ask = Double.parseDouble(jsonArray.getString(0).replaceAll(",", ""));
		ticker.bid = Double.parseDouble(jsonArray.getString(1).replaceAll(",", ""));
		ticker.last = ticker.ask;
		ticker.timestamp = Long.valueOf(jsonArray.getString(2));
	}	
}
