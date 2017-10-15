package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;
import com.mobnetic.coinguardian.util.TimeUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Btcturk extends Market {

	private final static String NAME = "BtcTurk";
	private final static String TTS_NAME = "Btc Turk";
	private final static String URL = "https://www.btcturk.com/api/ticker";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.TRY
			});
		CURRENCY_PAIRS.put(VirtualCurrency.ETH, new String[]{
				VirtualCurrency.BTC,
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
	protected void parseTicker(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONArray tickerJsonArray = new JSONArray(responseString);
		final String pairId = checkerInfo.getCurrencyBase()+checkerInfo.getCurrencyCounter();
		for (int i = 0; i < tickerJsonArray.length(); ++i) {
			final JSONObject tickerJsonObject = tickerJsonArray.getJSONObject(i);
			if (pairId.equals(tickerJsonObject.getString("pair"))) {
				ticker.bid = tickerJsonObject.getDouble("bid");
				ticker.ask = tickerJsonObject.getDouble("ask");
				ticker.vol = tickerJsonObject.getDouble("volume");
				ticker.high = tickerJsonObject.getDouble("high");
				ticker.low = tickerJsonObject.getDouble("low");
				ticker.last = tickerJsonObject.getDouble("last");
				ticker.timestamp = (long) (tickerJsonObject.getDouble("timestamp") * TimeUtils.MILLIS_IN_SECOND);
				break;
			}
		}
	}
}
