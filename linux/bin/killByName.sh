#!/bin/bash

pname=$1

echo "kill process $pname"
pgrep $pname | xargs kill
