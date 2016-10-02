package com.mobnetic.coinguardiandatamodule.tester.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import android.content.Context;
import android.text.Html;
import android.text.SpannableStringBuilder;

import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.mobnetic.coinguardiandatamodule.tester.R;

public class CheckErrorsUtils {
	
	private final static int RAW_RESPONSE_CHARS_LIMIT = 5000;
	
	public final static String parseVolleyErrorMsg(Context context, VolleyError error) {
		if(error instanceof NetworkError)
			return context.getString(R.string.check_error_network);
		else if(error instanceof TimeoutError)
			return context.getString(R.string.check_error_timeout);
		else if(error instanceof ServerError)
			return context.getString(R.string.check_error_server);
		else if(error instanceof ParseError)
			return context.getString(R.string.check_error_parse);
		else
			return context.getString(R.string.check_error_unknown);
	}
	
	public final static String formatError(Context context, String errorMsg) {
		return context.getString(R.string.check_error_generic_prefix, errorMsg!=null ? errorMsg : "UNKNOWN");
	}
	
	private static String formatMapToHtmlString(Map<String, String> headers) {
		String output = "";
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			output += String.format("<b>%1$s</b> = %2$s<br\\>", entry.getKey(), entry.getValue());
		}
		return output;
	}
	
	public static SpannableStringBuilder formatResponseDebug(Context context, SpannableStringBuilder ssb, String url, Map<String, String> requestHeaders, NetworkResponse networkResponse, String rawResponse, Exception exception) {
		if(url!=null){
			ssb.append("\n\n");
			ssb.append(context.getString(R.string.ticker_raw_url, url));
		}
		
		if(requestHeaders!=null){
			ssb.append("\n\n");
			ssb.append(Html.fromHtml(context.getString(R.string.ticker_raw_request_headers)+"<br\\><small>"+CheckErrorsUtils.formatMapToHtmlString(requestHeaders)+"</small>"));
		}
		
		if(networkResponse!=null){
			ssb.append("\n\n");
			ssb.append(context.getString(R.string.ticker_raw_response_code, String.valueOf(networkResponse.statusCode)));
			ssb.append("\n\n");
			ssb.append(Html.fromHtml(context.getString(R.string.ticker_raw_response_headers)+"<br\\><small>"+CheckErrorsUtils.formatMapToHtmlString(networkResponse.headers)+"</small>"));
		}
		if(rawResponse!=null){
			ssb.append("\n\n");
			String limitedRawResponse = rawResponse; 
			if(rawResponse.length() > RAW_RESPONSE_CHARS_LIMIT) {
				limitedRawResponse = rawResponse.substring(0, RAW_RESPONSE_CHARS_LIMIT)+"...";
			}
			ssb.append(Html.fromHtml(context.getString(R.string.ticker_raw_response)+"<br\\><small>"+limitedRawResponse+"</small>"));
		}
		if(exception!=null){
			ssb.append("\n\n");
			ssb.append(Html.fromHtml(context.getString(R.string.ticker_raw_stacktrace)+"<br\\><small>"+printException(exception)+"</small>"));
		}
		
		return ssb;
	}
	
	private static String printException(Exception e) {
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}
}
