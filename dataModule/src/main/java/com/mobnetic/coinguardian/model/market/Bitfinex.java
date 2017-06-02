package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;
import com.mobnetic.coinguardian.util.ParseUtils;
import com.mobnetic.coinguardian.util.TimeUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;

public class Bitfinex extends Market {

	private final static String NAME = "Bitfinex";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://api.bitfinex.com/v1/pubticker/%1$s%2$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				Currency.USD,
				VirtualCurrency.BTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DASH, new String[]{
				Currency.USD,
				VirtualCurrency.BTC
		});
		CURRENCY_PAIRS.put(VirtualCurrency.XMR, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.ETC, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
		CURRENCY_PAIRS.put(VirtualCurrency.ETH, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.RRT, new String[]{
				VirtualCurrency.BTC,
				Currency.USD
		});
	}
	
	public Bitfinex() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL,
				fixCurrency(checkerInfo.getCurrencyBase()).toLowerCase(Locale.US),
				fixCurrency(checkerInfo.getCurrencyCounter()).toLowerCase(Locale.US));
	}

	private String fixCurrency(String currency) {
		if(VirtualCurrency.DASH.equals(currency)) {
			return VirtualCurrency.DSH;
		}
		return currency;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = ParseUtils.getDoubleFromString(jsonObject, "bid");
		ticker.ask = ParseUtils.getDoubleFromString(jsonObject, "ask");
		ticker.vol = ParseUtils.getDoubleFromString(jsonObject, "volume");
		ticker.high = ParseUtils.getDoubleFromString(jsonObject, "high");
		ticker.low = ParseUtils.getDoubleFromString(jsonObject, "low");
		ticker.last = ParseUtils.getDoubleFromString(jsonObject, "last_price");
		ticker.timestamp = (long) (jsonObject.getDouble("timestamp")*TimeUtils.MILLIS_IN_SECOND);
	}
}
