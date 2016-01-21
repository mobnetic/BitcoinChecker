package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class CoinbaseExchange extends Market {

	private final static String NAME = "Coinbase Exchange";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://api.exchange.coinbase.com/products/BTC-%1$s/ticker";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD,
				Currency.EUR,
				Currency.GBP
			});
	}
	
	public CoinbaseExchange() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public int getNumOfRequests(CheckerInfo checkerInfo) {
		return 1;
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.ask = jsonObject.getDouble("ask");
		ticker.bid = jsonObject.getDouble("bid");
		ticker.last = jsonObject.getDouble("price");
		ticker.vol = jsonObject.getDouble("volume");
	}
}
