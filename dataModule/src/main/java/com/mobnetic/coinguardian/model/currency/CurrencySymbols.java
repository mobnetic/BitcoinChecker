package com.mobnetic.coinguardian.model.currency;

import java.util.HashMap;

public class CurrencySymbols {

	public final static HashMap<String, String> CURRENCY_SYMBOLS = new HashMap<String, String>();
	static {
		CURRENCY_SYMBOLS.put(Currency.USD, "$");
		CURRENCY_SYMBOLS.put(Currency.PLN, "zł");
		CURRENCY_SYMBOLS.put(Currency.CNY, "¥");
		CURRENCY_SYMBOLS.put(Currency.EUR, "€");
//		CURRENCY_SYMBOLS.put(CAD, "$");
		CURRENCY_SYMBOLS.put(Currency.GBP, "£");
		CURRENCY_SYMBOLS.put(Currency.CHF, "Fr");
		CURRENCY_SYMBOLS.put(Currency.RUB, "р.");
		CURRENCY_SYMBOLS.put(Currency.RUR, "р.");
		CURRENCY_SYMBOLS.put(Currency.AUD, "$");
		CURRENCY_SYMBOLS.put(Currency.SEK, "kr");
		CURRENCY_SYMBOLS.put(Currency.DKK, "kr");
		CURRENCY_SYMBOLS.put(Currency.HKD, "$");
		CURRENCY_SYMBOLS.put(Currency.SGD, "$");
		CURRENCY_SYMBOLS.put(Currency.THB, "฿");
		CURRENCY_SYMBOLS.put(Currency.NZD, "$");
		CURRENCY_SYMBOLS.put(Currency.JPY, "¥");
		CURRENCY_SYMBOLS.put(Currency.BRL, "R$");
		CURRENCY_SYMBOLS.put(Currency.KRW, "₩");
		
		CURRENCY_SYMBOLS.put(Currency.AFN, "؋");
		CURRENCY_SYMBOLS.put(Currency.ALL, "L");
		CURRENCY_SYMBOLS.put(Currency.DZD, "د.ج");
		CURRENCY_SYMBOLS.put(Currency.AOA, "Kz");
		CURRENCY_SYMBOLS.put(Currency.ARS, "$");
		CURRENCY_SYMBOLS.put(Currency.AMD, "դր.");
		CURRENCY_SYMBOLS.put(Currency.AWG, "ƒ");
		CURRENCY_SYMBOLS.put(Currency.AZN, "m");
		CURRENCY_SYMBOLS.put(Currency.BSD, "$");
		CURRENCY_SYMBOLS.put(Currency.BHD, "ب.د");
		CURRENCY_SYMBOLS.put(Currency.BDT, "৳");
		CURRENCY_SYMBOLS.put(Currency.BBD, "$");
		CURRENCY_SYMBOLS.put(Currency.BYR, "Br");
		CURRENCY_SYMBOLS.put(Currency.BZD, "$");
		CURRENCY_SYMBOLS.put(Currency.BMD, "$");
		CURRENCY_SYMBOLS.put(Currency.BTN, "Nu.");
		CURRENCY_SYMBOLS.put(Currency.BOB, "Bs.");
		CURRENCY_SYMBOLS.put(Currency.BAM, "КМ");
		CURRENCY_SYMBOLS.put(Currency.BWP, "P");
		CURRENCY_SYMBOLS.put(Currency.BND, "$");
		CURRENCY_SYMBOLS.put(Currency.BGN, "лв");
		CURRENCY_SYMBOLS.put(Currency.BIF, "Fr");
		CURRENCY_SYMBOLS.put(Currency.TRY, "TL");
		CURRENCY_SYMBOLS.put(Currency.ZAR, "R");
		CURRENCY_SYMBOLS.put(Currency.IDR, "Rp");
	}
}
