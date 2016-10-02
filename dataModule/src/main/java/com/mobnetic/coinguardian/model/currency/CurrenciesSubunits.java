package com.mobnetic.coinguardian.model.currency;

import java.util.HashMap;

import com.mobnetic.coinguardian.model.CurrencySubunit;
import com.mobnetic.coinguardian.model.CurrencySubunitsMap;

public class CurrenciesSubunits {

	public final static HashMap<String, CurrencySubunitsMap> CURRENCIES_SUBUNITS = new HashMap<String, CurrencySubunitsMap>();
	static {
		CURRENCIES_SUBUNITS.put(VirtualCurrency.BTC, new CurrencySubunitsMap(
				new CurrencySubunit(VirtualCurrency.BTC, 1),
				new CurrencySubunit(VirtualCurrency.mBTC, 1000),
				new CurrencySubunit(VirtualCurrency.uBTC, 1000000),
				new CurrencySubunit(VirtualCurrency.Satoshi, 100000000, false)
			)
		);
		CURRENCIES_SUBUNITS.put(VirtualCurrency.LTC, new CurrencySubunitsMap(
				new CurrencySubunit(VirtualCurrency.LTC, 1),
				new CurrencySubunit(VirtualCurrency.mLTC, 1000)
			)
		);
	}
}
