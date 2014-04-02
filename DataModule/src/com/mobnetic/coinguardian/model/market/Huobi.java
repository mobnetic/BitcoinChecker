package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class Huobi extends Market {

	private final static String NAME = "Huobi";
	private final static String TTS_NAME = NAME;
	private final static String URL_BTC = "https://market.huobi.com/staticmarket/detail.html";
	private final static String URL_LTC = "https://market.huobi.com/staticmarket/detail_ltc.html";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.CNY
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				Currency.CNY
			});
	}
	
	public Huobi() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		if(VirtualCurrency.LTC.equals(checkerInfo.getCurrencyBase()))
			return URL_LTC;
		else
			return URL_BTC;
	}
	
	@Override
	protected void parseTicker(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final String startString = "view_detail(";
		final String endString = ")";
		
		if(responseString!=null && responseString.length()>(startString.length()+endString.length()))
			responseString = responseString.substring(startString.length(), responseString.length()-endString.length());
		super.parseTicker(requestId, responseString, ticker, checkerInfo);
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		JSONArray buys = jsonObject.optJSONArray("buys");
		if(buys!=null && buys.length()>0)
			ticker.bid = buys.getJSONObject(0).getDouble("price");
		JSONArray sells = jsonObject.optJSONArray("sells");
		if(sells!=null && sells.length()>0)
			ticker.ask = sells.getJSONObject(0).getDouble("price");
		ticker.vol = jsonObject.getDouble("amount");
		ticker.high = jsonObject.getDouble("p_high");
		ticker.low = jsonObject.getDouble("p_low");
		ticker.last = jsonObject.getDouble("p_last");
	}
}
