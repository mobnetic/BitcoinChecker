package com.mobnetic.coinguardian.model;


public class CurrencyPairInfo implements Comparable<CurrencyPairInfo>{
	
	protected final String currencyBase;
	protected final String currencyCounter;
	protected final String currencyPairId;
	
	public CurrencyPairInfo(String currencyBase, String currencyCounter, String currencyPairId) {
		this.currencyBase = currencyBase;
		this.currencyCounter = currencyCounter;
		this.currencyPairId = currencyPairId;
	}
	
	public String getCurrencyBase() {
		return currencyBase;
	}
	
	public String getCurrencyCounter() {
		return currencyCounter;
	}
	
	public String getCurrencyPairId() {
		return currencyPairId;
	}
	
	@Override
	public int compareTo(CurrencyPairInfo another) throws NullPointerException {
		if(currencyBase==null || another.currencyBase==null || currencyCounter==null || another.currencyCounter==null)
			throw new NullPointerException();
		
		int compBase = currencyBase.compareToIgnoreCase(another.currencyBase);
		return compBase!=0 ? compBase : currencyCounter.compareToIgnoreCase(another.currencyCounter);
	}
}
