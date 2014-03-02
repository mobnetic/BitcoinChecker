package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class VaultOfSatoshi extends Market {

	public final static String NAME = "VaultOfSatoshi";
	public final static String TTS_NAME = "Vault Of Satoshi";
	public final static String URL = "https://api.vaultofsatoshi.com/public/ticker?order_currency=%1$s&payment_currency=%2$s";
	public final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.CAD,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				Currency.CAD,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.PPC, new String[]{
				Currency.CAD,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DOGE, new String[]{
				Currency.CAD,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.FTC, new String[]{
				Currency.CAD,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.XPM, new String[]{
				Currency.CAD,
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.QRK, new String[]{
				Currency.CAD,
				Currency.USD
			});
	}
	
	public VaultOfSatoshi() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
	}
	
	@Override
	protected void parseTickerInnerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject dataObject = jsonObject.getJSONObject("data");
		
		ticker.vol = getDoubleFromMtgoxFormatObject(dataObject, "volume_1day");
		ticker.high = getDoubleFromMtgoxFormatObject(dataObject, "max_price");
		ticker.low = getDoubleFromMtgoxFormatObject(dataObject, "min_price");
		ticker.last = getDoubleFromMtgoxFormatObject(dataObject, "closing_price");
		ticker.timestamp = dataObject.getLong("date");
	}
	
	private double getDoubleFromMtgoxFormatObject(JSONObject jsonObject, String key) throws Exception {
		final JSONObject innerObject = jsonObject.getJSONObject(key);
		return innerObject.getDouble("value");
	}
}
