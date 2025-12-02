#!/bin/bash
set -e

if [ -z "$1" ]; then
    echo "Usage: ./release.sh <version>"
    echo "Example: ./release.sh 1.0.0"
    exit 1
fi

VERSION=$1
TAG="v${VERSION}"
RELEASE_VERSION="${TAG}"
REPO_OWNER="yossibank"
REPO_NAME="KotlinTemplate"
MODULE_NAME="kotlinMultiplatformLibrary"
ASSET_NAME="KotlinMultiplatformLibrary.xcframework.zip"
TMP_BRANCH="kmp_release_${VERSION}"

# VERSIONがsemver仕様であるか確認
if [[ ! $VERSION =~ ^[0-9]+\.[0-9]+\.[0-9]+$ ]]; then
    echo "VERSION should be in semver format like 1.0.0"
    exit 1
fi

ZIPFILE="./${MODULE_NAME}/build/${ASSET_NAME}"

echo "🚀 Starting release process for version ${VERSION}..."

# ========================================
# 1. XCFramework をビルド & パッケージ化
# ========================================
echo "📦 Building XCFramework from scratch..."
./gradlew :${MODULE_NAME}:buildXCFramework

echo "📦 Packaging XCFramework..."
./gradlew :${MODULE_NAME}:packageXCFramework

if [ ! -f "${ZIPFILE}" ]; then
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
    echo "⚠️ Tag ${TAG} already exists. Deleting..."
    git tag -d "${TAG}"
    git push origin ":refs/tags/${TAG}" 2>/dev/null || echo "Remote tag doesn't exist"
fi

if gh release view "${RELEASE_VERSION}" >/dev/null 2>&1; then
    echo "⚠️ Release ${RELEASE_VERSION} already exists. Deleting..."
    gh release delete "${RELEASE_VERSION}" --yes
fi

# 一時ブランチが存在する場合は削除
git branch -D ${TMP_BRANCH} 2>/dev/null || true
git push origin --delete ${TMP_BRANCH} 2>/dev/null || true

# ========================================
# 3. タグを取得し、直前のリリースタグを取得
# ========================================
git fetch --tags
PREVIOUS_RELEASE_TAG=$(git tag --sort=-creatordate | head -n 1) || true
echo "📌 Previous release tag: ${PREVIOUS_RELEASE_TAG}"

# ========================================
# 4. ドラフトリリースを作成 & アセットをアップロード
# ========================================
echo "🎉 Creating draft GitHub Release..."
if [ -n "$PREVIOUS_RELEASE_TAG" ]; then
    gh release create ${RELEASE_VERSION} -d --generate-notes --notes-start-tag ${PREVIOUS_RELEASE_TAG}
else
    gh release create ${RELEASE_VERSION} -d --generate-notes
fi

gh release upload ${RELEASE_VERSION} ${ZIPFILE}

# ========================================
# 5. Asset API URLを取得
# ========================================
echo "📝 Retrieving asset API URL..."
sleep 3

ASSET_API_URL=$(gh release view ${RELEASE_VERSION} --json assets | jq -r '.assets[0].apiUrl')

if [ -z "$ASSET_API_URL" ] || [ "$ASSET_API_URL" = "null" ]; then
    echo "❌ Failed to get Asset API URL"
    exit 1
fi

ASSET_API_URL="${ASSET_API_URL}.zip"
echo "📦 Asset API URL: ${ASSET_API_URL}"

# ========================================
# 6. Package.swift と build.gradle.kts を更新
# ========================================
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
            url: "${ASSET_API_URL}",
            checksum: "${CHECKSUM}"
        )
    ]
)
EOF

sed -i.bak "s/version = \".*\"/version = \"${VERSION}\"/" ${MODULE_NAME}/build.gradle.kts
rm ${MODULE_NAME}/build.gradle.kts.bak

# ========================================
# 7. 一時ブランチでコミット & タグ作成
# ========================================
echo "📝 Creating temporary branch and committing..."
git checkout -b ${TMP_BRANCH}
git add Package.swift ${MODULE_NAME}/build.gradle.kts
git commit -m "Release ${TAG}"

echo "🏷️  Creating tag ${TAG}..."
git tag -a ${TAG} -m "KotlinMultiplatformLibrary ${VERSION}"
git push origin ${TAG}

# ========================================
# 8. リリースのタグを更新 & 公開
# ========================================
echo "🔄 Updating release tag and publishing..."
gh release edit ${RELEASE_VERSION} --tag ${TAG} --draft=false

# ========================================
# 9. mainブランチにマージ
# ========================================
echo "🔀 Merging to main..."
git checkout main
git merge ${TMP_BRANCH}
git push origin main

# 一時ブランチを削除
git branch -d ${TMP_BRANCH}

echo ""
echo "========================================="
echo "✅ Release ${TAG} completed successfully!"
echo "========================================="
echo "🔗 Release URL: https://github.com/${REPO_OWNER}/${REPO_NAME}/releases/tag/${TAG}"
echo "📦 Asset URL: ${ASSET_API_URL}"
echo "🔑 Checksum: ${CHECKSUM}"
echo ""
echo "📝 To use this library:"
echo "   Add to ~/.netrc:"
echo "   machine api.github.com"
echo "     login YOUR_GITHUB_USERNAME"
echo "     password YOUR_PERSONAL_ACCESS_TOKEN"
echo "========================================="