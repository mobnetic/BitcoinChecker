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

public class Mercado extends Market {

	private final static String NAME = "Mercado Bitcoin";
	private final static String TTS_NAME = "Mercado";
	private final static String URL_BTC = "https://www.mercadobitcoin.com.br/api/ticker/";
	private final static String URL_LTC = "https://www.mercadobitcoin.com.br/api/ticker_litecoin/";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.BRL
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				Currency.BRL
			});
	}
	
	public Mercado() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return VirtualCurrency.LTC.equals(checkerInfo.getCurrencyBase()) ? URL_LTC : URL_BTC;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject tickerJsonObject = jsonObject.getJSONObject("ticker");
		
		ticker.bid = tickerJsonObject.getDouble("buy");
		ticker.ask = tickerJsonObject.getDouble("sell");
		ticker.vol = tickerJsonObject.getDouble("vol");
		ticker.high = tickerJsonObject.getDouble("high");
		ticker.low = tickerJsonObject.getDouble("low");
		ticker.last = tickerJsonObject.getDouble("last");
		ticker.timestamp = tickerJsonObject.getLong("date")*TimeUtils.MILLIS_IN_SECOND;
	}
}
