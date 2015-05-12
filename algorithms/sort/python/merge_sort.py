#!/usr/bin/python
# -*- encoding=utf8 -*-

#using merge sort method to sort the list elements

def merge_sort(alist):

    if len(alist)<=1:
        return alist

    middle=int(len(alist)/2)
    left=merge_sort(alist[:middle])
    right=merge_sort(alist[middle:])
    return merge(left,right)




def merge(left,right):
    merged=[]

    #    because list in python has pop operation from right to left
    while left and right:
        merged.append(left.pop() if left[-1]>=right[-1] else right.pop())
    while left:
        merged.append(left.pop())
    while right:
        merged.append(right.pop())

    merged.reverse()
    #print merged
    return merged


my_list=[2,1,4,53,4,5,3,29,7,4]

print "before sort :", my_list
sorted_list=merge_sort(my_list)
print "after sort :",sorted_list


        



