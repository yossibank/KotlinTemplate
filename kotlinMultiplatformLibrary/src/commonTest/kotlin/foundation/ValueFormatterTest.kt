package foundation

import com.example.kotlinmultiplatformlibrary.foundation.ValueFormatter
import com.example.kotlinmultiplatformlibrary.foundation.ValueStyle
import kotlin.test.Test
import kotlin.test.assertEquals

class ValueFormatterTest {
    @Test
    fun formatted_value() {
        // arrange
        val value = 1000.0
        val expected = "1,000"

        // act
        val valueFormatter = ValueFormatter(value)
        val actual = valueFormatter.format()

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun formatted_minus_value() {
        // arrange
        val value = -1000.0
        val expected = "-1,000"

        // act
        val valueFormatter = ValueFormatter(value)
        val actual = valueFormatter.format()

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun formatted_null_value() {
        // arrange
        val value = null
        val expected = "+---円"

        // act
        val valueFormatter = ValueFormatter(
            value = value,
            style = ValueStyle(
                prefix = ValueStyle.Prefix.Plus,
                suffix = ValueStyle.Suffix.Yen
            )
        )
        val actual = valueFormatter.format()

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun formatted_nan_value() {
        // arrange
        val value = Double.NaN
        val expected = "---"

        // act
        val valueFormatter = ValueFormatter(value)
        val actual = valueFormatter.format()

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun formatted_positive_infinity_value() {
        // arrange
        val value = Double.POSITIVE_INFINITY
        val expected = "---"

        // act
        val valueFormatter = ValueFormatter(value)
        val actual = valueFormatter.format()

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun formatted_negative_infinity_value() {
        // arrange
        val value = Double.NEGATIVE_INFINITY
        val expected = "---"

        // act
        val valueFormatter = ValueFormatter(value)
        val actual = valueFormatter.format()

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun formatted_max_value() {
        // arrange
        val value = Double.MAX_VALUE
        val expected = "---"

        // act
        val valueFormatter = ValueFormatter(value)
        val actual = valueFormatter.format()

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun formatted_min_value() {
        // arrange
        val value = Double.MIN_VALUE
        val expected = "---"

        // act
        val valueFormatter = ValueFormatter(value)
        val actual = valueFormatter.format()

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun formatted_decimal_one_value() {
        // arrange
        val value = 10000.2
        val expected = "10,000.2"

        // act
        val valueFormatter = ValueFormatter(value)
        val actual = valueFormatter.format()

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun formatted_decimal_two_value() {
        // arrange
        val value = 10000.28
        val expected = "10,000.28"

        // act
        val valueFormatter = ValueFormatter(value)
        val actual = valueFormatter.format()

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun formatted_decimal_three_value() {
        // arrange
        val value = 10000.258
        val expected = "10,000.26"

        // act
        val valueFormatter = ValueFormatter(value)
        val actual = valueFormatter.format()

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun formatted_decimal_over_value() {
        // arrange
        val value = 10000.24912456
        val expected = "10,000.25"

        // act
        val valueFormatter = ValueFormatter(value)
        val actual = valueFormatter.format()

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun prefix_none() {
        // arrange
        val value = 10000.0
        val expected = "10,000"

        // act
        val valueFormatter = ValueFormatter(
            value = value,
            style = ValueStyle(prefix = ValueStyle.Prefix.None)
        )
        val actual = valueFormatter.format()

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun prefix_plus() {
        // arrange
        val value = 10000.0
        val expected = "+10,000"

        // act
        val valueFormatter = ValueFormatter(
            value = value,
            style = ValueStyle(prefix = ValueStyle.Prefix.Plus)
        )
        val actual = valueFormatter.format()

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun prefix_custom() {
        // arrange
        val value = 10000.0
        val expected = "$10,000"

        // act
        val valueFormatter = ValueFormatter(
            value = value,
            style = ValueStyle(prefix = ValueStyle.Prefix.Custom("$"))
        )
        val actual = valueFormatter.format()

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun suffix_none() {
        // arrange
        val value = 10000.0
        val expected = "10,000"

        // act
        val valueFormatter = ValueFormatter(
            value = value,
            style = ValueStyle(suffix = ValueStyle.Suffix.None)
        )
        val actual = valueFormatter.format()

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun suffix_yen() {
        // arrange
        val value = 10000.0
        val expected = "10,000円"

        // act
        val valueFormatter = ValueFormatter(
            value = value,
            style = ValueStyle(suffix = ValueStyle.Suffix.Yen)
        )
        val actual = valueFormatter.format()

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun suffix_dollar() {
        // arrange
        val value = 10000.0
        val expected = "10,000ドル"

        // act
        val valueFormatter = ValueFormatter(
            value = value,
            style = ValueStyle(suffix = ValueStyle.Suffix.Dollar)
        )
        val actual = valueFormatter.format()

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun suffix_hundred_million_yen() {
        // arrange
        val value = 168_000_000_000.0
        val expected = "1,680億円"

        // act
        val valueFormatter = ValueFormatter(
            value = value,
            style = ValueStyle(suffix = ValueStyle.Suffix.HundredMillionYen)
        )
        val actual = valueFormatter.format()

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun suffix_hundred_million_dollar() {
        // arrange
        val value = 16_800_000.0
        val expected = "0.17億ドル"

        // act
        val valueFormatter = ValueFormatter(
            value = value,
            style = ValueStyle(suffix = ValueStyle.Suffix.HundredMillionDollar)
        )
        val actual = valueFormatter.format()

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun suffix_percent() {
        // arrange
        val value = 98.72
        val expected = "98.72%"

        // act
        val valueFormatter = ValueFormatter(
            value = value,
            style = ValueStyle(suffix = ValueStyle.Suffix.Percent)
        )
        val actual = valueFormatter.format()

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun suffix_custom() {
        // arrange
        val value = 15.0
        val expected = "15回"

        // act
        val valueFormatter = ValueFormatter(
            value = value,
            style = ValueStyle(suffix = ValueStyle.Suffix.Custom("回"))
        )
        val actual = valueFormatter.format()

        // assert
        assertEquals(
            expected,
            actual
        )
    }
}