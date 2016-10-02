package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class CryptoTrade extends Market {

	private final static String NAME = "Crypto-Trade";
	private final static String TTS_NAME = "Crypto Trade";
	private final static String URL = "https://crypto-trade.com/api/1/ticker/%1$s_%2$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD,
				Currency.EUR
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.NMC, new String[]{
				Currency.USD,
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.XPM, new String[]{
				Currency.USD,
				VirtualCurrency.BTC,
				VirtualCurrency.PPC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.PPC, new String[]{
				Currency.USD,
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.TRC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.FTC, new String[]{
				Currency.USD,
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DVC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.WDC, new String[]{
				Currency.USD,
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DGC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.UTC, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC
			});
	}
	
	public CryptoTrade() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject dataObject = jsonObject.getJSONObject("data");
		
		ticker.bid = dataObject.getDouble("max_bid");
		ticker.ask = dataObject.getDouble("min_ask");
		ticker.vol = dataObject.getDouble("vol_"+checkerInfo.getCurrencyBaseLowerCase());
		ticker.high = dataObject.getDouble("high");
		ticker.low = dataObject.getDouble("low");
		ticker.last = dataObject.getDouble("last");
	}
	
	@Override
	protected String parseErrorFromJsonObject(int requestId, JSONObject jsonObject, CheckerInfo checkerInfo) throws Exception {
		return jsonObject.getString("error");
	}
}
