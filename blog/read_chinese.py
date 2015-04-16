#!/usr/bin/python
#-*- encoding=utf8 -*-
import sys

print(sys.argv)
if len(sys.argv)==2:
    filename=sys.argv[1]
    print(filename)
    fc=open(filename)
    for line in fc:
        for c in line:
            print(c,)
