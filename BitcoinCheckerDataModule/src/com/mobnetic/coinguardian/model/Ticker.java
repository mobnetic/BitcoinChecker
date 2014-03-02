package com.mobnetic.coinguardian.model;


public class Ticker {

	public double bid;
	public double ask;
	public double vol;
	public double high;
	public double low;
	public double last;
	public long timestamp;
	
	public final static int NO_DATA = -1;
	
	public Ticker() {
		bid = NO_DATA;
		ask = NO_DATA;
		vol = NO_DATA;
		high = NO_DATA;
		low = NO_DATA;
		last = NO_DATA;
		timestamp = NO_DATA;
	}
}
