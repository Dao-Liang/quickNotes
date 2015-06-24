#!/bin/bash

repos=( ~/gitworkspace/quickNotes ~/gitworkspace/automatic_vim ~/gitworkspace/django18_doc_cn)

for repo in ${repos[@]}; do
    echo -e "\n---------updating in repository $repo-------\n"
    cd $repo
    git fetch origin 
    git pull origin master 
done


