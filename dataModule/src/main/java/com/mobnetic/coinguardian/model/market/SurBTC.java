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

/**
 * Implements SurBTC connection to the API
 * @author Eduardo Laguna
 */
public class SurBTC extends Market {

    private final static String NAME = "SurBTC";
    private final static String TTS_NAME = "sur BTC";
    private final static String URL = "https://www.surbtc.com/api/v2/markets/%1$s-%2$s/ticker";
    private final static String URL_CURRENCY_PAIRS = "https://www.surbtc.com/api/v2/markets.json";
    private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();

    public SurBTC() {
        super(NAME, TTS_NAME, null);
    }

    @Override
    public String getUrl(int requestId, CheckerInfo checkerInfo) {
        return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
    }

    @Override
    public String getCurrencyPairsUrl(int requestId) {
        return URL_CURRENCY_PAIRS;
    }

    @Override
    protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
        JSONArray markets = jsonObject.getJSONArray("markets");
        for (int i = 0; i < markets.length(); i++) {
            JSONObject market = markets.getJSONObject(i);
            pairs.add(new CurrencyPairInfo(market.getString("base_currency"), market.getString("quote_currency"), market.getString("id")));
        }
        super.parseCurrencyPairsFromJsonObject(requestId, jsonObject, pairs);
    }

    @Override
    protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
        JSONObject jsonTicker = jsonObject.getJSONObject("ticker");
        ticker.bid = jsonTicker.getJSONArray("max_bid").getDouble(0);
        ticker.ask = jsonTicker.getJSONArray("min_ask").getDouble(0);
        ticker.vol = jsonTicker.getJSONArray("volume").getDouble(0);
        ticker.last = jsonTicker.getJSONArray("last_price").getDouble(0);
    }
}
