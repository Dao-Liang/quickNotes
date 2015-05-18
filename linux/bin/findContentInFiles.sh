#!/bin/bash

pattern=$1
path=$2

echo "pattern" $1 ", path" $2
grep -r --color=auto -n -i "$1" $2
