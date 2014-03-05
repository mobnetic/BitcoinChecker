package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class CryptoRush extends Market {

	public final static String NAME = "CryptoRush";
	public final static String TTS_NAME = "Crypto Rush";
	public final static String URL = "https://cryptorush.in/api.php?get=market&m=%1$s&b=%2$s&json=true&key=APIKEY&id=YOURID";
	public final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency._21, new String[]{
				VirtualCurrency.BTC,
				VirtualCurrency.LTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency._42, new String[]{
				VirtualCurrency.BTC,
				VirtualCurrency.DOGE,
				VirtualCurrency.LTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency._66, new String[]{
				VirtualCurrency.BTC,
				VirtualCurrency.DOGE,
				VirtualCurrency.LTC
			});
		// TODO
		CURRENCY_PAIRS.put(VirtualCurrency.DOGE, new String[]{
				VirtualCurrency.BTC,
				VirtualCurrency.DGC,
				VirtualCurrency.LTC
			});
		// TODO	
	}
	
	public CryptoRush() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("current_bid");
		ticker.ask = jsonObject.getDouble("current_ask");
		ticker.vol = jsonObject.getDouble("volume_base_24h");
		ticker.high = jsonObject.getDouble("highest_24h");
		ticker.low = jsonObject.getDouble("lowest_24h");
		ticker.last = jsonObject.getDouble("last_trade");
	}
}