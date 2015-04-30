#!/bin/bash

#filename FileTypePlugin_Install.sh

#usage: install user-defined ftplugin files

# 1. create ftplugin in system

if [ ! -d ~/.vim/ftplugin ]; then
	echo "creating ftplugin dir for vim"
	mkdir -p ~/.vim/ftplugin
fi


#you can using comment or delete comment to decided use which filetype you want
#plugin for java filetype
install_ftplugin java
#plugin for xhtml filetype
install_ftplugin xhtml
#plugin for xml filetype
install_ftplugin xml
#plugin for markdown filetype
install_ftplugin markdown
#plugin for python filetype
install_ftplugin python
#plugin for sh filetype
install_ftplugin sh
#plugin for vim filetype
install_ftplugin vim



#function to config filetype plugins for vim in system
function install_ftplugin {
	
	#filetype name
	filetype=$1
	
	#already configured file in current .vim folder
	color_file=./.vim/ftplugin/${filetype}/color.vim
	common_file=./.vim/ftplugin/${filetype}/common.vim
	
	
	target_color_file=~/.vim/ftplugin/${filetype}/color.vim
	target_common_file=~/.vim/ftplugin/${filetype}/common.vim
	
	source_dir=./.vim/ftplugin/${filetype}
	target_dir=~/.vim/ftplugin/${filetype}
	
	if [[ -d $source_dir ]]; then
		mkdir -p $target_dir
	fi
	
	#copy the already configured files into system vim .vim folder
	
	echo "copying $color_file into $target_color_file";
	if [[ -f $color_file  ]]; then
		cp $color_file  $target_color_file
	fi
	
	echo "copying $common_file into $target_common_file";
	if [[ -f $common_file ]]; then
		cp $common_file  $target_common_file
	fi
}
