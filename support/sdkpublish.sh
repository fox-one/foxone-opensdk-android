#!/usr/bin/env bash

echo "start sdk packaging and publishing ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"

PROJECT_PATH=`pwd`
echo "project dir:: ${PROJECT_PATH}"

for m in utils components framework resources account_core account assets_core assets market_core market opensdk; do
    curPath="${PROJECT_PATH}/${m}"
    echo "now working at ${curPath} --------------"

    cd ${curPath}
    pwd
    echo "command: assembleRelease ++++++"
    ../gradlew assembleRelease
    echo "command: install ++++++"
    ../gradlew install
    echo "command: bintrayUpload ++++++"
    ../gradlew bintrayUpload
done

echo "Complete SDK publishing ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"