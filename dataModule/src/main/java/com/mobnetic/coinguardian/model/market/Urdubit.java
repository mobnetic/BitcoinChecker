package com.mobnetic.coinguardian.model.market;

        import java.util.HashMap;
        import java.util.LinkedHashMap;
        import org.json.JSONObject;

        import com.mobnetic.coinguardian.model.CheckerInfo;
        import com.mobnetic.coinguardian.model.Market;
        import com.mobnetic.coinguardian.model.Ticker;
        import com.mobnetic.coinguardian.model.currency.Currency;
        import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

/**
 * Created by naveed on 17/01/2017.
 */

public class Urdubit extends Market {

    private final static String NAME = "Urdubit";
    private final static String TTS_NAME = NAME;
    private final static String URL = "https://api.blinktrade.com/api/v1/%2$s/ticker?crypto_currency=%1$s";
    private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
    static {
        CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
                Currency.PKR
        });

    }

    public Urdubit() {
        super(NAME, TTS_NAME, CURRENCY_PAIRS);
    }

    @Override
    public String getUrl(int requestId, CheckerInfo checkerInfo) {
        return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
    }

    @Override
    protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
        ticker.bid = jsonObject.getDouble("buy");
        ticker.ask = jsonObject.getDouble("sell");
        ticker.vol = jsonObject.getDouble("vol");
        ticker.high = jsonObject.getDouble("high");
        ticker.low = jsonObject.getDouble("low");
        ticker.last = jsonObject.getDouble("last");
        //ticker.timestamp = jsonObject.getLong("timestamp");
    }

}
