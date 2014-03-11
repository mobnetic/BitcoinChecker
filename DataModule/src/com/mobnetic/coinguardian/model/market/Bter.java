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

public class Bter extends Market {

	private final static String NAME = "Bter";
	private final static String TTS_NAME = "B ter";
	private final static String URL = "http://data.bter.com/api/1/ticker/%1$s_%2$s";
	private final static String URL_CURRENCY_PAIRS = "http://data.bter.com/api/1/pairs";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BQC, new String[]{ Currency.CNY, VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.BTB, new String[]{ Currency.CNY, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{ Currency.CNY });
		CURRENCY_PAIRS.put(VirtualCurrency.BTQ, new String[]{ Currency.CNY });
		CURRENCY_PAIRS.put(VirtualCurrency.BUK, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CDC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CENT, new String[]{ Currency.CNY, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CMC, new String[]{ Currency.CNY, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CNC, new String[]{ Currency.CNY, VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.DGC, new String[]{ Currency.CNY, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.DOGE, new String[]{ Currency.CNY, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.DTC, new String[]{ Currency.CNY, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.DVC, new String[]{ Currency.CNY, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.EXC, new String[]{ Currency.CNY, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.FRC, new String[]{ Currency.CNY, VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.FTC, new String[]{ Currency.CNY, VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.IFC, new String[]{ Currency.CNY, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{ Currency.CNY, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MAX, new String[]{ Currency.CNY, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MEC, new String[]{ Currency.CNY, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MINT, new String[]{ Currency.CNY, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MMC, new String[]{ Currency.CNY, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.NEC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.NET, new String[]{ Currency.CNY, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.NMC, new String[]{ Currency.CNY, VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.NXT, new String[]{ Currency.CNY, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.PPC, new String[]{ Currency.CNY, VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.PTS, new String[]{ Currency.CNY, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.QRK, new String[]{ Currency.CNY, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.RED, new String[]{ Currency.CNY, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.SRC, new String[]{ Currency.CNY, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.TAG, new String[]{ Currency.CNY, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.TIPS, new String[]{ Currency.CNY, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.TIX, new String[]{ Currency.CNY, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.VTC, new String[]{ Currency.CNY, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.WDC, new String[]{ Currency.CNY, VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.XPM, new String[]{ Currency.CNY, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.YAC, new String[]{ Currency.CNY, VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.ZCC, new String[]{ Currency.CNY, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.ZET, new String[]{ Currency.CNY, VirtualCurrency.BTC });

	}
	
	public Bter() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("buy");
		ticker.ask = jsonObject.getDouble("sell");
		ticker.vol = jsonObject.getDouble("vol_"+checkerInfo.getCurrencyBaseLowerCase());
		ticker.high = jsonObject.getDouble("high");
		ticker.low = jsonObject.getDouble("low");
		ticker.last = jsonObject.getDouble("last");
	}
	
	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl() {
		return URL_CURRENCY_PAIRS;
	}
	
	@Override
	protected void parseCurrencyPairs(String responseString, List<CurrencyPairInfo> pairs) throws Exception {
		JSONArray jsonArray = new JSONArray(responseString);
		for(int i=0; i<jsonArray.length(); ++i) {
			String pairId = jsonArray.getString(i);
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
