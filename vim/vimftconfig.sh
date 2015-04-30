#!/bin/bash

#script to config filetype plugins for vim

#filetype name
filetype=$1

#already configured file in current .vim folder
color_file=.vim/ftplugin/${filetype}/color.vim
common_file=.vim/ftplugin/${filetype}/common.vim

target_color_file=~/.vim/ftplugin/${filetype}/color.vim
target_common_file=~/.vim/ftplugin/${filetype}/common.vim


#copy the already configured files into system vim .vim folder

echo "copying $color_file into $target_color_file";

cp $color_file  $target_color_file

echo "copying $common_file into $target_common_file";

cp $common_file  $target_common_file


