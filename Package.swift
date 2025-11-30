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
            url: "https://api.github.com/repos/yossibank/KotlinTemplate/releases/assets/322487197.zip",
            checksum: "3f1132608c7a7f76178e8100d9b0e936aadcb8c4c821a311f73590a04586566b"
        )
    ]
)
