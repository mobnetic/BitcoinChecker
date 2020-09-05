package com.aneonex.bitcoinchecker.datamodule.model.market

import com.aneonex.bitcoinchecker.datamodule.model.CheckerInfo
import com.aneonex.bitcoinchecker.datamodule.model.CurrencyPairInfo
import com.aneonex.bitcoinchecker.datamodule.model.Market
import com.aneonex.bitcoinchecker.datamodule.model.Ticker
import org.json.JSONObject
import java.util.*

class Cryptsy : Market(NAME, TTS_NAME, null) {
    companion object {
        private const val NAME = "Cryptsy"
        private const val TTS_NAME = NAME
        private const val URL = "https://cryptsy.com/api/v2/markets/%1\$s"
        private const val URL_CURRENCY_PAIRS = "https://cryptsy.com/api/v2/markets"
        private val CURRENCY_PAIRS_IDS = HashMap<String, Int>(151)

        init {
            // Kept for backward compatibility
            CURRENCY_PAIRS_IDS["42_BTC"] = 141
            CURRENCY_PAIRS_IDS["ADT_LTC"] = 94
            CURRENCY_PAIRS_IDS["ADT_XPM"] = 113
            CURRENCY_PAIRS_IDS["ALF_BTC"] = 57
            CURRENCY_PAIRS_IDS["AMC_BTC"] = 43
            CURRENCY_PAIRS_IDS["ANC_BTC"] = 66
            CURRENCY_PAIRS_IDS["ANC_LTC"] = 121
            CURRENCY_PAIRS_IDS["ARG_BTC"] = 48
            CURRENCY_PAIRS_IDS["ASC_LTC"] = 111
            CURRENCY_PAIRS_IDS["ASC_XPM"] = 112
            CURRENCY_PAIRS_IDS["AUR_BTC"] = 160
            CURRENCY_PAIRS_IDS["AUR_LTC"] = 161
            CURRENCY_PAIRS_IDS["BCX_BTC"] = 142
            CURRENCY_PAIRS_IDS["BEN_BTC"] = 157
            CURRENCY_PAIRS_IDS["BET_BTC"] = 129
            CURRENCY_PAIRS_IDS["BQC_BTC"] = 10
            CURRENCY_PAIRS_IDS["BTB_BTC"] = 23
            CURRENCY_PAIRS_IDS["BTE_BTC"] = 49
            CURRENCY_PAIRS_IDS["BTG_BTC"] = 50
            CURRENCY_PAIRS_IDS["BUK_BTC"] = 102
            CURRENCY_PAIRS_IDS["CACH_BTC"] = 154
            CURRENCY_PAIRS_IDS["CAP_BTC"] = 53
            CURRENCY_PAIRS_IDS["CASH_BTC"] = 150
            CURRENCY_PAIRS_IDS["CAT_BTC"] = 136
            CURRENCY_PAIRS_IDS["CGB_BTC"] = 70
            CURRENCY_PAIRS_IDS["CGB_LTC"] = 123
            CURRENCY_PAIRS_IDS["CLR_BTC"] = 95
            CURRENCY_PAIRS_IDS["CMC_BTC"] = 74
            CURRENCY_PAIRS_IDS["CNC_BTC"] = 8
            CURRENCY_PAIRS_IDS["CNC_LTC"] = 17
            CURRENCY_PAIRS_IDS["COL_LTC"] = 109
            CURRENCY_PAIRS_IDS["COL_XPM"] = 110
            CURRENCY_PAIRS_IDS["CPR_LTC"] = 91
            CURRENCY_PAIRS_IDS["CRC_BTC"] = 58
            CURRENCY_PAIRS_IDS["CSC_BTC"] = 68
            CURRENCY_PAIRS_IDS["DBL_LTC"] = 46
            CURRENCY_PAIRS_IDS["DEM_BTC"] = 131
            CURRENCY_PAIRS_IDS["DGB_BTC"] = 167
            CURRENCY_PAIRS_IDS["DGC_BTC"] = 26
            CURRENCY_PAIRS_IDS["DGC_LTC"] = 96
            CURRENCY_PAIRS_IDS["DMD_BTC"] = 72
            CURRENCY_PAIRS_IDS["DOGE_BTC"] = 132
            CURRENCY_PAIRS_IDS["DOGE_LTC"] = 135
            CURRENCY_PAIRS_IDS["DRK_BTC"] = 155
            CURRENCY_PAIRS_IDS["DVC_BTC"] = 40
            CURRENCY_PAIRS_IDS["DVC_LTC"] = 52
            CURRENCY_PAIRS_IDS["DVC_XPM"] = 122
            CURRENCY_PAIRS_IDS["EAC_BTC"] = 139
            CURRENCY_PAIRS_IDS["ELC_BTC"] = 12
            CURRENCY_PAIRS_IDS["ELP_LTC"] = 93
            CURRENCY_PAIRS_IDS["EMD_BTC"] = 69
            CURRENCY_PAIRS_IDS["EZC_BTC"] = 47
            CURRENCY_PAIRS_IDS["EZC_LTC"] = 55
            CURRENCY_PAIRS_IDS["FFC_BTC"] = 138
            CURRENCY_PAIRS_IDS["FLAP_BTC"] = 165
            CURRENCY_PAIRS_IDS["FLO_LTC"] = 61
            CURRENCY_PAIRS_IDS["FRC_BTC"] = 39
            CURRENCY_PAIRS_IDS["FRK_BTC"] = 33
            CURRENCY_PAIRS_IDS["FST_BTC"] = 44
            CURRENCY_PAIRS_IDS["FST_LTC"] = 124
            CURRENCY_PAIRS_IDS["FTC_BTC"] = 5
            CURRENCY_PAIRS_IDS["GDC_BTC"] = 82
            CURRENCY_PAIRS_IDS["GLC_BTC"] = 76
            CURRENCY_PAIRS_IDS["GLD_BTC"] = 30
            CURRENCY_PAIRS_IDS["GLD_LTC"] = 36
            CURRENCY_PAIRS_IDS["GLX_BTC"] = 78
            CURRENCY_PAIRS_IDS["GME_LTC"] = 84
            CURRENCY_PAIRS_IDS["HBN_BTC"] = 80
            CURRENCY_PAIRS_IDS["IFC_BTC"] = 59
            CURRENCY_PAIRS_IDS["IFC_LTC"] = 60
            CURRENCY_PAIRS_IDS["IFC_XPM"] = 105
            CURRENCY_PAIRS_IDS["IXC_BTC"] = 38
            CURRENCY_PAIRS_IDS["JKC_BTC"] = 25
            CURRENCY_PAIRS_IDS["JKC_LTC"] = 35
            CURRENCY_PAIRS_IDS["KGC_BTC"] = 65
            CURRENCY_PAIRS_IDS["LEAF_BTC"] = 148
            CURRENCY_PAIRS_IDS["LK7_BTC"] = 116
            CURRENCY_PAIRS_IDS["LKY_BTC"] = 34
            CURRENCY_PAIRS_IDS["LOT_BTC"] = 137
            CURRENCY_PAIRS_IDS["LTC_BTC"] = 3
            CURRENCY_PAIRS_IDS["MAX_BTC"] = 152
            CURRENCY_PAIRS_IDS["MEC_BTC"] = 45
            CURRENCY_PAIRS_IDS["MEC_LTC"] = 100
            CURRENCY_PAIRS_IDS["MEM_LTC"] = 56
            CURRENCY_PAIRS_IDS["MEOW_BTC"] = 149
            CURRENCY_PAIRS_IDS["MINT_BTC"] = 156
            CURRENCY_PAIRS_IDS["MNC_BTC"] = 7
            CURRENCY_PAIRS_IDS["MOON_LTC"] = 145
            CURRENCY_PAIRS_IDS["MST_LTC"] = 62
            CURRENCY_PAIRS_IDS["MZC_BTC"] = 164
            CURRENCY_PAIRS_IDS["NAN_BTC"] = 64
            CURRENCY_PAIRS_IDS["NBL_BTC"] = 32
            CURRENCY_PAIRS_IDS["NEC_BTC"] = 90
            CURRENCY_PAIRS_IDS["NET_BTC"] = 134
            CURRENCY_PAIRS_IDS["NET_LTC"] = 108
            CURRENCY_PAIRS_IDS["NET_XPM"] = 104
            CURRENCY_PAIRS_IDS["NMC_BTC"] = 29
            CURRENCY_PAIRS_IDS["NRB_BTC"] = 54
            CURRENCY_PAIRS_IDS["NVC_BTC"] = 13
            CURRENCY_PAIRS_IDS["NXT_BTC"] = 159
            CURRENCY_PAIRS_IDS["NXT_LTC"] = 162
            CURRENCY_PAIRS_IDS["ORB_BTC"] = 75
            CURRENCY_PAIRS_IDS["OSC_BTC"] = 144
            CURRENCY_PAIRS_IDS["PHS_BTC"] = 86
            CURRENCY_PAIRS_IDS["POINTS_BTC"] = 120
            CURRENCY_PAIRS_IDS["PPC_BTC"] = 28
            CURRENCY_PAIRS_IDS["PPC_LTC"] = 125
            CURRENCY_PAIRS_IDS["PTS_BTC"] = 119
            CURRENCY_PAIRS_IDS["PXC_BTC"] = 31
            CURRENCY_PAIRS_IDS["PXC_LTC"] = 101
            CURRENCY_PAIRS_IDS["PYC_BTC"] = 92
            CURRENCY_PAIRS_IDS["QRK_BTC"] = 71
            CURRENCY_PAIRS_IDS["QRK_LTC"] = 126
            CURRENCY_PAIRS_IDS["RDD_BTC"] = 169
            CURRENCY_PAIRS_IDS["RED_LTC"] = 87
            CURRENCY_PAIRS_IDS["RPC_BTC"] = 143
            CURRENCY_PAIRS_IDS["RYC_BTC"] = 9
            CURRENCY_PAIRS_IDS["RYC_LTC"] = 37
            CURRENCY_PAIRS_IDS["SAT_BTC"] = 168
            CURRENCY_PAIRS_IDS["SBC_BTC"] = 51
            CURRENCY_PAIRS_IDS["SBC_LTC"] = 128
            CURRENCY_PAIRS_IDS["SMC_BTC"] = 158
            CURRENCY_PAIRS_IDS["SPT_BTC"] = 81
            CURRENCY_PAIRS_IDS["SRC_BTC"] = 88
            CURRENCY_PAIRS_IDS["STR_BTC"] = 83
            CURRENCY_PAIRS_IDS["SXC_BTC"] = 153
            CURRENCY_PAIRS_IDS["SXC_LTC"] = 98
            CURRENCY_PAIRS_IDS["TAG_BTC"] = 117
            CURRENCY_PAIRS_IDS["TAK_BTC"] = 166
            CURRENCY_PAIRS_IDS["TEK_BTC"] = 114
            CURRENCY_PAIRS_IDS["TGC_BTC"] = 130
            CURRENCY_PAIRS_IDS["TIPS_LTC"] = 147
            CURRENCY_PAIRS_IDS["TIX_LTC"] = 107
            CURRENCY_PAIRS_IDS["TIX_XPM"] = 103
            CURRENCY_PAIRS_IDS["TRC_BTC"] = 27
            CURRENCY_PAIRS_IDS["UNO_BTC"] = 133
            CURRENCY_PAIRS_IDS["UTC_BTC"] = 163
            CURRENCY_PAIRS_IDS["VTC_BTC"] = 151
            CURRENCY_PAIRS_IDS["WDC_BTC"] = 14
            CURRENCY_PAIRS_IDS["WDC_LTC"] = 21
            CURRENCY_PAIRS_IDS["XJO_BTC"] = 115
            CURRENCY_PAIRS_IDS["XNC_LTC"] = 67
            CURRENCY_PAIRS_IDS["XPM_BTC"] = 63
            CURRENCY_PAIRS_IDS["XPM_LTC"] = 106
            CURRENCY_PAIRS_IDS["YAC_BTC"] = 11
            CURRENCY_PAIRS_IDS["YAC_LTC"] = 22
            CURRENCY_PAIRS_IDS["YBC_BTC"] = 73
            CURRENCY_PAIRS_IDS["ZCC_BTC"] = 140
            CURRENCY_PAIRS_IDS["ZED_BTC"] = 170
            CURRENCY_PAIRS_IDS["ZET_BTC"] = 85
            CURRENCY_PAIRS_IDS["ZET_LTC"] = 127
        }
    }

