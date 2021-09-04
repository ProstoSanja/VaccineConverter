@echo off

cd frontend || exit
call yarn install
call yarn build
cd ../
call ./gradlew clean
call ./gradlew bootJar
for /F %%i in ('git rev-parse --short HEAD') do docker build -t prostosanja/vaccineconverter:latest -t prostosanja/vaccineconverter:%%i .
docker push prostosanja/vaccineconverter --all-tags
