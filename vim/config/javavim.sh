#!/usr/bin/bash

#script to config sh filetype edit in vim
filetype="java"

color_file=~/.vim/ftplugin/${filetype}/color.vim
echo $color_file

common_file=~/.vim/ftplugin/${filetype}/common.vim
echo $common_file


cat <<EOF >>$color_file
set background=dark
color molokai 
EOF

cat <<EOF >$common_file
iabbrev if 	if(){<CR><CR><Esc>2kf)h
iabbrev ifel if(){<CR><CR><Esc>Aelse{<CR><CR><Esc>4k0f)h
iabbrev while while(){<CR><CR><Esc>2kf)h
iabbrev for for(){<CR><CR><Esc>2kf)h
iabbrev main public static void main(String[] args){<CR><Esc>1ko
iabbrev out System.out.println();<Esc>0f)h
EOF


