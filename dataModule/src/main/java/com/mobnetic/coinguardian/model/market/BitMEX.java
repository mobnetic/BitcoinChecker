package com.mobnetic.coinguardian.model.market;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

public class BitMEX extends Market {

    private final static String NAME = "BitMEX";
    private final static String TTS_NAME = NAME;
    private final static String URL = "https://www.bitmex.com/api/v1/instrument" +
            "?symbol=%1$s" +
            "&columns=bidPrice,askPrice,turnover24h,highPrice,lowPrice,lastPrice";
    private final static String URL_CURRENCY_PAIRS = "https://www.bitmex.com/api/v1/instrument" +
            "?columns=rootSymbol,typ" +
            "&filter={\"state\":\"Open\"}";
    private final static SimpleDateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
    static {
        ISO_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public BitMEX() {
        super(NAME, TTS_NAME, null);
    }

    @Override
    public String getUrl(int requestId, CheckerInfo checkerInfo) {
        return String.format(URL, checkerInfo.getCurrencyPairId());
    }

    @Override
    protected void parseTicker(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
        this.parseTickerFromJsonObject(requestId, new JSONArray(responseString).getJSONObject(0), ticker, checkerInfo);
    }

    @Override
    protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
        ticker.bid = jsonObject.getDouble("bidPrice");
        ticker.ask = jsonObject.getDouble("askPrice");
        // This comes back in Satoshis
        ticker.vol = jsonObject.getDouble("turnover24h") / 1e8;
        if (!jsonObject.isNull("highPrice"))
            ticker.high = jsonObject.getDouble("highPrice");
        if (!jsonObject.isNull("lowPrice"))
            ticker.low = jsonObject.getDouble("lowPrice");
        ticker.last = jsonObject.getDouble("lastPrice");
        // This is an ISO timestamp representing UTC time
        ticker.timestamp = ISO_DATE_FORMAT.parse(jsonObject.getString("timestamp")).getTime();
    }

    // ====================
    // Get currency pairs
    // ====================
    @Override
    public String getCurrencyPairsUrl(int requestId) {
        return URL_CURRENCY_PAIRS;
    }

    @Override
    protected void parseCurrencyPairs(int requestId, String responseString, List<CurrencyPairInfo> pairs) throws Exception {
        JSONArray instruments = new JSONArray(responseString);
        for (int i = 0; i < instruments.length(); i++) {
          this.parseCurrencyPairsFromJsonObject(requestId, instruments.getJSONObject(i), pairs);
        }
    }

    @Override
    protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
        String base = jsonObject.getString("rootSymbol");
        String id = jsonObject.getString("symbol");
        String quote = id.substring(id.indexOf(base) + base.length());

        // Binary
        if (jsonObject.getString("typ").equals("FFICSX")) {
            quote = base;
            base = "BINARY";
        }

        pairs.add(new CurrencyPairInfo(base, quote, id));
    }
}
