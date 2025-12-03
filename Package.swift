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
            url: "https://api.github.com/repos/yossibank/KotlinTemplate/releases/assets/323842541.zip",
            checksum: "f4dcf212d5dc9dd976b69a8d47f81888715b3f80e2f629736d22dc0ca0cb9df6"
        )
    ]
)
