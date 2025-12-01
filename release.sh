#!/bin/bash

set -e  # エラーが発生したら終了

# 引数チェック
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
# 1. 古いビルド成果物を削除（キャッシュクリア）
# ========================================
echo "🧹 Cleaning old build artifacts..."

# 生成ファイルの削除
echo "  Checking for specific artifacts..."
[ -f "${MODULE_NAME}/build/xcframework/KotlinMultiplatformLibrary.xcframework" ] && \
    echo "  Removing old xcframework..." && \
    rm -rf "${MODULE_NAME}/build/xcframework"

[ -f "${MODULE_NAME}/build/checksum.txt" ] && \
    echo "  Removing old checksum..." && \
    rm -f "${MODULE_NAME}/build/checksum.txt"

[ -f "${MODULE_NAME}/build/KotlinMultiplatformLibrary.xcframework.zip" ] && \
    echo "  Removing old zip..." && \
    rm -f "${MODULE_NAME}/build/KotlinMultiplatformLibrary.xcframework.zip"

# Gradleキャッシュもクリーン
echo "  Running Gradle clean..."
./gradlew :${MODULE_NAME}:clean

echo "✅ Cleanup completed"

# ========================================
# 2. XCFramework をビルド & パッケージ化
# ========================================
echo "📦 Building XCFramework from scratch..."
./gradlew :${MODULE_NAME}:buildXCFramework

echo "📦 Packaging XCFramework..."
./gradlew :${MODULE_NAME}:packageXCFramework

# ビルド成果物の存在確認
if [ ! -f "${MODULE_NAME}/build/${ASSET_NAME}" ]; then
    echo "❌ Error: ${ASSET_NAME} was not created"
    exit 1
fi

if [ ! -f "${MODULE_NAME}/build/checksum.txt" ]; then
    echo "❌ Error: checksum.txt was not created"
    exit 1
fi

echo "✅ Build artifacts verified"

# ========================================
# 3. チェックサムを取得
# ========================================
CHECKSUM=$(cat ${MODULE_NAME}/build/checksum.txt)
echo "🔑 Checksum: ${CHECKSUM}"

# ========================================
# 4. Git コミットとタグ
# ========================================
echo "📝 Committing version update..."
# build.gradle.ktsのバージョンを更新
sed -i.bak "s/version = \".*\"/version = \"${VERSION}\"/" ${MODULE_NAME}/build.gradle.kts
rm ${MODULE_NAME}/build.gradle.kts.bak

git add ${MODULE_NAME}/build.gradle.kts
git commit -m "Release ${TAG}" || echo "No changes to commit"

# 既存のタグとリリースを削除（存在する場合）
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

echo "🏷️  Creating tag ${TAG}..."
git tag ${TAG}

echo "⬆️  Pushing to GitHub..."
git push origin main
git push origin ${TAG}

# ========================================
# 5. GitHub Release を作成
# ========================================
echo "🎉 Creating GitHub Release..."
gh release create ${TAG} \
  ${MODULE_NAME}/build/${ASSET_NAME} \
  --title "${TAG}" \
  --notes "Release ${TAG}

## Changes
- Update XCFramework
- Checksum: \`${CHECKSUM}\`

## Installation

### For Private Repository
1. Generate a GitHub Personal Access Token with \`repo\` scope
2. Add to \`~/.netrc\`:
\`\`\`
machine api.github.com
  login YOUR_GITHUB_USERNAME
  password YOUR_PERSONAL_ACCESS_TOKEN
\`\`\`

3. Add the package to your \`Package.swift\`:
\`\`\`swift
.package(
    url: \"https://github.com/${REPO_OWNER}/${REPO_NAME}\",
    from: \"${VERSION}\"
)
\`\`\`

### Checksum
\`\`\`
${CHECKSUM}
\`\`\`
"

# ========================================
# 6. Asset IDを取得してPackage.swiftを更新
# ========================================
echo "📝 Getting Asset ID and updating Package.swift..."
sleep 5  # APIの反映を待つ

# GitHub APIでRelease情報を取得
echo "🔍 Fetching release information..."
RELEASE_ID=$(gh api repos/${REPO_OWNER}/${REPO_NAME}/releases/tags/${TAG} | jq -r '.id' 2>/dev/null)
echo "Release ID: ${RELEASE_ID}"

if [ -z "$RELEASE_ID" ] || [ "$RELEASE_ID" = "null" ]; then
    echo "❌ Failed to get Release ID"
    echo "Trying alternative method..."

    # 代替方法: すべてのリリースから検索
    RELEASE_ID=$(gh api repos/${REPO_OWNER}/${REPO_NAME}/releases | jq -r ".[] | select(.tag_name == \"${TAG}\") | .id" 2>/dev/null)

    if [ -z "$RELEASE_ID" ] || [ "$RELEASE_ID" = "null" ]; then
        echo "❌ Still failed to get Release ID"
        echo "Please check the release manually at:"
        echo "https://github.com/${REPO_OWNER}/${REPO_NAME}/releases/tag/${TAG}"
        exit 1
    fi

    echo "Release ID (from alternative method): ${RELEASE_ID}"
fi

# Asset IDを取得
echo "🔍 Fetching asset information..."
ASSET_ID=$(gh api repos/${REPO_OWNER}/${REPO_NAME}/releases/${RELEASE_ID}/assets | jq -r ".[] | select(.name == \"${ASSET_NAME}\") | .id" 2>/dev/null)
echo "Asset ID: ${ASSET_ID}"

if [ -z "$ASSET_ID" ] || [ "$ASSET_ID" = "null" ]; then
    echo "❌ Failed to get Asset ID for ${ASSET_NAME}"
    echo "Available assets:"
    gh api repos/${REPO_OWNER}/${REPO_NAME}/releases/${RELEASE_ID}/assets | jq -r '.[] | "\(.name) (ID: \(.id))"' 2>/dev/null || echo "Failed to list assets"

    echo ""
    echo "You can manually get the Asset ID with:"
    echo "gh api repos/${REPO_OWNER}/${REPO_NAME}/releases/${RELEASE_ID}/assets"
    exit 1
fi

# Package.swiftを更新（API URL形式）
ASSET_URL="https://api.github.com/repos/${REPO_OWNER}/${REPO_NAME}/releases/assets/${ASSET_ID}.zip"

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

echo "✅ Package.swift created with Asset URL"

# Package.swiftをコミット
git add Package.swift
git commit -m "Update Package.swift for ${TAG} with Asset ID ${ASSET_ID}"
git push origin main

echo ""
echo "========================================="
echo "✅ Release ${TAG} completed successfully!"
echo "========================================="
echo "🔗 Release URL: https://github.com/${REPO_OWNER}/${REPO_NAME}/releases/tag/${TAG}"
echo "📦 Asset URL: ${ASSET_URL}"
echo "🔑 Checksum: ${CHECKSUM}"
echo ""
echo "📝 To use this library in a private repository:"
echo "   Add to ~/.netrc:"
echo "   machine api.github.com"
echo "     login YOUR_GITHUB_USERNAME"
echo "     password YOUR_PERSONAL_ACCESS_TOKEN"
echo "========================================="