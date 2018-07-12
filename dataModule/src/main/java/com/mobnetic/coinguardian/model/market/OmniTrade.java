package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class OmniTrade extends Market {

	private final static String NAME = "OmniTrade";
	private final static String TTS_NAME = "Omni Trade";
	private final static String URL = "https://omnitrade.io/api/v2/tickers/%1$s%2$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.BRL
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				Currency.BRL
			});
		CURRENCY_PAIRS.put(VirtualCurrency.BCH, new String[]{
				Currency.BRL
			});
		CURRENCY_PAIRS.put(VirtualCurrency.BTG, new String[]{
				Currency.BRL
			});
		CURRENCY_PAIRS.put(VirtualCurrency.ETH, new String[]{
				Currency.BRL
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DASH, new String[]{
				Currency.BRL
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DCR, new String[]{
				Currency.BRL
			});
	}

	public OmniTrade() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
	}

	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject dataJsonObject = jsonObject.getJSONObject("ticker");

		ticker.bid = dataJsonObject.getDouble("buy");
		ticker.ask = dataJsonObject.getDouble("sell");
		ticker.low = dataJsonObject.getDouble("low");
		ticker.high = dataJsonObject.getDouble("high");
		ticker.last = dataJsonObject.getDouble("last");
		ticker.vol = dataJsonObject.getDouble("vol");
	}
}
