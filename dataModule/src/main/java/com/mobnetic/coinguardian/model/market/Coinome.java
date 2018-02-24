package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Coinome extends Market {

	private final static String NAME = "Coinome";
	private final static String TTS_NAME = "Coin ome";
	private final static String URL = "https://www.coinome.com/api/v1/ticker.json";
	private final static String URL_CURRENCY_PAIRS = URL;

	public Coinome() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject tickerJsonObject = jsonObject.getJSONObject(checkerInfo.getCurrencyBaseLowerCase()+"-"+checkerInfo.getCurrencyCounterLowerCase());
		ticker.bid = tickerJsonObject.getDouble("highest_bid");
		ticker.ask = tickerJsonObject.getDouble("lowest_ask");
		//ticker.vol = tickerJsonObject.getDouble("24hr_volume"); Currently null
		ticker.last = tickerJsonObject.getDouble("last");
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
		final JSONArray currencyPairArray = jsonObject.names();
		for(int i=0; i<currencyPairArray.length(); ++i) {
			String[] currencyPairStringArray = currencyPairArray.getString(i).split("-");
			if(currencyPairStringArray.length >= 2){
				pairs.add(new CurrencyPairInfo(currencyPairStringArray[0], currencyPairStringArray[1], null));
			}
		}
	}
}

