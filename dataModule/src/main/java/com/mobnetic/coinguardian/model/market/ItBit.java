package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class ItBit extends Market {

	private final static String NAME = "itBit";
	private final static String TTS_NAME = "It Bit";
	private final static String URL = "https://api.itbit.com/v1/markets/%1$s%2$s/ticker";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD,
				Currency.SGD,
				Currency.EUR
			});
	}
	
	public ItBit() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	private String fixCurrency(String currency) {
		if (VirtualCurrency.BTC.equals(currency)) return VirtualCurrency.XBT;
		return currency;
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, fixCurrency(checkerInfo.getCurrencyBase()), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("bid");
		ticker.ask = jsonObject.getDouble("ask");
		ticker.vol = jsonObject.getDouble("volume24h");
		ticker.high = jsonObject.getDouble("high24h");
		ticker.low = jsonObject.getDouble("low24h");
		ticker.last = jsonObject.getDouble("lastPrice");
	}
	
	@Override
	protected String parseErrorFromJsonObject(int requestId, JSONObject jsonObject, CheckerInfo checkerInfo) throws Exception {
		return jsonObject.getString("message"); // not working?
	}
}
