package com.example.kotlinmultiplatformlibrary.foundation

import co.touchlab.skie.configuration.annotations.DefaultArgumentInterop

data class ValueStyle @DefaultArgumentInterop.Enabled constructor(
    val prefix: Prefix = Prefix.None,
    val suffix: Suffix = Suffix.None
) {
    sealed class Prefix(val unit: String) {
        data object None : Prefix("")
        data object Plus : Prefix("+")
        data class Custom(val custom: String) : Prefix(custom)
    }

    sealed class Suffix(val unit: String, val divisor: Double = 1.0) {
        data object None : Suffix("")
        data object Yen : Suffix("円")
        data object Dollar : Suffix("ドル")
        data object HundredMillionYen : Suffix("億円", 100_000_000.0)
        data object HundredMillionDollar : Suffix("億ドル", 100_000_000.0)
        data object Percent : Suffix("%")
        data class Custom(val custom: String) : Suffix(custom)
    }
}