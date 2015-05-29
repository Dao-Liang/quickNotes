"source ~/.vim/config/Vundle_Plugins.vim 
"source ~/.vim/config/Common_config.vim 
"source ~/.vim/config/Color_Config.vim
"source ~/.vim/config/Mapping_Config.vim
"source ~/.vim/config/Function_Config.vim
"source ~/.vim/config/Vundle_Plugins_Cofig.vim 
"source ~/.vim/config/Environment_Config.vim
"source ~/.vim/config/FileType_Config.vim 
"source ~/.vim/config/Abbrevs_Common.vim 
"source ~/.vim/config/Gvim_Config.vim 


"Basic configuration
set nocompatible
set number
set ruler
set showcmd 
filetype on
syntax on

"Basic Object for editing file
	"word
	"line
	"paragraph
	"'block'
	""block"
	"{block}
	"[block]
	"<block>
	"(block)
	"begin of line
	"end of line
	"first line of file
	"last line of file
	"percentage of file

"Basic Operation Commands
"Insert
"insert at cursor
"insert after cursor
"insert at the end of the line
"insert a blank line down
"insert a blank line up

"Delete
"delete a char
"delete a word
"delete a line

"Replace
"replace a char
"repalce a word

"Change
"change a word: cw
"change a line: cc|S

"Undo

"Redo

"Jump
"left
"right
"up
"down
"jump a word: forward of backward
"jump next line
"jump up line
"jump begin of line
"jump end of line
"jump begin of file
"jump end of file

"Copy

"Paste

"Join lines


"Color Config
color pablo

"Mappings
inoremap jj <Esc>
inoremap <leader>w <Esc>:w<CR>
nnoremap <leader>w :w<CR>

"Abbrevs Config


"Basic Plugins


"Filetype Plugins


