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
            url: "https://api.github.com/repos/yossibank/KotlinTemplate/releases/assets/323385661.zip",
            checksum: "38f7b99237489337a5a46cce9bedcf14f8eb4cad5a1e02c64953abc9a963da5a"
        )
    ]
)
