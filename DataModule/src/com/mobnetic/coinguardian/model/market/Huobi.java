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
	private final static String URL = "https://detail.huobi.com/staticmarket/detail.html?jsoncallback=";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.CNY
			});
	}
	
	public Huobi() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerInner(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		if(responseString!=null && responseString.length()>13)
			responseString = responseString.substring("view_detail(".length(), responseString.length()-")".length());
		super.parseTickerInner(requestId, responseString, ticker, checkerInfo);
	}
	
	@Override
	protected void parseTickerInnerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
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
