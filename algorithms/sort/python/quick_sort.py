#!/usr/bin/python
# -*- encoding=utf8 -*-

#quick sort method implemented by python

def quick_sort(alist,low,high):

    i,j=low,high

    if i >= j:
        return alist

    key=alist[i]

    while i < j:
        while i < j and alist[j] >= key:
            j-=1

        alist[i]=alist[j]

        while i < j and alist[i] <= key:
            i+=1

        alist[j]=alist[i]

    alist[i]=key

    quick_sort(alist,low, i-1)
    quick_sort(alist, j+1, high)

    print alist
    return alist

my_list=[3,2,1,6,4,3,7,8,2,4,23]
print "before sort",my_list
quick_sort(my_list,0,len(my_list)-1)
print "after sort", my_list


