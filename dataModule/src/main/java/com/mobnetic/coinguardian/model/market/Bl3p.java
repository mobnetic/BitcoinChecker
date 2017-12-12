package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Bl3p extends Market {

    private final static String NAME = "BL3P";
    private final static String TTS_NAME = NAME;
    private final static String URL = "https://api.bl3p.eu/1/%1$s%2$s/ticker";
    private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<>();

    static {
        CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
                Currency.EUR
        });
        CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
                Currency.EUR
        });
    }

    public Bl3p() {
        super(NAME, TTS_NAME, CURRENCY_PAIRS);
    }

    @Override
    public String getUrl(int requestId, CheckerInfo checkerInfo) {
        return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
    }

    @Override
    protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
        ticker.bid = jsonObject.getDouble("bid");
        ticker.ask = jsonObject.getDouble("ask");
        ticker.vol = jsonObject.getJSONObject("volume").getDouble("24h");
        ticker.high = jsonObject.getDouble("high");
        ticker.low = jsonObject.getDouble("low");
        ticker.last = jsonObject.getDouble("last");
        ticker.timestamp = jsonObject.getLong("timestamp");
    }
}
