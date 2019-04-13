#!/usr/bin/env bash

rm -rf /opt/workspace/* &&
cd /opt/workspace &&
git clone --depth 1 https://github.com/vvoskanyan/xRandNetAPI.git ;
cd xRandNetAPI &&
/opt/gradle/bin/gradle build -x test &&
pkill java &&
rm -rf /opt/xrandnet/* &&
cp build/libs/xRandnetAPI-1.0.0.jar /opt/xrandnet/ &&
cd ~ &&
rm -rf /opt/workspace/* &&
nohup java -jar /opt/xrandnet/xRandnetAPI-1.0.0.jar  --Dspring.config.name=src/main/resources/application.prod.properties &
