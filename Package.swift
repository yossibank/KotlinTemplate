// swift-tools-version:5.9
import PackageDescription

let package = Package(
    name: "KotlinMultiplatformLibrary",
    platforms: [
        .iOS(.v15)
    ],
    products: [
        .library(
            name: "KotlinMultiplatformLibrary",
            targets: ["KotlinMultiplatformLibrary"]
        ),
    ],
    targets: [
        .binaryTarget(
            name: "KotlinMultiplatformLibrary",
            url: "https://github.com/yossibank/KotlinTemplate/releases/download/v1.1.6/KotlinMultiplatformLibrary.xcframework.zip",
            checksum: "2af5d45f17c9de6e13ace9af6a0651c05cdf301d2a75935c08b4cd9f0fde40d1"
        )
    ]
)
