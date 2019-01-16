#!/usr/bin/env bash
mkdir ~/$2/
find . -name $1 -print0 | while IFS= read -r -d $'\0' file; do
    rsync -rR $(dirname "$file") ~/$2/
done