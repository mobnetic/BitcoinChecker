package com.mobnetic.coinguardian.util;

import java.text.DecimalFormat;
import java.util.Date;

import android.content.Context;
import android.text.format.DateFormat;
import android.text.format.DateUtils;

import com.mobnetic.coinguardian.model.CurrencySubunit;

public class FormatUtilsBase {

	// ====================
	// Double formatting
	// ====================
	private final static DecimalFormat FORMAT_TWO_DECIMAL = new DecimalFormat("0.00");
	private final static DecimalFormat FORMAT_THREE_SIGNIFICANT_AT_MOST = new DecimalFormat("@##");
	private final static DecimalFormat FORMAT_EIGHT_SIGNIFICANT_AT_MOST = new DecimalFormat("@#######");
	
	// ====================
	// Format methods
	// ====================
	public static String formatDouble(double value, boolean isPrice) {
		return formatDouble(value<1 ? FORMAT_THREE_SIGNIFICANT_AT_MOST : FORMAT_TWO_DECIMAL, value);
	}
	
	public static String formatDoubleWithThreeMax(double value) {
		return formatDouble(FORMAT_THREE_SIGNIFICANT_AT_MOST, value);
	}
	
	protected static String formatDoubleWithEightMax(double value) {
		return formatDouble(FORMAT_EIGHT_SIGNIFICANT_AT_MOST, value);
	}

	protected final static String formatDouble(DecimalFormat decimalFormat, double value) {
		synchronized (decimalFormat) {
			try {
				return decimalFormat.format(value);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return String.valueOf(value);
		}
	}
	
	// ====================
	// Price formatting
	// ====================
	public static String formatPriceWithCurrency(double price, CurrencySubunit subunitDst) {
		return formatPriceWithCurrency(price, subunitDst, true);
	}
	
	protected static String formatPriceWithCurrency(double price, CurrencySubunit subunitDst, boolean showCurrencyDst) {
		String priceString = formatPriceValueForSubunit(price, subunitDst, false, false);
		if(showCurrencyDst) {
			priceString = formatPriceWithCurrency(priceString, subunitDst.name);
		}
		
		return priceString;
	}
	
	public static String formatPriceWithCurrency(double value, String currency) {
		return formatPriceWithCurrency(formatDouble(value, true), currency);
	}
	
	public static String formatPriceWithCurrency(String priceString, String currency) {
		return priceString+" "+CurrencyUtils.getCurrencySymbol(currency);
	}
	
	public static String formatPriceValueForSubunit(double price, CurrencySubunit subunitDst, boolean forceInteger, boolean skipNoSignificantDecimal) {
		price *= subunitDst.subunitToUnit;
		if(!subunitDst.allowDecimal || forceInteger)
			return String.valueOf((long)(price+0.5f));
		else if(skipNoSignificantDecimal)
			return formatDoubleWithEightMax(price);
		else
			return formatDouble(price, true);
	}
	
	// ====================
	// Date && Time formatting
	// ====================
	public static String formatSameDayTimeOrDate(Context context, long time) {
		if (DateUtils.isToday(time)) {
	        return DateFormat.getTimeFormat(context).format(new Date(time));
	    } else {
	    	return DateFormat.getDateFormat(context).format(new Date(time));
	    }
	}
	
	public static String formatDateTime(Context context, long time) {
		final Date date = new Date(time);
		return DateFormat.getTimeFormat(context).format(date)+", "+DateFormat.getDateFormat(context).format(date);
	}
}
