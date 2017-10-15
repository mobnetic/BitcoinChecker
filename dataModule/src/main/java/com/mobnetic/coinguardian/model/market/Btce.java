package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class Btce extends Market {

	private final static String NAME = "WEX";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://wex.nz/api/3/ticker/%1$s";
	private final static String URL_CURRENCY_PAIRS = "https://wex.nz/api/3/info";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD,
				Currency.RUR,
				Currency.EUR
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD,
				Currency.RUR,
				Currency.EUR
			});
		CURRENCY_PAIRS.put(VirtualCurrency.NMC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.NVC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(Currency.USD, new String[]{
				Currency.RUR
			});
		CURRENCY_PAIRS.put(Currency.EUR, new String[]{
				Currency.USD,
				Currency.RUR
			});
		CURRENCY_PAIRS.put(VirtualCurrency.PPC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
	}
	
	public Btce() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		String pairId = checkerInfo.getCurrencyPairId();
		if(checkerInfo.getCurrencyPairId() == null) {
			pairId = String.format("%1$s_%2$s", checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
		}
		return String.format(URL, pairId);
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONArray names = jsonObject.names();
		JSONObject tickerJsonObject = jsonObject.getJSONObject(names.getString(0));
		
		ticker.bid = tickerJsonObject.getDouble("sell");	// REVERSED!
		ticker.ask = tickerJsonObject.getDouble("buy");	// REVERSED!
		ticker.vol = tickerJsonObject.getDouble("vol");
		ticker.high = tickerJsonObject.getDouble("high");
		ticker.low = tickerJsonObject.getDouble("low");
		ticker.last = tickerJsonObject.getDouble("last");
		ticker.timestamp = tickerJsonObject.getLong("updated");
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
		final JSONObject pairsJsonObject = jsonObject.getJSONObject("pairs");
		final JSONArray pairsNames = pairsJsonObject.names();
		for(int i=0; i<pairsNames.length(); ++i) {
			String pairId = pairsNames.getString(i);
			if(pairId==null)
				continue;
			String[] currencies = pairId.split("_");
			if(currencies.length!=2)
				continue;
			
			String currencyBase = currencies[0].toUpperCase(Locale.ENGLISH);
			String currencyCounter = currencies[1].toUpperCase(Locale.ENGLISH);
			pairs.add(new CurrencyPairInfo(currencyBase, currencyCounter, pairId));
		}
	}
}