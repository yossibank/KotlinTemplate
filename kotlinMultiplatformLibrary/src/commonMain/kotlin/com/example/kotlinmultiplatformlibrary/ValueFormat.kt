package com.example.kotlinmultiplatformlibrary

data class ValueFormat(
    val prefix: Prefix = Prefix.None,
    val suffix: Suffix = Suffix.None
) {
    sealed class Prefix(val unit: String) {
        data object None : Prefix("")
        data object Plus : Prefix("+")
        data class Custom(val custom: String) : Prefix(custom)
    }

    sealed class Suffix(val unit: String) {
        data object None : Suffix("")
        data object Yen : Suffix("円")
        data object Dollar : Suffix("ドル")
        data object HundredMillionYen : Suffix("百万円")
        data object HundredMillionDollar : Suffix("百万ドル")
        data object Percent : Suffix("%")
        data class Custom(val custom: String) : Suffix(custom)
    }
}