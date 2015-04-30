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
if [ -f ./.vimrc ]; then
	echo "there is old .vimrc exists, delete it"
	rm ./.vimrc
fi

#create all configuration file locally and then move them to system


#=================create following folders for vim
#	bundle
#	colors
#	ftplugin

#===========configure vundle plugin manager for vim
. ./config/Vundle_Install.sh
#============================================

#========install colorscheme for vim=========
. ./config/Color_Install.sh

#============================================



#====configure self-defined filetypes for vim
. ./config/FileTypePlugin_Install.sh
#============================================


#==============create local .vimrc file

#==========configure vundle plugins for vim
echo "configuaring vundle plugins for vim"
cat < ./config/Vundle_Plugins.vim >>./.vimrc

#config vim common behaviors
cat < ./config/Common_config.vim >>./.vimrc
#run vim in commandline to install 
vim +PluginInstall +qall
#============================================

#============================================
cat < ./config/Color_Config.vim >> ./.vimrc
#============================================


#===========configure mappings for .vimrc
cat < ./config/Mapping_Config.vim >>./.vimrc
#============================================

#==========configure vundle plugins configuration for vim
cat < ./config/Vundle_Plugins_Cofig.vim >>./.vimrc
#============================================


#==========config vim envirment==============
cat < ./config/Environment_Config.vim >> ./.vimrc
#========================================


#write gvim config into .vimrc
cat < ./config/Gvim_Config.vim >>./.vimrc


#copy current .vimrc to system .vimrc
echo "creating .vimrc file for vim configuration"
cp ./.vimrc ~/.vimrc
