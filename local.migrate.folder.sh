#!/bin/bash

function renameFolders() {
  for folder in $(find . -depth -type d -name "$1");
  do
      sedString="s/$1/$2/g"
      newFolder="$(echo $folder | sed $sedString)"
      echo $folder "$newFolder"
  done
}

renameFolders "androidTest" "androidUnitTest"
