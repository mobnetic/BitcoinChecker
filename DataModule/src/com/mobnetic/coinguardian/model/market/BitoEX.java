package com.mobnetic.coinguardian.model.market;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

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
		return String.format(URL, new Date().getTime());
	}

	@Override
	protected void parseTicker(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		JSONArray jsonArray = new JSONArray(responseString);
		JSONObject jsonContainer = new JSONObject();
		jsonContainer.put("ask",jsonArray.getString(0).replace(",", ""));
		jsonContainer.put("bid",jsonArray.getString(1).replace(",", ""));
		jsonContainer.put("last",jsonArray.getString(0).replace(",", ""));
		jsonContainer.put("timestamp", jsonArray.getString(2));
		parseTickerFromJsonObject(requestId, jsonContainer, ticker, checkerInfo);
	}

	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.ask = jsonObject.getDouble("ask");
		ticker.bid = jsonObject.getDouble("bid");
		ticker.last =  jsonObject.getDouble("last");
		ticker.timestamp = jsonObject.getLong("timestamp");
	}
}
