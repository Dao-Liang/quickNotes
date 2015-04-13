set nocompatible
filetype off

set rtp+=~/.vim/bundle/Vundle.vim
call vundle#begin()

Plugin 'gmarik/Vundle.vim'
Plugin 'scrooloose/nerdtree'
Plugin 'Nopik/vim-nerdtree-direnter'
Plugin 'jistr/vim-nerdtree-tabs'
Plugin 'scrooloose/syntastic'
Plugin 'javacomplete'
Plugin 'vim-scripts/taglist.vim'
Plugin 'vim-scripts/dbext.vim'
Plugin 'vim-scripts/java_getset.vim'
Plugin 'rkulla/pydiction'
call vundle#end()
filetype plugin indent on

" set tags file place
set tags+=./tags,tags

set incsearch
set nu
set ruler
set autoindent
set hlsearch
set ignorecase smartcase
set mouse=a
set showcmd
syntax on

"set working path to find file
"set path+=/home/local/RISKOFFICE/liang.guisheng/ROWorkspace/**
set path+=$PWD/**

map <leader>pyr :!python 
map <leader>s	 <ESC>:w<CR>
map <leader>dp	:w<CR>:!ant deploy-exploded-dev<CR>

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
"tab navigation configs"
nnoremap <C-Left> :tabprevious<CR>
nnoremap <C-Right> :tabnext<CR>
let g:nerdtree_tabs_open_on_console_startup=0		
let g:nerdtree_tabs_open_on_gui_startup=0		
"Config plugins
let NERDTreeMapOpenInTab='<Enter>'
map <leader>n <plug>NERDTreeTabsToggle<CR>

map <leader>tl :TlistToggle<CR>
let Tlist_Use_Right_Window=1

let g:pydiction_location='~/.vim/bundle/pydiction/complete-dict'
let g:pydiction_menu_height=5

nnoremap <silent> <buffer> <leader>i :JavaImport<CR>

nnoremap <silent> <buffer> <leader>d :JavaDocSearch -x declarations<CR>
nnoremap <silent> <buffer> <CR> :JavaSearchContext<CR>


"Setting color for different filetype
"autocmd Filetype java color koehler
"autocmd Filetype java color elflord

"settings for java programming


"autocomplete match bracks
inoremap ( ()<Esc>i
inoremap [ []<Esc>i
inoremap { {}<Esc>i
inoremap < <><Esc>i
inoremap ) <c-r>=ClosePair(')')<CR>
inoremap ] <c-r>=ClosePair(']')<CR>
inoremap > <c-r>=ClosePair('>')<CR>
inoremap } <c-r>=ClosePair('}')<CR>
"inoremap } <c-r>=CloseBracket('}')<CR>
inoremap " <c-r>=QuoteDelim('"')<CR>
inoremap ' <c-r>=QuoteDelim("'")<CR>

function! ClosePair(char)
	if getline('.')[col('.')-1] == a:char
		return "\<Right>"
	else
		return a:char
	endif
endfunction

function CloseBracket(char)
	if match(getline(line('.')+1),'\s*}')<0
		return "<CR>}"
	else
		return "\<Esc>j0f}a"
	end if
endfunction

function QuoteDelim(char)
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

"Setting javacomplete
setlocal omnifunc=javacomplete#Complete
autocmd Filetype java set omnifunc=javacomplete#Complete
autocmd Filetype java set completefunc=javacomplete#CompleteParamsInfo
"autocmd Filetype java inoremap <buffer> . .<C-X><C-O><C-P><Down>
"java programming configurations


colorscheme pyte

"--------GUI configurations

"hiden gvim menu bar
set guioptions-=m 

"hiden gvim toolbar
set guioptions-=T

"hiden gvim right-hand scroll bar
"set guioptions-=r

"hiden gvim left-hand scroll bar
"set guioptions-=L

"GUI font configura
set guifont=UbuntuMono\ 9
"---------end GUI configurations
