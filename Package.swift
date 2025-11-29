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
            name: "SharedLibrary",
            url: "https://api.github.com/repos/yossibank/KotlinTemplate/releases/assets/assetID.zip",
            checksum: "checksum"
        )
    ]
)
