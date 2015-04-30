#!/bin/bash

#filename vundle_instal.sh

#usage: install vundle for vim

# 1.installing vundle into local folder 

if [ ! -d ./.vim/bundle ]; then
	echo "creating bundle dir for Vundle plugin of vim"
	mkdir -p ./.vim/bundle;
fi

#install vundle for vim
if [ ! -d ./.vim/bundle/Vundle.vim ];then
	echo "clone Vundle into current system for vim plugin management"
	git clone https://github.com/gmarik/Vundle.vim.git ./.vim/bundle/Vundle.vim;
fi

# 2. install local folder into system

if [ ! -d ~/.vim/bundle ]; then
	echo "creating bundle dir for Vundle plugin of vim"
	mkdir -p ~/.vim/bundle;
fi

#install vundle for vim
if [ ! -d ~/.vim/bundle/Vundle.vim ];then
	echo "clone Vundle into current system for vim plugin management"

	cp -r ./.vim/bundle/Vundle.vim ~/.vim/bundle/
fi


