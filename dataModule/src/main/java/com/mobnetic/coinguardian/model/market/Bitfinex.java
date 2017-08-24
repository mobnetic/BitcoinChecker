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

public class Bitfinex extends Market {

	private final static String NAME = "Bitfinex";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://api.bitfinex.com/v1/pubticker/%1$s";
	private final static String URL_CURRENCY_PAIRS = "https://api.bitfinex.com/v1/symbols";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BCC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.BCH, new String[]{
				VirtualCurrency.BTC,
				VirtualCurrency.ETH,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.BCU, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.DSH, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.EOS, new String[]{
				VirtualCurrency.BTC,
				VirtualCurrency.ETH,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.ETC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.ETH, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.IOT, new String[]{
				VirtualCurrency.BTC,
				VirtualCurrency.ETH,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.OMG, new String[]{
				VirtualCurrency.BTC,
				VirtualCurrency.ETH,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.RRT, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.SAN, new String[]{
				VirtualCurrency.BTC,
				VirtualCurrency.ETH,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.XMR, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.XRP, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.ZEC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
	}

	public Bitfinex() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		String pairId = checkerInfo.getCurrencyPairId();
		if (pairId == null) {
			pairId = String.format("%1$s%2$s",
					checkerInfo.getCurrencyBaseLowerCase(),
					checkerInfo.getCurrencyCounterLowerCase());
		}
		return String.format(URL, pairId);
	}

	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = ParseUtils.getDoubleFromString(jsonObject, "bid");
		ticker.ask = ParseUtils.getDoubleFromString(jsonObject, "ask");
		ticker.vol = ParseUtils.getDoubleFromString(jsonObject, "volume");
		ticker.high = ParseUtils.getDoubleFromString(jsonObject, "high");
		ticker.low = ParseUtils.getDoubleFromString(jsonObject, "low");
		ticker.last = ParseUtils.getDoubleFromString(jsonObject, "last_price");
		ticker.timestamp = (long) (jsonObject.getDouble("timestamp")*TimeUtils.MILLIS_IN_SECOND);
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
			final String pairId = pairsArray.getString(i);
			if (pairId != null && pairId.length() == 6) {
				pairs.add(new CurrencyPairInfo(
						pairId.substring(0, 3).toUpperCase(Locale.US),
						pairId.substring(3).toUpperCase(Locale.US),
						pairId));
			}
		}
	}
}
