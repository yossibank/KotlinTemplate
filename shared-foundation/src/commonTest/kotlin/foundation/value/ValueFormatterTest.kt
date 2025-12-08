package foundation.value

import yossibank.shared.foundation.value.ValueCustom
import yossibank.shared.foundation.value.ValueFormatter
import yossibank.shared.foundation.value.ValuePrefix
import yossibank.shared.foundation.value.ValueStyle
import yossibank.shared.foundation.value.ValueSuffix
import kotlin.test.Test
import kotlin.test.assertEquals

class ValueFormatterTest {
    @Test
    fun formatted_value() {
        // arrange
        val value = 1000.0
        val expected = "1,000"

        // act
        val actual = ValueFormatter(value).format()

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
        val actual = ValueFormatter(value).format()

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
        val actual = ValueFormatter(value).format(
            ValueStyle(
                prefix = ValuePrefix.Plus,
                suffix = ValueSuffix.Yen
            )
        )

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
        val actual = ValueFormatter(value).format()

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
        val actual = ValueFormatter(value).format()

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
        val actual = ValueFormatter(value).format()

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
        val actual = ValueFormatter(value).format()

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
        val actual = ValueFormatter(value).format()

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
        val actual = ValueFormatter(value).format()

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
        val actual = ValueFormatter(value).format()

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
        val actual = ValueFormatter(value).format()

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
        val actual = ValueFormatter(value).format()

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
        val actual = ValueFormatter(value).format(
            ValueStyle(prefix = ValuePrefix.None)
        )

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
        val actual = ValueFormatter(value = value).format(
            ValueStyle(prefix = ValuePrefix.Plus)
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }

    @Test
    fun prefix_custom() {
        // arrange
        val value = 50.15
        val expected = "約50.15"

        // act
        val actual = ValueFormatter(value).format(
            ValueStyle(
                prefix = ValuePrefix.Custom,
                custom = ValueCustom(prefix = "約")
            )
        )

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
        val actual = ValueFormatter(value).format(
            ValueStyle(suffix = ValueSuffix.None)
        )

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
        val actual = ValueFormatter(value).format(
            ValueStyle(suffix = ValueSuffix.Yen)
        )

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
        val actual = ValueFormatter(value).format(
            ValueStyle(suffix = ValueSuffix.HundredMillionYen)
        )

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
        val actual = ValueFormatter(value).format(
            ValueStyle(suffix = ValueSuffix.Dollar)
        )

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
        val actual = ValueFormatter(value).format(
            ValueStyle(suffix = ValueSuffix.HundredMillionDollar)
        )

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
        val actual = ValueFormatter(value).format(
            ValueStyle(suffix = ValueSuffix.Percent)
        )

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
        val actual = ValueFormatter(value).format(
            ValueStyle(
                suffix = ValueSuffix.Custom,
                custom = ValueCustom(suffix = "回")
            )
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }
}