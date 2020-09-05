package com.aneonex.bitcoinchecker.tester.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairsListWithDate;

public class MarketCurrencyPairsStore {

	private static SharedPreferences getSharedPreferences(Context context) {
		return context.getApplicationContext().getSharedPreferences("MARKET_CURRENCIY_PAIRS", Context.MODE_PRIVATE);
	}
	
	public final static void savePairsForMarket(Context context, String marketKey, CurrencyPairsListWithDate currencyPairsListWithDate) {
		try {
			savePairsStringForMarket(context, marketKey, new Gson().toJson(currencyPairsListWithDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private final static void savePairsStringForMarket(Context context, String marketKey, String jsonString) {
		getSharedPreferences(context).edit().putString(marketKey, jsonString).commit();
	}
	
	public final static CurrencyPairsListWithDate getPairsForMarket(Context context, String marketKey) {
		try {
			return new Gson().fromJson(getPairsStringForMarket(context, marketKey), CurrencyPairsListWithDate.class);
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	private final static String getPairsStringForMarket(Context context, String marketKey) {
		return getSharedPreferences(context).getString(marketKey, null);
	}
}
