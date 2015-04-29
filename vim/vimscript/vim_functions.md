

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



## vim Dictionary ##
