package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Kraken extends Market {

	private final static String NAME = "Kraken";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://api.kraken.com/0/public/Ticker?pair=%1$s";
	private final static String URL_CURRENCY_PAIRS = "https://api.kraken.com/0/public/AssetPairs";
	
	public Kraken() {
		super(NAME, TTS_NAME, null);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		if(checkerInfo.getCurrencyPairId()!=null) {
			return String.format(URL, checkerInfo.getCurrencyPairId()); 
		} else {
			final String currencyBase = fixCurrency(checkerInfo.getCurrencyBase());
			final String currencyCounter = fixCurrency(checkerInfo.getCurrencyCounter());
			return String.format(URL, currencyBase+currencyCounter);
		}
	}
	
	private String fixCurrency(String currency) {
		if (VirtualCurrency.BTC.equals(currency)) return VirtualCurrency.XBT;
		if (VirtualCurrency.VEN.equals(currency)) return VirtualCurrency.XVN;
		if (VirtualCurrency.DOGE.equals(currency)) return VirtualCurrency.XDG;
		return currency;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject resultObject = jsonObject.getJSONObject("result");
		final JSONObject pairObject = resultObject.getJSONObject(resultObject.names().getString(0));
		
		ticker.bid = getDoubleFromJsonArrayObject(pairObject, "b");
		ticker.ask = getDoubleFromJsonArrayObject(pairObject, "a");
		ticker.vol = getDoubleFromJsonArrayObject(pairObject, "v");
		ticker.high = getDoubleFromJsonArrayObject(pairObject, "h");
		ticker.low = getDoubleFromJsonArrayObject(pairObject, "l");
		ticker.last = getDoubleFromJsonArrayObject(pairObject, "c");
	}
	
	private double getDoubleFromJsonArrayObject(JSONObject jsonObject, String arrayKey) throws Exception {
		final JSONArray jsonArray = jsonObject.getJSONArray(arrayKey);
		return jsonArray!=null && jsonArray.length()>0 ? jsonArray.getDouble(0) : 0;
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
        final JSONObject result = jsonObject.getJSONObject("result");
        final JSONArray pairNames = result.names();
        
        for(int i=0; i<pairNames.length(); ++i) {
        	final String pairId = pairNames.getString(i);
        	if (pairId != null && pairId.indexOf('.') == -1) {
	        	final JSONObject pairJsonObject = result.getJSONObject(pairId);
	        	
	            pairs.add(new CurrencyPairInfo(
	            		parseCurrency(pairJsonObject.getString("base")),
	            		parseCurrency(pairJsonObject.getString("quote")),
	            		pairId));
        	}
        }
    }
    
    private String parseCurrency(String currency) {
		if(currency!=null && currency.length()>=2) {
			final char firstChar = currency.charAt(0);
			if (firstChar == 'Z' || firstChar == 'X') {
				currency = currency.substring(1);
			}
		}
		if (VirtualCurrency.XBT.equals(currency)) return VirtualCurrency.BTC;
		if (VirtualCurrency.XVN.equals(currency)) return VirtualCurrency.VEN;
		if (VirtualCurrency.XDG.equals(currency)) return VirtualCurrency.DOGE;
		return currency;
	}
}
