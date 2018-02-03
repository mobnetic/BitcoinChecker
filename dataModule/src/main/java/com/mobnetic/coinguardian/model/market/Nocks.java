package com.mobnetic.coinguardian.model.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

public class Nocks extends Market {

	private final static String NAME = "Nocks";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://api.nocks.com/api/v2/trade-market/%1$s-%2$s";
	private final static String URL_CURRENCY_PAIRS = "https://api.nocks.com/api/v2/trade-market";

	public Nocks() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject dataObject = jsonObject.getJSONObject("data");
		final JSONObject propertyObject = dataObject.getJSONObject(dataObject.names().getString(0));

	    ticker.bid = getDoubleFromJsonObject(dataObject, "buy");
		ticker.ask = getDoubleFromJsonObject(dataObject, "sell");
		ticker.vol = getDoubleFromJsonObject(dataObject, "volume");
		ticker.high = getDoubleFromJsonObject(dataObject, "high");
		ticker.low = getDoubleFromJsonObject(dataObject, "low");
		ticker.last = getDoubleFromJsonObject(dataObject, "last");
	}

	private double getDoubleFromJsonObject(JSONObject jsonObject, String key) throws Exception {
	    return jsonObject.getJSONObject(key).getDouble("amount");
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
	    final JSONArray resultJsonArray = jsonObject.getJSONArray("data");

	    for(int i=0; i<resultJsonArray.length(); ++i) {
	        final JSONObject marketJsonObject = resultJsonArray.getJSONObject(i);
	        final String symbol = marketJsonObject.getString("code");
	        pairs.add(new CurrencyPairInfo(
	                symbol.substring(0, 3),
                    symbol.substring(4, 7),
                    symbol));
        }
    }
}
