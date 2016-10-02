package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.Currency;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class Vircurex extends Market {

	private final static String NAME = "Vircurex";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://api.vircurex.com/api/get_info_for_1_currency.json?base=%1$s&alt=%2$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.ANC,
				VirtualCurrency.DGC,
				VirtualCurrency.DOGE,
				VirtualCurrency.DVC,
				VirtualCurrency.FRC,
				VirtualCurrency.FTC,
				VirtualCurrency.I0C,
				VirtualCurrency.IXC,
				VirtualCurrency.LTC,
				VirtualCurrency.NMC,
				VirtualCurrency.NVC,
				VirtualCurrency.NXT,
				VirtualCurrency.PPC,
				VirtualCurrency.QRK,
				VirtualCurrency.TRC,
				VirtualCurrency.VTC,
				VirtualCurrency.WDC,
				VirtualCurrency.XPM
			});
		CURRENCY_PAIRS.put(VirtualCurrency.ANC, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC,
				VirtualCurrency.DGC,
				VirtualCurrency.DOGE,
				VirtualCurrency.DVC,
				VirtualCurrency.FRC,
				VirtualCurrency.FTC,
				VirtualCurrency.I0C,
				VirtualCurrency.IXC,
				VirtualCurrency.LTC,
				VirtualCurrency.NMC,
				VirtualCurrency.NVC,
				VirtualCurrency.NXT,
				VirtualCurrency.PPC,
				VirtualCurrency.QRK,
				VirtualCurrency.TRC,
				VirtualCurrency.VTC,
				VirtualCurrency.WDC,
				VirtualCurrency.XPM
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DGC, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC,
				VirtualCurrency.ANC,
				VirtualCurrency.DOGE,
				VirtualCurrency.DVC,
				VirtualCurrency.FRC,
				VirtualCurrency.FTC,
				VirtualCurrency.I0C,
				VirtualCurrency.IXC,
				VirtualCurrency.LTC,
				VirtualCurrency.NMC,
				VirtualCurrency.NVC,
				VirtualCurrency.NXT,
				VirtualCurrency.PPC,
				VirtualCurrency.QRK,
				VirtualCurrency.TRC,
				VirtualCurrency.VTC,
				VirtualCurrency.WDC,
				VirtualCurrency.XPM
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DOGE, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC,
				VirtualCurrency.ANC,
				VirtualCurrency.DGC,
				VirtualCurrency.DVC,
				VirtualCurrency.FRC,
				VirtualCurrency.FTC,
				VirtualCurrency.I0C,
				VirtualCurrency.IXC,
				VirtualCurrency.LTC,
				VirtualCurrency.NMC,
				VirtualCurrency.NVC,
				VirtualCurrency.NXT,
				VirtualCurrency.PPC,
				VirtualCurrency.QRK,
				VirtualCurrency.TRC,
				VirtualCurrency.VTC,
				VirtualCurrency.WDC,
				VirtualCurrency.XPM
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DVC, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC,
				VirtualCurrency.ANC,
				VirtualCurrency.DGC,
				VirtualCurrency.DOGE,
				VirtualCurrency.FRC,
				VirtualCurrency.FTC,
				VirtualCurrency.I0C,
				VirtualCurrency.IXC,
				VirtualCurrency.LTC,
				VirtualCurrency.NMC,
				VirtualCurrency.NVC,
				VirtualCurrency.NXT,
				VirtualCurrency.PPC,
				VirtualCurrency.QRK,
				VirtualCurrency.TRC,
				VirtualCurrency.VTC,
				VirtualCurrency.WDC,
				VirtualCurrency.XPM
			});
		CURRENCY_PAIRS.put(VirtualCurrency.FRC, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC,
				VirtualCurrency.ANC,
				VirtualCurrency.DGC,
				VirtualCurrency.DOGE,
				VirtualCurrency.DVC,
				VirtualCurrency.FTC,
				VirtualCurrency.I0C,
				VirtualCurrency.IXC,
				VirtualCurrency.LTC,
				VirtualCurrency.NMC,
				VirtualCurrency.NVC,
				VirtualCurrency.NXT,
				VirtualCurrency.PPC,
				VirtualCurrency.QRK,
				VirtualCurrency.TRC,
				VirtualCurrency.VTC,
				VirtualCurrency.WDC,
				VirtualCurrency.XPM
			});
		CURRENCY_PAIRS.put(VirtualCurrency.FTC, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC,
				VirtualCurrency.ANC,
				VirtualCurrency.DGC,
				VirtualCurrency.DOGE,
				VirtualCurrency.DVC,
				VirtualCurrency.FRC,
				VirtualCurrency.I0C,
				VirtualCurrency.IXC,
				VirtualCurrency.LTC,
				VirtualCurrency.NMC,
				VirtualCurrency.NVC,
				VirtualCurrency.NXT,
				VirtualCurrency.PPC,
				VirtualCurrency.QRK,
				VirtualCurrency.TRC,
				VirtualCurrency.VTC,
				VirtualCurrency.WDC,
				VirtualCurrency.XPM
			});
		CURRENCY_PAIRS.put(VirtualCurrency.I0C, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC,
				VirtualCurrency.ANC,
				VirtualCurrency.DGC,
				VirtualCurrency.DOGE,
				VirtualCurrency.DVC,
				VirtualCurrency.FRC,
				VirtualCurrency.FTC,
				VirtualCurrency.IXC,
				VirtualCurrency.LTC,
				VirtualCurrency.NMC,
				VirtualCurrency.NVC,
				VirtualCurrency.NXT,
				VirtualCurrency.PPC,
				VirtualCurrency.QRK,
				VirtualCurrency.TRC,
				VirtualCurrency.VTC,
				VirtualCurrency.WDC,
				VirtualCurrency.XPM
			});
		CURRENCY_PAIRS.put(VirtualCurrency.IXC, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC,
				VirtualCurrency.ANC,
				VirtualCurrency.DGC,
				VirtualCurrency.DOGE,
				VirtualCurrency.DVC,
				VirtualCurrency.FRC,
				VirtualCurrency.FTC,
				VirtualCurrency.I0C,
				VirtualCurrency.LTC,
				VirtualCurrency.NMC,
				VirtualCurrency.NVC,
				VirtualCurrency.NXT,
				VirtualCurrency.PPC,
				VirtualCurrency.QRK,
				VirtualCurrency.TRC,
				VirtualCurrency.VTC,
				VirtualCurrency.WDC,
				VirtualCurrency.XPM
			});
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC,
				VirtualCurrency.ANC,
				VirtualCurrency.DGC,
				VirtualCurrency.DOGE,
				VirtualCurrency.DVC,
				VirtualCurrency.FRC,
				VirtualCurrency.FTC,
				VirtualCurrency.I0C,
				VirtualCurrency.IXC,
				VirtualCurrency.NMC,
				VirtualCurrency.NVC,
				VirtualCurrency.NXT,
				VirtualCurrency.PPC,
				VirtualCurrency.QRK,
				VirtualCurrency.TRC,
				VirtualCurrency.VTC,
				VirtualCurrency.WDC,
				VirtualCurrency.XPM
			});
		CURRENCY_PAIRS.put(VirtualCurrency.NMC, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC,
				VirtualCurrency.ANC,
				VirtualCurrency.DGC,
				VirtualCurrency.DOGE,
				VirtualCurrency.DVC,
				VirtualCurrency.FRC,
				VirtualCurrency.FTC,
				VirtualCurrency.I0C,
				VirtualCurrency.IXC,
				VirtualCurrency.LTC,
				VirtualCurrency.NVC,
				VirtualCurrency.NXT,
				VirtualCurrency.PPC,
				VirtualCurrency.QRK,
				VirtualCurrency.TRC,
				VirtualCurrency.VTC,
				VirtualCurrency.WDC,
				VirtualCurrency.XPM
			});
		CURRENCY_PAIRS.put(VirtualCurrency.NVC, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC,
				VirtualCurrency.ANC,
				VirtualCurrency.DGC,
				VirtualCurrency.DOGE,
				VirtualCurrency.DVC,
				VirtualCurrency.FRC,
				VirtualCurrency.FTC,
				VirtualCurrency.I0C,
				VirtualCurrency.IXC,
				VirtualCurrency.LTC,
				VirtualCurrency.NMC,
				VirtualCurrency.NXT,
				VirtualCurrency.PPC,
				VirtualCurrency.QRK,
				VirtualCurrency.TRC,
				VirtualCurrency.VTC,
				VirtualCurrency.WDC,
				VirtualCurrency.XPM
			});
		CURRENCY_PAIRS.put(VirtualCurrency.NXT, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC,
				VirtualCurrency.ANC,
				VirtualCurrency.DGC,
				VirtualCurrency.DOGE,
				VirtualCurrency.DVC,
				VirtualCurrency.FRC,
				VirtualCurrency.FTC,
				VirtualCurrency.I0C,
				VirtualCurrency.IXC,
				VirtualCurrency.LTC,
				VirtualCurrency.NMC,
				VirtualCurrency.NVC,
				VirtualCurrency.PPC,
				VirtualCurrency.QRK,
				VirtualCurrency.TRC,
				VirtualCurrency.VTC,
				VirtualCurrency.WDC,
				VirtualCurrency.XPM
			});
		CURRENCY_PAIRS.put(VirtualCurrency.PPC, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC,
				VirtualCurrency.ANC,
				VirtualCurrency.DGC,
				VirtualCurrency.DOGE,
				VirtualCurrency.DVC,
				VirtualCurrency.FRC,
				VirtualCurrency.FTC,
				VirtualCurrency.I0C,
				VirtualCurrency.IXC,
				VirtualCurrency.LTC,
				VirtualCurrency.NMC,
				VirtualCurrency.NVC,
				VirtualCurrency.NXT,
				VirtualCurrency.QRK,
				VirtualCurrency.TRC,
				VirtualCurrency.VTC,
				VirtualCurrency.WDC,
				VirtualCurrency.XPM
			});
		CURRENCY_PAIRS.put(VirtualCurrency.QRK, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC,
				VirtualCurrency.ANC,
				VirtualCurrency.DGC,
				VirtualCurrency.DOGE,
				VirtualCurrency.DVC,
				VirtualCurrency.FRC,
				VirtualCurrency.FTC,
				VirtualCurrency.I0C,
				VirtualCurrency.IXC,
				VirtualCurrency.LTC,
				VirtualCurrency.NMC,
				VirtualCurrency.NVC,
				VirtualCurrency.NXT,
				VirtualCurrency.PPC,
				VirtualCurrency.TRC,
				VirtualCurrency.VTC,
				VirtualCurrency.WDC,
				VirtualCurrency.XPM
			});
		CURRENCY_PAIRS.put(VirtualCurrency.TRC, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC,
				VirtualCurrency.ANC,
				VirtualCurrency.DGC,
				VirtualCurrency.DOGE,
				VirtualCurrency.DVC,
				VirtualCurrency.FRC,
				VirtualCurrency.FTC,
				VirtualCurrency.I0C,
				VirtualCurrency.IXC,
				VirtualCurrency.LTC,
				VirtualCurrency.NMC,
				VirtualCurrency.NVC,
				VirtualCurrency.NXT,
				VirtualCurrency.PPC,
				VirtualCurrency.QRK,
				VirtualCurrency.VTC,
				VirtualCurrency.WDC,
				VirtualCurrency.XPM
			});
		CURRENCY_PAIRS.put(VirtualCurrency.VTC, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC,
				VirtualCurrency.ANC,
				VirtualCurrency.DGC,
				VirtualCurrency.DOGE,
				VirtualCurrency.DVC,
				VirtualCurrency.FRC,
				VirtualCurrency.FTC,
				VirtualCurrency.I0C,
				VirtualCurrency.IXC,
				VirtualCurrency.LTC,
				VirtualCurrency.NMC,
				VirtualCurrency.NVC,
				VirtualCurrency.NXT,
				VirtualCurrency.PPC,
				VirtualCurrency.QRK,
				VirtualCurrency.TRC,
				VirtualCurrency.WDC,
				VirtualCurrency.XPM
			});
		CURRENCY_PAIRS.put(VirtualCurrency.WDC, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC,
				VirtualCurrency.ANC,
				VirtualCurrency.DGC,
				VirtualCurrency.DOGE,
				VirtualCurrency.DVC,
				VirtualCurrency.FRC,
				VirtualCurrency.FTC,
				VirtualCurrency.I0C,
				VirtualCurrency.IXC,
				VirtualCurrency.LTC,
				VirtualCurrency.NMC,
				VirtualCurrency.NVC,
				VirtualCurrency.NXT,
				VirtualCurrency.PPC,
				VirtualCurrency.QRK,
				VirtualCurrency.TRC,
				VirtualCurrency.VTC,
				VirtualCurrency.XPM
			});
		CURRENCY_PAIRS.put(VirtualCurrency.XPM, new String[]{
				Currency.USD,
				Currency.EUR,
				VirtualCurrency.BTC,
				VirtualCurrency.ANC,
				VirtualCurrency.DGC,
				VirtualCurrency.DOGE,
				VirtualCurrency.DVC,
				VirtualCurrency.FRC,
				VirtualCurrency.FTC,
				VirtualCurrency.I0C,
				VirtualCurrency.IXC,
				VirtualCurrency.LTC,
				VirtualCurrency.NMC,
				VirtualCurrency.NVC,
				VirtualCurrency.NXT,
				VirtualCurrency.PPC,
				VirtualCurrency.QRK,
				VirtualCurrency.TRC,
				VirtualCurrency.VTC,
				VirtualCurrency.WDC,
			});
	}
	
	public Vircurex() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("highest_bid");
		ticker.ask = jsonObject.getDouble("lowest_ask");
		ticker.vol = jsonObject.getDouble("volume");
//		ticker.high;
//		ticker.low;
		ticker.last = jsonObject.getDouble("last_trade");
	}
}
