#!/bin/zsh
#
#

#---------array parameter
liang="liangguisheng"
typeset -A record
record=(name liang sex man age 27)

echo ${(P)${record[name]}}
#

