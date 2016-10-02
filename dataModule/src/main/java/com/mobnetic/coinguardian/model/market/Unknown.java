package com.mobnetic.coinguardian.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.currency.VirtualCurrency;
import com.mobnetic.coinguardiandatamodule.R;

public class Unknown extends Market {

	private final static String NAME = "UNKNOWN";
	private final static String TTS_NAME = NAME;
	private final static String URL = "";
	private final static HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap<String, CharSequence[]>();
	static {
		CURRENCY_PAIRS.put(VirtualCurrency.BTC, new String[]{ VirtualCurrency.BTC });
	}
	
	public Unknown() {
		super(NAME, TTS_NAME, CURRENCY_PAIRS);
	}
	
	@Override
	public int getCautionResId() {
		return R.string.market_caution_unknown;
	}
	
	@Override
	public String getUrl(int requestId, CheckerInfo checkerInfo) {
		return URL;
	}
}
