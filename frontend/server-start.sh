#!/bin/bash

# 백엔드 서버 실행
echo "Starting backend server..."
cd ../backend/funnyboard/build/libs
java -jar funnyboard-0.0.1-SNAPSHOT.jar &