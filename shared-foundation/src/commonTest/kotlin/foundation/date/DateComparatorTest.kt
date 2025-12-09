package foundation.date

import yossibank.shared.foundation.date.DateComparator
import kotlin.test.Test
import kotlin.test.assertEquals

class DateComparatorTest {
    @Test
    fun timeDifference_seconds() {
        // arrange
        val now: Long = 1765000830 // 2025-12-06 15:00:30
        val target: Long = 1765000800 // 2025-12-06 15:00:00
        val expected = "30秒前"

        // actual
        val actual = DateComparator().timeDifference(
            nowEpochTime = now,
            targetEpochTime = target
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun timeDifference_minutes() {
        // arrange
        val now: Long = 1765000830 // 2025-12-06 15:00:30
        val target: Long = 1764997280 // 2025-12-06 14:01:20
        val expected = "59分10秒前"

        // actual
        val actual = DateComparator().timeDifference(
            nowEpochTime = now,
            targetEpochTime = target
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun timeDifference_hours() {
        // arrange
        val now: Long = 1765000830 // 2025-12-06 15:00:30
        val target: Long = 1764990070 // 2025-12-06 12:01:10
        val expected = "2時間59分20秒前"

        // actual
        val actual = DateComparator().timeDifference(
            nowEpochTime = now,
            targetEpochTime = target
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }
}