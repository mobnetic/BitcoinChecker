package com.mobnetic.coinguardian.model.market;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

public class CryptoAltex extends Market {

	private final static String NAME = "CryptoAltex";
	private final static String TTS_NAME = "Crypto Altex";
	private final static String URL = "https://www.cryptoaltex.com/api/public_v2.php";
	
	public CryptoAltex() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject pairObject = jsonObject.getJSONObject(checkerInfo.getCurrencyPairId());
		ticker.vol = pairObject.getDouble("24_hours_volume");
		ticker.high = pairObject.getDouble("24_hours_price_high");
		ticker.low = pairObject.getDouble("24_hours_price_low");
		ticker.last = pairObject.getDouble("last_trade");
	}
	
	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL;
	}
	
	@Override
	protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray pairsNamesArray = jsonObject.names();
		
		for(int i=0; i<pairsNamesArray.length(); ++i) {
			final String pairName = pairsNamesArray.getString(i);
			final String[] split = pairName.split("/");
			if(split!=null && split.length>=2) {
				pairs.add(new CurrencyPairInfo(split[0], split[1], pairName));
			}
		}
	}
}
