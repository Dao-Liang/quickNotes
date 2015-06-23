#!/usr/bin/python
# -*- encoding=utf8 -*-

keywords=[
        "package", "import", "public", "private", "protected", "abstract",
        "class", "interface", "extends", "implements", "for", "while",
        "do", "if", "else", "switch", "case", "try",
        "catch", "final", "finally", "throw", "throws", "assert",
        "this", "super", "volatile", "synchronized", "return", "break",
        "continue", "new", "instanceof", "native", "static", "enum",
        "boolean", "byte", "char", "default", "double", "float",
        "int", "long", "short", "void", "transient", "strictfp"
        ]

tokenType=[
       "MODIFIER",
       "JAVATYPE",
       "IDENTIFIER",
       "STRING",
       "NUMBER",
       "OPERATOR",
       "IFBLOCK",
       "WHILEBLOCK",
       "TRYBLOCK",
       "SWITCHBLOCK",
       "FORBLOCK",
        ]
replaceChars=[
        "(", ")", "{", "}", ";", ":", ",", "\""      
        ]

print len(keywords)

java_file=open("./ClientPortfolioManagerBean.java","r")

for line in java_file:
    for char in replaceChars:
        line.replace(char," "+char+" ")

    for token in line.split(" "):
        print token,"\n"

