package com.mobnetic.coinguardian.model;

import java.util.Locale;

public class CheckerInfo extends CurrencyPairInfo {

	public CheckerInfo(String currencyBase, String currencyCounter, String currencyPairId) {
		super(currencyBase, currencyCounter, currencyPairId);
	}
	
	public String getCurrencyBaseLowerCase() {
		return getCurrencyBase().toLowerCase(Locale.US);
	}
	
	public String getCurrencyCounterLowerCase() {
		return getCurrencyCounter().toLowerCase(Locale.US);
	}
}
