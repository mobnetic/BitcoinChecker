package com.mobnetic.coinguardian.model.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardiandatamodule.R;

public class CryptoRush extends Market {

	public final static String NAME = "CryptoRush";
	public final static String TTS_NAME = "Crypto Rush";
	public final static String URL = "https://cryptorush.in/marketdata/all.json";
	private final static String URL_CURRENCY_PAIRS = URL;
	
	public CryptoRush() {
		super(NAME, TTS_NAME, null);
	}
	
	@Override
	public int getCautionResId() {
		return R.string.market_caution_much_data;
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject pairJsonObject = jsonObject.getJSONObject(checkerInfo.getCurrencyBase()+"/"+checkerInfo.getCurrencyCounter());
		
		ticker.bid = pairJsonObject.getDouble("current_bid");
		ticker.ask = pairJsonObject.getDouble("current_ask");
		ticker.vol = pairJsonObject.getDouble("volume_base_24h");
		ticker.high = pairJsonObject.getDouble("highest_24h");
		ticker.low = pairJsonObject.getDouble("lowest_24h");
		ticker.last = pairJsonObject.getDouble("last_trade");
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
		final JSONArray pairNames = jsonObject.names();
		
		for(int i=0; i<pairNames.length(); ++i) {
			String pairId = pairNames.getString(i);
			if(pairId==null)
				continue;
			String[] currencies = pairId.split("/");
			if(currencies.length!=2)
				continue;
			
			pairs.add(new CurrencyPairInfo(currencies[0], currencies[1], pairId));
		}
	}
}