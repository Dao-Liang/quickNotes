#!/bin/zsh
#
#
#name="guisheng"

#if((condition)){statements}
print ${+name} #0 if name is not set, or 1
print ${name:-"liang"} ${name-"liang"} #"liang" if name is not set, or name's value

print ${name+"liang"} ${name:+"liang"} #"liang" if name is set, or nothing 

#print ${name="liang"} ${name:="liang"} #if name not set, set it to "liang"
#print ${name::="Liang"}  #anyway the value is "Liang"

print "\n\${name?word} \${name:?word}"
print ${name?"guisheng"}

print "\nString pattern match condition"
print ${name#"gu"} ${name##"gui"} #match the beginning and remove the matched part
print ${name%"ng"} ${name%%"eng"} #match the ending and remove the matched part

print "\nif match then use empty string to replace the value"
print ${name:#"guisheng"}

arrayv=("name" "age" "sex")
print $arrayv
print ${name:|arrayv} 
print $arrayv
print ${name:*arrayv} 
print $arrayv


print "\nsubstitute String operations"
print ${name:3}
print ${name:0:3}

print "\nString pattern match and replace"
print ${name/gui/Gui} #replace only the first find pattern
print ${name//s/S} #replace all found pattern

print ${#name}

if (( ${+name}==0  )){
    print "name parameter is not set yet, $name"
} else {
    print "name parameter is setted!$name"
}
