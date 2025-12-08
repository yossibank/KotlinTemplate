package yossibank.shared.foundation.date

import co.touchlab.skie.configuration.annotations.DefaultArgumentInterop
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.atTime

class DateConverter {
    @DefaultArgumentInterop.Enabled
    fun formatToString(
        value: String?,
        nullValue: String = "---",
        format: DateFormat
    ): String {
        if (value.isNullOrEmpty()) return nullValue
        val date = stringToDate(value) ?: return nullValue
        return dateToString(date, format)
    }

    fun dateToString(
        date: LocalDateTime,
        format: DateFormat
    ): String {
        return format.formatter.format(date)
    }

    fun stringToDate(dateString: String): LocalDateTime? {
        DateFullyFormat.entries.firstNotNullOfOrNull {
            runCatching {
                it.formatter.parse(dateString)
            }.getOrNull()
        }?.let { return it }

        DatePartiallyFormat.entries.firstNotNullOfOrNull {
            runCatching {
                it.formatter.parse(dateString).atTime(0, 0)
            }.getOrNull()
        }?.let { return it }

        return null
    }
}