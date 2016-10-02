package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class Okcoin extends Market {

	private final static String NAME = "OKCoin";
	private final static String TTS_NAME = "OK Coin";
	private final static String URL_USD = "https://www.okcoin.com/api/v1/ticker.do?symbol=%1$s_%2$s";
	private final static String URL_CNY = "https://www.okcoin.cn/api/v1/ticker.do?symbol=%1$s_%2$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.CNY,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				Currency.CNY,
				Currency.USD
			});
	}
	
	public Okcoin() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		if(Currency.USD.equals(checkerInfo.getCurrencyCounter())) {
			return String.format(URL_USD, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
		} else {
			return String.format(URL_CNY, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
		}
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
