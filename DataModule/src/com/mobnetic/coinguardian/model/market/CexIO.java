package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class CexIO extends Market {

	private final static String NAME = "CEX.IO";
	private final static String TTS_NAME = "CEX IO";
	private final static String URL = "https://cex.io/api/ticker/%1$s/%2$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.USD, new String[]{
				VirtualCurrency.BTC,
				VirtualCurrency.GHS,
				VirtualCurrency.LTC,
				VirtualCurrency.DOGE,
				VirtualCurrency.DRK
			});
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				VirtualCurrency.GHS,
				VirtualCurrency.LTC,
				VirtualCurrency.DOGE,
				VirtualCurrency.DRK,
				VirtualCurrency.NMC,
				VirtualCurrency.IXC,
				VirtualCurrency.POT,
				VirtualCurrency.ANC,
				VirtualCurrency.MEC,
				VirtualCurrency.WDC,
				VirtualCurrency.FTC,
				VirtualCurrency.DGB,
				VirtualCurrency.USDE,
				VirtualCurrency.MYR,
				VirtualCurrency.AUR
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				VirtualCurrency.GHS,
				VirtualCurrency.DOGE,
				VirtualCurrency.DRK,
				VirtualCurrency.MEC,
				VirtualCurrency.WDC,
				VirtualCurrency.ANC,
				VirtualCurrency.FTC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.EUR, new String[]{
				VirtualCurrency.BTC,
				VirtualCurrency.LTC,
				VirtualCurrency.DOGE
			});
	}
	
	public CexIO() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		
		if(jsonObject.has("bid"))
			ticker.bid = jsonObject.getDouble("bid");
		if(jsonObject.has("ask"))
			ticker.ask = jsonObject.getDouble("ask");
		ticker.vol = jsonObject.getDouble("volume");
		ticker.high = jsonObject.getDouble("high");
		ticker.low = jsonObject.getDouble("low");
		ticker.last = jsonObject.getDouble("last");
	}
}
