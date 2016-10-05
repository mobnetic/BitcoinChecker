package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.CurrencyPairInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;

public class Cryptsy extends Market {

	private final static String NAME = "Cryptsy";
	private final static String TTS_NAME = NAME;
	private final static String URL = "https://cryptsy.com/api/v2/markets/%1$s";
	private final static String URL_CURRENCY_PAIRS = "https://cryptsy.com/api/v2/markets";
	private final static HashMap<String, Integer> CURRENCY_PAIRS_IDS = new HashMap<String, Integer>(151);
	static {
		// Kept for backward compatibility
		CURRENCY_PAIRS_IDS.put("42_BTC", 141);
		CURRENCY_PAIRS_IDS.put("ADT_LTC", 94);
		CURRENCY_PAIRS_IDS.put("ADT_XPM", 113);
		CURRENCY_PAIRS_IDS.put("ALF_BTC", 57);
		CURRENCY_PAIRS_IDS.put("AMC_BTC", 43);
		CURRENCY_PAIRS_IDS.put("ANC_BTC", 66);
		CURRENCY_PAIRS_IDS.put("ANC_LTC", 121);
		CURRENCY_PAIRS_IDS.put("ARG_BTC", 48);
		CURRENCY_PAIRS_IDS.put("ASC_LTC", 111);
		CURRENCY_PAIRS_IDS.put("ASC_XPM", 112);
		CURRENCY_PAIRS_IDS.put("AUR_BTC", 160);
		CURRENCY_PAIRS_IDS.put("AUR_LTC", 161);
		CURRENCY_PAIRS_IDS.put("BCX_BTC", 142);
		CURRENCY_PAIRS_IDS.put("BEN_BTC", 157);
		CURRENCY_PAIRS_IDS.put("BET_BTC", 129);
		CURRENCY_PAIRS_IDS.put("BQC_BTC", 10);
		CURRENCY_PAIRS_IDS.put("BTB_BTC", 23);
		CURRENCY_PAIRS_IDS.put("BTE_BTC", 49);
		CURRENCY_PAIRS_IDS.put("BTG_BTC", 50);
		CURRENCY_PAIRS_IDS.put("BUK_BTC", 102);
		CURRENCY_PAIRS_IDS.put("CACH_BTC", 154);
		CURRENCY_PAIRS_IDS.put("CAP_BTC", 53);
		CURRENCY_PAIRS_IDS.put("CASH_BTC", 150);
		CURRENCY_PAIRS_IDS.put("CAT_BTC", 136);
		CURRENCY_PAIRS_IDS.put("CGB_BTC", 70);
		CURRENCY_PAIRS_IDS.put("CGB_LTC", 123);
		CURRENCY_PAIRS_IDS.put("CLR_BTC", 95);
		CURRENCY_PAIRS_IDS.put("CMC_BTC", 74);
		CURRENCY_PAIRS_IDS.put("CNC_BTC", 8);
		CURRENCY_PAIRS_IDS.put("CNC_LTC", 17);
		CURRENCY_PAIRS_IDS.put("COL_LTC", 109);
		CURRENCY_PAIRS_IDS.put("COL_XPM", 110);
		CURRENCY_PAIRS_IDS.put("CPR_LTC", 91);
		CURRENCY_PAIRS_IDS.put("CRC_BTC", 58);
		CURRENCY_PAIRS_IDS.put("CSC_BTC", 68);
		CURRENCY_PAIRS_IDS.put("DBL_LTC", 46);
		CURRENCY_PAIRS_IDS.put("DEM_BTC", 131);
		CURRENCY_PAIRS_IDS.put("DGB_BTC", 167);
		CURRENCY_PAIRS_IDS.put("DGC_BTC", 26);
		CURRENCY_PAIRS_IDS.put("DGC_LTC", 96);
		CURRENCY_PAIRS_IDS.put("DMD_BTC", 72);
		CURRENCY_PAIRS_IDS.put("DOGE_BTC", 132);
		CURRENCY_PAIRS_IDS.put("DOGE_LTC", 135);
		CURRENCY_PAIRS_IDS.put("DRK_BTC", 155);
		CURRENCY_PAIRS_IDS.put("DVC_BTC", 40);
		CURRENCY_PAIRS_IDS.put("DVC_LTC", 52);
		CURRENCY_PAIRS_IDS.put("DVC_XPM", 122);
		CURRENCY_PAIRS_IDS.put("EAC_BTC", 139);
		CURRENCY_PAIRS_IDS.put("ELC_BTC", 12);
		CURRENCY_PAIRS_IDS.put("ELP_LTC", 93);
		CURRENCY_PAIRS_IDS.put("EMD_BTC", 69);
		CURRENCY_PAIRS_IDS.put("EZC_BTC", 47);
		CURRENCY_PAIRS_IDS.put("EZC_LTC", 55);
		CURRENCY_PAIRS_IDS.put("FFC_BTC", 138);
		CURRENCY_PAIRS_IDS.put("FLAP_BTC", 165);
		CURRENCY_PAIRS_IDS.put("FLO_LTC", 61);
		CURRENCY_PAIRS_IDS.put("FRC_BTC", 39);
		CURRENCY_PAIRS_IDS.put("FRK_BTC", 33);
		CURRENCY_PAIRS_IDS.put("FST_BTC", 44);
		CURRENCY_PAIRS_IDS.put("FST_LTC", 124);
		CURRENCY_PAIRS_IDS.put("FTC_BTC", 5);
		CURRENCY_PAIRS_IDS.put("GDC_BTC", 82);
		CURRENCY_PAIRS_IDS.put("GLC_BTC", 76);
		CURRENCY_PAIRS_IDS.put("GLD_BTC", 30);
		CURRENCY_PAIRS_IDS.put("GLD_LTC", 36);
		CURRENCY_PAIRS_IDS.put("GLX_BTC", 78);
		CURRENCY_PAIRS_IDS.put("GME_LTC", 84);
		CURRENCY_PAIRS_IDS.put("HBN_BTC", 80);
		CURRENCY_PAIRS_IDS.put("IFC_BTC", 59);
		CURRENCY_PAIRS_IDS.put("IFC_LTC", 60);
		CURRENCY_PAIRS_IDS.put("IFC_XPM", 105);
		CURRENCY_PAIRS_IDS.put("IXC_BTC", 38);
		CURRENCY_PAIRS_IDS.put("JKC_BTC", 25);
		CURRENCY_PAIRS_IDS.put("JKC_LTC", 35);
		CURRENCY_PAIRS_IDS.put("KGC_BTC", 65);
		CURRENCY_PAIRS_IDS.put("LEAF_BTC", 148);
		CURRENCY_PAIRS_IDS.put("LK7_BTC", 116);
		CURRENCY_PAIRS_IDS.put("LKY_BTC", 34);
		CURRENCY_PAIRS_IDS.put("LOT_BTC", 137);
		CURRENCY_PAIRS_IDS.put("LTC_BTC", 3);
		CURRENCY_PAIRS_IDS.put("MAX_BTC", 152);
		CURRENCY_PAIRS_IDS.put("MEC_BTC", 45);
		CURRENCY_PAIRS_IDS.put("MEC_LTC", 100);
		CURRENCY_PAIRS_IDS.put("MEM_LTC", 56);
		CURRENCY_PAIRS_IDS.put("MEOW_BTC", 149);
		CURRENCY_PAIRS_IDS.put("MINT_BTC", 156);
		CURRENCY_PAIRS_IDS.put("MNC_BTC", 7);
		CURRENCY_PAIRS_IDS.put("MOON_LTC", 145);
		CURRENCY_PAIRS_IDS.put("MST_LTC", 62);
		CURRENCY_PAIRS_IDS.put("MZC_BTC", 164);
		CURRENCY_PAIRS_IDS.put("NAN_BTC", 64);
		CURRENCY_PAIRS_IDS.put("NBL_BTC", 32);
		CURRENCY_PAIRS_IDS.put("NEC_BTC", 90);
		CURRENCY_PAIRS_IDS.put("NET_BTC", 134);
		CURRENCY_PAIRS_IDS.put("NET_LTC", 108);
		CURRENCY_PAIRS_IDS.put("NET_XPM", 104);
		CURRENCY_PAIRS_IDS.put("NMC_BTC", 29);
		CURRENCY_PAIRS_IDS.put("NRB_BTC", 54);
		CURRENCY_PAIRS_IDS.put("NVC_BTC", 13);
		CURRENCY_PAIRS_IDS.put("NXT_BTC", 159);
		CURRENCY_PAIRS_IDS.put("NXT_LTC", 162);
		CURRENCY_PAIRS_IDS.put("ORB_BTC", 75);
		CURRENCY_PAIRS_IDS.put("OSC_BTC", 144);
		CURRENCY_PAIRS_IDS.put("PHS_BTC", 86);
		CURRENCY_PAIRS_IDS.put("POINTS_BTC", 120);
		CURRENCY_PAIRS_IDS.put("PPC_BTC", 28);
		CURRENCY_PAIRS_IDS.put("PPC_LTC", 125);
		CURRENCY_PAIRS_IDS.put("PTS_BTC", 119);
		CURRENCY_PAIRS_IDS.put("PXC_BTC", 31);
		CURRENCY_PAIRS_IDS.put("PXC_LTC", 101);
		CURRENCY_PAIRS_IDS.put("PYC_BTC", 92);
		CURRENCY_PAIRS_IDS.put("QRK_BTC", 71);
		CURRENCY_PAIRS_IDS.put("QRK_LTC", 126);
		CURRENCY_PAIRS_IDS.put("RDD_BTC", 169);
		CURRENCY_PAIRS_IDS.put("RED_LTC", 87);
		CURRENCY_PAIRS_IDS.put("RPC_BTC", 143);
		CURRENCY_PAIRS_IDS.put("RYC_BTC", 9);
		CURRENCY_PAIRS_IDS.put("RYC_LTC", 37);
		CURRENCY_PAIRS_IDS.put("SAT_BTC", 168);
		CURRENCY_PAIRS_IDS.put("SBC_BTC", 51);
		CURRENCY_PAIRS_IDS.put("SBC_LTC", 128);
		CURRENCY_PAIRS_IDS.put("SMC_BTC", 158);
		CURRENCY_PAIRS_IDS.put("SPT_BTC", 81);
		CURRENCY_PAIRS_IDS.put("SRC_BTC", 88);
		CURRENCY_PAIRS_IDS.put("STR_BTC", 83);
		CURRENCY_PAIRS_IDS.put("SXC_BTC", 153);
		CURRENCY_PAIRS_IDS.put("SXC_LTC", 98);
		CURRENCY_PAIRS_IDS.put("TAG_BTC", 117);
		CURRENCY_PAIRS_IDS.put("TAK_BTC", 166);
		CURRENCY_PAIRS_IDS.put("TEK_BTC", 114);
		CURRENCY_PAIRS_IDS.put("TGC_BTC", 130);
		CURRENCY_PAIRS_IDS.put("TIPS_LTC", 147);
		CURRENCY_PAIRS_IDS.put("TIX_LTC", 107);
		CURRENCY_PAIRS_IDS.put("TIX_XPM", 103);
		CURRENCY_PAIRS_IDS.put("TRC_BTC", 27);
		CURRENCY_PAIRS_IDS.put("UNO_BTC", 133);
		CURRENCY_PAIRS_IDS.put("UTC_BTC", 163);
		CURRENCY_PAIRS_IDS.put("VTC_BTC", 151);
		CURRENCY_PAIRS_IDS.put("WDC_BTC", 14);
		CURRENCY_PAIRS_IDS.put("WDC_LTC", 21);
		CURRENCY_PAIRS_IDS.put("XJO_BTC", 115);
		CURRENCY_PAIRS_IDS.put("XNC_LTC", 67);
		CURRENCY_PAIRS_IDS.put("XPM_BTC", 63);
		CURRENCY_PAIRS_IDS.put("XPM_LTC", 106);
		CURRENCY_PAIRS_IDS.put("YAC_BTC", 11);
		CURRENCY_PAIRS_IDS.put("YAC_LTC", 22);
		CURRENCY_PAIRS_IDS.put("YBC_BTC", 73);
		CURRENCY_PAIRS_IDS.put("ZCC_BTC", 140);
		CURRENCY_PAIRS_IDS.put("ZED_BTC", 170);
		CURRENCY_PAIRS_IDS.put("ZET_BTC", 85);
		CURRENCY_PAIRS_IDS.put("ZET_LTC", 127);
	}
	
