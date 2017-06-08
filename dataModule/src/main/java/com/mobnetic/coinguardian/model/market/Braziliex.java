package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Braziliex extends Market {

    private final static String NAME = "Braziliex";
    private final static String TTS_NAME = "Braziliex";
    private final static String URL = "https://braziliex.com/api/v1/public/ticker/";
    private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();

    static {
        CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
                Currency.BRL
        });
        CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
                Currency.BRL
        });
        CURRENCY_PAIRS.put(VirtualCurrency.ETH, new String[]{
                Currency.BRL
        });
        CURRENCY_PAIRS.put(VirtualCurrency.XMR, new String[]{
                Currency.BRL
        });
        CURRENCY_PAIRS.put(VirtualCurrency.DASH, new String[]{
                Currency.BRL
        });
    }

    public Braziliex() {
        super(NAME, TTS_NAME, CURRENCY_PAIRS);
    }

    @Override
    public String getUrl(int requestId, CheckerInfo checkerInfo) {
        return URL.concat(checkerInfo.getCurrencyBaseLowerCase())
                .concat("_")
                .concat(Currency.BRL.toLowerCase());
    }

    @Override
    protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
        ticker.vol = jsonObject.getDouble("baseVolume");
        ticker.high = jsonObject.getDouble("highestBid");
        ticker.low = jsonObject.getDouble("lowestAsk");
        ticker.last = jsonObject.getDouble("last");
    }
}
