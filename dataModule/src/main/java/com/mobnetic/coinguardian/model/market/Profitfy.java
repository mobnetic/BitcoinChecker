/**
 * Author: Hanelore <BM-2cW3xmeXeQTzvfcw3Dgtp7UqeaFE7m7eRz@bitmessage.ch>
 * Date: Wed Out 24 20:56:00 2018
 * Desc: Rhama Congratulations \o/
 */

package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class Profitfy extends Market {

	private final static String NAME = "Profitfy";
	private final static String TTS_NAME = "Profitfy";
	private final static String URL = "https://profitfy.trade/api/v1/public/ticker/%1$s/%2$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.BRL
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				Currency.BRL
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DCR, new String[]{
				Currency.BRL
			});
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.LTC
			});	
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.DCR
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				Currency.DCR
			});						
	}

	public Profitfy() {
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
		ticker.high = dataJsonObject.getDouble("max");
		ticker.low = dataJsonObject.getDouble("min");
		ticker.last = dataJsonObject.getDouble("last");
	}
}