	public Cryptsy() {
		super(NAME, TTS_NAME, null);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		if(checkerInfo.getCurrencyPairId()==null) {
			final String pairString = String.format("%1$s_%2$s", checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
			if(CURRENCY_PAIRS_IDS.containsKey(pairString)) {
				return String.format(URL, String.valueOf(CURRENCY_PAIRS_IDS.get(pairString)));
			}
		}
		return String.format(URL, checkerInfo.getCurrencyPairId());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject dataJsonObject = jsonObject.getJSONObject("data");
		final JSONObject daySummaryObject = dataJsonObject.getJSONObject("24hr");
				
		// ticker.vol = daySummaryObject.getDouble("volume"); TODO enable later when there will be any documentation of real data to test on
		ticker.high = daySummaryObject.getDouble("price_high");
		ticker.low = daySummaryObject.getDouble("price_low");
		ticker.last = dataJsonObject.getJSONObject("last_trade").getDouble("price");
	}
	
	@Override
	protected String parseError(int requestId, String responseString, CheckerInfo checkerInfo) throws Exception {
		if(checkerInfo.getCurrencyPairId()==null) {
			return "Perform sync and re-add this Checker";
		}
		return super.parseError(requestId, responseString, checkerInfo);
	}
	
	// ====================
	// Get currency pairs
	// ====================
	@Override
	public String getCurrencyPairsUrl(int requestId) {
		return URL_CURRENCY_PAIRS;
	}
	
	@Override
	protected void parseCurrencyPairsFromJsonObject(int requestId, JSONObject jsonObject, List<CurrencyPairInfo> pairs) throws Exception {
		final JSONArray dataJsonArray = jsonObject.getJSONArray("data");
		for(int i=0; i<dataJsonArray.length(); ++i) {
			final JSONObject marketObject = dataJsonArray.getJSONObject(i);
			final String currencyPair = marketObject.getString("label");
			if(currencyPair==null) {
				continue;
			}
			final String[] currencies = currencyPair.split("/");
			if(currencies.length!=2 || currencies[0] == null || currencies[1] == null)
				continue;
			
			pairs.add(new CurrencyPairInfo(
					currencies[0],
					currencies[1],
					marketObject.getString("id")
				));
		}
	}
}
