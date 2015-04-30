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

"=================filetype settings========================"
"setting filetype 
"Markdown
au BufNewFile,BufRead *.md setf markdown
"------------------"



"======================common vim configurations=============
filetype plugin indent on
set incsearch
set nu
set ruler
set autoindent
set tabstop=4
set hlsearch
set ignorecase smartcase
set mouse=a
set showcmd
" vim 自身命令行模式智能补全
set wildmenu
syntax on

"set working path to find file
"set path+=/home/local/RISKOFFICE/liang.guisheng/ROWorkspace/**
set path+=$PWD/**
" set tags file place
set tags+=./tags,tags
"============================================================"


"===============config color for vim========="
colorscheme molokai
"============================================"

"================mappings====================================
map <leader>pyr :!python 
map <leader>s	 <ESC>:w<CR>
map <leader>dp	:w<CR>:!ant deploy-exploded-dev<CR>

"导航映射"
nmap ,b  0
nmap ,e  $
nmap <leader>pa %

"copy to system clipboard
map <leader>yc  <Esc>ggvG$"+y

map <c-h> <c-w>h 
map <c-l> <c-w>l 
map <c-j> <c-w>j 
map <c-k> <c-w>k 
map <leader>cp "+yy
map <leader>v "+p
map <leader>f :tabfind 
map <leader>e :e<CR>
map <leader>c :!
map <leader>x :x<CR>
"autocomplete match bracks
inoremap ( ()<Esc>i
inoremap [ []<Esc>i
inoremap { {}<Esc>i
inoremap < <><Esc>i
inoremap ) <c-r>=ClosePair(')')<CR>
inoremap ] <c-r>=ClosePair(']')<CR>
inoremap > <c-r>=ClosePair('>')<CR>
inoremap } <c-r>=ClosePair('}')<CR>
inoremap " <c-r>=QuoteDelim('"')<CR>
inoremap ' <c-r>=QuoteDelim("'")<CR>
inoremap ` <c-r>=QuoteDelim("`")<CR>
inoremap  <Tab> <c-r>=SkipBracket()<CR>


"tab navigation configs"
nnoremap <C-Left> :tabprevious<CR>
nnoremap <C-Right> :tabnext<CR>
"===================================================="


"====================user-define functions======================"
"use <tab> will jump the following chars
function! SkipBracket()
	let line=getline('.')
	let col=col('.')
	let target=line[col-1]
	echo target
	if match(target,"[\\]\\)\\}>'\"`,\\.:]") <0
		echo target "not matched"
		return "\<Tab>"
	else
		echo target "matching"
		return "\<Right>"
	endif 
endfunction

"to avoid type right bracket
function! ClosePair(char)
	if getline('.')[col('.')-1] == a:char
		return "\<Right>"
	else
		return a:char
	endif
endfunction

"used for quote chars
function! QuoteDelim(char)
	let line=getline('.')
	let col=col('.')
	if line[col - 2] == "\\"
		return a:char
	elseif line[col - 1] == a:char
		return "\<Right>"
	else
		return a:char.a:char."\<Esc>i"
	endif
endfunction
"===================================================="



"==================Config vundle plugins===========================
"
let g:nerdtree_tabs_open_on_console_startup=0		
let g:nerdtree_tabs_open_on_gui_startup=0		
let NERDTreeMapOpenInTab='<Enter>'
map <leader>n <plug>NERDTreeTabsToggle<CR>

map <leader>tl :TlistToggle<CR>
let Tlist_Use_Right_Window=1

let g:pydiction_location='~/.vim/bundle/pydiction/complete-dict'
let g:pydiction_menu_height=5

nnoremap <silent> <buffer> <leader>i :JavaImport<CR>

nnoremap <silent> <buffer> <leader>d :JavaDocSearch -x declarations<CR>
nnoremap <silent> <buffer> <CR> :JavaSearchContext<CR>
"=================================================================="

"====================vim environment config===================="
"config vim path
set path+=$PWD/**

"========================================"


"==================config for gvim======================
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

"========================================
