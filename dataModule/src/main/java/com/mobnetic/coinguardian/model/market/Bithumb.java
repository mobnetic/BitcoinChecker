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

public class Bithumb extends Market {

    private final static String NAME = "Bithumb";
    private final static String TTS_NAME = NAME;
    private final static String URL_TICKER = "https://api.bithumb.com/public/ticker/%1$s";
    private final static String URL_ORDERS = "https://api.bithumb.com/public/orderbook/%1$s?count=1";
    private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<>();

    static {
        CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
                Currency.KRW
        });
        CURRENCY_PAIRS.put(VirtualCurrency.ETH, new String[]{
                Currency.KRW
        });
        CURRENCY_PAIRS.put(VirtualCurrency.ETC, new String[]{
                Currency.KRW
        });
        CURRENCY_PAIRS.put(VirtualCurrency.DASH, new String[]{
                Currency.KRW
        });
        CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
                Currency.KRW
        });
        CURRENCY_PAIRS.put(VirtualCurrency.XRP, new String[]{
                Currency.KRW
        });
        CURRENCY_PAIRS.put(VirtualCurrency.BCH, new String[]{
                Currency.KRW
        });
        CURRENCY_PAIRS.put(VirtualCurrency.XMR, new String[]{
                Currency.KRW
        });
        CURRENCY_PAIRS.put(VirtualCurrency.ZEC, new String[]{
                Currency.KRW
        });
        CURRENCY_PAIRS.put(VirtualCurrency.QTUM, new String[]{
                Currency.KRW
        });
    }

    public Bithumb() {
        super(NAME, TTS_NAME, CURRENCY_PAIRS);
    }

    @Override
    public int getNumOfRequests(CheckerInfo checkerRecord) {
        return 2;
    }

    @Override
    public String getUrl(int requestId, CheckerInfo checkerInfo) {
        if (requestId == 0) {
            return String.format(URL_TICKER, checkerInfo.getCurrencyBaseLowerCase());
        } else {
            return String.format(URL_ORDERS, checkerInfo.getCurrencyBaseLowerCase());
        }
    }

    @Override
    protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker,
                                             CheckerInfo checkerInfo) throws Exception {
        JSONObject dataObject = jsonObject.getJSONObject("data");

        if (requestId == 0) {
            ticker.vol = dataObject.getDouble("volume_1day");
            ticker.high = dataObject.getDouble("max_price");
            ticker.low = dataObject.getDouble("min_price");
            ticker.last = dataObject.getDouble("closing_price");
            ticker.timestamp = dataObject.getLong("date");
        } else {
            ticker.bid = getFirstPriceFromOrder(dataObject, "bids");
            ticker.ask = getFirstPriceFromOrder(dataObject, "asks");
        }
    }

    @Override
    protected String parseErrorFromJsonObject(int requestId, JSONObject jsonObject,
                                              CheckerInfo checkerInfo) throws Exception {
        return jsonObject.getString("message");
    }

    private double getFirstPriceFromOrder(JSONObject jsonObject, String key) throws Exception {
        JSONArray array = jsonObject.getJSONArray(key);

        if (array.length() == 0) {
            return Ticker.NO_DATA;
        }

        JSONObject first = array.getJSONObject(0);
        return first.getDouble("price");
    }
}