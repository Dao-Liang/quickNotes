#!/bin/zsh
#
#
# zsh Arithmetic evaluations
# ((arithmetic expresstion)) or let "arithmetic expresstion"

a=0

until (( $a>5 )){
    echo "until $a"
    ((a++))
}

while (( $a<10 )){
    echo $a
    ((a++))
}

for (( i=0 ;i<10; i++)){
    echo $i

}

for element ( liang gui sheng ){
    echo $element
}

select element ( yes no ){
    echo $element
    break
}

#repeat num[;]{statements}
repeat 10 ;{

    echo "hello"
}

name="liang"

case $name  {

    "liang")
        echo "start with li";;
    "ang")
        echo "end with ang";;
}

#case exp in pattern) statements;;/case exp{pattern) statement;;..}
case $name {

    "liang")    
        echo "name is equal to Liang";;
}
