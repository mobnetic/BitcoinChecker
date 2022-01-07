package com.aneonex.bitcoinchecker.tester.volley

import com.android.volley.VolleyError

class UnknownVolleyError(cause: Throwable?) : VolleyError(cause) {
    companion object {
        private const val serialVersionUID = -8541129282633613311L
    }
}