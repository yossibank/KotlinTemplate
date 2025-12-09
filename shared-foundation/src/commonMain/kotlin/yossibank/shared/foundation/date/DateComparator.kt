package yossibank.shared.foundation.date

import co.touchlab.skie.configuration.annotations.DefaultArgumentInterop
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class DateComparator {
    @OptIn(ExperimentalTime::class)
    @DefaultArgumentInterop.Enabled
    fun timeDifference(
        nowEpochTime: Long = Clock.System.now().epochSeconds,
        targetEpochTime: Long
    ): String {
        val differenceSeconds = (nowEpochTime - targetEpochTime).toInt()

        val hours = differenceSeconds / 3600
        val minutes = (differenceSeconds % 3600) / 60
        val seconds = differenceSeconds % 60

        return when {
            hours > 0 -> "${hours}時間${minutes}分${seconds}秒前"
            minutes > 0 -> "${minutes}分${seconds}秒前"
            else -> "${seconds}秒前"
        }
    }
}