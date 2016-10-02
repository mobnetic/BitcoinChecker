package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class CoinTraderNet extends Market {

	private final static String NAME = "CoinTrader.net";
	private final static String TTS_NAME = "Coin Trader";
	private final static String URL = "https://www.cointrader.net/api4/stats/daily/%1$s%2$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD,
				Currency.CAD
			});
	}
	
	public CoinTraderNet() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject dataJsonObject = jsonObject.getJSONObject("data");
		final JSONArray dataNamesArray = dataJsonObject.names();
		final JSONObject tickerJsonObject = dataJsonObject.getJSONObject(dataNamesArray.getString(0));
		ticker.bid = tickerJsonObject.getDouble("bid");
		ticker.ask = tickerJsonObject.getDouble("offer");
		ticker.vol = tickerJsonObject.getDouble("volume");
		ticker.high = tickerJsonObject.getDouble("high");
		ticker.low = tickerJsonObject.getDouble("low");
		ticker.last = tickerJsonObject.getDouble("lastTradePrice");
	}
}
