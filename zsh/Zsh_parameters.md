Zsh Parameters
=================

###Patermeter Expansion ###
    ${ǹame}

    ${+name}
    
    ${name-word}
    ${name:-word}


    ${name+word}
    ${name:+word}

    ${name=word}
    ${name:=word}
    ${name::=word}


    ${name?word}
    ${name:?word}


    ${name#pattern}
    ${name##pattern}

    ${name%pattern}
    ${name%%pattern}

    ${name:#pattern}


    ${name:|arrayname}
    

    ${name:*arrayname}

    ${name:^arrayname}
    ${name:^^arrayname}


    ${name:offset}
    ${name:offset:length}

    ${name/pattern/repl}
    ${name//pattern/repl}
    
    ${#spec}
    ${^spec}
    ${=spec}
    ${~spec}


####Parameter Expansion Flags ####
**Usage: ${(flags)parameter-expanssion-expression}**

    #  
    %
    @
    A
    a
    c
    C
    D
    e
    f
    F
    g:opts:
    i
    k
    L
    n
    o
    O
    P
    q
    Q
    t
    u
    U
    v
    V
    w
    X
    z
    0

    p
    ~
    j:string:
    l:expr::string1::string2:
    m
    r:expr::string1::string2:
    s:string:
    Z:opts:
    _:flags:

    S
    I:expr:
    B
    E
    M
    N
    R


####Parameter Expansion Rules ####

1. Nested subsitution
2. Internal parameter flags
3. Parameter subscripting

