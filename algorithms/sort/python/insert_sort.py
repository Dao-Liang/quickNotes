#!/usr/bin/python
# -*- encoding=utf8 -*-

def insert_sort(alist):

    for j in range(1,len(alist)):

        key=alist[j] #start form the second element in list
        i=j-1        #the elements before j are already sorted

        #find the index which is the elemnet smaller than current element
        while i >= 0 and alist[i] > key:
            alist[i+1]=alist[i]
            i-=1

        #write the key in the right place
        alist[i+1]=key

    #return alist


my_list=[2,3,4,1,2,4,5,6,3,22]
print "before sort the list:", my_list
insert_sort(my_list)
print "after sort the list:",my_list




        
