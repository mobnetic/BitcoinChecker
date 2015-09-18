package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class OkcoinFuturesPrice extends Market {

	private final static String NAME = "OKCoin Futures Price";
	private final static String TTS_NAME = "Ok coin feature price";
	private final static String URL_USD_1w = "https://www.okcoin.com/api/v1/future_ticker.do?symbol=%1$s_%2$s&contract_type=this_week";
	private final static String URL_USD_2w = "https://www.okcoin.com/api/v1/future_ticker.do?symbol=%1$s_%2$s&contract_type=next_week";
	private final static String URL_USD_3m = "https://www.okcoin.com/api/v1/future_ticker.do?symbol=%1$s_%2$s&contract_type=quarter";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC_1WeekFutures, new String[]{
//				Currency.CNY, //At this time (2015-09-17) no API exist for www.okcoin.cn. Check https://www.okcoin.cn/about/rest_api.do
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.BTC_2WeeksFutures, new String[]{
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.BTC_3MonthsFutures, new String[]{
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC_1WeekFutures, new String[]{
//				Currency.CNY, //At this time (2015-09-17) no API exist for www.okcoin.cn. Check https://www.okcoin.cn/about/rest_api.do
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC_2WeeksFutures, new String[]{
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC_3MonthsFutures, new String[]{
				Currency.USD
			});
	}
	
	public OkcoinFuturesPrice() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		if(checkerInfo.getCurrencyBase().contains("TC This Week Futures")) {
		   return String.format(URL_USD_1w, checkerInfo.getCurrencyBaseLowerCase().split(" ")[0], checkerInfo.getCurrencyCounterLowerCase());
		} else if(checkerInfo.getCurrencyBase().contains("TC Next Week Futures")) {
		   return String.format(URL_USD_2w, checkerInfo.getCurrencyBaseLowerCase().split(" ")[0], checkerInfo.getCurrencyCounterLowerCase());
		} else if(checkerInfo.getCurrencyBase().contains("TC Next Quarter Futures")) {
		   return String.format(URL_USD_3m, checkerInfo.getCurrencyBaseLowerCase().split(" ")[0], checkerInfo.getCurrencyCounterLowerCase());
		}
		return "https://www.okcoin.com/about/rest_api.do"; //URL API - should never happen
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject tickerJsonObject = jsonObject.getJSONObject("ticker");
		
		ticker.bid = tickerJsonObject.getDouble("buy");
		ticker.ask = tickerJsonObject.getDouble("sell");
		ticker.vol = tickerJsonObject.getDouble("vol");
		ticker.high = tickerJsonObject.getDouble("high");
		ticker.low = tickerJsonObject.getDouble("low");
		ticker.last = tickerJsonObject.getDouble("last");
	}
}
