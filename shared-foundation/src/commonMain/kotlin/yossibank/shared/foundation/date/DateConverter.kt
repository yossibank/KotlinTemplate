package yossibank.shared.foundation.date

import co.touchlab.skie.configuration.annotations.DefaultArgumentInterop
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class DateConverter {
    @DefaultArgumentInterop.Enabled
    fun formatToString(
        value: String?,
        nullValue: String = "---",
        format: DateFormat
    ): String {
        if (value.isNullOrEmpty()) return nullValue
        val date = stringToDate(value) ?: return nullValue
        return format.formatter.format(date)
    }

    @OptIn(ExperimentalTime::class)
    @DefaultArgumentInterop.Enabled
    fun epochToString(
        epoch: Long,
        timeZone: String = "Asia/Tokyo",
        format: DateFormat
    ): String {
        val instant = Instant.fromEpochSeconds(epoch)
        val timeZone = TimeZone.of(timeZone)
        val date = instant.toLocalDateTime(timeZone)
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