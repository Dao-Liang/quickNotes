#!/bin/zsh
#
#

echo -e "\n\nstyle1 for name ( words) statements"
#style 1 
for name (liang juliana)
{
    echo $name
}
echo -e "\n\nstyle2 for name [in words] term statements"

#style 2
for name in juliana gui liang;
{
    echo $name
}

echo -e "\n\nstyle3 for ((exp1;exp2;exp3)) statements"
#style 3
for ((i=0;i<3;i++))
{
    echo "current iter $i"
}


echo -e "\n\nstyle4 foreach name...(words list) statements end"
#style 4 
foreach name (juliana gui liang)
    echo "foreach name $name"
end 
