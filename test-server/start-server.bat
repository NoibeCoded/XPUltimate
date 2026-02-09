@echo off
setlocal
set ROOT_DIR=%~dp0.. 
cd /d "%ROOT_DIR%"
echo Starting Minecraft server (1.21.x) via Gradle task :v1_21_R1:runServer
gradlew.bat :v1_21_R1:runServer --console=plain
