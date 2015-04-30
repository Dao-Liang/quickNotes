
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

