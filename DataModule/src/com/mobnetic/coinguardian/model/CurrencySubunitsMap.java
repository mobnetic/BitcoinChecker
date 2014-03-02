package com.mobnetic.coinguardian.model;

import java.util.LinkedHashMap;

public class CurrencySubunitsMap extends LinkedHashMap<Long, CurrencySubunit> {

	private static final long serialVersionUID = -7219011491064245859L;
	
	public CurrencySubunitsMap(CurrencySubunit... currencySubunits) {
		for(CurrencySubunit currencySubunit : currencySubunits)
			put(currencySubunit.subunitToUnit, currencySubunit);
	}
	
}
