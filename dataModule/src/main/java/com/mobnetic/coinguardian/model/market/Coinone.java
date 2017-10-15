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

public class Coinone extends Market {

	private final static String NAME = "Coinone";
	private final static String TTS_NAME = NAME;
	private final static String URL_TICKER = "https://api.coinone.co.kr/ticker?currency=%1$s";
	private final static String URL_ORDERS = "https://api.coinone.co.kr/orderbook?currency=%1$s";
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
		CURRENCY_PAIRS.put(VirtualCurrency.XRP, new String[]{
				Currency.KRW
		});
		CURRENCY_PAIRS.put(VirtualCurrency.BCH, new String[]{
				Currency.KRW
		});
		CURRENCY_PAIRS.put(VirtualCurrency.QTUM, new String[]{
				Currency.KRW
		});
	}

	public Coinone() {
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
        if(requestId == 0) {
            ticker.vol = jsonObject.getDouble("volume");
            ticker.high = jsonObject.getDouble("high");
            ticker.low = jsonObject.getDouble("low");
            ticker.last = jsonObject.getDouble("last");
            ticker.timestamp = jsonObject.getLong("timestamp");
        } else {
            ticker.bid = getFirstPriceFromOrder(jsonObject, "bid");
            ticker.ask = getFirstPriceFromOrder(jsonObject, "ask");
        }
	}

    private double getFirstPriceFromOrder(JSONObject jsonObject, String key) throws Exception {
        JSONArray array = jsonObject.getJSONArray(key);

        if(array.length() == 0) {
            return Ticker.NO_DATA;
        }

        JSONObject first = array.getJSONObject(0);
        return first.getDouble("price");
    }
}