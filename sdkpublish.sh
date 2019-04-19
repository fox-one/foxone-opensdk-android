#!/usr/bin/env bash

echo "start sdk packaging and publishing ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"

PROJECT_PATH=`pwd`
echo "project dir:: ${PROJECT_PATH}"

for m in support-common support-component support-framework support-debug support-debug-no-op passport payment ex otc cloud; do
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