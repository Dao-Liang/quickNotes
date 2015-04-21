#!/usr/bin/bash

#script to config sh filetype edit in vim
filetype="sh"

color_file=~/.vim/ftplugin/${filetype}/color.vim
echo $color_file

common_file=~/.vim/ftplugin/${filetype}/common.vim
echo $common_file


cat <<EOF >>$color_file
color default
EOF

cat <<EOF >$common_file

iabbrev if if [  ]; then<CR><CR>fi<Esc>2kf[l

EOF
