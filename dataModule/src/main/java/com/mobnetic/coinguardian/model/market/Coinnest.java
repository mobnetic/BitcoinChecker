package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Coinnest extends Market {

    private final static String NAME = "Coinnest";
    private final static String TTS_NAME = NAME;
    private final static String URL_BASE = "https://api.coinnest.co.kr";
    private final static String URL_TICKER = URL_BASE + "/api/pub/ticker?coin=%1$s";
    private final static String URL_ORDERS = URL_BASE + "/api/pub/depth?coin=%1$s";
    private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<>();

    static {
        CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
                Currency.KRW
        });
        CURRENCY_PAIRS.put(VirtualCurrency.BCC, new String[]{
                Currency.KRW
        });
        CURRENCY_PAIRS.put(VirtualCurrency.ETH, new String[]{
                Currency.KRW
        });
        CURRENCY_PAIRS.put(VirtualCurrency.ETC, new String[]{
                Currency.KRW
        });
        CURRENCY_PAIRS.put(VirtualCurrency.QTUM, new String[]{
                Currency.KRW
        });
        CURRENCY_PAIRS.put(VirtualCurrency.NEO, new String[]{
                Currency.KRW
        });
        CURRENCY_PAIRS.put(VirtualCurrency.KNC, new String[]{
                Currency.KRW
        });
        CURRENCY_PAIRS.put(VirtualCurrency.TSL, new String[]{
                Currency.KRW
        });
        CURRENCY_PAIRS.put(VirtualCurrency.OMG, new String[]{
                Currency.KRW
        });
    }

    public Coinnest() {
        super(NAME, TTS_NAME, CURRENCY_PAIRS);
    }

    @Override
    public int getNumOfRequests(CheckerInfo checkerRecord) {
        return 1;
    }

    @Override
    public String getUrl(int requestId, CheckerInfo checkerInfo) {
        return String.format(URL_TICKER, checkerInfo.getCurrencyBaseLowerCase());
    }

    @Override
    protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker,
                                             CheckerInfo checkerInfo) throws Exception {
        ticker.high = jsonObject.getDouble("high");
        ticker.low = jsonObject.getDouble("low");
        ticker.bid = jsonObject.getDouble("buy");
        ticker.ask = jsonObject.getDouble("sell");
        ticker.last = jsonObject.getDouble("last");
        ticker.vol = jsonObject.getDouble("vol");
        ticker.timestamp = jsonObject.getLong("time");
    }

    @Override
    protected String parseErrorFromJsonObject(int requestId, JSONObject jsonObject,
                                              CheckerInfo checkerInfo) throws Exception {
        return jsonObject.getString("msg");
    }
}