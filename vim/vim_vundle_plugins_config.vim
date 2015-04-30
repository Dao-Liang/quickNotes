

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
