package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Exmo extends Market {

	private final static String NAME = "Exmo";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://api.exmo.com/v1/ticker/";
	private final static String URL_CURRENCY_PAIRS = "https://api.exmo.com/v1/pair_settings/";

	public Exmo() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject pairJsonObject = jsonObject.getJSONObject(checkerInfo.getCurrencyBase()+"_"+checkerInfo.getCurrencyCounter());
		ticker.bid = pairJsonObject.getDouble("buy_price");
		ticker.ask = pairJsonObject.getDouble("sell_price");
		ticker.vol = pairJsonObject.getDouble("vol");
		ticker.high = pairJsonObject.getDouble("high");
		ticker.low = pairJsonObject.getDouble("low");
		ticker.last = pairJsonObject.getDouble("last_trade");
	}

	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL_CURRENCY_PAIRS;
	}


	@Override
	protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray pairIds = jsonObject.names();
		for(int i=0; i<pairIds.length(); ++i) {
			String pairId = pairIds.getString(i);
			if(pairId==null)
				continue;
			String[] currencies = pairId.split("_");
			if(currencies.length != 2)
				continue;

			pairs.add(new CurrencyPairInfo(currencies[0], currencies[1], pairId));
		}
	}
}