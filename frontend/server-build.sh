#!/bin/bash

# 백엔드 서버 빌드
echo "Building backend server..."
cd ../backend/funnyboard
./gradlew.bat clean build