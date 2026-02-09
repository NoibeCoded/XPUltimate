Test Server for XP Bottles (Minecraft 1.21.x)
- This module provides a convenient way to run a local Paper server to test the plugin.
- Prerequisites: Java 17 (or as required by your Paper dev bundle), Gradle wrapper present in the repo.
- How to use:
  1. Refresh Gradle in IDE and ensure the Gradle task v1_21_R1:runServer is available.
  2. From the root, run the Gradle task for the test server: ./gradlew :test-server:runServer or use the Gradle tool window.
  3. If the runServer task is not available, you can run the generated server via the script in this directory after building.
- Scripts:
  - start-server.sh: shell script to start the server using the Gradle task.
  - start-server.bat: Windows batch wrapper to start the server.
