#!/bin/bash
cd frontend || exit
yarn install
yarn build
cd ../
./gradlew clean
./gradlew bootJar
docker build -t prostosanja/vaccineconverter:latest -t prostosanja/vaccineconverter:$(git rev-parse --short HEAD) .
docker push prostosanja/vaccineconverter --all-tags
