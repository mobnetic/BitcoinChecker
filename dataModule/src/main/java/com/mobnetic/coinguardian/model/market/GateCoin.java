package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class GateCoin extends Market {

	private final static String NAME = "GateCoin";
	private final static String TTS_NAME = "Gate Coin";
	private final static String URL = "https://api.gatecoin.com/Public/LiveTickers";

	public GateCoin() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONArray pairNames = jsonObject.getJSONArray("tickers"); //returned JSON text is an ARRAY of JSONObjects
		String userCurrencyPairChoice = checkerInfo.getCurrencyBase().concat(checkerInfo.getCurrencyCounter());
		//each JSONObject in the Array has a currency pair and its corresponding ticker details
		for (int i = 0; i < pairNames.length(); ++i) {
			JSONObject tickerDetails = pairNames.getJSONObject(i);
			String currentPairId = tickerDetails.getString("currencyPair");
			if (currentPairId.equals(userCurrencyPairChoice)) {
				ticker.bid = tickerDetails.getDouble("bid");
				ticker.ask = tickerDetails.getDouble("ask");
				ticker.vol = tickerDetails.getDouble("volume");
				ticker.high = tickerDetails.getDouble("high");
				ticker.low = tickerDetails.getDouble("low");
				ticker.last = tickerDetails.getDouble("last");
				ticker.timestamp = tickerDetails.getLong("createDateTime");
				break;
			}
		}
	}

	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL;
	}

	@Override
	protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
			final JSONArray pairNames = jsonObject.getJSONArray("tickers"); //returned JSON text is an ARRAY of JSONObjects
			for (int i = 0; i < pairNames.length(); ++i) {
				//each JSONObject in the Array has a currency pair and its corresponding ticker details
				String pairId = pairNames.getJSONObject(i).getString("currencyPair");
				if (pairId == null)
					continue;
				String[] currencies = new String[2];
				//split by index - use char positions (start, end+1) as index
				currencies[0] = pairId.substring(0, 3); //base currency
				currencies[1] = pairId.substring(3, 6); //counter currency
				if (currencies.length != 2)
					continue;
				pairs.add(new CurrencyPairInfo(currencies[0], currencies[1], pairId));
			}
	}
}
