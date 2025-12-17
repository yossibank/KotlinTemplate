package foundation.date

import kotlinx.datetime.LocalDateTime
import yossibank.shared.foundation.date.DateConverter
import yossibank.shared.foundation.date.DateFormat
import kotlin.test.Test
import kotlin.test.assertEquals

class DateConverterTest {
    @Test
    fun formatToString_null() {
        // arrange
        val value: String? = null
        val expected = "--月--日"

        // act
        val actual = DateConverter().formatToString(
            value = value,
            nullValue = "--月--日",
            format = DateFormat.Djp
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun formatToString_HJp() {
        // arrange
        val value = "2025-12-06T15:00:00"
        val expected = "15時"

        // act
        val actual = DateConverter().formatToString(
            value = value,
            format = DateFormat.Hjp
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun formatToString_HMJp() {
        // arrange
        val value = "2025-12-06T15:00:00"
        val expected = "15時0分"

        // act
        val actual = DateConverter().formatToString(
            value = value,
            format = DateFormat.HmJp
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun formatToString_DJp() {
        // arrange
        val value = "2025-12-06T15:00:00"
        val expected = "6日"

        // act
        val actual = DateConverter().formatToString(
            value = value,
            format = DateFormat.Djp
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun formatToString_MJp() {
        // arrange
        val value = "2025-12-06T15:00:00"
        val expected = "12月"

        // act
        val actual = DateConverter().formatToString(
            value = value,
            format = DateFormat.Mjp
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun formatToString_MDJp() {
        // arrange
        val value = "2025-12-06T15:00:00"
        val expected = "12月6日"

        // act
        val actual = DateConverter().formatToString(
            value = value,
            format = DateFormat.MdJp
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun formatToString_YJp() {
        // arrange
        val value = "2025-12-06T15:00:00"
        val expected = "2025年"

        // act
        val actual = DateConverter().formatToString(
            value = value,
            format = DateFormat.Yjp
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun formatToString_YMDJp() {
        // arrange
        val value = "2025-12-06T15:00:00"
        val expected = "2025年12月6日"

        // act
        val actual = DateConverter().formatToString(
            value = value,
            format = DateFormat.YmdJp
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun formatToString_MDEHmsJp() {
        // arrange
        val value = "2025-12-06 15:45:45"
        val expected = "12/6(土) 15:45:45"

        // act
        val actual = DateConverter().formatToString(
            value = value,
            format = DateFormat.MdehmsJp
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun epochToString_HJp() {
        // arrange
        val value: Long = 1765000800 // 2025-12-06 15:00:00
        val expected = "15時"

        // act
        val actual = DateConverter().epochToString(
            epoch = value,
            format = DateFormat.Hjp
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun epochToString_HMJp() {
        // arrange
        val value: Long = 1765000800 // 2025-12-06 15:00:00
        val expected = "15時0分"

        // act
        val actual = DateConverter().epochToString(
            epoch = value,
            format = DateFormat.HmJp
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun epochToString_DJp() {
        // arrange
        val value: Long = 1765000800 // 2025-12-06 15:00:00
        val expected = "6日"

        // act
        val actual = DateConverter().epochToString(
            epoch = value,
            format = DateFormat.Djp
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun epochToString_MJp() {
        // arrange
        val value: Long = 1765000800 // 2025-12-06 15:00:00
        val expected = "12月"

        // act
        val actual = DateConverter().epochToString(
            epoch = value,
            format = DateFormat.Mjp
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun epochToString_MDJp() {
        // arrange
        val value: Long = 1765000800 // 2025-12-06 15:00:00
        val expected = "12月6日"

        // act
        val actual = DateConverter().epochToString(
            epoch = value,
            format = DateFormat.MdJp
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun epochToString_YJp() {
        // arrange
        val value: Long = 1765000800 // 2025-12-06 15:00:00
        val expected = "2025年"

        // act
        val actual = DateConverter().epochToString(
            epoch = value,
            format = DateFormat.Yjp
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun epochToString_MDEHmsJp() {
        // arrange
        val value: Long = 1765003500 // 2025-12-06 15:45:00
        val expected = "12/6(土) 15:45:00"

        // act
        val actual = DateConverter().epochToString(
            epoch = value,
            format = DateFormat.MdehmsJp
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun epochToString_YMDJp() {
        // arrange
        val value: Long = 1765000800
        val expected = "2025年12月6日"

        // act
        val actual = DateConverter().epochToString(
            epoch = value,
            format = DateFormat.YmdJp
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun stringToDate_Iso8601() {
        // arrange
        val value = "2025-12-06T15:00:00"
        val expected = LocalDateTime(2025, 12, 6, 15, 0, 0)

        // act
        val actual = DateConverter().stringToDate(value)

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun stringToDate_Iso8601Minute() {
        // arrange
        val value = "2025-12-06T15:00"
        val expected = LocalDateTime(2025, 12, 6, 15, 0, 0)

        // act
        val actual = DateConverter().stringToDate(value)

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun stringToDate_Slash() {
        // arrange
        val value = "2025/12/06 15:00:00"
        val expected = LocalDateTime(2025, 12, 6, 15, 0, 0)

        // act
        val actual = DateConverter().stringToDate(value)

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun stringToDate_SlashMinute() {
        // arrange
        val value = "2025/12/06 15:00"
        val expected = LocalDateTime(2025, 12, 6, 15, 0, 0)

        // act
        val actual = DateConverter().stringToDate(value)

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun stringToDate_SlashDate() {
        // arrange
        val value = "2025/12/06"
        val expected = LocalDateTime(2025, 12, 6, 0, 0, 0)

        // act
        val actual = DateConverter().stringToDate(value)

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun stringToDate_Hyphen() {
        // arrange
        val value = "2025-12-06 15:00:00"
        val expected = LocalDateTime(2025, 12, 6, 15, 0, 0)

        // act
        val actual = DateConverter().stringToDate(value)

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun stringToDate_HyphenMinute() {
        // arrange
        val value = "2025-12-06 15:00"
        val expected = LocalDateTime(2025, 12, 6, 15, 0, 0)

        // act
        val actual = DateConverter().stringToDate(value)

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun stringToDate_HyphenDate() {
        // arrange
        val value = "2025-12-06"
        val expected = LocalDateTime(2025, 12, 6, 0, 0, 0)

        // act
        val actual = DateConverter().stringToDate(value)

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun stringToDate_Jp() {
        // arrange
        val value = "2025年12月06日 15時00分00秒"
        val expected = LocalDateTime(2025, 12, 6, 15, 0, 0)

        // act
        val actual = DateConverter().stringToDate(value)

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun stringToDate_JpMinute() {
        // arrange
        val value = "2025年12月06日 15時00分"
        val expected = LocalDateTime(2025, 12, 6, 15, 0, 0)

        // act
        val actual = DateConverter().stringToDate(value)

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun stringToDate_JpDate() {
        // arrange
        val value = "2025年12月06日"
        val expected = LocalDateTime(2025, 12, 6, 0, 0, 0)

        // act
        val actual = DateConverter().stringToDate(value)

        // assert
        assertEquals(
            expected,
            actual
        )
    }
}