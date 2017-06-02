package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class BitFlyerFX extends Market {

	private final static String NAME = "bitFlyer FX";
	private final static String TTS_NAME = "bit flyer FX";
	private final static String URL = "https://api.bitflyer.jp/v1/ticker?product_code=FX_%1$s_%2$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.JPY,
			});
	}

	public BitFlyerFX() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("best_bid");
		ticker.ask = jsonObject.getDouble("best_ask");
		ticker.vol = jsonObject.getDouble("volume_by_product");
		ticker.last = jsonObject.getDouble("ltp");
	}
}
