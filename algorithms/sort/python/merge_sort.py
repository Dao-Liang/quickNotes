#!/usr/bin/python
# -*- encoding=utf8 -*-

#using merge sort method to sort the list elements

def merge_sort(alist,start,end):

    if start< end:
        m=(start+end)/2
        merge_sort(alist,start,m)
        merge_sort(alist,m+1,end)
        merge(alist,start,m,end)
        print alist,"\n"


def merge(alist,start,middle,end):

    list1=alist[start:middle-start]
    list2=alist[middle:end]

    i,j=0,0
    c=0
    while i<len(list1) and j<len(list2):
        if list1[i] <list2[j]:
            alist[c]=list1[i]
            i+=1
            c+=1
        else:
            alist[c]=list2[j]
            j+=1
            c+=1

    if i < len(list1):
        for a in list1[i:]:
            alist[c]=a
            c+=1

    if j < len(list2):
        for b in list2[j:]:
            alist[c]=b
            c+=1

my_list=[2,1,4,53,4,5,3,29,7,4]

print "before sort :", my_list
merge_sort(my_list,0,len(my_list))
print "after sort :",my_list


        



