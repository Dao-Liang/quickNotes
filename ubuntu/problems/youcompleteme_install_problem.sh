#!/bin/bash
set -eu

#try to install YouCompleteMe meet some problems

#provide pythonLIBs support
sudo apt-get install python-dev

#install cmake for compile
sudo apt-get install build-essential cmake

#CMAKE-CXX-COMPILER-NOTFOUND
sudo apt-get install g++
