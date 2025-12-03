package com.example.kotlinmultiplatformlibrary.sample

import co.touchlab.skie.configuration.annotations.DefaultArgumentInterop
import com.example.kotlinmultiplatformlibrary.platform

class Sample {
    @DefaultArgumentInterop.Enabled
    fun string(label: String = "Sample"): String {
        return "$label ${platform()}"
    }
}