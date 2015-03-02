"abbrev 
iabbrev main public static void main(String[] args){}<Esc>i<CR>
iabbrev sysout System.out.println()<Esc>i

"compile and run
map <leader>jc :!javac 
map <leader>jr :!java

"auto match bracks
inoremap ( ()<Esc>i
inoremap [ []<Esc>i
inoremap { {}<Esc>i
inoremap < <><Esc>i
inoremap ) <c-r>=ClosePair(')')<CR>
inoremap ] <c-r>=ClosePair(']')<CR>
inoremap > <c-r>=ClosePair('>')<CR>
"inoremap } <c-r>=CloseBracket('}')<CR>
inoremap } <c-r>=ClosePair('}')<CR>
inoremap " <c-r>=QuoteDelim('"')<CR>
inoremap ' <c-r>=QuoteDelim("'")<CR>

function! ClosePair(char)
	if getline('.')[col('.')-1] == a:char
		return "\<Right>"
	else
		return a:char
	endif
endfunction

function! CloseBracket()
	if match(getline(line('.')+1),'\s*}')<0
		return "<CR>}"
	else
		return "\<Esc>j0f}a"
	end if
endfunction

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

"Setting javacomplete
setlocal omnifunc=javacomplete#Complete
autocmd Filetype java set omnifunc=javacomplete#Complete
autocmd Filetype java set completefunc=javacomplete#CompleteParamsInfo
autocmd Filetype java inoremap <buffer> .<Tab> .<C-X><C-O><C-P><Down>

"Setting java fold
"set foldmethod=syntax
"set foldenable
"syn region foldBraces start=/{/ end=/}/ transparent fold
"syn region foldJavadoc start=,/\*\*, end=,\*/, transparent fold keepend
"set foldlevel=0
"set foldnestmax=10
