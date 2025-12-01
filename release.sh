#!/bin/bash

set -e

if [ -z "$1" ]; then
    echo "Usage: ./release.sh <version>"
    echo "Example: ./release.sh 1.0.0"
    exit 1
fi

VERSION=$1
TAG="v${VERSION}"
REPO_OWNER="yossibank"
REPO_NAME="KotlinTemplate"
MODULE_NAME="kotlinMultiplatformLibrary"
ASSET_NAME="KotlinMultiplatformLibrary.xcframework.zip"

echo "🚀 Starting release process for version ${VERSION}..."

# ========================================
# 0. キャッシュを完全にクリア
# ========================================
echo "🧹 Cleaning all caches..."
./gradlew clean
rm -rf ${MODULE_NAME}/build
rm -rf build
rm -rf .gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin
rm -rf ~/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-native*

# ========================================
# 1. XCFramework をビルド & パッケージ化
# ========================================
echo "📦 Building XCFramework from scratch..."
./gradlew :${MODULE_NAME}:buildXCFramework --rerun-tasks --no-build-cache

echo "📦 Packaging XCFramework..."
./gradlew :${MODULE_NAME}:packageXCFramework --rerun-tasks --no-build-cache

if [ ! -f "${MODULE_NAME}/build/${ASSET_NAME}" ]; then
    echo "❌ Error: ${ASSET_NAME} was not created"
    exit 1
fi

if [ ! -f "${MODULE_NAME}/build/checksum.txt" ]; then
    echo "❌ Error: checksum.txt was not created"
    exit 1
fi

echo "✅ Build artifacts verified"

CHECKSUM=$(cat ${MODULE_NAME}/build/checksum.txt)
echo "🔑 Checksum: ${CHECKSUM}"

# ========================================
# 2. 既存のタグとリリースを削除
# ========================================
echo "🔍 Checking for existing tag and release..."
if git rev-parse "${TAG}" >/dev/null 2>&1; then
    echo "⚠️  Tag ${TAG} already exists. Deleting..."
    git tag -d "${TAG}"
    git push origin ":refs/tags/${TAG}" 2>/dev/null || echo "Remote tag doesn't exist"
fi

if gh release view "${TAG}" >/dev/null 2>&1; then
    echo "⚠️  Release ${TAG} already exists. Deleting..."
    gh release delete "${TAG}" --yes
fi

# ========================================
# 3. GitHub Release を作成（タグなしで先にアセットをアップロード）
# ========================================
echo "🎉 Creating GitHub Release..."
gh release create ${TAG} \
  ${MODULE_NAME}/build/${ASSET_NAME} \
  --title "${TAG}" \
  --notes "Release ${TAG}

## Changes
- Update XCFramework
- Checksum: \`${CHECKSUM}\`
"

# ========================================
# 4. Asset IDを取得
# ========================================
echo "📝 Getting Asset ID..."
sleep 5

RELEASE_ID=$(gh api repos/${REPO_OWNER}/${REPO_NAME}/releases/tags/${TAG} | jq -r '.id' 2>/dev/null)
echo "Release ID: ${RELEASE_ID}"

if [ -z "$RELEASE_ID" ] || [ "$RELEASE_ID" = "null" ]; then
    echo "❌ Failed to get Release ID"
    exit 1
fi

ASSET_ID=$(gh api repos/${REPO_OWNER}/${REPO_NAME}/releases/${RELEASE_ID}/assets | jq -r ".[] | select(.name == \"${ASSET_NAME}\") | .id" 2>/dev/null)
echo "Asset ID: ${ASSET_ID}"

if [ -z "$ASSET_ID" ] || [ "$ASSET_ID" = "null" ]; then
    echo "❌ Failed to get Asset ID"
    exit 1
fi

# ========================================
# 5. Package.swift と build.gradle.kts を更新
# ========================================
ASSET_URL="https://github.com/${REPO_OWNER}/${REPO_NAME}/releases/download/${TAG}/${ASSET_NAME}"

echo "📝 Creating Package.swift..."
cat > Package.swift << EOF
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
            url: "${ASSET_URL}",
            checksum: "${CHECKSUM}"
        )
    ]
)
EOF

sed -i.bak "s/version = \".*\"/version = \"${VERSION}\"/" ${MODULE_NAME}/build.gradle.kts
rm ${MODULE_NAME}/build.gradle.kts.bak

# ========================================
# 6. コミット & タグを更新
# ========================================
echo "📝 Committing changes..."
git add Package.swift ${MODULE_NAME}/build.gradle.kts
git commit -m "Release ${TAG}"
git push origin main

# 古いタグを削除して新しいタグを作成
echo "🏷️  Updating tag ${TAG}..."
git tag -d "${TAG}" 2>/dev/null || true
git push origin ":refs/tags/${TAG}" 2>/dev/null || true
gh release delete "${TAG}" --yes 2>/dev/null || true

git tag ${TAG}
git push origin ${TAG}

# リリースを再作成
gh release create ${TAG} \
  ${MODULE_NAME}/build/${ASSET_NAME} \
  --title "${TAG}" \
  --notes "Release ${TAG}

## Changes
- Update XCFramework
- Checksum: \`${CHECKSUM}\`

## Installation
\`\`\`swift
.package(
    url: \"https://github.com/${REPO_OWNER}/${REPO_NAME}\",
    from: \"${VERSION}\"
)
\`\`\`
"

echo ""
echo "========================================="
echo "✅ Release ${TAG} completed successfully!"
echo "========================================="
echo "🔗 Release URL: https://github.com/${REPO_OWNER}/${REPO_NAME}/releases/tag/${TAG}"
echo "📦 Asset URL: ${ASSET_URL}"
echo "🔑 Checksum: ${CHECKSUM}"
echo "========================================="