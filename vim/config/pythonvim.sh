#!/usr/bin/bash

#script to config sh filetype edit in vim
filetype="python"

color_file=~/.vim/ftplugin/${filetype}/color.vim
echo $color_file

common_file=~/.vim/ftplugin/${filetype}/common.vim
echo $common_file


cat <<EOF >>$color_file
set background=dark
color darkblue
EOF

cat <<EOF >>$common_file
let g:pydiction_location='~/.vim/bundle/pydiction/complete-dict'
let g:pydiction_menu_height=5
EOF
