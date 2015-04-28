
## String manipulation ##
### nr2char() ###






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
