package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class BitMarketPL extends Market {

	private final static String SYMBOL_SWAP = "SWAP";

	private final static String NAME = "BitMarket.pl";
	private final static String TTS_NAME = "Bit Market";
	private final static String URL = "https://www.bitmarket.pl/json/%1$s%2$s/ticker.json";
	private final static String URL_SWAP = "https://bitmarket.pl/json/swap%1$s/swap.json";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.EUR,
				Currency.PLN,
				SYMBOL_SWAP
			});
		CURRENCY_PAIRS.put(VirtualCurrency.BCC, new String[]{
				Currency.PLN
		});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				VirtualCurrency.BTC,
				Currency.PLN,
				SYMBOL_SWAP
			});
		CURRENCY_PAIRS.put(VirtualCurrency.ETH, new String[]{
				SYMBOL_SWAP
		});
	}
	
	public BitMarketPL() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	private boolean isSwap(CheckerInfo checkerInfo) {
		return SYMBOL_SWAP.equals(checkerInfo.getCurrencyCounter());
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		if (isSwap(checkerInfo)){
			return String.format(URL_SWAP, checkerInfo.getCurrencyBase());
		}
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		if (isSwap(checkerInfo)){
			ticker.last = jsonObject.getDouble("cutoff");
		} else {
			ticker.bid = jsonObject.getDouble("bid");
			ticker.ask = jsonObject.getDouble("ask");
			ticker.vol = jsonObject.getDouble("volume");
			ticker.high = jsonObject.getDouble("high");
			ticker.low = jsonObject.getDouble("low");
			ticker.last = jsonObject.getDouble("last");
		}
	}
}
