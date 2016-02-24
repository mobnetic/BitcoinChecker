package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Coinbase extends Market {

	private final static String NAME = "Coinbase";
	private final static String TTS_NAME = NAME;
	private final static String URL_TICKER_BUY = "https://api.coinbase.com/v2/prices/buy?currency=%1$s";
	private final static String URL_TICKER_SELL = "https://api.coinbase.com/v2/prices/sell?currency=%1$s";
	private final static String URL_CURRENCY_PAIRS = "https://api.coinbase.com/v2/currencies";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD
			});
	}
	
	public Coinbase() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public int getNumOfRequests(CheckerInfo checkerInfo) {
		return 2;
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		if (requestId == 0) {
			return String.format(URL_TICKER_BUY, checkerInfo.getCurrencyCounter());
		} else {
			return String.format(URL_TICKER_SELL, checkerInfo.getCurrencyCounter());
		}
	}

	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		JSONObject data = jsonObject.getJSONObject("data");
		if (requestId == 0) {
			ticker.ask = data.getDouble("amount");
			ticker.last = data.getDouble("amount");
		} else {
			ticker.bid = data.getDouble("amount");
		}
	}

	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL_CURRENCY_PAIRS;
	}

	@Override
	protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray data = jsonObject.getJSONArray("data");
		for (int i = 0; i < data.length(); i++) {
			final JSONObject currencyJsonObject = data.getJSONObject(i);
			final String currency = currencyJsonObject.getString("id");
			pairs.add(new CurrencyPairInfo(VirtualCurrency.BTC, currency, currency));
		}
	}

}
