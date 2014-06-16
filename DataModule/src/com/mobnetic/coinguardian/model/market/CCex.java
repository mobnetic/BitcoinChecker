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

public class CCex extends Market {

	public final static String NAME = "C-CEX";
	public final static String TTS_NAME = "C-Cex";
	public final static String URL = "https://c-cex.com/t/%1$s-%2$s.json";
	public final static String URL_CURRENCY_PAIRS = "https://c-cex.com/t/pairs.json";
	public final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency._66, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.ALB, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.ANT, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.AUR, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.BLC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.BTQ, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.COIN, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.CSC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DGC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DOGE, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DRK, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DUCK, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DVC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.FRK, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.FRY, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.FSC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.FTC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.GRC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.IFC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.IQD, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.IXC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.KRN, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LDC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LEAF, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.MIM, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.MINT, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.MTC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.NMC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.PCC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.PLC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.PMC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.PPC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.Q2C, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.QRK, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.RUBY, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.STC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.SYN, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.TES, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.TTC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.UNI, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.USDE, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.UTC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.VTC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.WDC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.ZED, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
	}
	
	public CCex() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		JSONObject tickerObject = jsonObject.getJSONObject("ticker");
		
		ticker.bid = tickerObject.getDouble("buy");
		ticker.ask = tickerObject.getDouble("sell");
		ticker.high = tickerObject.getDouble("high");
		ticker.low = tickerObject.getDouble("low");
		ticker.last = tickerObject.getDouble("lastprice");
//		ticker.timestamp = tickerObject.getLong("updated");	// strange date?
	}
	
	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL_CURRENCY_PAIRS;
	}
	
	@Override
	protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray pairsJsonArray = jsonObject.getJSONArray("pairs");
		
		for(int i=0; i<pairsJsonArray.length(); ++i) {
			String pair = pairsJsonArray.getString(i);
			if(pair==null)
				continue;
			String[] currencies = pair.split("-", 2);
			if(currencies.length!=2 || currencies[0]==null || currencies[1]==null)
				continue;
			
			pairs.add(new CurrencyPairInfo(currencies[0].toUpperCase(Locale.US), currencies[1].toUpperCase(Locale.US), pair));
		}
	}
}
