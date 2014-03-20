package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;
import com.mobnetic.coinguardiandatamodule.R;

public class CryptoRush extends Market {

	public final static String NAME = "CryptoRush";
	public final static String TTS_NAME = "Crypto Rush";
	public final static String URL = "https://cryptorush.in/marketdata/all.json";
	private final static String URL_CURRENCY_PAIRS = URL;
	public final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency._10_5, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency._21, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency._42, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency._66, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency._888, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.ALT, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.ANI, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.AUR, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.BAT, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.BC, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.BCC, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.BCX, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.BEE, new String[]{ VirtualCurrency.DOGE, VirtualCurrency.LTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.BEER, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.BELA, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.BELI, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.BEN, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.BLA, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.BTCS, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.BTP, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.BUR, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CARB, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CAT, new String[]{ VirtualCurrency.BTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.COINO, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DGC });
		CURRENCY_PAIRS.put(VirtualCurrency.COL, new String[]{ VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.COLA, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CR, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CRA, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CRD, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CREA, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.CRN, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CRS, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CTM, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.DELTA, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.DGB, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.DGC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.DOGE, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DGC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.DRK, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.DUCK, new String[]{ VirtualCurrency.BTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.EAC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.ECC, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.ECN, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.EMO, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.ETOK, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.EUC, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.FCK, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.FLAP, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.FOX, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.FRY, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DGC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.FRZ, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.FSS, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.FST, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.FUNK, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.GOAT, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.GOX, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.GPUC, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.GRUMP, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.HEX, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.HRO, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.HXC, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.ICN, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.IFC, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.IND, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.KARM, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.KKC, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.KRN, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.KUN, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.LEAF, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.LGBT, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.LOT, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.LTB, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.LYC, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.MAX, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DGC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MCR, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MEOW, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.MIM, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MINT, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MMC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MRS, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MRY, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MTC, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.MYR, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.MZC, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.NDL, new String[]{ VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.NKA, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.NOTE, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.NYAN, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.OIL, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.ORG, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.ORO, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.P$, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.PAND, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.PANDA, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC, VirtualCurrency.PAND });
		CURRENCY_PAIRS.put(VirtualCurrency.PCN, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.PENG, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.PHI, new String[]{ VirtualCurrency.BTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.PIC, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.PMC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.PND, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.POD, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.POT, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.PT, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.PTC, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.PWNY, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.PXL, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DGC, VirtualCurrency.DOGE, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.Q2C, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.QB, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.QRK, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.RAD, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.RAIN, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.RBBT, new String[]{ VirtualCurrency.FOX, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.RDD, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.RED, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.RON, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.RSC, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.RUBY, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.SAT, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.SBX, new String[]{ VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.SCO, new String[]{ VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.SOCHI, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.SPA, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.STL, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.STY, new String[]{ VirtualCurrency.BTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.SUN, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.SXC, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.SYN, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.TAK, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.TEA, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.TEL, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.TES, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.TFC, new String[]{ VirtualCurrency.BTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.THOR, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.TIPS, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.TOP, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.TRL, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.TTC, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.UFO, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.UNC, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.UNI, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.UNO, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.USDE, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.UTC, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.VDC, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.VGC, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.VLT, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.VMP, new String[]{ VirtualCurrency.BTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.VOLT, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.VTC, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.XIV, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.XXL, new String[]{ VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.YANG, new String[]{ VirtualCurrency.BTC, VirtualCurrency.DOGE, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.YIN, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.ZEU, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC, VirtualCurrency.POT });
		CURRENCY_PAIRS.put(VirtualCurrency.ZMB, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.ZTC, new String[]{ VirtualCurrency.BTC });
	}
	
	public CryptoRush() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public int getCautionResId() {
		return R.string.market_caution_much_data;
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject pairJsonObject = jsonObject.getJSONObject(checkerInfo.getCurrencyBase()+"/"+checkerInfo.getCurrencyCounter());
		
		ticker.bid = pairJsonObject.getDouble("current_bid");
		ticker.ask = pairJsonObject.getDouble("current_ask");
		ticker.vol = pairJsonObject.getDouble("volume_base_24h");
		ticker.high = pairJsonObject.getDouble("highest_24h");
		ticker.low = pairJsonObject.getDouble("lowest_24h");
		ticker.last = pairJsonObject.getDouble("last_trade");
	}
	
	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl() {
		return URL_CURRENCY_PAIRS;
	}
	
	@Override
	protected void parseCurrencyPairsFromJsonObject(JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray pairNames = jsonObject.names();
		
		for(int i=0; i<pairNames.length(); ++i) {
			String pairId = pairNames.getString(i);
			if(pairId==null)
				continue;
			String[] currencies = pairId.split("/");
			if(currencies.length!=2)
				continue;
			
			pairs.add(new CurrencyPairInfo(currencies[0], currencies[1], pairId));
		}
	}
}