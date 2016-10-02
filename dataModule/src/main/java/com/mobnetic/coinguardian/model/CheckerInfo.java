package com.mobnetic.coinguardian.model;

import java.util.Locale;

public class CheckerInfo extends CurrencyPairInfo {

	protected final int contractType;
	
	public CheckerInfo(String currencyBase, String currencyCounter, String currencyPairId, int contractType) {
		super(currencyBase, currencyCounter, currencyPairId);
		this.contractType = contractType;
	}
	
	public String getCurrencyBaseLowerCase() {
		return getCurrencyBase().toLowerCase(Locale.US);
	}
	
	public String getCurrencyCounterLowerCase() {
		return getCurrencyCounter().toLowerCase(Locale.US);
	}
	
	public int getContractType() {
		return contractType;
	}
}
