// @joseccnet contribution.
package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class Bitreserve extends Market {

	private final static String NAME = "Bitreserve";
	private final static String TTS_NAME = "Bit Reserve";
	private final static String URL = "https://api.bitreserve.org/v0/ticker";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>(1);
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD
			});
                CURRENCY_PAIRS.put(Currency.USD, new String[]{
                                VirtualCurrency.BTC,
                                Currency.MXN,
                                Currency.AED,
                                Currency.AUD,
                                Currency.ARS,
                                Currency.BRL,
                                Currency.CAD,
                                Currency.CHF,
                                Currency.CNY,
                                Currency.DKK,
                                Currency.EUR,
                                Currency.GBP,
                                Currency.HKD,
                                Currency.ILS,
                                Currency.INR,
                                Currency.JPY,
                                Currency.KES,
                                Currency.NOK,
                                Currency.NZD,
                                Currency.PHP,
                                Currency.PLN,
                                Currency.SEK,
                                Currency.SGD,
                                Currency.XAG,
                                Currency.XAU,
                                Currency.XPD,
                                Currency.XPT
                        });
                CURRENCY_PAIRS.put(Currency.MXN, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(Currency.AED, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(Currency.ARS, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(Currency.AUD, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(Currency.BRL, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(Currency.CAD, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(Currency.CHF, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(Currency.CNY, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(Currency.DKK, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(Currency.EUR, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(Currency.GBP, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(Currency.HKD, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(Currency.ILS, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(Currency.INR, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(Currency.JPY, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(Currency.KES, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(Currency.NOK, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(Currency.NZD, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(Currency.PHP, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(Currency.PLN, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(Currency.SEK, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(Currency.SGD, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(VirtualCurrency.XAG, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(VirtualCurrency.XAU, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(VirtualCurrency.XPD, new String[]{
                                Currency.USD
                        });
                CURRENCY_PAIRS.put(VirtualCurrency.XPT, new String[]{
                                Currency.USD
                        });
	}
	
	public Bitreserve() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}

        @Override
        protected void parseTicker(int requestId, String responseString, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
                parseTickerFromJsonObject(requestId, getJSONObjectfromJSONArray(responseString,checkerInfo.getCurrencyBase()+checkerInfo.getCurrencyCounter()), ticker, checkerInfo);
        }
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = getDoubleFromString(jsonObject, "bid");
		ticker.ask = getDoubleFromString(jsonObject, "ask");
		ticker.last = ((ticker.bid+ticker.ask)/2); //This is how bitreserve operate on production(as I observed)
	}
	
	private double getDoubleFromString(JSONObject jsonObject, String name) throws NumberFormatException, JSONException {
		return Double.parseDouble(jsonObject.getString(name));
	}

        private JSONObject getJSONObjectfromJSONArray(String str, String currencypair){
           JSONArray jarray = null;
           JSONObject jobject = null;
           try {
               jarray = new JSONArray(str);
              for(int i=0 ; i<jarray.length() ; i++) {
                  JSONObject jobj = jarray.getJSONObject(i);
                  if(jobj.getString("pair").equals(currencypair)){
                     jobject = jobj;
                     return jobject;
                  }
               }
               if(jobject == null){
                  String newbase=currencypair.substring(3,6);
                  String newcounter=currencypair.substring(0,3);
                  String newcurrencypair=newbase+newcounter;
                  jobject = getJSONObjectfromJSONArray(str,newcurrencypair);
                  String new_pair=newcurrencypair;
                  String new_ask=String.valueOf(1.0/Double.parseDouble(jobject.getString("ask")));
                  String new_bid=String.valueOf(1.0/Double.parseDouble(jobject.getString("bid")));
                  String new_currency=newcounter;
                  JSONObject obj=new JSONObject();
                  obj.put("pair",new_pair);
                  obj.put("ask",new_ask);
                  obj.put("bid",new_bid);
                  obj.put("currency",new_currency);
                  return obj;
               }
           } catch (Exception e) {
               throw new RuntimeException("Failed to create JSON Object.", e);
           }
        return jobject;
        }
}
