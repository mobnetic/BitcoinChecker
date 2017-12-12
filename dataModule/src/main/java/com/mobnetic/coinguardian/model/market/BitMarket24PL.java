package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class BitMarket24PL extends Market {

	private final static String NAME = "BitMarket24.pl";
	private final static String TTS_NAME = "Bit Market 24";
	private final static String URL = "https://bitmarket24.pl/api/%1$s_%2$s/status.json";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BCC, new String[]{
				Currency.PLN
			});
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.PLN
			});
		CURRENCY_PAIRS.put(VirtualCurrency.BTG, new String[]{
				Currency.PLN
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				VirtualCurrency.BTC,
				Currency.PLN
			});
	}
	
	public BitMarket24PL() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		if (!jsonObject.isNull("bid")) {
			ticker.bid = jsonObject.getDouble("bid");
		}
		if (!jsonObject.isNull("ask")) {
			ticker.ask = jsonObject.getDouble("ask");
		}
		if (!jsonObject.isNull("volume")) {
			ticker.vol = jsonObject.getDouble("volume");
		}
		if (!jsonObject.isNull("high")) {
			ticker.high = jsonObject.getDouble("high");
		}
		if (!jsonObject.isNull("low")) {
			ticker.low = jsonObject.getDouble("low");
		}
		ticker.last = jsonObject.getDouble("last");
	}
}
