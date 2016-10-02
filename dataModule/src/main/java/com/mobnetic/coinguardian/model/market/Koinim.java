package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class Koinim extends Market {

	public final static String NAME = "Koinim";
	public final static String TTS_NAME = NAME;
	public final static String URL_BTC = "https://koinim.com/ticker/";
	public final static String URL_LTC = "https://koinim.com/ticker/ltc/";
	public final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.TRY
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				Currency.TRY
			});
	}
	
	public Koinim() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		if(VirtualCurrency.LTC.equals(checkerInfo.getCurrencyBase())) {
			return URL_LTC;
		}
		return URL_BTC;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("buy");
		ticker.ask = jsonObject.getDouble("sell");
		ticker.vol = jsonObject.getDouble("volume");
		ticker.high = jsonObject.getDouble("high");
		ticker.low = jsonObject.getDouble("low");
		ticker.last = jsonObject.getDouble("last_order");
	}
}
