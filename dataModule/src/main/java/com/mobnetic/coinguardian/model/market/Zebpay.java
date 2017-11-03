package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Zebpay extends Market {

	private final static String NAME = "Zebpay";
	private final static String TTS_NAME = "Zeb Pay";
	private final static String URL = "https://www.zebapi.com/api/v1/market/ticker/%1$s/%2$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.INR
			});
	}

	public Zebpay() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("sell");
		ticker.ask = jsonObject.getDouble("buy");
		ticker.vol = jsonObject.getDouble("volume");
		ticker.last = jsonObject.getDouble("market");
	}
}
