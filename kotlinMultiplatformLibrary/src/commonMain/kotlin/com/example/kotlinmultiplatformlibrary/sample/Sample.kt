package com.example.kotlinmultiplatformlibrary.sample

import com.example.kotlinmultiplatformlibrary.platform

class Sample {
    fun string(): String {
        return "Sample ${platform()}"
    }
}