package yossibank.shared.foundation

import platform.UIKit.UIDevice

actual fun platform() = "iOS"
actual fun version() = UIDevice.currentDevice.systemVersion()