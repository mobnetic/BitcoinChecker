package com.mobnetic.coinguardiandatamodule.tester.volley;

import com.android.volley.ParseError;

public class CheckerErrorParsedError extends ParseError {

	private final String rawResponse;
	private final String errorMsg;
	
	private static final long serialVersionUID = -8541129282633613311L;
	
	public CheckerErrorParsedError(String rawResponse, String errorMsg) {
		super();
		this.rawResponse = rawResponse;
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	
	public String getRawResponse() {
		return rawResponse;
	}
}
