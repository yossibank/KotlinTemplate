package io.github.yossibank.kotlinmultiplatformlibrary

import platform.UIKit.UIDevice

actual fun platform() = "iOS"
actual fun name() = UIDevice.currentDevice.systemName()