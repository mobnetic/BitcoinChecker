package com.mobnetic.coinguardian.util;

import java.util.ArrayList;

import com.mobnetic.coinguardian.config.MarketsConfig;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.market.Unknown;

public class MarketsConfigUtils {

	private final static Market UNKNOWN = new Unknown();
	
	public static Market getMarketById(int id) {
		synchronized (MarketsConfig.MARKETS) {
			if(id>=0 && id<MarketsConfig.MARKETS.size()) {
				return new ArrayList<Market>(MarketsConfig.MARKETS.values()).get(id);
			}
		}
		return UNKNOWN;
	}
	
	public static Market getMarketByKey(String key) {
		synchronized (MarketsConfig.MARKETS) {
			if(MarketsConfig.MARKETS.containsKey(key))
				return MarketsConfig.MARKETS.get(key);
		}
		return UNKNOWN;
	}
	
	public static int getMarketIdByKey(String key) {
		int i=0;
		for(Market market : MarketsConfig.MARKETS.values()){
			if(market.key.equals(key))
				return i;
			++i;
		}
		
		return 0;
	}
}
