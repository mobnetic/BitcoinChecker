package com.aneonex.bitcoinchecker.datamodule.util

import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairsListWithDate
import com.aneonex.bitcoinchecker.datamodule.model.currency.CurrencyPairsMap

class CurrencyPairsMapHelper(currencyPairsListWithDate: CurrencyPairsListWithDate?) {
    val date: Long
    val currencyPairs: CurrencyPairsMap = CurrencyPairsMap()
    private val currencyPairsIds: HashMap<String, String?> = HashMap()
    var pairsCount = 0
    fun getCurrencyPairId(currencyBase: String?, currencyCounter: String?): String? {
        return currencyPairsIds[createCurrencyPairKey(currencyBase, currencyCounter)]
    }

    private fun createCurrencyPairKey(currencyBase: String?, currencyCounter: String?): String {
        return String.format("%1\$s_%2\$s", currencyBase, currencyCounter)
    }

    init {

        if (currencyPairsListWithDate == null) {
            date = 0
        }
        else {
            date = currencyPairsListWithDate.date

            if(currencyPairsListWithDate.pairs != null) {
                val sortedPairs = currencyPairsListWithDate.pairs!!

                pairsCount = sortedPairs.size

                // Calculate size for every currency group
                val currencyGroupSizes = HashMap<String, Int>()
                for (currencyPairInfo in sortedPairs) {
                    var currentCurrencyGroupSize = currencyGroupSizes[currencyPairInfo.currencyBase]
                    if (currentCurrencyGroupSize == null) {
                        currentCurrencyGroupSize = 1
                    } else {
                        ++currentCurrencyGroupSize
                    }
                    currencyGroupSizes[currencyPairInfo.currencyBase] = currentCurrencyGroupSize
                }

                var currentGroupPositionToInsert = 0
                for (currencyPairInfo in sortedPairs) {
                    var currencyGroup = currencyPairs[currencyPairInfo.currencyBase]
                    if (currencyGroup == null) {
                        // Initialize array with pre-calculated size
                        currencyGroup = Array(currencyGroupSizes[currencyPairInfo.currencyBase] ?: 0) { String() }
                        currencyPairs[currencyPairInfo.currencyBase] = currencyGroup
                        currentGroupPositionToInsert = 0
                    } else {
                        ++currentGroupPositionToInsert
                    }
                    currencyGroup[currentGroupPositionToInsert] = currencyPairInfo.currencyCounter
                    if (currencyPairInfo.currencyPairId != null) {
                        val pairKey = createCurrencyPairKey(currencyPairInfo.currencyBase, currencyPairInfo.currencyCounter)
                        currencyPairsIds[pairKey] = currencyPairInfo.currencyPairId
                    }
                }
            }
        }
    }
}