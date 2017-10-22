package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Braziliex extends Market {

    private final static String NAME = "Braziliex";
    private final static String TTS_NAME = "Braziliex";
    private final static String URL = "https://braziliex.com/api/v1/public/ticker/%1$s_%2$s";
    private final static String URL_CURRENCY_PAIRS = "https://braziliex.com/api/v1/public/currencies";

    public Braziliex() {
        super(NAME, TTS_NAME, null);
    }

    @Override
    public String getCurrencyPairsUrl(int requestId) {
        return URL_CURRENCY_PAIRS;
    }

    @Override
    public String getUrl(int requestId, CheckerInfo checkerInfo) {
        return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
    }

    @Override
    protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
        final JSONArray pairsJsonArray = jsonObject.names();
        for (int i = 0; i < pairsJsonArray.length(); ++i) {
            final String pairName = pairsJsonArray.getString(i);
            pairs.add(new CurrencyPairInfo(pairName.toUpperCase(), Currency.BRL, pairName.toUpperCase()));
        }
    }

    @Override
    protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
        ticker.ask = jsonObject.getDouble("lowestAsk24");
        ticker.bid = jsonObject.getDouble("highestBid24");
        ticker.vol = jsonObject.getDouble("baseVolume");
        ticker.high = jsonObject.getDouble("highestBid");
        ticker.low = jsonObject.getDouble("lowestAsk");
        ticker.last = jsonObject.getDouble("last");
    }
}
