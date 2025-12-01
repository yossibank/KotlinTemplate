package com.example.kotlinmultiplatformlibrary.setting

import com.example.kotlinmultiplatformlibrary.platform

class Setting {
    fun test(): String {
        return print()
    }

    private fun print(): String {
        return "print ${platform()}"
    }
}