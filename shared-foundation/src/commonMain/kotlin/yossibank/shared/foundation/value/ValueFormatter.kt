package yossibank.shared.foundation.value

import co.touchlab.skie.configuration.annotations.DefaultArgumentInterop
import kotlin.math.roundToLong

class ValueFormatter @DefaultArgumentInterop.Enabled constructor(
    val value: Double?,
    val nullValue: String = "---"
) {
    @DefaultArgumentInterop.Enabled
    fun format(style: ValueStyle = ValueStyle()): String {
        val prefix = when (style.prefix) {
            ValuePrefix.Custom -> style.custom.prefix
            else -> style.prefix.unit
        }

        val suffix = when (style.suffix) {
            ValueSuffix.Custom -> style.custom.suffix
            else -> style.suffix.unit
        }

        val divisor = when (style.suffix) {
            ValueSuffix.Custom -> style.custom.divisor
            else -> style.suffix.divisor
        }

        val nullString = prefix + nullValue + suffix

        val formattedValue = when {
            value == null -> null
            value == Double.MAX_VALUE -> null
            value == Double.MIN_VALUE -> null
            !value.isFinite() -> null
            else -> value / divisor
        } ?: return nullString

        val (integer, decimal) = formattedParts(formattedValue)

        val integerString = formattedInteger(integer)
        val decimalString = formattedDecimal(decimal)

        return prefix + integerString + decimalString + suffix
    }

    private fun formattedParts(value: Double): Pair<String, String> {
        val roundedValue = (value * 100).roundToLong() / 100.0
        val roundedParts = roundedValue.toString().split(".")

        return Pair(
            roundedParts.getOrElse(0) { "" },
            roundedParts.getOrElse(1) { "" }
        )
    }

    private fun formattedInteger(integer: String): String {
        val isNegative = integer.startsWith("-")
        val digits = if (isNegative) integer.drop(1) else integer

        val formattedValue = digits
            .reversed()
            .chunked(3)
            .joinToString(",")
            .reversed()

        return if (isNegative) "-$formattedValue" else formattedValue
    }

    private fun formattedDecimal(decimal: String): String {
        if (decimal.isEmpty()) return ""

        val truncated = decimal.take(2)
        val trimmed = truncated.trimEnd('0')

        return if (trimmed.isEmpty()) "" else ".$trimmed"
    }
}