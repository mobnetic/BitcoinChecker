package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class BitMaszyna extends Market {

	private final static String NAME = "BitMaszyna.pl";
	private final static String TTS_NAME = "Bit Maszyna";
	private final static String URL = "https://bitmaszyna.pl/api/%1$s%2$s/ticker.json";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.PLN
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				Currency.PLN
			});
	}
	
	public BitMaszyna() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("bid");
		ticker.ask = jsonObject.getDouble("ask");
		ticker.vol = jsonObject.getDouble("volume1");
		ticker.high = jsonObject.getDouble("high");
		ticker.low = jsonObject.getDouble("low");
		ticker.last = jsonObject.getDouble("last");
	}
}
