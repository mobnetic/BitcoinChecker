package com.aneonex.bitcoinchecker.datamodule.model

object Futures {
    const val CONTRACT_TYPE_WEEKLY = 0
    const val CONTRACT_TYPE_BIWEEKLY = 1
//    const val CONTRACT_TYPE_MONTHLY = 2
//    const val CONTRACT_TYPE_BIMONTHLY = 3
    const val CONTRACT_TYPE_QUARTERLY = 4
    const val CONTRACT_TYPE_BIQUARTERLY = 5

    private val CONTRACT_TYPE_SHORT_NAMES = arrayOf(
            "1W",
            "2W",
            "1M",
            "2M",
            "3M",
            "6M"
    )

    fun getContractTypeShortName(contractType: Int): String? {
        return if (contractType >= 0 && contractType < CONTRACT_TYPE_SHORT_NAMES.size) {
            CONTRACT_TYPE_SHORT_NAMES[contractType]
        } else null
    }
}