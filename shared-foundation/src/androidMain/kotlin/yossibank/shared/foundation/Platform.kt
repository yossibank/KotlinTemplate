package yossibank.shared.foundation

import android.os.Build

actual fun platform() = "Android"
actual fun version() = Build.VERSION.SDK_INT.toString()