    override fun getUrl(requestId: Int, checkerInfo: CheckerInfo): String {
        if (checkerInfo.currencyPairId == null) {
            val pairString = String.format("%1\$s_%2\$s", checkerInfo.currencyBase, checkerInfo.currencyCounter)
            if (CURRENCY_PAIRS_IDS.containsKey(pairString)) {
                return String.format(URL, CURRENCY_PAIRS_IDS[pairString].toString())
            }
        }
        return String.format(URL, checkerInfo.currencyPairId)
    }

    @Throws(Exception::class)
    override fun parseTickerFromJsonObject(requestId: Int, jsonObject: JSONObject, ticker: Ticker, checkerInfo: CheckerInfo) {
        val dataJsonObject = jsonObject.getJSONObject("data")
        val daySummaryObject = dataJsonObject.getJSONObject("24hr")

        // ticker.vol = daySummaryObject.getDouble("volume"); TODO enable later when there will be any documentation of real data to test on
        ticker.high = daySummaryObject.getDouble("price_high")
        ticker.low = daySummaryObject.getDouble("price_low")
        ticker.last = dataJsonObject.getJSONObject("last_trade").getDouble("price")
    }

    @Throws(Exception::class)
    override fun parseError(requestId: Int, responseString: String, checkerInfo: CheckerInfo): String? {
        return if (checkerInfo.currencyPairId == null) {
            "Perform sync and re-add this Checker"
        } else super.parseError(requestId, responseString, checkerInfo)
    }

    // ====================
    // Get currency pairs
    // ====================
    override fun getCurrencyPairsUrl(requestId: Int): String? {
        return URL_CURRENCY_PAIRS
    }

    @Throws(Exception::class)
    override fun parseCurrencyPairsFromJsonObject(requestId: Int, jsonObject: JSONObject, pairs: MutableList<CurrencyPairInfo>) {
        val dataJsonArray = jsonObject.getJSONArray("data")
        for (i in 0 until dataJsonArray.length()) {
            val marketObject = dataJsonArray.getJSONObject(i)
            val currencyPair = marketObject.getString("label") ?: continue
            val currencies: Array<String> = currencyPair.split("/".toRegex()).toTypedArray()
            if (currencies.size != 2 || currencies[0] == null || currencies[1] == null) continue
            pairs.add(CurrencyPairInfo(
                    currencies[0],
                    currencies[1],
                    marketObject.getString("id")
            ))
        }
    }
}