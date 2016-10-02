package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class BitcoinToYou extends Market {

	private final static String NAME = "BitcoinToYou";
	private final static String TTS_NAME = "Bitcoin To You";
	private final static String URL = "https://www.bitcointoyou.com/api/ticker.aspx";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.BRL
			});
	}
	
	public BitcoinToYou() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
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
	}
}
