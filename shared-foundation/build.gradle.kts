import org.apache.commons.io.output.ByteArrayOutputStream

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.android.lint)
    alias(libs.plugins.skie)
}

group = "io.github.yossibank.kotlinmultiplatformlibrary"
version = "1.2.4"

kotlin {
    androidLibrary {
        namespace = "io.github.yossibank.kotlinmultiplatformlibrary"
        compileSdk = 36
        minSdk = 24

        withHostTestBuilder {}.configure {}
    }

    val iosTargets = listOf(
        iosArm64(),
        iosSimulatorArm64()
    )

    iosTargets.forEach {
        it.binaries.framework {
            baseName = "KotlinMultiplatformLibrary"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
            implementation(libs.skie.annotations)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

skie {
    build {
        produceDistributableFramework()
    }
}

// BuildXCFrameworkTask
abstract class BuildXCFrameworkTask : DefaultTask() {
    @get:Inject
    abstract val execOperations: ExecOperations

    @get:Input
    abstract val frameworkName: Property<String>

    @get:InputDirectory
    abstract val projectDir: DirectoryProperty

    @get:InputDirectory
    abstract val iosArm64Framework: DirectoryProperty

    @get:InputDirectory
    abstract val iosSimulatorArm64Framework: DirectoryProperty

    @get:OutputDirectory
    abstract val xcframeworkDir: DirectoryProperty

    @TaskAction
    fun execute() {
        val arm64 = iosArm64Framework.get().asFile
        val simulator = iosSimulatorArm64Framework.get().asFile
        val output = xcframeworkDir.get().asFile
        val workDir = projectDir.get().asFile

        // 既存の XCFramework を削除
        if (output.exists()) {
            output.deleteRecursively()
        }

        // XCFramework を作成
        execOperations.exec {
            workingDir(workDir)
            commandLine(
                "xcodebuild", "-create-xcframework",
                "-framework", arm64.absolutePath,
                "-framework", simulator.absolutePath,
                "-output", output.absolutePath
            )
        }

        logger.lifecycle("✅ XCFramework created at: ${output.absolutePath}")
    }
}

tasks.register<BuildXCFrameworkTask>("buildXCFramework") {
    dependsOn(
        "linkReleaseFrameworkIosArm64",
        "linkReleaseFrameworkIosSimulatorArm64"
    )

    val frameworkNameValue = "KotlinMultiplatformLibrary"
    val buildDir = layout.buildDirectory.get().asFile

    group = "build"
    description = "Create XCFramework for iOS"

    frameworkName.set(frameworkNameValue)
    projectDir.set(layout.projectDirectory)
    iosArm64Framework.set(file("$buildDir/bin/iosArm64/releaseFramework/$frameworkNameValue.framework"))
    iosSimulatorArm64Framework.set(file("$buildDir/bin/iosSimulatorArm64/releaseFramework/$frameworkNameValue.framework"))
    xcframeworkDir.set(file("$buildDir/xcframework/$frameworkNameValue.xcframework"))
}

// PackageXCFrameworkTask
abstract class PackageXCFrameworkTask : DefaultTask() {
    @get:Inject
    abstract val execOperations: ExecOperations

    @get:Input
    abstract val xcframeworkName: Property<String>

    @get:InputDirectory
    abstract val projectDir: DirectoryProperty

    @get:InputDirectory
    abstract val xcframeworkDir: DirectoryProperty

    @get:OutputFile
    abstract val zipFile: RegularFileProperty

    @get:OutputFile
    abstract val checksumFile: RegularFileProperty

    @TaskAction
    fun execute() {
        val xcfDir = xcframeworkDir.get().asFile
        val zip = zipFile.get().asFile
        val checksum = checksumFile.get().asFile
        val workDir = projectDir.get().asFile

        // 既存の zip を削除
        if (zip.exists()) {
            zip.delete()
        }

        // zip 化
        execOperations.exec {
            workingDir(xcfDir.parentFile)
            commandLine("zip", "-r", "-y", zip.absolutePath, xcfDir.name)
        }

        // チェックサム計算
        val checksumOutput = ByteArrayOutputStream()
        execOperations.exec {
            workingDir(workDir)
            commandLine(
                "swift",
                "package",
                "compute-checksum",
                zip.absolutePath
            )
            standardOutput = checksumOutput
        }

        val checksumValue = checksumOutput.toString().trim()
        checksum.writeText(checksumValue)

        logger.lifecycle("\n✅ XCFramework packaged successfully!")
        logger.lifecycle("📦 Location: ${zip.absolutePath}")
        logger.lifecycle("🔑 Checksum: $checksumValue")
        logger.lifecycle("📝 Checksum saved to: ${checksum.absolutePath}")
        logger.lifecycle("\nUpdate Package.swift with:")
        logger.lifecycle(
            """
            .binaryTarget(
                name: "${xcframeworkName.get()}",
                url: "https://github.com/yossibank/KotlinTemplate/releases/download/VERSION/${xcframeworkName.get()}.xcframework.zip",
                checksum: "$checksumValue"
            )
        """.trimIndent()
        )
    }
}

tasks.register<PackageXCFrameworkTask>("packageXCFramework") {
    dependsOn("buildXCFramework")

    val frameworkNameValue = "KotlinMultiplatformLibrary"
    val buildDir = layout.buildDirectory.get().asFile

    group = "build"
    description = "Package XCFramework as zip with checksum"

    xcframeworkName.set(frameworkNameValue)
    projectDir.set(layout.projectDirectory)
    xcframeworkDir.set(file("$buildDir/xcframework/$frameworkNameValue.xcframework"))
    zipFile.set(file("$buildDir/$frameworkNameValue.xcframework.zip"))
    checksumFile.set(file("$buildDir/checksum.txt"))
}