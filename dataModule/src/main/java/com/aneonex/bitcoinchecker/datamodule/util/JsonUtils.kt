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

fun JSONObject.forEachName(function: (name: String, item: JSONObject) -> Unit) {
    val namesJsonArray = this.names()!!
    for (i in 0 until namesJsonArray.length()) {
        val name = namesJsonArray.getString(i)
        val item = this.getJSONObject(name)
        function(name, item)
    }
}
