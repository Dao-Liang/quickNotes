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


