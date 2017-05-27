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

public class Bitso extends Market {

	private final static String NAME = "Bitso";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://api.bitso.com/public/info";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>(1);
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.MXN
			});
		CURRENCY_PAIRS.put(VirtualCurrency.ETH, new String[]{
				Currency.MXN
			});
		CURRENCY_PAIRS.put(VirtualCurrency.XRP, new String[]{
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
		final JSONObject pairJsonObject = jsonObject.getJSONObject(checkerInfo.getCurrencyBaseLowerCase()+"_"+checkerInfo.getCurrencyCounterLowerCase());
		ticker.bid = ParseUtils.getDoubleFromString(pairJsonObject, "buy");
		ticker.ask = ParseUtils.getDoubleFromString(pairJsonObject, "sell");
		ticker.vol = ParseUtils.getDoubleFromString(pairJsonObject, "volume");
		ticker.last = ParseUtils.getDoubleFromString(pairJsonObject, "rate");
	}
}
