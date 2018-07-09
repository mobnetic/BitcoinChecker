package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class BitcoinTrade extends Market {

	private final static String NAME = "BitcoinTrade";
	private final static String TTS_NAME = "Bitcoin Trade";
	private final static String URL = "https://api.bitcointrade.com.br/v1/public/%1$s/ticker/";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.BRL
			});
		CURRENCY_PAIRS.put(VirtualCurrency.ETH, new String[]{
				Currency.BRL
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				Currency.BRL
			});
	}

	public BitcoinTrade() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase());
	}

	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject dataJsonObject = jsonObject.getJSONObject("data");

		ticker.bid = dataJsonObject.getDouble("buy");
		ticker.ask = dataJsonObject.getDouble("sell");
		ticker.vol = dataJsonObject.getDouble("volume");
		ticker.high = dataJsonObject.getDouble("high");
		ticker.low = dataJsonObject.getDouble("low");
		ticker.last = dataJsonObject.getDouble("last");
	}
}
