package com.mobnetic.coinguardian.util;

import com.mobnetic.coinguardian.model.CurrencySubunit;
import com.mobnetic.coinguardian.model.CurrencySubunitsMap;
import com.mobnetic.coinguardian.model.currency.CurrenciesSubunits;
import com.mobnetic.coinguardian.model.currency.CurrencySymbols;

public class CurrencyUtils {

	public static String getCurrencySymbol(String currency) {
		return CurrencySymbols.CURRENCY_SYMBOLS.containsKey(currency) ? CurrencySymbols.CURRENCY_SYMBOLS.get(currency) : currency;
	}
	
	public static CurrencySubunit getCurrencySubunit(String currency, long subunitToUnit) {
		if(CurrenciesSubunits.CURRENCIES_SUBUNITS.containsKey(currency)) {
			CurrencySubunitsMap subunits = CurrenciesSubunits.CURRENCIES_SUBUNITS.get(currency);
			if(subunits.containsKey(subunitToUnit))
				return subunits.get(subunitToUnit);
		}
		
		return new CurrencySubunit(currency, 1);
	}
}
