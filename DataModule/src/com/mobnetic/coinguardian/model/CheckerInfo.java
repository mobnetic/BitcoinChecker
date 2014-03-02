package com.mobnetic.coinguardian.model;

import java.util.Locale;

public class CheckerInfo {

	private final String currencyBase;
	private final String currencyCounter;
	
	public CheckerInfo(String currencyBase, String currencyCounter) {
		this.currencyBase = currencyBase;
		this.currencyCounter = currencyCounter;
	}
	
	public String getCurrencyBase() {
		return currencyBase;
	}
	public String getCurrencyBaseLowerCase() {
		return getCurrencyBase().toLowerCase(Locale.US);
	}
	
	public String getCurrencyCounter() {
		return currencyCounter;
	}
	public String getCurrencyCounterLowerCase() {
		return getCurrencyCounter().toLowerCase(Locale.US);
	}
}
