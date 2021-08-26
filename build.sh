#!/bin/bash
cd frontend || exit
yarn install
yarn build
cd ../
./gradlew clean
./gradlew bootJar
docker build -t prostosanja/vaccineconverter:latest .
docker push prostosanja/vaccineconverter:latest
