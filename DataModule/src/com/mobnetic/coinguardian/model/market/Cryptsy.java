package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class Cryptsy extends Market {

	private final static String NAME = "Cryptsy";
	private final static String TTS_NAME = NAME;
	private final static String URL = "http://pubapi.cryptsy.com/api.php?method=singlemarketdata&marketid=%1$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	private final static HashMap<String, Integer> CURRENCY_PAIRS_IDS = new HashMap<String, Integer>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency._42, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.ALF, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.AMC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.ANC, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.ARG, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.ASC, new String[]{ VirtualCurrency.XPM, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.AUR, new String[]{ VirtualCurrency.BTC, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.BCX, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.BEN, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.BET, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.BQC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.BTB, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.BTE, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.BTG, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.BUK, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CACH, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CAP, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CASH, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CAT, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CGB, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CLR, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CMC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CNC, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.COL, new String[]{ VirtualCurrency.XPM, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CPR, new String[]{ VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CRC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.CSC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.DBL, new String[]{ VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.DEM, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.DGC, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.DMD, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.DOGE, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.DRK, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.DVC, new String[]{ VirtualCurrency.XPM, VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.EAC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.ELC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.ELP, new String[]{ VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.EMD, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.EZC, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.FFC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.FLO, new String[]{ VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.FRC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.FRK, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.FST, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.FTC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.GDC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.GLC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.GLD, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.GLX, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.GME, new String[]{ VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.HBN, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.IFC, new String[]{ VirtualCurrency.XPM, VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.IXC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.JKC, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.KGC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.LEAF, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.LK7, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.LKY, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.LOT, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.LTC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MAX, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MEC, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MEM, new String[]{ VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MEOW, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MINT, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MNC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MOON, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.MST, new String[]{ VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.NAN, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.NBL, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.NEC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.NET, new String[]{ VirtualCurrency.XPM, VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.NMC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.NRB, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.NVC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.NXT, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.ORB, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.OSC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.PHS, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.POINTS, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.PPC, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.PTS, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.PXC, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.PYC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.QRK, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.RED, new String[]{ VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.RPC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.RYC, new String[]{ VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.SBC, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.SMC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.SPT, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.SRC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.STR, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.SXC, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.TAG, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.TEK, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.TGC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.TIPS, new String[]{ VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.TIX, new String[]{ VirtualCurrency.XPM, VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.TRC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.UNO, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.VTC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.WDC, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.XJO, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.XNC, new String[]{ VirtualCurrency.LTC });
		CURRENCY_PAIRS.put(VirtualCurrency.XPM, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.YAC, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.YBC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.ZCC, new String[]{ VirtualCurrency.BTC });
		CURRENCY_PAIRS.put(VirtualCurrency.ZET, new String[]{ VirtualCurrency.LTC, VirtualCurrency.BTC });
		CURRENCY_PAIRS_IDS.put("42_BTC", 141);
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
		CURRENCY_PAIRS_IDS.put("MOON_BTC", 146);
		CURRENCY_PAIRS_IDS.put("MOON_LTC", 145);
		CURRENCY_PAIRS_IDS.put("MST_LTC", 62);
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
		CURRENCY_PAIRS_IDS.put("RED_LTC", 87);
		CURRENCY_PAIRS_IDS.put("RPC_BTC", 143);
		CURRENCY_PAIRS_IDS.put("RYC_LTC", 37);
		CURRENCY_PAIRS_IDS.put("SBC_BTC", 51);
		CURRENCY_PAIRS_IDS.put("SBC_LTC", 128);
		CURRENCY_PAIRS_IDS.put("SMC_BTC", 158);
		CURRENCY_PAIRS_IDS.put("SPT_BTC", 81);
		CURRENCY_PAIRS_IDS.put("SRC_BTC", 88);
		CURRENCY_PAIRS_IDS.put("STR_BTC", 83);
		CURRENCY_PAIRS_IDS.put("SXC_BTC", 153);
		CURRENCY_PAIRS_IDS.put("SXC_LTC", 98);
		CURRENCY_PAIRS_IDS.put("TAG_BTC", 117);
		CURRENCY_PAIRS_IDS.put("TEK_BTC", 114);
		CURRENCY_PAIRS_IDS.put("TGC_BTC", 130);
		CURRENCY_PAIRS_IDS.put("TIPS_LTC", 147);
		CURRENCY_PAIRS_IDS.put("TIX_LTC", 107);
		CURRENCY_PAIRS_IDS.put("TIX_XPM", 103);
		CURRENCY_PAIRS_IDS.put("TRC_BTC", 27);
		CURRENCY_PAIRS_IDS.put("UNO_BTC", 133);
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
		CURRENCY_PAIRS_IDS.put("ZET_BTC", 85);
		CURRENCY_PAIRS_IDS.put("ZET_LTC", 127);

	}
	
	public Cryptsy() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		final String pairString = String.format("%1$s_%2$s", checkerInfo.getCurrencyBase(), checkerInfo.getCurrencyCounter());
		if(CURRENCY_PAIRS_IDS.containsKey(pairString))
			return String.format(URL, String.valueOf(CURRENCY_PAIRS_IDS.get(pairString)));	
		return URL;
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		final JSONObject returnObject = jsonObject.getJSONObject("return");
		final JSONObject marketsObject = returnObject.getJSONObject("markets");
		final JSONObject pairObject = marketsObject.getJSONObject(marketsObject.names().getString(0));
		
		ticker.bid = getFirstPriceFromOrdersArray(pairObject.optJSONArray("buyorders"));
		ticker.ask = getFirstPriceFromOrdersArray(pairObject.optJSONArray("sellorders"));
		ticker.vol = pairObject.getDouble("volume");
//		ticker.high;
//		ticker.low;
		ticker.last = pairObject.getDouble("lasttradeprice");
//		ticker.timestamp;
	}
	
	private double getFirstPriceFromOrdersArray(JSONArray ordersArray) {
		try {
			if(ordersArray!=null && ordersArray.length()>0) {
				final JSONObject jsonObject = ordersArray.optJSONObject(0);
				if(jsonObject!=null)
					return jsonObject.getDouble("price");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Ticker.NO_DATA;
	}
}
