package yossibank.shared.foundation

import co.touchlab.skie.configuration.annotations.DefaultArgumentInterop

object SharedFoundation {
    @DefaultArgumentInterop.Enabled
    fun device(label: String = "SharedFoundation"): String {
        return "$label: ${platform()} ${name()}"
    }
}

expect fun platform(): String
expect fun name(): String