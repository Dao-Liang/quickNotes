#!/bin/bash
set -eu

count=0

while [[ $count < 10 ]]; do

    echo "count $count"

    let count++
done

