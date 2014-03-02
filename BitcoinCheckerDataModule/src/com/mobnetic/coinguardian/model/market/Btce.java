package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class Btce extends Market {

	public final static String NAME = "Btc-e";
	public final static String TTS_NAME = NAME;
	public final static String URL = "https://btc-e.com/api/2/%1$s_%2$s/ticker";
	public final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
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
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.TRC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.PPC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.FTC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.XPM, new String[]{
				VirtualCurrency.BTC
			});
	}
	
	public Btce() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
	}
	
	@Override
	protected void parseTickerInnerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		JSONObject tickerJsonObject = jsonObject.getJSONObject("ticker");
		
		ticker.bid = tickerJsonObject.getDouble("sell");	// REVERSED!
		ticker.ask = tickerJsonObject.getDouble("buy");	// REVERSED!
		ticker.vol = tickerJsonObject.getDouble("vol");
		ticker.high = tickerJsonObject.getDouble("high");
		ticker.low = tickerJsonObject.getDouble("low");
		ticker.last = tickerJsonObject.getDouble("last");
		ticker.timestamp = tickerJsonObject.getLong("updated");
	}
}