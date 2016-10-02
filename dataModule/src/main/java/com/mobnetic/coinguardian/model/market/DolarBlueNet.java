package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;

public class DolarBlueNet extends Market {

	private final static String NAME = "DolarBlue.net";
	private final static String TTS_NAME = "Dolar Blue";
	private final static String URL = "http://dolar.bitplanet.info/api.php";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(Currency.USD, new String[]{
				Currency.ARS
			});
	}
	
	public DolarBlueNet() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.ask = jsonObject.getDouble("venta");	
		ticker.bid = jsonObject.getDouble("compra");	
		ticker.last = ticker.ask;
	}
}
