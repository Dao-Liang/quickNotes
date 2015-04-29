

================================================================================
## vim variables ##

### declare variable ###

	`let var=value`


### variable types ###
	1. global variable without any prefix with <g:> prefix
	2. variable local to a script file with <s:> prefix 
	3. variable local to a buffer with <b:> prefix
	4. variable local to a window with <w:> prefix
	5. variable predefined by vim with <v:> prefix
	6. variable locla to function with <a:> prefix


### delete variables ###

	`unlet var_name`
	`unlet! var_name` <dont show warning for variable not exists>


### check variable exists ###
	
	`exists("var_name")`


### string variable ###


#### special characters in double-quote strings ####


	\t			<Tab>
	\n			<NL>, line break
	\r			<CR>, <Enter>
	\e			<Esc>
	\b			<BS>, backspace
	\"			"
	\\			\, backslash
	\<Esc>		<Esc>
	\<C-W>		CTRL-W
	
	the last two are just examples, it is a form to include the special key "name"

================================================================================
## vim Expression ##


### expression types ###
	1. numbers
	2. strings
	3. variables
	4. $NAME  *environment variable*
	5. &name  *vim options*
	6. @r	  *register*


### mathematics ###
	1. a+b
	2. a-b
	3. a\*b
	4. a/b
	5. a%b

================================================================================
## executing expression ##

	"execute" command allows executing the result of an expression, it is a very
	powerfull way to build comamnds and execute them

	`:execute "tag ".tag_name`

	"." is used to concatenat the string "tag " with the value of variable tag_name

	"execute" command can only execute colon command.

	"normal" command cna execute <Normal> mode commands, however its argument is 
	not an expression but a literal command characters, for example:

		:normal gg=G

	to make "normal" work with expression, combine "execute" with it, for example:
		:execute "normal ". normal_commands

	


================================================================================
## vim Conditionals ##

*only {condition} evaluetes to true(non-zero) will the {stament} be executed.*

	```
	if 		{condition}
		{statements}
	elseif 	{condition}
		{statement}
	else
		{statement}
	endif
	```


### logic operations ###
	
	a == b
	a != b
	a >  b
	a >= b
	a <  b
	a <= b

	Logic operators work both for numbers and strings, when comparing two strings,
	the mataematical difference is used. this compares byte values, which may not
	be right for some languages.


	for strings there are two more items:
	a =~ b 		 matches with
	a !~ b		 does not match with

	*here be is a regex pattern*

	<ignorecase> option is used when comparing strings,
	append "#" to match *case*		!~#
	append "?" to ignore *case*		==?
	

### vim looping ###

the form to use while is like following:

	while counter <40
		call do_something()
		
		if skip_flag
			continue
		endif

		if finished_flag
			break
		endif
		
		sleep 50m
	endwhile

================================================================================
## vim Using functions ##

	vim defines many <functions> and provides a large amount of functionality that
	way.


### vim invoke function ###
	
	:call function_name(arguments)


### vim define function ###
	
	basic function declaration as follows:

		function {name}({var1},{var2},...)
			{body}
		endfunction

	*note* function name must begin with a capital letter

	a complete function define example is as follows:
		
		function Min(num1, num2)
		  if a:num1 < a:num2
		    let smaller = a:num1
		  else
		    let smaller = a:num2
		  endif
		  return smaller
		endfunction

	to redefine a function that already exists, use <function!> to define
	function


#### vim function using range ####


##### define function with range #####
	
	function Count_words() range
	  let lnum = a:firstline  		*first line in range*
	  let n = 0
	  while lnum <= a:lastline 		*last line in range*
	    let n = n + len(split(getline(lnum)))
	    let lnum = lnum + 1
	  endwhile
	  echo "found " . n . " words"
	endfunction


##### call function with range #####
	
	:10,30call Count_words()

	function with range will executed one time using the range



#### variable number of arguments ####
	
	vim enables you to define functions have variable number of arguments.
	
		:function Show(start,...)
	
	the length of variable arguments can be maximum 20.

		a:0 	contains the number of extra arguments
		a:1 	contains the first optional argument
		a:2     contains the second optional argument
		...


#### list functions ####
	
	:function command lists the names and arguments of all user-defined functions

	use function_name as argument of :function command can see what is the function.


