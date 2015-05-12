#!/usr/bin/python
# -*- encoding=utf8 -*-

#filename merge_sort2.py

def merge_sort2(alist,start, end):

    if (end-start)<=1:
        return  

    middle=(start+end)/2
    merge_sort2(alist,start,middle)
    merge_sort2(alist,middle,end)
    merge2(alist,start,middle,end)

def merge2(alist,start,middle,end):
    lista=alist[start:middle]
    listb=alist[middle:end]

    len1=len(lista)
    len2=len(listb)
    i,j=0,0
    c=start
    while i<len1 and j<len2:
        if lista[i] <= listb[j]:
            alist[c]=lista[i]
            i+=1;
            c+=1
        else:
            alist[c]=listb[j]
            j+=1
            c+=1

    for x in lista[i:]:
        alist[c]=x
        c+=1

    for y in listb[j:]:
        alist[c]=y
        c+=1
        
my_list=[2,3,4,1,2,6,5,4,56,7,6,8,9,2,1,4,3]
print "before sort", my_list
merge_sort2(my_list,0,len(my_list))
print "after sort", my_list


