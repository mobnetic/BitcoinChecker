package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap; 

public class SatoshiTango extends Market {

    private final static String NAME = "SatoshiTango";
    private final static String TTS_NAME = "Satoshi Tango";
    private final static String URL = "https://api.satoshitango.com/v2/ticker";

    private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();

    static {
        CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
                Currency.USD,
                Currency.ARS,
                Currency.EUR
        });
    }

    public SatoshiTango() {
        super(NAME, TTS_NAME, CURRENCY_PAIRS);
    }

    @Override
    public String getUrl(int requestId, CheckerInfo checkerInfo) {
        return URL;
    }

    @Override
    protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
        String currencyCode = checkerInfo.getCurrencyCounterLowerCase() + checkerInfo.getCurrencyBaseLowerCase();

        final JSONObject tickerJsonObject = jsonObject.getJSONObject("data");
        final JSONObject buyObject = tickerJsonObject.getJSONObject("compra");
        final JSONObject sellObject = tickerJsonObject.getJSONObject("venta");

        ticker.ask = buyObject.getDouble(currencyCode);
        ticker.bid = sellObject.getDouble(currencyCode);
        ticker.last = ticker.ask;
    }
}
