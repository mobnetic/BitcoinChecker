package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;


public class Kucoin extends Market {

    private final static String NAME = "Kucoin";
    private final static String TTS_NAME = NAME;
    private final static String URL = "https://api.kucoin.com/v1/open/tick?symbol=%1$s";

    private final static String URL_COINS_PAIRS = "https://api.kucoin.com/v1/market/open/symbols";

    public Kucoin() {
        super(NAME, TTS_NAME, null);
    }

    @Override
    public String getUrl(int requestId, CheckerInfo checkerInfo) {
        return String.format(URL, checkerInfo.getCurrencyPairId());
    }

    @Override
    protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
        JSONObject tickerJsonObject = jsonObject.getJSONObject("data");
        ticker.bid = tickerJsonObject.getDouble("buy");
        ticker.ask = tickerJsonObject.getDouble("sell");
        ticker.vol = tickerJsonObject.getDouble("vol");
        ticker.high = tickerJsonObject.getDouble("high");
        ticker.low = tickerJsonObject.getDouble("low");
        ticker.last = tickerJsonObject.getDouble("lastDealPrice");
        ticker.timestamp = tickerJsonObject.getLong("datetime");
    }

    // ====================
    // Get currency pairs
    // ====================
    @Override
    public String getCurrencyPairsUrl(int requestId) {
        return URL_COINS_PAIRS;
    }

    @Override
    protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
        final JSONArray data = jsonObject.getJSONArray("data");

        for(int i=0; i< data.length(); ++i) {
            final JSONObject pairJsonObject = data.getJSONObject(i);
            pairs.add(new CurrencyPairInfo(
                pairJsonObject.getString("coinType"),
                pairJsonObject.getString("coinTypePair"),
                pairJsonObject.getString("symbol")
            ));
        }
    }

}
