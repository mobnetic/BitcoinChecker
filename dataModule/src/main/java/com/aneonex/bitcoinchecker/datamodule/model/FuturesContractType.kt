package com.aneonex.bitcoinchecker.datamodule.model

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.temporal.TemporalAdjusters

enum class FuturesContractType(val value: Int) {
    NONE(0),
    PERPETUAL(1),
    WEEKLY(2),
    BIWEEKLY(3),
    MONTHLY(4),
    BIMONTHLY(5),
    QUARTERLY(6),
    BIQUARTERLY(7);

    override fun toString(): String {
        return getShortName(this) ?: "None"
    }

    companion object {
        fun fromInt(value: Int): FuturesContractType = values().firstOrNull { it.value == value } ?: NONE

        fun getShortName(contractType: FuturesContractType): String? =
            when (contractType) {
                NONE -> null
                PERPETUAL ->
                    @Suppress("SpellCheckingInspection")
                    "Perp"
                WEEKLY -> "1W"
                BIWEEKLY -> "2W"
                MONTHLY -> "1M"
                BIMONTHLY -> "2M"
                QUARTERLY -> "1Q"
                BIQUARTERLY -> "2Q"
            }

        fun getDeliveryDate(contractType: FuturesContractType): LocalDate? {
            fun getCurrentDate() = LocalDate.now(ZoneOffset.UTC)

            return when(contractType) {
                NONE,
                PERPETUAL ->
                    null

                WEEKLY -> getEndOfWeek(getCurrentDate())
                BIWEEKLY -> getEndOfWeek(getCurrentDate().plusWeeks(1))

                MONTHLY -> getEndOfMonth(getCurrentDate())
                BIMONTHLY -> getEndOfMonth(getCurrentDate().plusMonths(1))

                QUARTERLY -> getEndOfQuarter(getCurrentDate())
                BIQUARTERLY -> getEndOfQuarter(getCurrentDate().plusMonths(3))
//                else -> throw MarketParseException("Unexpected contact type")
            }
        }

        private fun getEndOfWeek(currentDate: LocalDate): LocalDate =
            if(currentDate.dayOfWeek == DayOfWeek.FRIDAY) currentDate
            else currentDate.with(TemporalAdjusters.next(DayOfWeek.FRIDAY))

        private fun getEndOfMonth(currentDate: LocalDate): LocalDate =
            currentDate.with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY))

        private fun getEndOfQuarter(currentDate: LocalDate): LocalDate {
            val firstDayOfQuarter: LocalDate =
                currentDate.with(currentDate.month.firstMonthOfQuarter())
                    .with(TemporalAdjusters.firstDayOfMonth())

            return firstDayOfQuarter.plusMonths(2)
                .with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY))
        }
    }
}