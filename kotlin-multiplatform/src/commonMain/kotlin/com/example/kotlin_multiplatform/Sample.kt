package com.example.kotlin_multiplatform

class Sample {
    fun string(): String {
        return "Sample ${platform()}"
    }
}