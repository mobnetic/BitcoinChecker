package com.mobnetic.coinguardian.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.CurrencyPairsListWithDate;

public class CurrencyPairsMapHelper {

	private long date;
	private final HashMap<String, CharSequence[]> currencyPairs;
	private final HashMap<String, String> currencyPairsIds;
	private int pairsCount = 0;
	
	public CurrencyPairsMapHelper(CurrencyPairsListWithDate currencyPairsListWithDate) {
		currencyPairs = new LinkedHashMap<String, CharSequence[]>();
		currencyPairsIds = new HashMap<String, String>();
		
		if(currencyPairsListWithDate==null)
			return;
		
		date = currencyPairsListWithDate.date;
		List<CurrencyPairInfo> sortedPairs = currencyPairsListWithDate.pairs;
		pairsCount = sortedPairs.size();
				
		HashMap<String, Integer> currencyGroupSizes = new HashMap<String, Integer>();
		for(CurrencyPairInfo currencyPairInfo : sortedPairs) {
			Integer currentCurrencyGroupSize = currencyGroupSizes.get(currencyPairInfo.getCurrencyBase());
			if(currentCurrencyGroupSize==null) {
				currentCurrencyGroupSize = 1;
			} else {
				++currentCurrencyGroupSize;
			}
			currencyGroupSizes.put(currencyPairInfo.getCurrencyBase(), currentCurrencyGroupSize);
		}
		
		int currentGroupPositionToInsert = 0;
		for(CurrencyPairInfo currencyPairInfo : sortedPairs) {
			CharSequence[] currencyGroup = currencyPairs.get(currencyPairInfo.getCurrencyBase());
			if(currencyGroup==null) {
				currencyGroup = new CharSequence[currencyGroupSizes.get(currencyPairInfo.getCurrencyBase())];
				currencyPairs.put(currencyPairInfo.getCurrencyBase(), currencyGroup);
				currentGroupPositionToInsert = 0;
			} else {
				++currentGroupPositionToInsert;
			}
			currencyGroup[currentGroupPositionToInsert] = currencyPairInfo.getCurrencyCounter();
			
			if(currencyPairInfo.getCurrencyPairId()!=null) {
				currencyPairsIds.put(
						createCurrencyPairKey(currencyPairInfo.getCurrencyBase(), currencyPairInfo.getCurrencyCounter()),
						currencyPairInfo.getCurrencyPairId());
			}
		}
	}
	
	public long getDate() {
		return date;
	}
	
	public HashMap<String, CharSequence[]> getCurrencyPairs() {
		return currencyPairs;
	}
	
	public String getCurrencyPairId(String currencyBase, String currencyCounter) {
		return currencyPairsIds.get(createCurrencyPairKey(currencyBase, currencyCounter));
	}
	
	public int getPairsCount() {
		return pairsCount;
	}
	
	private String createCurrencyPairKey(String currencyBase, String currencyCounter) {
		return String.format("%1$s_%2$s", currencyBase, currencyCounter);
	}
}
