Bash条件表达式
=====================
    
    -a file 如果文件存在返回true.
    -b file 如果问件存在且是块设备的特殊文件返回true
    -c file 如果文件存在且是字符设备特殊文件返回true
    -d file 如果文件存在且是一个目录返回true
    -e file 如果文件存在返回true
    -f file 如果文件存在且是一个常规文件返回true
    -g file 如果文件存在且其set-group-id位被设置返回true
    -h file 如果文件存在且是一个符号链接返回True
    -k file 如果文件存在且其“sticky”位被设置返回True
    -p file 如果文件存在且是一个命名管道返回True
    -r file 如果文件存在且文件可读返回True 
    -s file 如果文件存在且其大小大于0返回True
    -t fd   如果文件描述符打开并且指向一个终端返回True
    -u file 如果文件存在且其set-user-id位被设置返回True
    -w file 如果文件存在且是可写的返回True
    -x file 如果文件存在且其是可执行的返回True
    -G file 如果文件存在且其被有效的组ID所有返回True 
    -L file 如果文件存在且其是一个符号链接返回True
    -N file 如果文件存在且其在最后一次读取之后被修改返回True
    -O file 如果文件存在且其被有效用户ID所有返回True
    -S file 如果文件存在且是一个套接字返回True
    file1 -ef file2 如果文件1和文件2指向相同的设备和相同的节点编号返回True
    file1 -nt file2 如果文件1比文件2的修改日期新，或者文件1存在&文件2不存在返回True
    file1 -ot file2 如果文件1比文件2的修改日期旧，或者文件2存在&文件1不存在返回True
    -o optname 如果shell选项生效，返回true. 所有的选项名称可以使用 set -o 查看。
    -v varname 如果shell变量名被设置并且赋值返回true。
    -R varname 如果shell变量名被设置并且是一个名称引用返回true.
    -z string 如果字符串的长度为0返回true
    -n string 如果字符串的长度不为0返回true
    
    string1 == string2
    string1 = string2
    如果字符串相等返回true，当使用[[命令是作为模式匹配， “=”应该与test命令一起使用
    string1 != string2如果字符串不相等返回true
    string1 < string2如果字符串1在字符串2前面返回true（按照辞典排序）
    string1 > string2如果字符串1在字符串2后面返回true（按照辞典排序）
   
    arg1 OP arg2
    此处的arg1和arg2应该是整数。
    OP 是以下操作符之一:‘-eq’, ‘-ne’, ‘-lt’, ‘-le’, ‘-gt’, or ‘-ge’. 
    这些算术二元操作符返回True，如果arg1==, <,<=, >,>= arg2.
    arg1和arg2可以为正负整数。
