package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;
import com.mobnetic.coinguardian.util.ParseUtils;
import com.mobnetic.coinguardian.util.TimeUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class Binance extends Market {

	private final static String NAME = "Binance";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://api.binance.com/api/v1/ticker/24hr?symbol=%1$s";
	private final static String URL_CURRENCY_PAIRS = "https://www.binance.com/api/v1/ticker/allPrices";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				Currency.USD
		});
	}

	public Binance() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		String pairId = checkerInfo.getCurrencyPairId();
		if (pairId == null) {
			pairId = String.format("%1$s%2$s",
					checkerInfo.getCurrencyBaseUpperCase(),
					checkerInfo.getCurrencyCounterUpperCase());
		}
		return String.format(URL, pairId);
	}

	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = ParseUtils.getDoubleFromString(jsonObject, "bidPrice");
		ticker.ask = ParseUtils.getDoubleFromString(jsonObject, "askPrice");
		ticker.vol = ParseUtils.getDoubleFromString(jsonObject, "volume");
		ticker.high = ParseUtils.getDoubleFromString(jsonObject, "highPrice");
		ticker.low = ParseUtils.getDoubleFromString(jsonObject, "lowPrice");
		ticker.last = ParseUtils.getDoubleFromString(jsonObject, "lastPrice");
		ticker.timestamp = System.currentTimeMillis();
	}

	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL_CURRENCY_PAIRS;
	}

	@Override
	protected void parseCurrencyPairs(int requestId, String responseString, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray pairsArray = new JSONArray(responseString);
		for (int i = 0; i < pairsArray.length(); ++i) {
			final JSONObject pairObject = pairsArray.getJSONObject(i);
			final String pairId = pairObject.optString("symbol");
			if (pairId != null && pairId.length() == 6) {
				pairs.add(new CurrencyPairInfo(
						pairId.substring(0, 3).toUpperCase(Locale.US),
						pairId.substring(3).toUpperCase(Locale.US),
						pairId));
			}
		}
	}
}
