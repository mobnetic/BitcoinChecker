package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class Kraken extends Market {

	private final static String NAME = "Kraken";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://api.kraken.com/0/public/Ticker?pair=%1$s%2$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD,
				Currency.EUR,
				Currency.KRW,
				VirtualCurrency.LTC,
				VirtualCurrency.DOGE,
				VirtualCurrency.NMC,
				VirtualCurrency.XRP,
				VirtualCurrency.VEN
		});
		CURRENCY_PAIRS.put(Currency.EUR, new String[]{
				VirtualCurrency.DOGE,
				VirtualCurrency.XRP,
				VirtualCurrency.VEN
			});
		CURRENCY_PAIRS.put(Currency.KRW, new String[]{
				VirtualCurrency.XRP
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				Currency.USD,
				Currency.EUR,
				Currency.KRW,
				VirtualCurrency.DOGE,
				VirtualCurrency.XRP
			});
		CURRENCY_PAIRS.put(VirtualCurrency.NMC, new String[]{
				Currency.USD,
				Currency.EUR,
				Currency.KRW,
				VirtualCurrency.DOGE,
				VirtualCurrency.XRP
			});
		CURRENCY_PAIRS.put(Currency.USD, new String[]{
				VirtualCurrency.DOGE,
				VirtualCurrency.XRP,
				VirtualCurrency.VEN
			});
		CURRENCY_PAIRS.put(VirtualCurrency.VEN, new String[]{
				VirtualCurrency.XRP
			});
	}
	
	public Kraken() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		String currencyBase = fixCurrency(checkerInfo.getCurrencyBase());
		String currencyCounter = fixCurrency(checkerInfo.getCurrencyCounter());
		
		return String.format(URL, currencyBase, currencyCounter);
	}
	
	private String fixCurrency(String currency) {
		if(VirtualCurrency.BTC.equals(currency))
			return VirtualCurrency.XBT;
		if(VirtualCurrency.VEN.equals(currency))
			return VirtualCurrency.XVN;
		if(VirtualCurrency.DOGE.equals(currency))
			return VirtualCurrency.XDG;
		return currency;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject resultObject = jsonObject.getJSONObject("result");
		final JSONObject pairObject = resultObject.getJSONObject(resultObject.names().getString(0));
		
		ticker.bid = getDoubleFromJsonArrayObject(pairObject, "b");
		ticker.ask = getDoubleFromJsonArrayObject(pairObject, "a");
		ticker.vol = getDoubleFromJsonArrayObject(pairObject, "v");
		ticker.high = getDoubleFromJsonArrayObject(pairObject, "h");
		ticker.low = getDoubleFromJsonArrayObject(pairObject, "l");
		ticker.last = getDoubleFromJsonArrayObject(pairObject, "c");
//		ticker.timestamp = ; //
	}
	
	private double getDoubleFromJsonArrayObject(JSONObject jsonObject, String arrayKey) throws Exception {
		final JSONArray jsonArray = jsonObject.getJSONArray(arrayKey);
		return jsonArray!=null && jsonArray.length()>0 ? jsonArray.getDouble(0) : 0;
	}
}
