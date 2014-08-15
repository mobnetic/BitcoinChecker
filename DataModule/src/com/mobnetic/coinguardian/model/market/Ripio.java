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
	private final static String TTS_NAME = "Ripio";
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
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject pricesJsonObject = jsonObject.getJSONObject("rates");
		ticker.high = pricesJsonObject.getDouble("ARS_BUY")*1.045; //Valor final con comisiones de Ripio
		ticker.ask = pricesJsonObject.getDouble("ARS_BUY");		
		ticker.last = ticker.ask;
	}
}
