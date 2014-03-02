package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class BitcoinAverage extends Market {

	public final static String NAME = "BitcoinAverage";
	public final static String TTS_NAME = "Bitcoin Average";
	public final static String URL = "https://api.bitcoinaverage.com/ticker/%1$s";
	public final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.AUD,
				Currency.BRL,
				Currency.CAD,
				Currency.CHF,
				Currency.CNY,
				Currency.CZK,
				Currency.EUR,
				Currency.GBP,
				Currency.ILS,
				Currency.JPY,
				Currency.NOK,
				Currency.NZD,
				Currency.PLN,
				Currency.RUB,
				Currency.SEK,
				Currency.SGD,
				Currency.USD,
				Currency.ZAR,
			});
	}
	
	public BitcoinAverage() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerInnerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("bid");
		ticker.ask = jsonObject.getDouble("ask");
		ticker.vol = jsonObject.getDouble("total_vol");
		ticker.last = jsonObject.getDouble("last");
	}
}
