[vim-substitute command][source]
=======================

### search ranges ###
    
    :s/foo/bar/g
    change each 'foo' to 'bar' in current line

    %
    in all lines

    5,12
    from line 5 to line 12

    'a,'b
    from mark 'a' to mark 'b'

    '<,'>
    all lines within visual selection 
    
    .,$
    from current line to the last line

    .,+2
    from current line to the next two lines

    g/^baz/
    all the lines match pattern "^baz"


### substitute metacharacters ###
    
    .,*,\,[,^,$

    +,?,|,&,{,(,) must be escaped to use their special funciton

    \/ equals "/"

    \t equals <tab>

    \s  equals "whitespace"

    \n  equals  newline

    \r  equals CR

### substitute flags###

    g
    global replace  

    i
    case insensitive search

    I
    case sensitive search

    c
    confirm replace action


[source]:http://vim.wikia.com/wiki/Search_and_replace
