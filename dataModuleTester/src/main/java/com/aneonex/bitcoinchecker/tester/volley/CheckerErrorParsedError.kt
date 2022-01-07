package com.aneonex.bitcoinchecker.tester.volley

import com.android.volley.ParseError

class CheckerErrorParsedError(val errorMsg: String?) : ParseError() {

    companion object {
        private const val serialVersionUID = -8541129282633613311L
    }
}