package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;
import com.mobnetic.coinguardian.util.ParseUtils;

public class SevenNineSix extends Market {

	private final static String NAME = "796";
	private final static String TTS_NAME = NAME;
	private final static String URL_BTC = "http://api.796.com/v3/futures/ticker.html?type=weekly";
	private final static String URL_LTC = "http://api.796.com/v3/futures/ticker.html?type=ltc";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				Currency.USD
		});
	}

	public SevenNineSix() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		if(VirtualCurrency.LTC.equals(checkerInfo.getCurrencyBase())) {
			return URL_LTC;
		} else {
			return URL_BTC;
		}
	}

	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject tickerJsonObject = jsonObject.getJSONObject("ticker");

		ticker.bid = ParseUtils.getDoubleFromString(tickerJsonObject, "buy");
		ticker.ask = ParseUtils.getDoubleFromString(tickerJsonObject, "sell");
		ticker.vol = ParseUtils.getDoubleFromString(tickerJsonObject, "vol");
		ticker.high = ParseUtils.getDoubleFromString(tickerJsonObject, "high");
		ticker.low = ParseUtils.getDoubleFromString(tickerJsonObject, "low");
		ticker.last = ParseUtils.getDoubleFromString(tickerJsonObject, "last");
	}
}