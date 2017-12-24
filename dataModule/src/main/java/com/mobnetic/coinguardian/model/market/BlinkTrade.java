/**
 * Author: Jorge Pereira <jpereiran@gmail.com>
 * Date: Fri Dec  8 15:07:27 -02 2017
 * Desc: Blinktrade market
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

public class BlinkTrade extends Market {
	
	private final static String NAME = "BlinkTrade";
	private final static String TTS_NAME = "Blink Trade";
	private final static String URL = "https://bitcambio_api.blinktrade.com/api/v1/%2$s/ticker?crypto_currency=%1$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.BRL
			});
	}
	
	public BlinkTrade() {
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
	}
}
