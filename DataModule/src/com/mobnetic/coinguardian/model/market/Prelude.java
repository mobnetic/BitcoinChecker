package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class Prelude extends Market {

	private final static String NAME = "Prelude";
	private final static String TTS_NAME = NAME;
	private final static String URL_1 = "https://api.prelude.io/pairings/%1$s";
	private final static String URL_2_BTC = "https://api.prelude.io/statistics/%1$s";
	private final static String URL_2_USD = "https://api.prelude.io/statistics-usd/%1$s";
	private final static String URL_CURRENCY_PAIRS_BTC = "https://api.prelude.io/pairings/btc";
	private final static String URL_CURRENCY_PAIRS_USD = "https://api.prelude.io/pairings/usd";
	
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency._888, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.AUR, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.BC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.DGB, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.DGC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.DOGE, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.DRK, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.EMC2, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.MAX, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.MEOW, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.MINT, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.PPC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.QRK, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
//		CURRENCY_PAIRS.put(VirtualCurrency.RDD, new String[]{
//				VirtualCurrency.BTC,
//				Currency.USD
//		});
		CURRENCY_PAIRS.put(VirtualCurrency.VTC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
//		CURRENCY_PAIRS.put(VirtualCurrency.ZET, new String[]{
//				VirtualCurrency.BTC,
//				Currency.USD
//		});
	}

	public Prelude() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		if (requestId == 0) {
			return String.format(URL_1, checkerInfo.getCurrencyCounterLowerCase());
		}

		if (Currency.USD.equals(checkerInfo.getCurrencyCounter())) {
			return String.format(URL_2_USD, checkerInfo.getCurrencyBase());
		} else {
			return String.format(URL_2_BTC, checkerInfo.getCurrencyBase());
		}
	}

	@Override
	public int getNumOfRequests(CheckerInfo checkerInfo) {
		return 2;
	}

	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		// commas?! use US number parser.
		NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
		if (requestId == 0) {
			JSONArray pairings = jsonObject.getJSONArray("pairings");
			for (int i = 0; i < pairings.length(); i++) {
				JSONObject pairing = pairings.getJSONObject(i);
				String pair = pairing.getString("pair");
				if (checkerInfo.getCurrencyBase().equals(pair)) {
					ticker.last = numberFormat.parse(pairing.getJSONObject("last_trade").getString("rate")).doubleValue();
					return;
				}
			}
		} else {
			JSONObject statistics = jsonObject.getJSONObject("statistics");
			ticker.vol = numberFormat.parse(statistics.getString("volume")).doubleValue();
			ticker.high = numberFormat.parse(statistics.getString("high")).doubleValue();
			ticker.low = numberFormat.parse(statistics.getString("low")).doubleValue();
		}
	}
	
	// ====================
	// Get currency pairs
	// ====================
	@Override
	public int getCurrencyPairsNumOfRequests() {
		return 2;
	}
	
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		if(requestId==1)
			return URL_CURRENCY_PAIRS_USD;
		return URL_CURRENCY_PAIRS_BTC;
	}
	
	@Override
	protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray pairingsArray = jsonObject.getJSONArray("pairings");
		final String currencyCounter = jsonObject.getString("from");
		
		for(int i=0; i<pairingsArray.length(); ++i) {
			JSONObject pairObject = pairingsArray.getJSONObject(i);
			pairs.add(new CurrencyPairInfo(
					pairObject.getString("pair"),
					currencyCounter,
					null
				));
		}
	}
}
