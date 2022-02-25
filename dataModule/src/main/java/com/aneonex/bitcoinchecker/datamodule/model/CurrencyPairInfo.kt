package com.aneonex.bitcoinchecker.datamodule.model

open class CurrencyPairInfo(
    val currencyBase: String,
    val currencyCounter: String,
    val currencyPairId: String?,
    val contractType: FuturesContractType = FuturesContractType.NONE
) : Comparable<CurrencyPairInfo> {

    @Suppress("unused") // Used by Gson
    private constructor() : this("", "", null)

    @Throws(NullPointerException::class)
    override fun compareTo(other: CurrencyPairInfo): Int {
        var compBase = currencyBase.compareTo(other.currencyBase, ignoreCase = true)
        if (compBase != 0) return compBase

        compBase = currencyCounter.compareTo(
            other.currencyCounter,
            ignoreCase = true
        )
        if (compBase != 0) return compBase

        return contractType.compareTo(other.contractType)
    }

    override fun toString(): String {
        fun tryGetContactName(): String {
            val resultName = FuturesContractType.getShortName(contractType)
            return if(resultName == null) "" else ":$resultName"
        }

        return currencyPairId ?:
        "$currencyBase:$currencyCounter" + tryGetContactName()
    }
}