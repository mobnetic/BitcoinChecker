package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class Coinbase extends Market {

	private final static String NAME = "Coinbase";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://coinbase.com/api/v1/prices/buy?currency=%1$s";
	private final static String URL_SECOND = "https://coinbase.com/api/v1/prices/sell?currency=%1$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD
//				Currency.EUR,
//				Currency.CHF,
//				Currency.GBP,
//				Currency.CAD,
//				Currency.CNY,
//				Currency.RUB,
//				Currency.JPY,
//				Currency.AUD,
//				Currency.AFN,
//				Currency.ALL,
//				Currency.DZD,
//				Currency.AOA,
//				Currency.ARS,
//				Currency.AMD,
//				Currency.AWG,
//				Currency.AZN,
//				Currency.BSD,
//				Currency.BHD,
//				Currency.BDT,
//				Currency.BBD,
//				Currency.BYR,
//				Currency.BZD,
//				Currency.BMD,
//				Currency.BTN,
//				Currency.BOB,
//				Currency.BAM,
//				Currency.BWP,
//				Currency.BRL,
//				Currency.BND,
//				Currency.BGN,
//				Currency.BIF,
//				Currency.KHR,
//				Currency.CVE,
//				Currency.KYD,
//				Currency.XAF,
//				Currency.XPF,
//				Currency.CLP,
//				Currency.COP,
//				Currency.KMF,
//				Currency.CDF,
//				Currency.CRC,
//				Currency.HRK,
//				Currency.CUP,
//				Currency.CZK,
//				Currency.DKK,
//				Currency.DJF,
//				Currency.DOP,
//				Currency.XCD,
//				Currency.EGP,
//				Currency.ERN,
//				Currency.EEK,
//				Currency.ETB,
//				Currency.FKP,
//				Currency.FJD,
//				Currency.GMD,
//				Currency.GEL,
//				Currency.GHS,
//				Currency.GHS,
//				Currency.GIP,
//				Currency.GTQ,
//				Currency.GNF,
//				Currency.GYD,
//				Currency.HTG,
//				Currency.HNL,
//				Currency.HKD,
//				Currency.HUF,
//				Currency.ISK,
//				Currency.INR,
//				Currency.IDR,
//				Currency.IRR,
//				Currency.IQD,
//				Currency.ILS,
//				Currency.JMD,
//				Currency.JOD,
//				Currency.KZT,
//				Currency.KES,
//				Currency.KWD,
//				Currency.KGS,
//				Currency.LAK,
//				Currency.LVL,
//				Currency.LBP,
//				Currency.LSL,
//				Currency.LRD,
//				Currency.LYD,
//				Currency.LTL,
//				Currency.MOP,
//				Currency.MKD,
//				Currency.MGA,
//				Currency.MWK,
//				Currency.MYR,
//				Currency.MVR,
//				Currency.MRO,
//				Currency.MUR,
//				Currency.MXN,
//				Currency.MDL,
//				Currency.MNT,
//				Currency.MAD,
//				Currency.MZN,
//				Currency.MMK,
//				Currency.NAD,
//				Currency.NPR,
//				Currency.ANG,
//				Currency.TWD,
//				Currency.NZD,
//				Currency.NIO,
//				Currency.NGN,
//				Currency.KPW,
//				Currency.NOK,
//				Currency.OMR,
//				Currency.PKR,
//				Currency.PAB,
//				Currency.PGK,
//				Currency.PYG,
//				Currency.PEN,
//				Currency.PHP,
//				Currency.PLN,
//				Currency.QAR,
//				Currency.RON,
//				Currency.RWF,
//				Currency.SHP,
//				Currency.SVC,
//				Currency.WST,
//				Currency.SAR,
//				Currency.RSD,
//				Currency.SCR,
//				Currency.SLL,
//				Currency.SGD,
//				Currency.SBD,
//				Currency.SOS,
//				Currency.ZAR,
//				Currency.KRW,
//				Currency.LKR,
//				Currency.SDG,
//				Currency.SRD,
//				Currency.SZL,
//				Currency.SEK,
//				Currency.SYP,
//				Currency.STD,
//				Currency.TJS,
//				Currency.TZS,
//				Currency.THB,
//				Currency.TOP,
//				Currency.TTD,
//				Currency.TND,
//				Currency.TRY,
//				Currency.TMM,
//				Currency.UGX,
//				Currency.UAH,
//				Currency.AED,
//				Currency.UYU,
//				Currency.UZS,
//				Currency.VUV,
//				Currency.VEF,
//				Currency.VND,
//				Currency.XOF,
//				Currency.YER,
//				Currency.ZMK,
//				Currency.ZWL
			});
	}
	
	public Coinbase() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public int getNumOfRequests(CheckerInfo checkerInfo) {
		return 2;
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		if(requestId==0)
			return String.format(URL, checkerInfo.getCurrencyCounter());
		else
			return String.format(URL_SECOND, checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		JSONObject subtotal = jsonObject.getJSONObject("subtotal");
		if(requestId==0) {
			ticker.ask = subtotal.getDouble("amount");
			ticker.last = subtotal.getDouble("amount");
		} else {
			ticker.bid = subtotal.getDouble("amount");
		}
	}
}
