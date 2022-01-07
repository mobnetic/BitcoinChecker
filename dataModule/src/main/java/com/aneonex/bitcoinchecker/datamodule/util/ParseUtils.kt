package com.aneonex.bitcoinchecker.datamodule.util

import org.json.JSONException
import org.json.JSONObject

object ParseUtils {
    @Throws(NumberFormatException::class, JSONException::class)
    fun getDoubleFromString(jsonObject: JSONObject, name: String): Double {
        return jsonObject.getString(name).toDouble()
    }
}