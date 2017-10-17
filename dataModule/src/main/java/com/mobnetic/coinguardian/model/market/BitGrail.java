package com.mobnetic.coinguardian.model.market.example;

import java.util.HashMap;
import java.util.LinkedHashMap;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class BitGrail extends Market {

	private final static String NAME = "BitGrail";
	private final static String TTS_NAME = Bit Grail; 
	private final static String URL = "https://bitgrail.com/api/v1/%1$s-%2$s/ticker";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				VirtualCurrency.XRB,
			});
    CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				VirtualCurrency.ETH,
			});
    CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				VirtualCurrency.CREA,
			});
    CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				VirtualCurrency.BCC,
			});
    CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				VirtualCurrency.DOGE,
			});
    CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				VirtualCurrency.CFT,
			});
    CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				VirtualCurrency.LSK,
			});
    CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				VirtualCurrency.LTC,
			});
		CURRENCY_PAIRS.put(VirtualCurrency.XRB, new String[]{
				VirtualCurrency.ETH,
			});
    CURRENCY_PAIRS.put(VirtualCurrency.XRB, new String[]{
				VirtualCurrency.BCC,
			});
    CURRENCY_PAIRS.put(VirtualCurrency.XRB, new String[]{
				VirtualCurrency.CFT,
			});
    CURRENCY_PAIRS.put(VirtualCurrency.XRB, new String[]{
				VirtualCurrency.LSK,
			});
    CURRENCY_PAIRS.put(VirtualCurrency.XRB, new String[]{
				VirtualCurrency.CREA,
			});
    CURRENCY_PAIRS.put(VirtualCurrency.XRB, new String[]{
				VirtualCurrency.LTC,
			});
    CURRENCY_PAIRS.put(VirtualCurrency.XRB, new String[]{
				VirtualCurrency.DOGE,
			});  
	}
	
	public BitGrail() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("bid");
		ticker.ask = jsonObject.getDouble("ask");
		ticker.vol = jsonObject.getDouble("volume");
		ticker.high = jsonObject.getDouble("high");
		ticker.low = jsonObject.getDouble("low");
		ticker.last = jsonObject.getDouble("last");
	}
}
