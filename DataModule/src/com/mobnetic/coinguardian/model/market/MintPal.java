package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class MintPal extends Market {

	private final static String NAME = "MintPal";
	private final static String TTS_NAME = "Mint Pal";
	private final static String URL = "https://api.mintpal.com/market/stats/%1$s/%2$s/";
	private final static String URL_CURRENCY_PAIRS = "https://api.mintpal.com/market/summary/";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.AUR, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.BC, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CAGE, new String[]{ VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CTM, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.DGB, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.DOGE, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.DOPE, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.DRK, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.EMO, new String[]{ VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.FLT, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.HIRO, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.HVC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.KARM, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.KDC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MINT, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MRC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MRS, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MZC, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.OLY, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.PANDA, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.PENG, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.PND, new String[]{ VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.POT, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.RBBT, new String[]{ VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.RIC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.SAT, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.SPA, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.SUN, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.TAK, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.TES, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.TOP, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.UNO, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.USDE, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.UTC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.ZED, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.ZEIT, new String[]{ VirtualCurrency.BTC });

	}
	
	public MintPal() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTicker(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		parseTickerFromJsonObject(requestId, new JSONArray(responseString).getJSONObject(0), ticker, checkerInfo);
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.vol = jsonObject.getDouble("24hvol");
		ticker.high = jsonObject.getDouble("24hhigh");
		ticker.low = jsonObject.getDouble("24hlow");
		ticker.last = jsonObject.getDouble("last_price");
	}
	
	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL_CURRENCY_PAIRS;
	}
	
	@Override
	protected void parseCurrencyPairs(int requestId, String responseString, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray jsonArray = new JSONArray(responseString);
		for(int i=0; i<jsonArray.length(); ++i) {
			JSONObject marketObject = jsonArray.getJSONObject(i);
			pairs.add(new CurrencyPairInfo(
					marketObject.getString("code"),
					marketObject.getString("exchange"),
					null
				));
		}
	}
}
