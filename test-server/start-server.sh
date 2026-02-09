#!/usr/bin/env bash
set -e
ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
echo "[TestServer] Starting Minecraft server (1.21.x) via Gradle task :v1_21_R1:runServer"
cd "$ROOT_DIR"
./gradlew :v1_21_R1:runServer --console=plain
