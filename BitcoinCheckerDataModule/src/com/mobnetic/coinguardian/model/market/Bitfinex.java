package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;
import com.mobnetic.coinguardian.util.TimeUtils;

public class Bitfinex extends Market {

	public final static String NAME = "Bitfinex";
	public final static String TTS_NAME = NAME;
	public final static String URL = "https://api.bitfinex.com/v1/ticker/%1$s%2$s";
	public final static String URL_SECOND = "https://api.bitfinex.com/v1/today/%1$s%2$s";
	public final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				Currency.USD,
				VirtualCurrency.BTC
			});
	}
	
	public Bitfinex() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public int getNumOfRequests(CheckerInfo checkerInfo) {
		return 2;
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		if(requestId==0)
			return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
		else
			return String.format(URL_SECOND, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
	}
	
	@Override
	protected void parseTickerInnerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		if(requestId==0) {
			ticker.bid = jsonObject.getDouble("bid");
			ticker.ask = jsonObject.getDouble("ask");
			ticker.last = jsonObject.getDouble("last_price");
			ticker.timestamp = (long) (jsonObject.getDouble("timestamp")*TimeUtils.MILLIS_IN_SECOND);
		} else {
			ticker.vol = jsonObject.getDouble("volume");
			ticker.high = jsonObject.getDouble("high");
			ticker.low = jsonObject.getDouble("low");
		}
	}
}
