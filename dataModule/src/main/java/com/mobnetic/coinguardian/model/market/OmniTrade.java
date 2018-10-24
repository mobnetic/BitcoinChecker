package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class OmniTrade extends Market {

	private final static String NAME = "OmniTrade";
	private final static String TTS_NAME = "Omni Trade";
	private final static String URL = "https://omnitrade.io/api/v2/tickers/%1$s";
	private final static String URL_CURRENCY_PAIRS = "https://omnitrade.io/api/v2/markets";

	public OmniTrade() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyPairId());
	}

	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject dataJsonObject = jsonObject.getJSONObject("ticker");

		ticker.bid = dataJsonObject.getDouble("buy");
		ticker.ask = dataJsonObject.getDouble("sell");
		ticker.low = dataJsonObject.getDouble("low");
		ticker.high = dataJsonObject.getDouble("high");
		ticker.last = dataJsonObject.getDouble("last");
		ticker.vol = dataJsonObject.getDouble("vol");
	}

    // ====================
    // Get currency pairs
    // ====================
    @Override
    public String getCurrencyPairsUrl(int requestId) {
        return URL_CURRENCY_PAIRS;
    }

    @Override
    protected void parseCurrencyPairs(int requestId, String responseString, List<CurrencyPairInfo> pairs) throws Exception {
        final JSONArray pairsArray = new JSONArray(responseString);
        for(int i=0; i<pairsArray.length(); ++i) {
            JSONObject pair = pairsArray.getJSONObject(i);
            String[] currencies = pair.getString("name").split("/");
            if(currencies.length!=2)
                continue;

            pairs.add(new CurrencyPairInfo(currencies[0], currencies[1], pair.getString("id")));
        }
    }
}
