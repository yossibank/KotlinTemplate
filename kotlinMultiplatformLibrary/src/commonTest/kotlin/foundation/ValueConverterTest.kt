package foundation

import com.example.kotlinmultiplatformlibrary.ValueConverter
import com.example.kotlinmultiplatformlibrary.ValueFormat
import kotlin.test.Test
import kotlin.test.assertEquals

class ValueConverterTest {
    @Test
    fun prefix_none() {
        // arrange
        val value = 10000.0
        val expected = "10,000"

        // act
        val actual = ValueConverter().format(
            formatter = ValueConverter.Formatter(value = value)
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
        val expected = "+10,000円"

        // act
        val actual = ValueConverter().format(
            formatter = ValueConverter.Formatter(
                value = value,
                valueFormat = ValueFormat(
                    prefix = ValueFormat.Prefix.Plus,
                    suffix = ValueFormat.Suffix.Yen
                )
            )
        )

        // assert
        assertEquals(
            expected,
            actual
        )
    }
}