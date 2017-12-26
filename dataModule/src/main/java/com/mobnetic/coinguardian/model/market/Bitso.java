package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;
import com.mobnetic.coinguardian.util.ParseUtils;

public class Bitso extends Market {

	private final static String NAME = "Bitso";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://api.bitso.com/public/info";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.MXN
			});
		CURRENCY_PAIRS.put(VirtualCurrency.ETH, new String[]{
				VirtualCurrency.BTC,
				Currency.MXN
			});
		CURRENCY_PAIRS.put(VirtualCurrency.XRP, new String[]{
				VirtualCurrency.BTC,
				Currency.MXN
			});
		CURRENCY_PAIRS.put(VirtualCurrency.BCH, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				VirtualCurrency.BTC,
				Currency.MXN
			});
	}
	
	public Bitso() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		String pairId = checkerInfo.getCurrencyPairId();
		if (pairId == null) {
			pairId = checkerInfo.getCurrencyBaseLowerCase()+"_"+checkerInfo.getCurrencyCounterLowerCase();
		}
		final JSONObject pairJsonObject = jsonObject.getJSONObject(pairId);
		ticker.bid = ParseUtils.getDoubleFromString(pairJsonObject, "buy");
		ticker.ask = ParseUtils.getDoubleFromString(pairJsonObject, "sell");
		ticker.vol = ParseUtils.getDoubleFromString(pairJsonObject, "volume");
		ticker.last = ParseUtils.getDoubleFromString(pairJsonObject, "rate");
	}

	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL;
	}

	@Override
	protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray pairIds = jsonObject.names();
		for(int i=0; i<pairIds.length(); ++i) {
			String pairId = pairIds.getString(i);
			if(pairId==null)
				continue;
			String[] currencies = pairId.split("_");
			if(currencies.length != 2)
				continue;

			pairs.add(new CurrencyPairInfo(
					currencies[0].toUpperCase(Locale.US),
					currencies[1].toUpperCase(Locale.US),
					pairId));
		}
	}
}
