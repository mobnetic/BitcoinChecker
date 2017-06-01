package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Futures;
import com.mobnetic.coinguardian.model.FuturesMarket;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class OKCoinFutures extends FuturesMarket {
	
	private final static String NAME = "OKCoin Futures";
	private final static String TTS_NAME = "OK Coin Futures";
	private final static String URL = "https://www.okex.com/api/v1/future_ticker.do?symbol=%1$s_%2$s&contract_type=%3$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
	//			Currency.CNY, //At this time (2015-09-17) no API exist for www.okcoin.cn. Check https://www.okcoin.cn/about/rest_api.do
				Currency.USD
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
	//			Currency.CNY, //At this time (2015-09-17) no API exist for www.okcoin.cn. Check https://www.okcoin.cn/about/rest_api.do
				Currency.USD
			});
	}
	private final static int[] CONTRACT_TYPES = new int[] {
		Futures.CONTRACT_TYPE_WEEKLY,
		Futures.CONTRACT_TYPE_BIWEEKLY,
		Futures.CONTRACT_TYPE_QUARTERLY
	};
	
	public OKCoinFutures() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS, CONTRACT_TYPES);
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo, int contractType) {
		return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase(), getContractTypeString(contractType));
	}
	
	private String getContractTypeString(int contractType) {
		switch (contractType) {
			default:
			case Futures.CONTRACT_TYPE_WEEKLY: return "this_week";
			case Futures.CONTRACT_TYPE_BIWEEKLY: return "next_week";
			case Futures.CONTRACT_TYPE_QUARTERLY: return "quarter";
		}
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
