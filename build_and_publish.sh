#!/bin/bash

function assert_success() {
    "${@}"
    local status=${?}
    if [ ${status} -ne 0 ]; then
        echo "### Error ${status} at: ${BASH_LINENO[*]} ###"
        exit ${status}
    fi
}

function build_and_publish() {
  export RELEASE_MODE=true
  project="$1"

  assert_success ./gradlew clean "$project:clean" "$project:build" "$project:publishToMavenLocal" "$project:bintrayUpload"
}

pids=()

while [ -n "$1" ]
do
  build_and_publish "$1" &
  pids[${#pids[*]}]=$!
  shift
done

for item in ${pids[*]}
do
  wait "$item"
done
