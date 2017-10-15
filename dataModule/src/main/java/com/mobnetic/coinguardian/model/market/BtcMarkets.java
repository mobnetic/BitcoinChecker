package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class BtcMarkets extends Market {

	private final static String NAME = "BtcMarkets.net";
	private final static String TTS_NAME = "BTC Markets net";
	private final static String URL = "https://api.btcmarkets.net/market/%1$s/%2$s/tick";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.AUD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				VirtualCurrency.BTC,
				Currency.AUD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.ETC, new String[]{
				VirtualCurrency.BTC,
				Currency.AUD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.ETH, new String[]{
				VirtualCurrency.BTC,
				Currency.AUD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.XRP, new String[]{
				VirtualCurrency.BTC,
				Currency.AUD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.BCH, new String[]{
				VirtualCurrency.BTC,
				Currency.AUD
			});
	}
	
	public BtcMarkets() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("bestBid");
		ticker.ask = jsonObject.getDouble("bestAsk");
		ticker.last = jsonObject.getDouble("lastPrice");
		ticker.timestamp = jsonObject.getLong("timestamp");
	}
}
