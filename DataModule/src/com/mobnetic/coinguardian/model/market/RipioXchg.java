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

public class RipioXchg extends Market {

	private final static String NAME = "Ripio xchg";
	private final static String TTS_NAME = "Ripio exchange";
	private final static String URL = "https://exchange.ripio.com/api/v1/book/";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.ARS
			});
	}
		
	public RipioXchg() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONArray bidJsonArray = jsonObject.getJSONArray("bids");
		final JSONArray askJsonArray = jsonObject.getJSONArray("asks");

		ticker.bid = bidJsonArray.getJSONObject(0).getDouble("price");
		ticker.ask = askJsonArray.getJSONObject(0).getDouble("price");
		ticker.timestamp = jsonObject.getInt("timestamp");
		
	}
}
