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

public class Btcturk extends Market {

	private final static String NAME = "BtcTurk";
	private final static String TTS_NAME = "Btc Turk";
	private final static String URL = "https://www.btcturk.com/api/ticker";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.TRY
			});
	}
	
	public Btcturk() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("Bid");
		ticker.ask = jsonObject.getDouble("Ask");
		ticker.vol = jsonObject.getDouble("Volume");
		ticker.high = jsonObject.getDouble("High");
		ticker.low = jsonObject.getDouble("Low");
		ticker.last = jsonObject.getDouble("Last");
		ticker.timestamp = (long) (jsonObject.getDouble("Timestamp")*TimeUtils.MILLIS_IN_SECOND);
	}
}
