package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;
import org.json.JSONObject;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;

public class CoinSwap extends Market {

	private final static String NAME = "Coin-Swap";
	private final static String TTS_NAME = "Coin Swap";
	private final static String URL = "https://api.coin-swap.net/market/stats/%2$s/%1$s";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{
				VirtualCurrency._66,
				VirtualCurrency.AC,
				VirtualCurrency.AID,
				VirtualCurrency.ASCE,
				VirtualCurrency.BBC,
				VirtualCurrency.BC,
				VirtualCurrency.BELA,
				VirtualCurrency.BLU,
				VirtualCurrency.BLZ,
				VirtualCurrency.BMY,
				VirtualCurrency.BORG,
				VirtualCurrency.BST,
				VirtualCurrency.BSY,
				VirtualCurrency.CAIx,
				VirtualCurrency.CAP,
				VirtualCurrency.CAPT,
				VirtualCurrency.CKC,
				VirtualCurrency.CLOAK,
				VirtualCurrency.CPC,
				VirtualCurrency.CPN,
				VirtualCurrency.CRD,
				VirtualCurrency.DOGE,
				VirtualCurrency.DRK,
				VirtualCurrency.EBG,
				VirtualCurrency.EMC,
				VirtualCurrency.FRAC,
				VirtualCurrency.FUSE,
				VirtualCurrency.GPC,
				VirtualCurrency.GUN,
				VirtualCurrency.HYP,
				VirtualCurrency.IMP,
				VirtualCurrency.IOC,
				VirtualCurrency.IPC,
				VirtualCurrency.KOOL,
				VirtualCurrency.KTK,
				VirtualCurrency.LAT,
				VirtualCurrency.LIRE,
				VirtualCurrency.LTC,
				VirtualCurrency.LTM,
				VirtualCurrency.MC,
				VirtualCurrency.POT,
				VirtualCurrency.PPL,
				VirtualCurrency.PRO,
				VirtualCurrency.PURE,
				VirtualCurrency.RIPO,
				VirtualCurrency.RSN,
				VirtualCurrency.SDC,
				VirtualCurrency.SHC,
				VirtualCurrency.SHIBE,
				VirtualCurrency.STL,
				VirtualCurrency.VIA,
				VirtualCurrency.VMC,
				VirtualCurrency.XGR,
				VirtualCurrency.YC
			});
		CURRENCY_PAIRS.put(VirtualCurrency.DOGE, new String[]{
				VirtualCurrency.ACT,
				VirtualCurrency.BLZ,
				VirtualCurrency.BORG,
				VirtualCurrency.BUNNY,
				VirtualCurrency.CCX,
				VirtualCurrency.DOJE,
				VirtualCurrency.HBC,
				VirtualCurrency.HTML,
				VirtualCurrency.LTC
				
			});
	}
	
	public CoinSwap() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}

	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return String.format(URL, checkerInfo.getCurrencyBaseLowerCase(), checkerInfo.getCurrencyCounterLowerCase());
	}
	
	@Override
	protected void parseTickerFromJsonObject(int requestId, JSONObject jsonObject, Ticker ticker, CheckerInfo checkerInfo) throws Exception {
		ticker.bid = jsonObject.getDouble("bid");
		ticker.ask = jsonObject.getDouble("ask");
		ticker.vol = jsonObject.getDouble("dayvolume");
		ticker.high = jsonObject.getDouble("dayhigh");
		ticker.low = jsonObject.getDouble("daylow");
		ticker.last = jsonObject.getDouble("lastprice");
	}
}
