package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Implements SurBTC connection to the API
 * @author Eduardo Laguna
 */
public class SurBTC extends Market {

    private final static String NAME = "SurBTC";
    private final static String TTS_NAME = NAME;
    private final static String URL = "https://www.surbtc.com/api/v2/markets/%1$s-%2$s/ticker";
    private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
    static {
        CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
                Currency.CLP,
                Currency.COP
        });
    }

    public SurBTC() {
        super(NAME, TTS_NAME, CURRENCY_PAIRS);
    }

    @Override
    public String getUrl(int requestId, CheckerInfo checkerInfo) {
        return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
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
