package com.example.kotlinmultiplatformlibrary.foundation

import co.touchlab.skie.configuration.annotations.DefaultArgumentInterop

data class ValueStyle @DefaultArgumentInterop.Enabled constructor(
    val prefix: ValuePrefix = ValuePrefix.None,
    val suffix: ValueSuffix = ValueSuffix.None
)

sealed class ValuePrefix(val unit: String) {
    data object None : ValuePrefix("")
    data object Plus : ValuePrefix("+")
    data class Custom(val custom: String) : ValuePrefix(custom)
}

sealed class ValueSuffix(val unit: String, val divisor: Double = 1.0) {
    data object None : ValueSuffix("")
    data object Yen : ValueSuffix("円")
    data object Dollar : ValueSuffix("ドル")
    data object HundredMillionYen : ValueSuffix("億円", 100_000_000.0)
    data object HundredMillionDollar : ValueSuffix("億ドル", 100_000_000.0)
    data object Percent : ValueSuffix("%")
    data class Custom(val custom: String) : ValueSuffix(custom)
}