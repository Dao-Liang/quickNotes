#!/bin/bash

#install vim
if [ -z $(which vim) ]; then
	echo "no vim exists on this sytem, installing vim!"
	sudo apt-get install vim;
fi
#install git
if [ -z $(which git) ]; then
	echo "no git exists on this sytem, installing git!"
	sudo apt-get install git;
fi

#create $myvimrc file
if [ -f ~/.vimrc ]; then
	echo "there is old .vimrc exists, delete it"
	rm ~/.vimrc
fi

echo "creating .vimrc file for vim configuration"
touch ~/.vimrc;

#create .vim bundle dir
if [ ! -d ~/.vim/bundle ]; then
	echo "creating bundle dir for Vundle plugin of vim"
	mkdir -p ~/.vim/bundle;
fi

#create .vim color dir
if [ ! -d ~/.vim/colors ]; then
	echo "creating colors dir for vim"
	mkdir -p ~/.vim/colors;
fi

mkftdir(){

	target_dir=~/.vim/ftplugin/$1
	
	if [ ! -d $target_dir ];then
		mkdir -p $target_dir	
	else
		return
	fi

	target_color=$target_dir/color.vim
	target_common=$target_dir/common.vim
	touch $target_color
	touch $target_common


}

#create .vim ftplugin dir
if [ ! -d ~/.vim/ftplugin ]; then
	echo "creating ftplugin dir for vim"
	mkdir -p ~/.vim/ftplugin
fi

#create ftplugin for different types file
mkftdir java
. ./javavim.sh

mkftdir sh
. ./shvim.sh

mkftdir python
. ./pythonvim.sh

#install vundle for vim
if [ ! -d ~/.vim/bundle/Vundle.vim ];then
	echo "clone Vundle into current system for vim plugin management"
	git clone https://github.com/gmarik/Vundle.vim.git ~/.vim/bundle/Vundle.vim;
fi

#install molokai color for vim
if [ ! -f ~/.vim/colors/molokai.vim ];then
	echo "installing molokai colorscheme for vim"
	git clone https://github.com/tomasr/molokai.git ~/.vim/molokai
	mv ~/.vim/molokai/colors/molokai.vim ~/.vim/colors
	rm -rf ~/.vim/molokai
fi

#write the plugin config into .vimrc
echo "configuaring plugins for vim"
cat <<EOF >>~/.vimrc
set nocompatible
filetype off

set rtp+=~/.vim/bundle/Vundle.vim
call vundle#begin()

"Plugins
Plugin 'gmarik/Vundle.vim'
Plugin 'scrooloose/nerdtree'
Plugin 'Nopik/vim-nerdtree-direnter'
Plugin 'jistr/vim-nerdtree-tabs'
Plugin 'vim-scripts/taglist.vim'
Plugin 'bling/vim-airline'
Plugin 'rkulla/pydiction'

call vundle#end()
filetype plugin indent on
EOF

#run vim in commandline to install 
vim +PluginInstall +qall

#write vim plugin configurations into .vimrc
cat <<EOF >>~/.vimrc


"-------vim plugin configurations
let g:nerdtree_tabs_open_on_console_startup=0
let g:nerdtree_tabs_open_on_gui_startup=0

let NERDTreeMapOpenInTab='<Enter>'
map <leader>n <plug>NERDTreeTabsToggle<CR>
map <leader>tl :TlistToggle<CR>
let Tlist_Use_Right_Window=1
set laststatus=2 " enable airline to display status
let g:airline_powerline_fonts=1 "using fonts from powerline
"------------------------------
EOF


#write some common vim config into .vimrc
cat <<EOF >>~/.vimrc

"--------vim common configurations
set incsearch
set nu
set ruler
set autoindent
set hlsearch
set ignorecase smartcase
set mouse=a
set showcmd
set tabstop=4
set wildmenu
color molokai
syntax on
"---------------------"

EOF

#write vim environment into .vimrc
cat <<EOF >>~/.vimrc
"------vim environment configuration"
set path+=$PWD/**

"--------------------"

EOF


#write vim mappings into .vimrc
cat <<EOF >>~/.vimrc
"------vim mapping configuration"
map <leader>s <ESC>:w<CR>
map <leader>yc <ESC>ggVG$"+y
map <leader>cp "+yy
map <leader>v "+p
map <leader>f :tabfind
map <leader>e :e<CR>
vnoremap <leader>y "+y

nmap lb 0
nmap le $

"windows navigation jump
map <c-h> <c-w>h
map <c-j> <c-w>j
map <c-k> <c-w>k
map <c-l> <c-w>l

"tags navigation configs
nnoremap <C-Left> :tabprevious<CR>
nnoremap <C-Right> :tabnext<CR>
"-----------------"

EOF

#write brakets function control into .vimrc
cat <<EOF >>~/.vimrc

"------vim braket and quote complete"
inoremap ( ()<Esc>i
inoremap [ []<Esc>i
inoremap { {}<Esc>i
inoremap < <><Esc>i
inoremap ) <c-r>=ClosePair(')')<CR>
inoremap } <c-r>=ClosePair('}')<CR>
inoremap ] <c-r>=ClosePair(']')<CR>
inoremap > <c-r>=ClosePair('>')<CR>

inoremap " <c-r>=QuoteDelim('"')<CR>
inoremap ' <c-r>=QuoteDelim("'")<CR>

function! ClosePair(char)
	if getline('.')[col('.') - 1]==a:char
		return "\<Right>"
	else
		return a:char
	endif
endfunction

function! CloseBracket(char)
	if match(getline(line('.') + 1),'\s*}')<0
		return "<CR>}"
	else
		return "\<Esc>j0f}a"
	endif
endfunction

function! QuoteDelim(char)
	let line=getline('.')
	let col=col('.')
	if line[col - 2] == "\\\\"
		return a:char
	elseif line[col - 1] == a:char
		return "\\<Right>"
	else
		return a:char.a:char."\\<Esc>i"
	endif
endfunction
"----------------"

EOF


#write gvim config into .vimrc
cat <<EOF >>~/.vimrc
"------Gvim configurations"
"hide gvim menu bar
set guioptions-=m

"hide gvim toolbar
set guioptions-=T

"hide gvim right-hand scroll bar
"set guioptions-=r

"hide gvim left-hand scroll bar
"set guioptions-=L

"gvim font
set guifont=UbuntuMono\ 9

"--------------------"
EOF
