"define title mapping
"header 1 #
map <leader>mt1  o<ESC>2i#<ESC>i  <ESC>i
"header 2 ##
map <leader>mt2	o<ESC>4i#<ESC>hi  <ESC>i
"header 3 ###
map <leader>mt3	o<ESC>6i#<ESC>2hi  <ESC>i
"header 4 #### 
map <leader>mt4	o<ESC>8i#<ESC>3hi  <ESC>i
"header 5 ##### 
map <leader>mt5	o<ESC>10i#<ESC>4hi  <ESC>i
"header 6 ######
map <leader>mt6	o<ESC>12i#<ESC>5hi  <ESC>i
"header 7 ######
map <leader>mt7	o<ESC>14i#<ESC>6hi  <ESC>i


" define emphasis
map <leader>ms1   i <ESC>2a*<ESC>i
map <leader>ms2   i <ESC>4a*<ESC>1hi
map <leader>ms3   i <ESC>6a*<ESC>2hi

" define paragraph
map <leader>mp o> 


" define order list


" define unorder list
map <leader>msl  o* 
map <leader>mps  o+ 
map <leader>mml  o- 


" define code block
" define single code block
map <leader>msc o`  `<ESC>2hi

" define multiple line code blcok
map <leader>mmc o```  <ESC>o```<ESC>O



"define link block
map <leader>ml i[]<ESC>i


