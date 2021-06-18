package com.aneonex.bitcoinchecker.datamodule.util

import org.json.JSONArray
import org.json.JSONObject


fun JSONArray.forEachString(function: (item: String) -> Unit) {
    for(i in 0 until this.length()) {
        function(this.getString(i))
    }
}

fun JSONArray.forEachJSONObject(function: (item: JSONObject) -> Unit) {
    for(i in 0 until this.length()) {
        function(this.getJSONObject(i))
    }
}

fun JSONArray.forEachJSONArray(function: (item: JSONArray) -> Unit) {
    for(i in 0 until this.length()) {
        function(this.getJSONArray(i))
    }
}