package io.github.yossibank.kotlinmultiplatformlibrary.sample

import co.touchlab.skie.configuration.annotations.DefaultArgumentInterop
import io.github.yossibank.kotlinmultiplatformlibrary.platform

class Sample {
    @DefaultArgumentInterop.Enabled
    fun string(label: String = "Sample"): String {
        return "$label ${platform()}"
    }
}