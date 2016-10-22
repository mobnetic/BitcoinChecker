package com.mobnetic.coinguardian.model;

import java.util.HashMap;

public abstract class FuturesMarket extends Market {
	
	public final int[] contractTypes;
			
	public FuturesMarket(String name, String ttsName, HashMap<String, CharSequence[]> currencyPairs, int[] contractTypes) {
		super(name, ttsName, currencyPairs);
		this.contractTypes = contractTypes;
	}

	@Override
	public final String getUrl(int requestId, CheckerInfo checkerInfo) {
		return getUrl(requestId, checkerInfo, checkerInfo.getContractType());
	}
	
	protected abstract String getUrl(int requestId, CheckerInfo checkerInfo, int contractType);
	
}
