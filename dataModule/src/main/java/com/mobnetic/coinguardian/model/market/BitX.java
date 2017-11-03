package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class BitX extends Market {

	private final static String NAME = "Luno";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://api.mybitx.com/api/1/ticker?pair=%1$s";
	private final static String URL_CURRENCY_PAIRS = "https://api.mybitx.com/api/1/tickers";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.IDR,
				Currency.SGD,
				Currency.MYR,
				Currency.NGN,
				Currency.ZAR
			});
	}
	
	public BitX() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		final String pairString;
		if(checkerInfo.getCurrencyPairId()==null) {
			pairString = String.format("%1$s%2$s", fixCurrency(checkerInfo.getCurrencyBase()), fixCurrency(checkerInfo.getCurrencyCounter()));
		} else {
			pairString = checkerInfo.getCurrencyPairId();
		}
		return String.format(URL, pairString);
	}
	
	private String fixCurrency(String currency) {
		if(VirtualCurrency.BTC.equals(currency)) {
			return VirtualCurrency.XBT;
		} else if(VirtualCurrency.XBT.equals(currency)) {
			return VirtualCurrency.BTC;
		}

		return currency;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("bid");
		ticker.ask = jsonObject.getDouble("ask");
		ticker.vol = jsonObject.getDouble("rolling_24_hour_volume");		
		ticker.last = jsonObject.getDouble("last_trade");		
		ticker.timestamp = jsonObject.getLong("timestamp");
	}

	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL_CURRENCY_PAIRS;
	}

	@Override
	protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray dataJsonArray = jsonObject.getJSONArray("tickers");
		for(int i=0; i<dataJsonArray.length(); ++i) {
			final String currencyPair = dataJsonArray.getJSONObject(i).getString("pair");
			if(currencyPair==null) {
				continue;
			}
			String currencyBase;
			String currencyCounter;
			try {
				currencyBase = fixCurrency(currencyPair.substring(0, 3));
				currencyCounter = fixCurrency(currencyPair.substring(3));
			} catch (Exception e) {
				continue;
			}
			pairs.add(new CurrencyPairInfo(
					currencyBase,
					currencyCounter,
					currencyPair
			));
		}
	}
}
