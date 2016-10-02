package com.mobnetic.coinguardian.model;

public class Futures {

	public final static int CONTRACT_TYPE_WEEKLY = 0;
	public final static int CONTRACT_TYPE_BIWEEKLY = 1;
	public final static int CONTRACT_TYPE_MONTHLY = 2;
	public final static int CONTRACT_TYPE_BIMONTHLY = 3;
	public final static int CONTRACT_TYPE_QUARTERLY = 4;
	
	private final static String[] CONTRACT_TYPE_SHORT_NAMES = new String[] {
		"1W",
		"2W",
		"1M",
		"2M",
		"3M"
	};
	
	public static String getContractTypeShortName(int contractType) {
		if (contractType >=0 && contractType < CONTRACT_TYPE_SHORT_NAMES.length) {
			return CONTRACT_TYPE_SHORT_NAMES[contractType];
		}
		return null;
	}
	
}
