package com.mobnetic.coinguardian.model.market;


import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;


public class Bitpanda extends Market {

    private final static String NAME = "Bitpanda";
    private final static String TTS_NAME = NAME;
    private final static String URL = "https://api.bitpanda.com/v1/ticker";



    public Bitpanda() {
        super(NAME, TTS_NAME, null);
    }

    @Override
    public String getUrl(int requestId, CheckerInfo checkerInfo) {
        return String.format(URL, checkerInfo.getCurrencyPairId());
    }

    @Override
    protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {

        ticker.last = jsonObject.getJSONObject(checkerInfo.getCurrencyBase()).getDouble(checkerInfo.getCurrencyCounter());

    }

    // ====================
    // Get currency pairs
    // ====================
    @Override
    public String getCurrencyPairsUrl(int requestId) {
        return URL;
    }

    @Override
    protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {

        final Iterator<String> data = jsonObject.keys();

        while(data.hasNext()) {
            final String baseCurrency = data.next();
            final Iterator<String> qouteCurrency = jsonObject.getJSONObject(baseCurrency).keys();

            while(qouteCurrency.hasNext()) {
                final String qoute = qouteCurrency.next();
                pairs.add(new CurrencyPairInfo(
                        baseCurrency,
                        qoute,
                        baseCurrency+":"+qoute
                ));
            }
        }
    }

}
