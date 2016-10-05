package com.mobnetic.coinguardian.model;

public class CurrencySymbol {

	public final String symbol;
	public final boolean symbolFirst;
	
	public CurrencySymbol(String symbol) {
		this(symbol, false);
	}
	
	public CurrencySymbol(String symbol, boolean symbolFirst) {
		this.symbol = symbol;
		this.symbolFirst = symbolFirst;
	}
}
