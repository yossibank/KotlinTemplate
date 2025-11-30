package foundation

import com.example.multiplatform.foundation.ValueConverter
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
}