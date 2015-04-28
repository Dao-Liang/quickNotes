

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
	
	the last two are just examples, it's a form to include the special key "name"

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
## vim Conditionals ##

*only {condition} evaluetes to true(non-zero) will the {stament} be executed.*

	```
	if {condition}
		{statements}
	elseif {condition}
		{statement}
	else
		{statement}
	endif
	```


### logic operations ###





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
