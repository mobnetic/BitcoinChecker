package com.aneonex.bitcoinchecker.datamodule.util

import android.content.Context
import android.text.format.DateFormat
import android.text.format.DateUtils
import com.aneonex.bitcoinchecker.datamodule.model.CurrencySubunit
import java.text.DecimalFormat
import java.util.*

object FormatUtilsBase {
    // ====================
    // Double formatting
    // ====================
    private val FORMAT_NO_DECIMAL = DecimalFormat("#,###")
    private val FORMAT_TWO_DECIMAL = DecimalFormat("#,###.00")
    private val FORMAT_FOUR_SIGNIFICANT_AT_MOST = DecimalFormat("@###")
    private val FORMAT_EIGHT_SIGNIFICANT_AT_MOST = DecimalFormat("@#######")

    // ====================
    // Format methods
    // ====================
    fun formatDouble(value: Double/*, isPrice: Boolean*/): String {
        val decimalFormat: DecimalFormat = when {
            value < 10 -> FORMAT_FOUR_SIGNIFICANT_AT_MOST
            value < 10000 -> FORMAT_TWO_DECIMAL
            else -> FORMAT_NO_DECIMAL
        }

        return formatDouble(decimalFormat, value)
    }

    @Suppress("unused")
    fun formatDoubleWithEightMax(value: Double): String {
        return formatDouble(FORMAT_EIGHT_SIGNIFICANT_AT_MOST, value)
    }

    @Suppress("unused")
    fun formatDoubleWithFourMax(value: Double): String {
        return formatDouble(FORMAT_FOUR_SIGNIFICANT_AT_MOST, value)
    }

    private fun formatDouble(decimalFormat: DecimalFormat, value: Double): String {
        synchronized(decimalFormat) {
            try {
                return decimalFormat.format(value)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return value.toString()
        }
    }

    // ====================
    // Price formatting
    // ====================
    fun formatPriceWithCurrency(price: Double, subunitDst: CurrencySubunit): String {
        return formatPriceWithCurrency(price, subunitDst, true)
    }

    fun formatPriceWithCurrency(price: Double, subunitDst: CurrencySubunit, showCurrencyDst: Boolean): String {
        var priceString = formatPriceValueForSubunit(price, subunitDst, forceInteger = false, skipNoSignificantDecimal = false)
        if (showCurrencyDst) {
            priceString = formatPriceWithCurrency(priceString, subunitDst.name)
        }
        return priceString
    }

    fun formatPriceWithCurrency(value: Double, currency: String): String {
        return formatPriceWithCurrency(formatDouble(value), currency)
    }

    private fun formatPriceWithCurrency(priceString: String, currency: String): String {
        return priceString + " " + CurrencyUtils.getCurrencySymbol(currency)
    }

    fun formatPriceValueForSubunit(price: Double, subunitDst: CurrencySubunit, forceInteger: Boolean, skipNoSignificantDecimal: Boolean): String {
        val calcPrice = price * subunitDst.subunitToUnit.toDouble()
        return if (!subunitDst.allowDecimal || forceInteger) return (calcPrice + 0.5f).toLong().toString()
            else if (skipNoSignificantDecimal) formatDoubleWithEightMax(calcPrice) else formatDouble(calcPrice)
    }

    // ====================
    // Date && Time formatting
    // ====================
    @kotlin.jvm.JvmStatic
    fun formatSameDayTimeOrDate(context: Context?, time: Long): String {
        return if (DateUtils.isToday(time)) {
            DateFormat.getTimeFormat(context).format(Date(time))
        } else {
            DateFormat.getDateFormat(context).format(Date(time))
        }
    }

    fun formatDateTime(context: Context?, time: Long): String {
        val date = Date(time)
        return DateFormat.getTimeFormat(context).format(date) + ", " + DateFormat.getDateFormat(context).format(date)
    }
}