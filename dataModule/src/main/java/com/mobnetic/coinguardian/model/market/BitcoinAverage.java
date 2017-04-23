package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;
import com.mobnetic.coinguardian.util.TimeUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class BitcoinAverage extends Market {

	private final static String NAME = "BitcoinAverage";
	private final static String TTS_NAME = "Bitcoin Average";
	private final static String URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/%1$s";
	private final static String URL_CURRENCY_PAIRS = "https://apiv2.bitcoinaverage.com/constants/symbols";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.AUD,
				Currency.BRL,
				Currency.CAD,
				Currency.CHF,
				Currency.CNY,
				Currency.CZK,
				Currency.EUR,
				Currency.GBP,
				Currency.ILS,
				Currency.JPY,
				Currency.NOK,
				Currency.NZD,
				Currency.PLN,
				Currency.RUB,
				Currency.SEK,
				Currency.SGD,
				Currency.USD,
				Currency.ZAR,
			});
	}
	
	public BitcoinAverage() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		final String pairId;
		if (checkerInfo.getCurrencyPairId() == null) {
			pairId = checkerInfo.getCurrencyBase() + checkerInfo.getCurrencyCounter();
		} else {
			pairId = checkerInfo.getCurrencyPairId();
		}
		return String.format(URL, pairId);
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("bid");
		ticker.ask = jsonObject.getDouble("ask");
		ticker.vol = jsonObject.getDouble("volume");
		ticker.high = jsonObject.getDouble("high");
		ticker.low = jsonObject.getDouble("low");
		ticker.last = jsonObject.getDouble("last");
		ticker.timestamp = jsonObject.getLong("timestamp") * TimeUtils.MILLIS_IN_SECOND;
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
		final JSONArray pairsJsonArray = jsonObject.getJSONObject("global").getJSONArray("symbols");
		for(int i=0; i<pairsJsonArray.length(); ++i) {
			final String pairId = pairsJsonArray.getString(i);
			final String currencyBase = pairId.substring(0, 3);
			final String currencyCounter = pairId.substring(3);
			pairs.add(new CurrencyPairInfo(currencyBase, currencyCounter, pairId));
		}
	}
}
