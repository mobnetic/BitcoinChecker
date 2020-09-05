package com.aneonex.bitcoinchecker.tester.volley;

import com.android.volley.VolleyError;

public class UnknownVolleyError extends VolleyError {

	private static final long serialVersionUID = -8541129282633613311L;
	
	public UnknownVolleyError(Throwable cause) {
		super(cause);
	}
}
