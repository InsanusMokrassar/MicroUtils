#!/bin/bash

function assert_success() {
    "${@}"
    local status=${?}
    if [ ${status} -ne 0 ]; then
        echo "### Error ${status} at: ${BASH_LINENO[*]} ###"
        exit ${status}
    fi
}

export RELEASE_MODE=true
project="$1"

assert_success ./gradlew clean
assert_success ./gradlew "$project:build"
assert_success ./gradlew "$project:publishToMavenLocal"
assert_success ./gradlew "$project:bintrayUpload"
