package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class CexIO extends Market {

	private final static String NAME = "CEX.IO";
	private final static String TTS_NAME = "CEX IO";
	private final static String URL = "https://cex.io/api/ticker/%1$s/%2$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD,
				Currency.EUR
			});
		CURRENCY_PAIRS.put(VirtualCurrency.GHS, new String[]{
				Currency.USD,
				VirtualCurrency.BTC,
				VirtualCurrency.LTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DOGE, new String[]{
				Currency.USD,
				VirtualCurrency.BTC,
				VirtualCurrency.LTC,
				Currency.EUR
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DRK, new String[]{
				Currency.USD,
				VirtualCurrency.BTC,
				VirtualCurrency.LTC
			});	
		CURRENCY_PAIRS.put(VirtualCurrency.NMC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.IXC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.POT, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.ANC, new String[]{
				VirtualCurrency.BTC,
				VirtualCurrency.LTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.MEC, new String[]{
				VirtualCurrency.BTC,
				VirtualCurrency.LTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.WDC, new String[]{
				VirtualCurrency.BTC,
				VirtualCurrency.LTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.FTC, new String[]{
				VirtualCurrency.BTC,
				VirtualCurrency.LTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DGB, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.USDE, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.MYR, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.AUR, new String[]{
				VirtualCurrency.BTC
			});			
	}
	
	public CexIO() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		
		if(jsonObject.has("bid"))
			ticker.bid = jsonObject.getDouble("bid");
		if(jsonObject.has("ask"))
			ticker.ask = jsonObject.getDouble("ask");
		ticker.vol = jsonObject.getDouble("volume");
		ticker.high = jsonObject.getDouble("high");
		ticker.low = jsonObject.getDouble("low");
		ticker.last = jsonObject.getDouble("last");
	}
}
