package com.mobnetic.coinguardian.model.market;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

public class BitMEX extends Market {

	private final static String NAME = "BitMEX";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://www.bitmex.com/api/v1/trade?symbol=%1$s%2$s&count=1&start=0&reverse=true";
	private final static String URL_QUOTES = "https://www.bitmex.com/api/v1/quote?symbol=%1$s%2$s&count=1&start=0&reverse=true";
	private final static String URL_CURRENCY_PAIRS = "https://www.bitmex.com/api/v1/instrument/activeIntervals";

	public BitMEX() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
        if (requestId == 0)
            return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
        else
            return String.format(URL_QUOTES, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}

	@Override
	public int getNumOfRequests(CheckerInfo checkerInfo) {
		return 2;
	}

	@Override
	protected void parseTicker(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		parseTickerFromJsonObject(requestId, new JSONArray(responseString).getJSONObject(0), ticker, checkerInfo);
	}

	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
        if (requestId == 0) {
            ticker.last = jsonObject.getDouble("price");
        } else {
            ticker.bid = jsonObject.getDouble("bidPrice");
            ticker.ask = jsonObject.getDouble("askPrice");
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
	protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray pairNames = jsonObject.symbols();

		for(int i=0; i<pairNames.length(); ++i) {
			String pairId = pairNames.getString(i);
			if(pairId==null)
				continue;

            try {
			String base = pairId.substring(0, 3);
			String counter = pairId.substring(3);
            } catch (IndexOutOfBoundsException e) {
                continue;
            }

			pairs.add(new CurrencyPairInfo(base, counter, pairId));
		}
	}
}
