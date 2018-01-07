package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

public class Huobi extends Market {

	private final static String NAME = "Huobi";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://api.huobi.pro/market/detail/merged?symbol=%s%s";
	private final static String URL_CURRENCY_PAIRS = "https://api.huobi.pro/v1/common/symbols";
	
	public Huobi() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject tickerJsonObject = jsonObject.getJSONObject("tick");
		ticker.bid = tickerJsonObject.getJSONArray("bid").getDouble(0);
		ticker.ask = tickerJsonObject.getJSONArray("ask").getDouble(0);
		ticker.vol = tickerJsonObject.getDouble("vol");
		ticker.high = tickerJsonObject.getDouble("high");
		ticker.low = tickerJsonObject.getDouble("low");
		ticker.last = tickerJsonObject.getDouble("close");
	}

	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL_CURRENCY_PAIRS;
	}

	@Override
	protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		if("ok".equalsIgnoreCase(jsonObject.getString("status"))) {
			final JSONArray data = jsonObject.getJSONArray("data");
			for(int i = 0; i < data.length(); i++) {
				final String base = data.getJSONObject(i).getString("base-currency").toUpperCase(Locale.US);
                final String counter = data.getJSONObject(i).getString("quote-currency").toUpperCase(Locale.US);
				pairs.add(new CurrencyPairInfo(base, counter, null));
			}
		} else {
			throw new Exception("Parse currency pairs error.");
		}
	}
}
