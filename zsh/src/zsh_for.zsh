#!/bin/zsh

#
#test for complex commands of zsh
echo "foreach grammar"
for name  (liang juliana)
    echo $name

#normal for statement
echo -e "\n\nnormal for statement"
for (( i=0; i<10; i++)) do
    echo "iterate $i"
done

#while do statement
echo -e "\n\nwhile do statement"
count=0
while [[ $count -lt 10 ]]
do
    echo "count $count"
    let count++
done

#until statement
let count=0
echo -e "\n\nUntil statement"
until (( $count>=10 ))
do 
    echo "count $count"
    let count++
done 

#repeat statement
echo -e "\n\nRepeat statement"
repeat $count do
    echo "repeat $count times"
done

#case statement
name="liang"
echo -e "\n\nCase statement"
case $name in "gui")
    echo "$name is equal gui";;

    "liang")
    echo "$name is equal liang";;
esac

#select statement
select name in liang theo juliana ;
do
    echo "current name is $name"
    break
done

#sub shell commands list
#( list )
#
#


#try-always statement
#
#{
# code which may cause an error
#} always{
#   this code is executed any way
#   (( TRY_BLOCK_ERROR = 0 ))
#}



#function
# func() {...} 2>&1
#



#conditional expression evaludate

#[[ exp ]]
