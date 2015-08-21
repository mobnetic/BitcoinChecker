package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class BtcXIndia extends Market {

	private final static String NAME = "BTCXIndia";
	private final static String TTS_NAME = "BTC X India";
	private final static String URL = "https://btcxindia.com/api/ticker";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.INR
			});
	}
	
	public BtcXIndia() {
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
		ticker.vol = jsonObject.getDouble("total_volume_24h");
		ticker.high = getDoubleFromString(jsonObject, "high");
		ticker.low = getDoubleFromString(jsonObject, "low");
		ticker.last = jsonObject.getDouble("last_traded_price");
	}
	
	private double getDoubleFromString(JSONObject jsonObject, String name) throws NumberFormatException, JSONException {
		return Double.parseDouble(jsonObject.getString(name));
	}
}
