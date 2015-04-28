"abbrev 
"main method abbrev
iabbrev main public static void main(String[] args){}<Esc>i<CR><Esc>ko
"print statement abbrev
iabbrev out System.out.println();<Esc>2h
"simple if statement
iabbrev iif if(){<CR><CR><Esc>2k0f(
"if else if statement
iabbrev ielf if(){<CR><CR><Esc>f}a else if(){<CR><CR><Esc>4k0f(
" simple if else statement
iabbrev ief if(){<CR><CR><Esc>f}a else{<CR><CR><Esc>4k0f(
"while statement abbreve
iabbrev iwle while(){<CR><CR><Esc>2k0f(
"for statement abbrev
iabbrev ifor for(){<CR><CR><Esc>2k0f(
"try catch statement abbrev
iabbrev itry try{<CR><CR><Esc>f}a catch(Exception e){<CR><CR><Esc>4ko
"switch statement abbrev
iabbrev iswt switch(){<CR><Esc>ko case : break<Esc>k0f(


"compile and run
map <leader>jc :!javac 
map <leader>jr :!java


"java single line comment in insert mode
function! CommentToggle()
	let line=getline('.')
	if line =~ '^//'
			return "\<Esc>0xxi"
	else
			return "\<Esc>0i//"
	endif

endfunction

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
inoremap  <Tab> <c-r>=SkipBracket()<CR>
inoremap <c-_> <c-r>=CommentToggle()<CR>

"if current char is in ],},),>,',",` then jump out of it
function! SkipBracket()

	let line=getline('.')
	let col=col('.')
	let target=line[col-1]
	echo target
	if match(target,"[\\]\\)\\}>'\"`;]") <0
		echo target "not matched"
		return "\<Tab>"
	else
		echo target "matching"
		return "\<Right>"
	endif 
endfunction




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
"
"



" syntastic bundle setting for java
"set statusline+=%#warningmsg#
"set statusline+=%{SyntasticStatuslineFlag()}
"set statusline+=%*

"let g:syntastic_always_populate_loc_list = 1
"let g:syntastic_auto_loc_list = 1
"let g:syntastic_check_on_open = 1
"let g:syntastic_check_on_wq = 0
