package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;
import com.mobnetic.coinguardian.util.ParseUtils;
import com.mobnetic.coinguardian.util.TimeUtils;

public class FoscEx extends Market {
	
	private final static String NAME = "Fosc-Ex";
	private final static String TTS_NAME = "Fosc Ex";
	private final static String URL = "http://www.fosc-ex.com/api-public-ticker";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.KNC, new String[]{
				Currency.KRW
			});
	}
	
	public FoscEx() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.vol = ParseUtils.getDoubleFromString(jsonObject, "volume");
		ticker.last = ParseUtils.getDoubleFromString(jsonObject, "last");
		ticker.timestamp = jsonObject.getLong("timestamp") * TimeUtils.MILLIS_IN_SECOND;
		
	}
	
	@Override
	protected String parseErrorFromJsonObject(int requestId, JSONObject jsonObject, CheckerInfo checkerInfo) throws Exception {
		return jsonObject.getString("error");
	}

}
