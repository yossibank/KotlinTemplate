package yossibank.shared.foundation.value

import co.touchlab.skie.configuration.annotations.DefaultArgumentInterop

data class ValueStyle @DefaultArgumentInterop.Enabled constructor(
    val prefix: ValuePrefix = ValuePrefix.None,
    val suffix: ValueSuffix = ValueSuffix.None,
    val custom: ValueCustom = ValueCustom.None
)

data class ValueCustom @DefaultArgumentInterop.Enabled constructor(
    val prefix: String = "",
    val suffix: String = "",
    val divisor: Double = 1.0
) {
    companion object {
        val None = ValueCustom()
    }
}

enum class ValuePrefix(val unit: String) {
    None(""),
    Plus("+"),
    Custom("")
}

enum class ValueSuffix(val unit: String, val divisor: Double = 1.0) {
    None(""),
    Yen("円"),
    HundredMillionYen("億円", 100_000_000.0),
    Dollar("ドル"),
    HundredMillionDollar("億ドル", 100_000_000.0),
    Percent("%"),
    Custom("")
}