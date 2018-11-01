#!/bin/sh

./gradlew :base:testDebug :landing:testDebug :places:testDebug :weather:testDebug --no-daemon --build-cache -x compileDebugRenderscript -x lint -Dorg.gradle.jvmargs="-Xmx3g -XX:MaxPermSize=4g -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8" --stacktrace