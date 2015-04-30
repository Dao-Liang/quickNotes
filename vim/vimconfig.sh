#!/bin/bash

#install vim
if [ -z $(which vim) ]; then
	echo "no vim exists on this sytem, installing vim!"
	sudo apt-get install vim;
fi

#install git
#actually when you get this file you already have git ^_^
if [ -z $(which git) ]; then
	echo "no git exists on this sytem, installing git!"
	sudo apt-get install git;
fi

#create $myvimrc file
if [ -f ~/.vimrc ]; then
	echo "there is old .vimrc exists, delete it"
#	rm ~/.vimrc
fi

#create all configuration file locally and then move them to system


#=================create following folders for vim
#	bundle
#	colors
#	ftplugin

#===========configure vundle plugin manager for vim
if [ ! -d ~/.vim/bundle ]; then
	echo "creating bundle dir for Vundle plugin of vim"
	mkdir -p ~/.vim/bundle;
fi

#install vundle for vim
if [ ! -d ~/.vim/bundle/Vundle.vim ];then
	echo "clone Vundle into current system for vim plugin management"
	git clone https://github.com/gmarik/Vundle.vim.git ~/.vim/bundle/Vundle.vim;
fi
#============================================






#========confugre self-defined colors for vim
if [ ! -d ~/.vim/colors ]; then
	echo "creating colors dir for vim"
	mkdir -p ~/.vim/colors;
fi

if [ ! -f ~/.vim/colors/molokai.vim ];then
	echo "installing molokai colorscheme for vim"
	git clone https://github.com/tomasr/molokai.git ~/.vim/molokai
	mv ~/.vim/molokai/colors/molokai.vim ~/.vim/colors
	rm -rf ~/.vim/molokai
fi
#============================================



#====configure self-defined filetypes for vim
if [ ! -d ~/.vim/ftplugin ]; then
	echo "creating ftplugin dir for vim"
	mkdir -p ~/.vim/ftplugin
fi

. ./vim_ftpluginconfig.sh
#============================================


#==============create local .vimrc file

#==========configure vundle plugins for vim
echo "configuaring vundle plugins for vim"
cat < vim_vundle_plugins.vim >>./.vimrc

#config vim common behaviors
cat < vim_common_config.vim >>./.vimrc
#run vim in commandline to install 
vim +PluginInstall +qall
#============================================


#===========configure mappings for .vimrc
cat < vim_mapping_config.vim >>./.vimrc
#============================================

#==========configure vundle plugins configuration for vim
cat < vim_vundle_plugins_config.vim  >>./.vimrc
#============================================


#==========config vim envirment==============
cat < vim_environment_config.vim >> ./.vimrc
#========================================


#write gvim config into .vimrc
cat < gvim_common_config.vim >>./.vimrc


#copy current .vimrc to system .vimrc
echo "creating .vimrc file for vim configuration"
cp ./.vimrc ~/.vimrc
