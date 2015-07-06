#!/bin/bash

exp="$1"
path=$2
#function used to find a file in specified path with specified filename pattern
function findFileByNameInPath {

    if [[ $# < 2 ]]; then
        echo "Usage findFileByNameInPath filename_pattern path"
        return
    fi

    filename=$1
    path=$2
    
    find $2 -name $1
}

findFileByNameInPath  $exp $path
