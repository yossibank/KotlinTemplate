package com.example.kotlinmultiplatformlibrary.test

enum class TestEnum {
    Test1,
    Test2,
    Test3
}

sealed class TestSealedEnum {
    data class Test1(val label1: String) : TestSealedEnum()
    data class Test2(val label2: String) : TestSealedEnum()
    data class Test3(val label3: String) : TestSealedEnum()
}