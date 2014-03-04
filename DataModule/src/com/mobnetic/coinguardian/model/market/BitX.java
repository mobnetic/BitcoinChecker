package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class BitX extends Market {

	private final static String NAME = "BitX";
	private final static String TTS_NAME = "Bit X";
	private final static String URL = "https://bitx.co.za/api/1/ticker?pair=%1$s%2$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.ZAR
			});
	}
	
	public BitX() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		String currencyBase = fixCurrency(checkerInfo.getCurrencyBase());
		String currencyCounter = checkerInfo.getCurrencyCounter();

		return String.format(URL, currencyBase, currencyCounter);
	}
	
	private String fixCurrency(String currency) {
		if(VirtualCurrency.BTC.equals(currency))
			return VirtualCurrency.XBT;

		return currency;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.last = jsonObject.getDouble("last_trade");		
		ticker.bid = jsonObject.getDouble("bid");
		ticker.ask = jsonObject.getDouble("ask");
		ticker.vol = jsonObject.getDouble("rolling_24_hour_volume");		
		ticker.timestamp = jsonObject.getLong("timestamp");
	}
}
