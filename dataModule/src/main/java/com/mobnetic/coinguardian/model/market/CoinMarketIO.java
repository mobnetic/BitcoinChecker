package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class CoinMarketIO extends Market {

	private final static String NAME = "CoinMarket.io";
	private final static String TTS_NAME = "Coin Market IO";
	private final static String URL = "https://coinmarket.io/ticker/%1$s%2$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.LEAF, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.USDE, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DGB, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.KDC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.CON, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.NOBL, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.SMC, new String[]{
				VirtualCurrency.BTC
			});
//		CURRENCY_PAIRS.put(VirtualCurrency.PRT, new String[]{
//				VirtualCurrency.BTC
//			});
		CURRENCY_PAIRS.put(VirtualCurrency.VTC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.UTC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.KARM, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.RDD, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.RPD, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.ICN, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.PENG, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.MINT, new String[]{
				VirtualCurrency.BTC
			});
	}
	
	public CoinMarketIO() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.vol = jsonObject.getDouble("volume24");
		ticker.last = jsonObject.getDouble("last");
	}
}
