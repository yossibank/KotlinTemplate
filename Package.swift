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
            url: "https://api.github.com/repos/yossibank/KotlinTemplate/releases/assets/323376802.zip",
            checksum: "4539beb686054b7cbaf13c54fce9eb86a2ad30d044a6fe9f142d034de69b3002"
        )
    ]
)
