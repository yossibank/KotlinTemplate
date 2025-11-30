package com.example.kotlinmultiplatformlibrary

import kotlin.math.roundToLong

class ValueConverter {
    data class Formatter(
        val value: Double?,
        val valueFormat: ValueFormat = ValueFormat(),
        val nullValue: String = "---"
    )

    fun format(formatter: Formatter): String {
        val prefix = formatter.valueFormat.prefix
        val suffix = formatter.valueFormat.suffix

        val value = formatter.value
            ?: return prefix.unit + formatter.nullValue + suffix.unit

        val rounded = (value * 100).roundToLong() / 100.0
        val roundedString = rounded.toString()

        val split = roundedString.split(".")
        val integer = split.getOrNull(0) ?: ""
        var decimal = split.getOrNull(1) ?: ""

        while (decimal.endsWith("0") && decimal != "0" && decimal.length > 2) {
            decimal = decimal.dropLast(1)
        }

        val decimalString = if (decimal.isEmpty() || decimal == "0") {
            ""
        } else {
            ".$decimal"
        }

        val integerString = comma(integer)

        return prefix.unit + integerString + decimalString + suffix.unit
    }

    private fun comma(value: String): String {
        val minus = value.startsWith("-")
        val integer = if (minus) value.drop(1) else value

        val formattedValue = integer
            .reversed()
            .chunked(3)
            .joinToString(",")
            .reversed()

        return if (minus) "-$formattedValue" else formattedValue
    }
}