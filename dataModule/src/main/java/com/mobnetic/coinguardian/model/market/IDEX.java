package com.mobnetic.coinguardian.model.market;

import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.util.ParseUtils;

public class IDEX extends Market {

    private final static String NAME = "IDEX";
    private final static String TTS_NAME = NAME;
    private final static String URL = "https://api.idex.market/returnTicker?market=%1$s";
    private final static String URL_CURRENCY_PAIRS = "https://api.idex.market/returnTicker";

    public IDEX() {
        super(NAME, TTS_NAME, null);
    }

    @Override
    public String getUrl(int requestId, CheckerInfo checkerInfo) {
        return String.format(URL, checkerInfo.getCurrencyPairId());
    }

    @Override
    protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
        ticker.bid = ParseUtils.getDoubleFromString(jsonObject, "highestBid");
        ticker.ask = ParseUtils.getDoubleFromString(jsonObject, "lowestAsk");
        ticker.vol = ParseUtils.getDoubleFromString(jsonObject, "quoteVolume");
        ticker.high = ParseUtils.getDoubleFromString(jsonObject, "high");
        ticker.low = ParseUtils.getDoubleFromString(jsonObject, "low");
        ticker.last = ParseUtils.getDoubleFromString(jsonObject, "last");
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
        Iterator<String> keys = jsonObject.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            String[] parts = key.split("_");
            pairs.add(new CurrencyPairInfo(parts[1], parts[0], key));
        }
    }
}
