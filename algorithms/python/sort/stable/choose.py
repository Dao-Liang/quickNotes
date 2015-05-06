#!/usr/bin/python
#-*- encoding=utf8 -*-

def choose(alist):
    print "before sort\n ", alist

    for i in range(len(alist)):

        small=i

        for j in range(i+1,len(alist)):

            if alist[j]<alist[small]:
                small=j

        alist[i],alist[small]=alist[small],alist[i]


    print "after sort \n", alist
    


mylist=[3,2,1,4,5,6,3,2]

choose(mylist)

