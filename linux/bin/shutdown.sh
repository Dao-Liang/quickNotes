#!/bin/bash

#update vim config
function update_vim {
    
    echo -e "------------updating auto-vim ------------------------"
    auto_vim=~/gitworkspace/automatic_vim/
    echo "change dir to $auto_vim"
    cd $auto_vim;
    echo "updating automatic_vim"
    git add -A; git commit -m 'update'; git push origin master;


    echo -e "\n\n\n\n-----------updating notes -------------------------"
    note=~/gitworkspace/quickNotes/
    echo "change dir to $note"
    cd $note
    echo "updating quickNotes"
    git add -A; git commit -m 'update'; git push origin master;

}

function turnoff {
    echo -e "\n\n\n\n------------turn off computer------------------------"
    sudo shutdown -h 2;
}

update_vim
turnoff
#sudo shutdown -h $1
