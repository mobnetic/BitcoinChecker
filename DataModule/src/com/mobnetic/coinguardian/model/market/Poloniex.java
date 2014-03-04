package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class Poloniex extends Market {

	private final static String NAME = "Poloniex";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://poloniex.com/public?command=returnTicker";
	private final static String URL_ORDERS = "https://poloniex.com/public?command=returnOrderBook&currencyPair=%1$s_%2$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.AUR, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.BC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.CACH, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.CASH, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.CGA, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.CNOTE, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.CON, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.CORG, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DIME, new String[]{
				VirtualCurrency.LTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DOGE, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DRK, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.EAC, new String[]{
				VirtualCurrency.LTC
		});
		CURRENCY_PAIRS.put(VirtualCurrency.eTOK, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.FLAP, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.FOX, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.FRQ, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.FZ, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.GLB, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.GRC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.HUC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.ICN, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.IXC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.IFC, new String[]{
				VirtualCurrency.LTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.KDC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LEAF, new String[]{
				VirtualCurrency.LTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.MAX, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.MEC, new String[]{
				VirtualCurrency.BTC,
				VirtualCurrency.LTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.MEOW, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.MINT, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.MMC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.MRC, new String[]{
				VirtualCurrency.LTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.MTS, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.MYR, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.NMC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.NOBL, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.NXT, new String[]{
				VirtualCurrency.BTC,
				VirtualCurrency.LTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.OLY, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.PMC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.PAND, new String[]{
				VirtualCurrency.LTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.PRC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.PTS, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.Q2C, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.REDD, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.RIC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.SMC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.SOC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.SUN, new String[]{
				VirtualCurrency.LTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.SXC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.USDE, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.UTC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.VTC, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.WIKI, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.WOLF, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.XCP, new String[]{
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.XPM, new String[]{
				VirtualCurrency.BTC
			});
	}
	
	public Poloniex() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public int getNumOfRequests(CheckerInfo checkerInfo) {
		return 2;
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		if(requestId==0)
			return URL;
		else
			return String.format(URL_ORDERS, checkerInfo.getCurrencyCounter(), checkerInfo.getCurrencyBase()); // Reversed currencies
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		if(requestId==0) {
			ticker.last = jsonObject.getDouble(checkerInfo.getCurrencyCounter()+"_"+checkerInfo.getCurrencyBase());	// Reversed currencies
		} else {
			ticker.bid = getFirstPriceFromOrder(jsonObject, "bids");
			ticker.ask = getFirstPriceFromOrder(jsonObject, "asks");
		}
	}
	
	private double getFirstPriceFromOrder(JSONObject jsonObject, String arrayName) throws Exception {
		if(jsonObject.has(arrayName)) {
			JSONArray jsonArray = jsonObject.getJSONArray(arrayName);
			if(jsonArray.length()>0) {
				JSONArray orderJsonArray = jsonArray.getJSONArray(0);
				return orderJsonArray.getDouble(0);
			}
		}
		
		return Ticker.NO_DATA;
	}
}
