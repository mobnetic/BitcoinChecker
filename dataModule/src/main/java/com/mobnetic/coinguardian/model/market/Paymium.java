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

public class Paymium extends Market {

	private final static String NAME = "Paymium";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://paymium.com/api/v1/data/eur/ticker";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.EUR
			});
	}
	
	public Paymium() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("bid");
		ticker.ask = jsonObject.getDouble("ask");
		ticker.vol = jsonObject.getDouble("volume");
		ticker.high = jsonObject.getDouble("high");
		ticker.low = jsonObject.getDouble("low");
		ticker.last = jsonObject.getDouble("price");
	}

	@Override
	protected String parseErrorFromJsonObject(int requestId, JSONObject jsonObject, CheckerInfo checkerInfo) throws Exception {
		final StringBuilder stringBuilder = new StringBuilder();
		final JSONArray errorsArray = jsonObject.getJSONArray("errors");
		for(int i=0; i<errorsArray.length(); ++i) {
			if(stringBuilder.length()!=0) {
				stringBuilder.append("\n");
			}
			stringBuilder.append(errorsArray.getString(i));
		}
		return stringBuilder.toString();
	}
}
