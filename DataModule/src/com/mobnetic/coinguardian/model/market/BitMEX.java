package com.mobnetic.coinguardian.model.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

public class BitMEX extends Market {

	private final static String NAME = "BitMEX";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://www.bitmex.com/api/v1/instrument?symbol=%1$s%2$s&columns=bidPrice,askPrice,volume24h,highPrice,lowPrice,lastPrice";
	private final static String URL_CURRENCY_PAIRS = "https://www.bitmex.com/api/v1/instrument/activeIntervals";

	public BitMEX() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
        return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}

	@Override
	protected void parseTicker(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		parseTickerFromJsonObject(requestId, new JSONArray(responseString).getJSONObject(0), ticker, checkerInfo);
	}

	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
        ticker.bid = jsonObject.getDouble("bidPrice");
        ticker.ask = jsonObject.getDouble("askPrice");
        ticker.last = jsonObject.getDouble("lastPrice");
        if (!jsonObject.isNull("highPrice"))
            ticker.high = jsonObject.getDouble("highPrice");
        if (!jsonObject.isNull("lowPrice"))
            ticker.low = jsonObject.getDouble("lowPrice");
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
		final JSONArray pairNames = jsonObject.getJSONArray("symbols");

		for(int i=0; i<pairNames.length(); ++i) {
			String pairId = pairNames.getString(i);
			if(pairId==null)
				continue;

            String base = null, counter = null;
            try {
                base = pairId.substring(0, 3);
                counter = pairId.substring(3);
            } catch (IndexOutOfBoundsException e) {
                continue;
            }

			pairs.add(new CurrencyPairInfo(base, counter, pairId));
		}
	}
}
