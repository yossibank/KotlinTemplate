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
            url: "https://api.github.com/repos/yossibank/KotlinTemplate/releases/assets/322216480.zip",
            checksum: "b6eed1a4aa8b54e47e3060012ac98b859adf36e1d7c72617e45072d8fc22553f"
        )
    ]
)
