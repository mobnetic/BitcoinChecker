package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;

public class DolarBluenet extends Market {

	private final static String NAME = "Dolarblue.net";
	private final static String TTS_NAME = "Dolar blue";
	private final static String URL = "http://ws.geeklab.com.ar/dolar/get-dolar-json.php";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(Currency.USD, new String[]{
				Currency.ARS
			});
	}
	
	public DolarBluenet() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.ask = jsonObject.getDouble("blue");	
		ticker.low = jsonObject.getDouble("libre");	//Dolar Oficial, no refleja el valor real de mercado
		ticker.last = ticker.ask;
	}
}
