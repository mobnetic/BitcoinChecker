package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

public class Bleutrade extends Market {
	
	private final static String NAME = "Bleutrade";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://bleutrade.com/api/v1/last_price?market=%1$s_%2$s";
	private final static String URL_2 = "https://bleutrade.com/api/v1/24h_trade_pair_info?market=%1$s_%2$s";
	private final static String URL_CURRENCY_PAIRS = "https://bleutrade.com/api/v1/trade_pairs";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	
	public Bleutrade() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public int getNumOfRequests(CheckerInfo checkerInfo) {
		return 2;
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		if(requestId==1)
			return String.format(URL_2, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTicker(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		if(requestId==1) {
			final String[] split = responseString.split(";");
			ticker.vol = Double.parseDouble(split[5]);
			ticker.high = Double.parseDouble(split[2]);
			ticker.low = Double.parseDouble(split[3]);
		} else {
			ticker.last = Double.parseDouble(responseString);
		}
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
		final String[] currencyPairs = responseString.split("\n");
		for(int i=0; i<currencyPairs.length; ++i) {
			final String pairId = currencyPairs[i];
			final int underlineIndex = pairId.indexOf('_');
			pairs.add(new CurrencyPairInfo(
					pairId.substring(0, underlineIndex),
					pairId.substring(underlineIndex+1),
					pairId
				));
		}
	}
}