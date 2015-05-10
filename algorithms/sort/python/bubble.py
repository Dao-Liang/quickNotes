#!/usr/bin/python
#-*- encoding=utf8 -*-

#filename ./bubble.py

#Usage: write bubble sort method with python

def bubble_sort(alist):

    print "before sort \n" , alist

    count=0

    for i in range(len(alist)):


        for j in range(i,len(alist)):
            
            if alist[i]>alist[j]:
                count+=1
                alist[i],alist[j]=alist[j],alist[i]
            
        #print "sorting :",alist

    print "swap times :",count

    print "after sort \n",alist


mylist=[1,2,3,5,6,3,2,9,7,5,6,4]

bubble_sort(mylist)
