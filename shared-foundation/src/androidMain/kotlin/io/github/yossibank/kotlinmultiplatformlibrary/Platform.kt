package io.github.yossibank.kotlinmultiplatformlibrary

actual fun platform() = "Android"
actual fun name() = android.os.Build.VERSION.SDK_INT.toString()