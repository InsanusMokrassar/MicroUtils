#!/bin/bash

for folder in $(find . -depth -type d -name "main");
do
    newFolder="`echo $folder | sed 's/main/androidMain/g'`"
    mv $folder "$newFolder"
done
