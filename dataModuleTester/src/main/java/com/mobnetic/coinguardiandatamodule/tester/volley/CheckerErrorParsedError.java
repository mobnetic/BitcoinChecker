package com.mobnetic.coinguardiandatamodule.tester.volley;

import com.android.volley.ParseError;

public class CheckerErrorParsedError extends ParseError {

	private final String errorMsg;
	
	private static final long serialVersionUID = -8541129282633613311L;
	
	public CheckerErrorParsedError(String errorMsg) {
		super();
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
}
