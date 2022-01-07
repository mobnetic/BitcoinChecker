package com.aneonex.bitcoinchecker.tester.util

import android.content.Context
import android.content.SharedPreferences
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairsListWithDate
import com.google.gson.Gson

object MarketCurrencyPairsStore {
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.applicationContext.getSharedPreferences("MARKET_CURRENCY_PAIRS", Context.MODE_PRIVATE)
    }

    fun savePairsForMarket(context: Context, marketKey: String, currencyPairsListWithDate: CurrencyPairsListWithDate?) {
        try {
            savePairsStringForMarket(context, marketKey, Gson().toJson(currencyPairsListWithDate))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun savePairsStringForMarket(context: Context, marketKey: String, jsonString: String) {
        getSharedPreferences(context).edit().putString(marketKey, jsonString).apply()
    }

    fun getPairsForMarket(context: Context, marketKey: String): CurrencyPairsListWithDate? {
        val pairsJson = getPairsStringForMarket(context, marketKey) ?: return null

        return try {
            Gson().fromJson(pairsJson, CurrencyPairsListWithDate::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getPairsStringForMarket(context: Context, marketKey: String): String? {
        return getSharedPreferences(context).getString(marketKey, null)
    }
}