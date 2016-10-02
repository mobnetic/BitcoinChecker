package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;
import com.mobnetic.coinguardian.util.TimeUtils;

public class Coinapult extends Market {

	private final static String NAME = "Coinapult";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://api.coinapult.com/api/ticker?market=%2$s_%1$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();

	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD,
				Currency.EUR,
				Currency.GBP,
				Currency.XAG,
				Currency.XAU
			});
	}

	public Coinapult() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected String parseErrorFromJsonObject(int requestId, JSONObject jsonObject, CheckerInfo checkerInfo) throws Exception {
		return jsonObject.getString("error");
	}

	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.last = jsonObject.getDouble("index");
		ticker.timestamp = jsonObject.getLong("updatetime") * TimeUtils.MILLIS_IN_SECOND;
	}
}
