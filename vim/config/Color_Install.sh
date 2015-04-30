#!/bin/bash

#filename color_install.sh

#usage: used to install colorscheme for vim

if [ ! -d ~/.vim/colors ]; then
	echo "creating colors dir for vim"
	mkdir -p ~/.vim/colors;
fi


if [ ! -f ./.vim/colors/molokai.vim ]; then

	echo "installing molokai colorscheme for vim"

	git clone https://github.com/tomasr/molokai.git ./.vim/molokai
	mv ./.vim/molokai/colors/molokai.vim  ./.vim/colors
	cp ./.vim/colors/molokai.vim  ~/.vim/colors

	rm -rf ../.vim/molokai
else
	echo "copying molokai into ~/.vim/colors"
	cp ./.vim/colors/molokai.vim ~/.vim/colors
fi

