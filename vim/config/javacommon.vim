iabbrev if 	if(){<CR><CR><Esc>2kf)h
iabbrev ifel if(){<CR><CR><Esc>Aelse{<CR><CR><Esc>4k0f)h
iabbrev while while(){<CR><CR><Esc>2kf)h
iabbrev for for(){<CR><CR><Esc>2kf)h
iabbrev main public static void main(String[] args){<CR><Esc>1ko
iabbrev out System.out.println();<Esc>0f)h


inoremap <c-_> <c-r>=CommentToggle()<CR>
nnoremap <c-_> i<c-r>=CommentToggle()<CR><Esc>
vnoremap <c-_> <Esc>i<c-r>=CommentToggle()<CR><Esc>

function! NCommentToggle()

	let line=getline('.')

	echo line
	
	if line =~ '^//'
			return "0xx"
	else
			return "0i//"
	endif
		
endfunction

function! CommentToggle()

	let line=getline('.')

	if line =~ '^//'
		return "\<Esc>0xxi"
	else
		return "\<Esc>0i//"
	endif
endfunction
