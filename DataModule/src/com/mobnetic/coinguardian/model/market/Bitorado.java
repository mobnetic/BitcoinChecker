package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;
import org.json.JSONObject;
import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class Bitorado extends Market
{
    private final static String NAME = "Bitorado";
    private final static String TTS_NAME = NAME;
    private final static String URL = "https://www.bitorado.com/api/market/%1$s-%2$s/ticker";
    private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
    static {
        CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
                Currency.PLN
            });
        CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
                Currency.PLN,
                VirtualCurrency.BTC
            });
        CURRENCY_PAIRS.put(VirtualCurrency.DOGE, new String[]{
                Currency.PLN,
                VirtualCurrency.BTC
            });
        CURRENCY_PAIRS.put(VirtualCurrency.FTC, new String[]{
                VirtualCurrency.BTC
            });
        CURRENCY_PAIRS.put(VirtualCurrency.NMC, new String[]{
                VirtualCurrency.BTC
            });
    }

    public Bitorado() {
        super(NAME, TTS_NAME, CURRENCY_PAIRS);
    }

    @Override
    public String getUrl(int requestId, CheckerInfo checkerInfo)
    {
        return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
    }

    @Override
    protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
        final JSONObject resultObject = jsonObject.getJSONObject("result");
        ticker.bid = resultObject.optDouble("buy", Ticker.NO_DATA);
        ticker.ask = resultObject.optDouble("sell", Ticker.NO_DATA);
        ticker.vol = resultObject.optDouble("vol", Ticker.NO_DATA);
        ticker.high = resultObject.optDouble("high", Ticker.NO_DATA);
        ticker.low = resultObject.optDouble("low", Ticker.NO_DATA);
        ticker.last = resultObject.optDouble("last", 0.0);
    }
}

