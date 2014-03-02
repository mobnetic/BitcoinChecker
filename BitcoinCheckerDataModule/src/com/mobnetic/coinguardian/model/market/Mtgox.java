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

public class Mtgox extends Market {

	public final static String NAME = "Mtgox";
	public final static String TTS_NAME = "MT gox";
	public final static String URL = "https://data.mtgox.com/api/2/%1$s%2$s/money/ticker";
	public final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD,
				Currency.EUR,
				Currency.CAD,
				Currency.GBP,
				Currency.CHF,
				Currency.RUB,
				Currency.AUD,
				Currency.SEK,
				Currency.DKK,
				Currency.HKD,
				Currency.PLN,
				Currency.CNY,
				Currency.SGD,
				Currency.THB,
				Currency.NZD,
				Currency.JPY
			});
	}
	
	public Mtgox() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerInnerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject dataObject = jsonObject.getJSONObject("data");
		
		ticker.bid = getDoubleFromMtgoxFormatObject(dataObject, "buy");
		ticker.ask = getDoubleFromMtgoxFormatObject(dataObject, "sell");
		ticker.vol = getDoubleFromMtgoxFormatObject(dataObject, "vol");
		ticker.high = getDoubleFromMtgoxFormatObject(dataObject, "high");
		ticker.low = getDoubleFromMtgoxFormatObject(dataObject, "low");
		ticker.last = getDoubleFromMtgoxFormatObject(dataObject, "last_local");
		ticker.timestamp = dataObject.getLong("now")/TimeUtils.NANOS_IN_MILLIS;
	}
	
	private double getDoubleFromMtgoxFormatObject(JSONObject jsonObject, String key) throws Exception {
		final JSONObject innerObject = jsonObject.getJSONObject(key);
		return innerObject.getDouble("value");
	}
}
