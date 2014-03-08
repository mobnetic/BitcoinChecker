package com.mobnetic.coinguardiandatamodule.tester.util;

import android.content.Context;

import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.mobnetic.coinguardiandatamodule.tester.R;

public class CheckErrorsUtils {
	
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
}