#### debugggin function ####
	
	can set **verbos** option to 12 or higher to see all function calls, set it to 
	15 or higher to see every executed line

#### deleting a function ####

	:delfunction function_name


#### function reference ####
	
	using the function() function with a funciton name can return a function's reference.

		:let result = 0		" or 1
		:function! Right()
		:  return 'Right!'
		:endfunc
		:function! Wrong()
		:  return 'Wrong!'
		:endfunc
		:
		:if result == 1
		:  let Afunc = function('Right')
		:else
		:  let Afunc = function('Wrong')
		:endif
		:echo call(Afunc, [])
 		Wrong! 

 	**note** the name of a variable that holds a function reference also must start
	with a capital. otherwise it could be confused with the name of a builtin function.

	the way to invoke a function that a variable refers to is with **call()** function,
	its first argument is the function reference, and the second argument is a *List*
	with arguments


### vim function-list ###

	String manipulation:					

		nr2char()		get a character by its ASCII value
		char2nr()		get ASCII value of a character
		str2nr()		convert a string to a Number
		str2float()		convert a string to a Float
		printf()		format a string according to % items
		escape()		escape characters in a string with a '\'
		shellescape()		escape a string for use with a shell command
		fnameescape()		escape a file name for use with a Vim command
		tr()			translate characters from one set to another
		strtrans()		translate a string to make it printable
		tolower()		turn a string to lowercase
		toupper()		turn a string to uppercase
		match()			position where a pattern matches in a string
		matchend()		position where a pattern match ends in a string
		matchstr()		match of a pattern in a string
		matchlist()		like matchstr() and also return submatches
		stridx()		first index of a short string in a long string
		strridx()		last index of a short string in a long string
		strlen()		length of a string
		substitute()		substitute a pattern match with a string
		submatch()		get a specific match in a ":substitute"
		strpart()		get part of a string
		expand()		expand special keywords
		iconv()			convert text from one encoding to another
		byteidx()		byte index of a character in a string
		repeat()		repeat a string multiple times
		eval()			evaluate a string expression


	List manipulation:			

		get()			get an item without error for wrong index
		len()			number of items in a List
		empty()			check if List is empty
		insert()		insert an item somewhere in a List
		add()			append an item to a List
		extend()		append a List to a List
		remove()		remove one or more items from a List
		copy()			make a shallow copy of a List
		deepcopy()		make a full copy of a List
		filter()		remove selected items from a List
		map()			change each List item
		sort()			sort a List
		reverse()		reverse the order of a List
		split()			split a String into a List
		join()			join List items into a String
		range()			return a List with a sequence of numbers
		string()		String representation of a List
		call()			call a function with List as arguments
		index()			index of a value in a List
		max()			maximum value in a List
		min()			minimum value in a List
		count()			count number of times a value appears in a List
		repeat()		repeat a List multiple times


	Dictionary manipulation:

		get()			get an entry without an error for a wrong key
		len()			number of entries in a Dictionary
		has_key()		check whether a key appears in a Dictionary
		empty()			check if Dictionary is empty
		remove()		remove an entry from a Dictionary
		extend()		add entries from one Dictionary to another
		filter()		remove selected entries from a Dictionary
		map()			change each Dictionary entry
		keys()			get List of Dictionary keys
		values()		get List of Dictionary values
		items()			get List of Dictionary key-value pairs
		copy()			make a shallow copy of a Dictionary
		deepcopy()		make a full copy of a Dictionary
		string()		String representation of a Dictionary
		max()			maximum value in a Dictionary
		min()			minimum value in a Dictionary
		count()			count number of times a value appears


	Floating point computation:

		float2nr()		convert Float to Number
		abs()			absolute value (also works for Number)
		round()			round off
		ceil()			round up
		floor()			round down
		trunc()			remove value after decimal point
		log10()			logarithm to base 10
		pow()			value of x to the exponent y
		sqrt()			square root
		sin()			sine
		cos()			cosine
		tan()			tangent
		asin()			arc sine
		acos()			arc cosine
		atan()			arc tangent
		atan2()			arc tangent
		sinh()			hyperbolic sine
		cosh()			hyperbolic cosine
		tanh()			hyperbolic tangent

	Variables:			

		type()			type of a variable
		islocked()		check if a variable is locked
		function()		get a Funcref for a function name
		getbufvar()		get a variable value from a specific buffer
		setbufvar()		set a variable in a specific buffer
		getwinvar()		get a variable from specific window
		gettabvar()		get a variable from specific tab page
		gettabwinvar()		get a variable from specific window & tab page
		setwinvar()		set a variable in a specific window
		settabvar()		set a variable in a specific tab page
		settabwinvar()		set a variable in a specific window & tab page
		garbagecollect()	possibly free memory


	Cursor and mark position:	

		col()			column number of the cursor or a mark
		virtcol()		screen column of the cursor or a mark
		line()			line number of the cursor or mark
		wincol()		window column number of the cursor
		winline()		window line number of the cursor
		cursor()		position the cursor at a line/column
		getpos()		get position of cursor, mark, etc.
		setpos()		set position of cursor, mark, etc.
		byte2line()		get line number at a specific byte count
		line2byte()		byte count at a specific line
		diff_filler()		get the number of filler lines above a line


	Working with text in the current buffer:

		getline()		get a line or list of lines from the buffer
		setline()		replace a line in the buffer
		append()		append line or list of lines in the buffer
		indent()		indent of a specific line
		cindent()		indent according to C indenting
		lispindent()		indent according to Lisp indenting
		nextnonblank()		find next non-blank line
		prevnonblank()		find previous non-blank line
		search()		find a match for a pattern
		searchpos()		find a match for a pattern
		searchpair()		find the other end of a start/skip/end
		searchpairpos()		find the other end of a start/skip/end
		searchdecl()		search for the declaration of a name


	System functions and manipulation of files:

		glob()			expand wildcards
		globpath()		expand wildcards in a number of directories
		findfile()		find a file in a list of directories
		finddir()		find a directory in a list of directories
		resolve()		find out where a shortcut points to
		fnamemodify()		modify a file name
		pathshorten()		shorten directory names in a path
		simplify()		simplify a path without changing its meaning
		executable()		check if an executable program exists
		filereadable()		check if a file can be read
		filewritable()		check if a file can be written to
		getfperm()		get the permissions of a file
		getftype()		get the kind of a file
		isdirectory()		check if a directory exists
		getfsize()		get the size of a file
		getcwd()		get the current working directory
		haslocaldir()		check if current window used |:lcd|
		tempname()		get the name of a temporary file
		mkdir()			create a new directory
		delete()		delete a file
		rename()		rename a file
		system()		get the result of a shell command
		hostname()		name of the system
		readfile()		read a file into a List of lines
		writefile()		write a List of lines into a file


	Date and Time:	

		getftime()		get last modification time of a file
		localtime()		get current time in seconds
		strftime()		convert time to a string
		reltime()		get the current or elapsed time accurately
		reltimestr()		convert reltime() result to a string


	Buffers, windows and the argument list:

		argc()			number of entries in the argument list
		argidx()		current position in the argument list
		argv()			get one entry from the argument list
		bufexists()		check if a buffer exists
		buflisted()		check if a buffer exists and is listed
		bufloaded()		check if a buffer exists and is loaded
		bufname()		get the name of a specific buffer
		bufnr()			get the buffer number of a specific buffer
		tabpagebuflist()	return List of buffers in a tab page
		tabpagenr()		get the number of a tab page
		tabpagewinnr()		like winnr() for a specified tab page
		winnr()			get the window number for the current window
		bufwinnr()		get the window number of a specific buffer
		winbufnr()		get the buffer number of a specific window
		getbufline()		get a list of lines from the specified buffer


	Command line:

		getcmdline()		get the current command line
		getcmdpos()		get position of the cursor in the command line
		setcmdpos()		set position of the cursor in the command line
		getcmdtype()		return the current command-line type


	Quickfix and location lists:

		getqflist()		list of quickfix errors
		setqflist()		modify a quickfix list
		getloclist()		list of location list items
		setloclist()		modify a location list


	Insert mode completion:

		complete()		set found matches
		complete_add()		add to found matches
		complete_check()	check if completion should be aborted
		pumvisible()		check if the popup menu is displayed


	Folding:		

		foldclosed()		check for a closed fold at a specific line
		foldclosedend()		like foldclosed() but return the last line
		foldlevel()		check for the fold level at a specific line
		foldtext()		generate the line displayed for a closed fold
		foldtextresult()	get the text displayed for a closed fold


	Syntax and highlighting:	

		clearmatches()		clear all matches defined by |matchadd()| and the |:match| commands
		getmatches()		get all matches defined by |matchadd()| and the |:match| commands
		hlexists()		check if a highlight group exists
		hlID()			get ID of a highlight group
		synID()			get syntax ID at a specific position
		synIDattr()		get a specific attribute of a syntax ID
		synIDtrans()		get translated syntax ID
		synstack()		get list of syntax IDs at a specific position
		synconcealed()		get info about concealing
		diff_hlID()		get highlight ID for diff mode at a position
		matchadd()		define a pattern to highlight (a "match")
		matcharg()		get info about |:match| arguments
		matchdelete()		delete a match defined by |matchadd()| or a
					|:match| command
		setmatches()		restore a list of matches saved by
				|getmatches()|


	Spelling:					

		spellbadword()		locate badly spelled word at or after cursor
		spellsuggest()		return suggested spelling corrections
		soundfold()		return the sound-a-like equivalent of a word


	History:		

		histadd()		add an item to a history
		histdel()		delete an item from a history
		histget()		get an item from a history
		histnr()		get highest index of a history list


	Interactive:

		browse()		put up a file requester
		browsedir()		put up a directory requester
		confirm()		let the user make a choice
		getchar()		get a character from the user
		getcharmod()		get modifiers for the last typed character
		feedkeys()		put characters in the typeahead queue
		input()			get a line from the user
		inputlist()		let the user pick an entry from a list
		inputsecret()		get a line from the user without showing it
		inputdialog()		get a line from the user in a dialog
		inputsave()		save and clear typeahead
		inputrestore()		restore typeahead


	GUI:	

		getfontname()		get name of current font being used
		getwinposx()		X position of the GUI Vim window
		getwinposy()		Y position of the GUI Vim window
	
	
	Vim server:	

		serverlist()		return the list of server names
		remote_send()		send command characters to a Vim server
		remote_expr()		evaluate an expression in a Vim server
		server2client()		send a reply to a client of a Vim server
		remote_peek()		check if there is a reply from a Vim server
		remote_read()		read a reply from a Vim server
		foreground()		move the Vim window to the foreground
		remote_foreground()	move the Vim server window to the foreground
	
	
	Window size and position:	

		winheight()		get height of a specific window
		winwidth()		get width of a specific window
		winrestcmd()		return command to restore window sizes
		winsaveview()		get view of current window
		winrestview()		restore saved view of current window
	
	
	Various:				

		mode()			get current editing mode
		visualmode()		last visual mode used
		hasmapto()		check if a mapping exists
		mapcheck()		check if a matching mapping exists
		maparg()		get rhs of a mapping
		exists()		check if a variable, function, etc. exists
		has()			check if a feature is supported in Vim
		changenr()		return number of most recent change
		cscope_connection()	check if a cscope connection exists
		did_filetype()		check if a FileType autocommand was used
		eventhandler()		check if invoked by an event handler
		getpid()		get process ID of Vim
		libcall()		call a function in an external library
		libcallnr()		idem, returning a number
		getreg()		get contents of a register
		getregtype()		get type of a register
		setreg()		set contents and type of a register
		taglist()		get list of matching tags
		tagfiles()		get a list of tags files
		mzeval()		evaluate |MzScheme| expression



================================================================================
## vim List ##

### define a list variable ###

	`let alist=[]`


### add element into list ###

	`:call add(alist,a_value)`	
	`:call add(alist,b_value)`	

### concatenate two list  ###

	`alist + ['abc','xyz']`

### extend a list directly ###
	`call extend(alist,['abc','xyz'])`

### different effect of add() and extend() ###
	extend() method will extend the current list with a given list, but the result is a list without
	embedded

	add() method will add the given list into the current list, and the result is a list with the given
	list become an element of the result list.


### for loop for list ###
	
	One of the nice things you can do with a  *List* is iterate over it:

		:let alist = ['one', 'two', 'three']
		:for n in alist
		:  echo n
		:endfor

		for {varname} in {list_expression}
			{commands}
		endfor

## vim Dictionary ##
