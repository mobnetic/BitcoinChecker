package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class Ripio extends Market {

	private final static String NAME = "Ripio";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://www.ripio.com/api/v1/rates/";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.ARS
			});
	}
	
	public Ripio() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject ratesJsonObject = jsonObject.getJSONObject("rates");
		ticker.bid = ratesJsonObject.getDouble("ARS_SELL");		// reversed
		ticker.ask = ratesJsonObject.getDouble("ARS_BUY");		// reversed
		ticker.last = ticker.ask;
	}
}
