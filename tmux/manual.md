[penBSD]
Manual Page Search Parameters
  	 Show named manual page
  	 Search with apropos query
NAME
tmux — terminal multiplexer
SYNOPSIS
tmux	[-2lCuv] [-c shell-command] [-f file] [-L socket-name] [-S socket-path] [command [flags]]
DESCRIPTION
tmux is a terminal multiplexer: it enables a number of terminals to be created, accessed, and controlled from a single screen. tmux may be detached from a screen and continue running in the background, then later reattached.
tmux是一个终端复用器：它可以在一个单独屏幕中创建，访问以及控制多个终端。tmux也可以从窗口中脱离应且继续在后台运行，以便在之后再回到会话。

When tmux is started it creates a new session with a single window and displays it on screen. A status line at the bottom of the screen shows information on the current session and is used to enter interactive commands.
当开启tmux之后，它会创建带有一个单独窗口的会话并且在屏幕中进行显示。在屏幕底部的状态行显示当前会话的信息并且用来进入交互式命令。

A session is a single collection of pseudo terminals under the management of tmux. Each session has one or more windows linked to it. A window occupies the entire screen and may be split into rectangular panes, each of which is a separate pseudo terminal (the pty(4) manual page documents the technical details of pseudo terminals). Any number of tmux instances may connect to the same session, and any number of windows may be present in the same session. Once all sessions are killed, tmux exits.
一个会话是一个在tmux管理下的伪终端集合，每个会话具有一个或多个窗口与其链接。一个窗口占用了整个屏幕，并且可以被分割成长方形的面板，每个面板分别为一个伪终端。多个tmux实例可能连接到同一个会话，并且任何的窗口可能在一个会话中表示。当所有的会话被终止之后，tmux就会退出。

Each session is persistent and will survive accidental disconnection (such as ssh(1) connection timeout) or intentional detaching (with the ‘C-b d’ key strokes). tmux may be reattached using:
每个会话都是持久的并且可能在意外失联或故意脱离之后生存下来，tumux可能使用以下命令来回到原来的会话：

    $ tmux attach

In tmux, a session is displayed on screen by a client and all sessions are managed by a single server. The server and each client are separate processes which communicate through a socket in /tmp.
在tmux中，一个会话由一个客户端在整个屏幕中显示，并且所有的会话都是由一个单独的服务器进行管理的.
这个服务器以及每个客户端时通过一个在/tmp中的socket进行交流的分开的进程。

The options are as follows:
具有下列的选项:

-2 
    Force tmux to assume the terminal supports 256 colours.
-2 
    强制tmux假设终端支持256颜色。
-C 
    Start in control mode (see the CONTROL MODE section). Given twice (-CC) disables echo.
-C 
    以控制模式开启，使用-CC来让echo失效
-c shell-command
-c shell-命令
Execute shell-command using the default shell. If necessary, the tmux server will be started to retrieve the default-shell option. This option is for compatibility with sh(1) when tmux is used as a login shell.
使用默认的shell来执行shell命令。如果有必要的话，tmux服务器会开启来检索默认的shell选项。这个选项用来当tmux作为一个登录shell时与sh进行兼容的。

-f file
-f 文件

Specify an alternative configuration file. By default, tmux loads the system configuration file from /etc/tmux.conf, if present, then looks for a user configuration file at ~/.tmux.conf.
制定一个可选的配置文件，默认情况下，tmux会从/etc/tmux.conf中加载系统配置文件，如果这个文件存在的话，
然后会尝试查找用户的配置文件 ~/.tmux.conf
The configuration file is a set of tmux commands which are executed in sequence when the server is first started. tmux loads configuration files once when the server process has started. The source-file command may be used to load a file later.
配置文件是一个tmux命令集合，其中的命令在服务器第一次启动时按顺序执行的。tmux会在服务器进程启动之后加载一次配置文件。
"source-file"命令可以用来在稍候加载一个文件

tmux shows any error messages from commands in configuration files in the first session created, and continues to process the rest of the configuration file.
tmux在第一次会话创建时会显示配置文件中的命令出现的任何错误，但是会继续处理配置文件的余下部分。

-L socket-name
-L socket-名字
tmux stores the server socket in a directory under TMUX_TMPDIR, TMPDIR if it is unset, or /tmp if both are unset. The default socket is named default. This option allows a different socket name to be specified, allowing several independent tmux servers to be run. Unlike -S a full path is not necessary: the sockets are all created in the same directory.
tmurx 将服务器socket存储在TMUX_TMPDIR目录下，如果这个变量没有设置的话就会使用TMPDIR替换，
或者当两者都不存在时，就会存储在/tmp目录下。默认的socket的名称为default.
这个选项允许指定一个不同的socket名称，允许多个独立的tmux服务器运行。 与
-S不同的是，不需要使用全路经：所有的sockets文件会创建在一个相同的目录下。

If the socket is accidentally removed, the SIGUSR1 signal may be sent to the tmux server process to recreate it (note that this will fail if any parent directories are missing).
如果socket被意外地删除了，那么SIGUSR1信号会发送给tmux服务器进程来重新创建socket文件(注意如果当之前描述的任何父目录不存在的话会出错)。

-l
Behave as a login shell. This flag currently has no effect and is for compatibility with other shells when using tmux as a login shell.
当作一个登录shell使用，当前这个标记没有什么效果并且是被用来当使用tmux作为登录shell时与其他shell进行兼容的。
-S socket-path
-S socket-路径
Specify a full alternative path to the server socket. If -S is specified, the default socket directory is not used and any -L flag is ignored.
为服务器的socket指定一个可选的全路经，当这个选项指定之后，那么默认的目录不会被使用,并且-L选项会被忽略。
-u
tmux attempts to guess if the terminal is likely to support UTF-8 by checking the first of the LC_ALL, LC_CTYPE and LANG environment variables to be set for the string "UTF-8". This is not always correct: the -u flag explicitly informs tmux that UTF-8 is supported.
tmux尝试通过第一个LC_ALL,LC_CTYPE和LANG环境变量来猜测终端是否可能支持UTF-8,这可能不会总是正确，这个 -u
选项显式地告知tmux UTF-8是支持的。
If the server is started from a client passed -u or where UTF-8 is detected, the utf8 and status-utf8 options are enabled in the global window and session options respectively.
如果服务器通过客户端传递-u或者检测到UTF-8进行启动的，那么utf8和status-utf8选项会分别在全局窗口和会话选项中生效。
-v
Request verbose logging. This option may be specified multiple times for increasing verbosity. Log messages will be saved into tmux-client-PID.log and tmux-server-PID.log files in the current directory, where PID is the PID of the server or client process.
请求详细登录，这个选项可能由于不断增长的修饰词被多次指定。登录消息会被存储在当前目录下的tmux-客户端PID.log和tmux-服务器PID.log文件中，其中的PID代表服务器或客户端进程ID。
command [flags]

This specifies one of a set of commands used to control tmux, as described in the following sections. If no commands are specified, the new-session command is assumed.
这个用来指定命令集合中的一个来控制tmux，如果没有指定任何命令那么假设一个新建会话命令。

KEY BINDINGS
键绑定

tmux may be controlled from an attached client by using a key combination of a prefix key, ‘C-b’ (Ctrl-b) by default, followed by a command key.
tmux可以通过一个前缀键与跟随的命令键进行结合的方式从一个附着的客户端进行控制，前缀键默认为'C-b'

The default command key bindings are:
默认的命令键绑定为:

C-b:  Send the prefix key (C-b) through to the application.
C-b: 给应用发送一个前缀键 C-b
C-o: Rotate the panes in the current window forwards.
C-o: 将当前窗口中的面板向前切换。
C-z: Suspend the tmux client.
C-z: 将tmux客户端挂起
!: Break the current pane out of the window.
!: 将当前面板在窗口中突出显示
": Split the current pane into two, top and bottom.
": 将当前的面板分割为上下两个面板
\#: List all paste buffers.
\#: 列出所有的粘贴缓存
$:Rename the current session.
$: 重命名当前会话
% Split the current pane into two, left and right.
%: 将当前面板分割为左右两个面板
& Kill the current window.
&: 终止当前窗口
' Prompt for a window index to select.
': 显示一个窗口索引来进行选择
( Switch the attached client to the previous session.
(: 将当前附着的客户端转换到前一个会话
) Switch the attached client to the next session.
): 将附着的客户端转换到下一个会话
\, Rename the current window.
\,: 重命名当前窗口
\- Delete the most recently copied buffer of text.
\-: 删除最近的复制文本缓存
. Prompt for an index to move the current window.
.: 提示一个索引来移动当前窗口
0 to 9: Select windows 0 to 9.
0-9: 选择0-9个窗口
: Enter the tmux command prompt.
: 输入tmux命令提示
;: Move to the previously active pane.
;: 移动到前面的活动面板

=: Choose which buffer to paste interactively from a list.
=: 从一个列表中选择一个缓存来交互式粘贴。
?: List all key bindings.
?: 列出所有的键绑定
D: Choose a client to detach.
D: 选择一个客户端来脱离其附着
L: Switch the attached client back to the last session.
L: 将附着的客户端切换到最后一个会话中
[: Enter copy mode to copy text or view the history.
[: 输入赋值模式来复制文本或查看历史
]: Paste the most recently copied buffer of text.
]: 粘贴最近复制的文本缓存
c: Create a new window.
c: 创建一个新的窗口
d: Detach the current client.
d: 脱离当前的客户端
f: Prompt to search for text in open windows.
f: 提示在打开的窗口中搜索文本
i: Display some information about the current window.
i: 显示关于当前窗口的一些信息
l: Move to the previously selected window.
l: 移动到之前选择的窗口
n: Change to the next window.
n: 移动到下一个窗口
o: Select the next pane in the current window.
o: 移动到当前窗口的下一个面板
p: Change to the previous window.
p: 移动到之前的窗口
q: Briefly display pane indexes.
q: 简单地显示面板索引
r: Force redraw of the attached client.
r: 强制重绘附着的客户端
s: Select a new session for the attached client interactively.
s: 为当前附着的客户端交互式地选择一个新的会话
t: Show the time.
t: 显示时间
w: Choose the current window interactively.
w: 交互式地选择当前的窗口
x: Kill the current pane.
x: 终止当前的面板
z: Toggle zoom state of the current pane.
z: 切换当前面板的放大状态
{: Swap the current pane with the previous pane.
{: 使用之前的面板来替换当前的面板
}: Swap the current pane with the next pane.
}: 使用下一个面板来替换当前的面板
~: Show previous messages from tmux, if any.
~: 显示tmux之前的消息如果存在的话。
Page Up: Enter copy mode and scroll one page up.
PageUp: 进入复制模式并且将页面向上滚动一页。
Up, Down, Left, Right: Change to the pane above, below, to the left, or to the right of the current pane.
Up,Down,Left,Right: 转换到当前面板的上, 下，左，右
M-1 to M-5: Arrange panes in one of the five preset layouts: even-horizontal, even-vertical, main-horizontal, main-vertical, or tiled.
M-1到M-5（M=Alt）：将面板按照预设的1-5个布局进行安排：偶数水平，偶数垂直，主水平，主垂直或平铺
Space: Arrange the current window in the next preset layout.
Space: 将当前窗口按照下一个预设布局进行安排
M-n: Move to the next window with a bell or activity marker.
M-n: 移动到下一个窗口并且带有一个响铃或者活动标记
M-o: Rotate the panes in the current window backwards.
M-o: 将当前窗口中的面板从前向后反转
M-p: Move to the previous window with a bell or activity marker.
M-p: 移动到前一个窗口并且带有响铃或者活动标记
C-Up, C-Down C-Left, C-Right: Resize the current pane in steps of one cell.
C-Up, C-Down C-Left, C-Right: 以一个单格的步调调整当前面板的大小
M-Up, M-Down M-Left, M-Right: Resize the current pane in steps of five cells.
M-Up, M-Down M-Left, M-Right: 以五个单格的步调调整当前面板的大小

Key bindings may be changed with the bind-key and unbind-key commands.
键绑定可以通过bind-key和unbind-key命令来改变。


COMMANDS
命令

This section contains a list of the commands supported by tmux. Most commands accept the optional -t argument with one of target-client, target-session target-window, or target-pane. These specify the client, session, window or pane which a command should affect.
这部分包含了tmux支持的命令列表，大部分的命令接收可选的-t参数与一个目标客户端，目标会话，目标窗口或者目标面板。
它们指定了命令会影响到的客户端，会话，窗口或面板

target-client should be the name of the pty(4) file to which the client is connected, for example either of /dev/ttyp1 or ttyp1 for the client attached to /dev/ttyp1. If no client is specified, tmux attempts to work out the client currently in use; if that fails, an error is reported. Clients may be listed with the list-clients command.
目标客户端应该为客户端链接的pty文件的名称，例如对于附着在/dev/ttyp1的客户端可能为 /dev/ttyp1或
ttyp1。如果没有指定客户端，tmux会尝试当前使用的客户端;
如果失败的话，就会报告一个错误。客户端可以通过list-clients命令列出。

target-session is tried as, in order:
目标会话会按照顺序进行尝试:

A session ID prefixed with a $.
一个以$作为前缀的会话ID。

An exact name of a session (as listed by the list-sessions command).
一个精确的会话名称（会在list-sessions命令中列出）。
The start of a session name, for example ‘mysess’ would match a session named ‘mysession’.
会话名称的开始部分，例如“mysess”会匹配一个名为"mysession"的会话。
An fnmatch(3) pattern which is matched against the session name.
一个与会话名称匹配的fnmatch 模式

If a single session is found, it is used as the target session; multiple matches produce an error. If a session is omitted, the current session is used if available; if no current session is available, the most recently used is chosen.
如果找到了一个单独的会话，就会将其作为目标会话；如果匹配多个会话就会产生错误。如果忽略一个会话的话，那么就会使用当前的会话-如果可用的话；如果当前会话不可用，那么最近使用的会话就会被选择。

target-window specifies a window in the form session:window. session follows the same rules as for target-session, and window is looked for in order as:
目标窗口通过session:window的格式来指定一个窗口。 会话按照target-session的规则，而窗口会按照以下的顺序来查找：

A special token, listed below.
一个下面列表中的特殊标记。
A window index, for example ‘mysession:1’ is window 1 in session ‘mysession’.
一个窗口索引，例如'mysession:1'表示会话'mysession'中的第一个窗口。
A window ID, such as @1.
一个窗口ID，例如@1。
An exact window name, such as ‘mysession:mywindow’.
一个精确的窗口名称，例如'mysession:mywindow'。
The start of a window name, such as ‘mysession:mywin’.
一个窗口名称的开始部分，例如'mysession:mywin'。
As an fnmatch(3) pattern matched against the window name.
一个于窗口名称相匹配的fnmatch模式。
An empty window name specifies the next unused index if appropriate (for example the new-window and link-window commands) otherwise the current window in session is chosen.
一个空窗口名称制定了下一个未使用的索引如果合适的话（例如new-window或link-window命令），否则会话中的当前窗口就被选择。
The following special tokens are available to indicate particular windows. Each has a single-character alternative form.
下面的特殊标记可用来指定一个特定的窗口。每个都具有一个可选的单字符格式。
Token		Meaning
符号        含义
{start}	^	The lowest-numbered window
{start} ^   最小数值的窗口
{end}	$	The highest-numbered window
{end}   $   最大数值的窗口
{last}	!	The last (previously current) window
{last}  !   最后一个窗口
{next}	+	The next window by number
{next}  +   按照数字的下一个窗口
{previous}	-	The previous window by number
{previous}  -   按照数字的上一个窗口
{mouse}	=	The window where the mouse event happened
{mouse} =   鼠标事件发生的窗口

target-pane may be a pane ID or takes a similar form to target-window but with the optional addition of a period followed by a pane index or pane ID, for example: ‘mysession:mywindow.1’. If the pane index is omitted, the currently active pane in the specified window is used. The following special tokens are available for the pane index:
目标面板可以是一个面板ID或者是一个于目标窗口相似的形式，但是带有一个额外可选的跟随面板索引或面板ID的点号“.”。例如：
"mysession:mywindow.1"。
如果忽略面板索引的话，那么指定窗口当前的活动面板就会被使用，下面的特殊符合可以作为面板索引使用：

Token		Meaning
符号        含义
{last}	!	The last (previously active) pane
{last}  !   最后一个面板
{next}	+	The next pane by number
{next}  +   数字指定的下一个面板
{previous}	-	The previous pane by number
{previous}  -   数字指定的前一个面板
{top}		The top pane
{top}       顶端面板
{bottom}		The bottom pane
{bottom}        底端面板
{left}		The leftmost pane
{left}      最左端面板
{right}		The rightmost pane
{right}     最右端面板
{top-left}		The top-left pane
{top-left}      左顶端面板
{top-right}		The top-right pane
{top-right}     右顶端面板
{bottom-left}		The bottom-left pane
{bottom-left}       左底端面板
{bottom-right}		The bottom-right pane
{bottom-right}      右底端面板
{up}		The pane above the active pane
{up}        活动面板上面的面板
{down}		The pane below the active pane
{down}      活动面板下面的面板
{left}		The pane to the left of the active pane
{left}      活动面板左边的面板
{right}		The pane to the right of the active pane
{right}     活动面板右边的面板
{mouse}	=	The pane where the mouse event happened
{mouse} =   鼠标事件发生的面板

The tokens ‘+’ and ‘-’ may be followed by an offset, for example:
符合'+'和'-'可能跟随一个位移，例如:
select-window -t:+2

Sessions, window and panes are each numbered with a unique ID; session IDs are prefixed with a ‘$’, windows with a ‘@’, and panes with a ‘%’. These are unique and are unchanged for the life of the session, window or pane in the tmux server. The pane ID is passed to the child process of the pane in the TMUX_PANE environment variable. IDs may be displayed using the ‘session_id’, ‘window_id’, or ‘pane_id’ formats (see the FORMATS section) and the display-message, list-sessions, list-windows or list-panes commands.
会话，窗口和面板都通过一个唯一的ID来进行数字编码；
会话ID带有一个'$'前缀，窗口ID带有一个'@'前缀，面板ID带有一个'%'前缀。这些在tmux服务器中的会话，窗口或面板生命周期中都是唯一不变的。面板ID通过TMUX_PANE环境变量传递给面板的子进程，ID可能使用'session_id','window_id'或'pane_id'何display-message,list-sesions,list-windows或list-panes命令的格式进行显示。


shell-command arguments are sh(1) commands. This may be a single argument passed to the shell, for example:
shell-command 参数时sh命令，这可能是一个传递给shell的单个参数，例如：

    new-window 'vi /etc/passwd'

Will run:
会运行：
    /bin/sh -c 'vi /etc/passwd'

Additionally, the new-window, new-session, split-window, respawn-window and respawn-pane commands allow shell-command to be given as multiple arguments and executed directly (without ‘sh -c’). This can avoid issues with shell quoting. For example:
此外，new-window,new-session,split-window, respawn-window以及respawn-pane命令允许 shell-command
作为多惨数给定并且可以直接执行（不需要 'sh -C'）。 者可以避免shell引用问题，例如：

    $ tmux new-window vi /etc/passwd

Will run vi(1) directly without invoking the shell.
会直接运行vi,而不需要调用shell。

command [arguments] refers to a tmux command, passed with the command and arguments separately, for example:
命令 [参数] 指向一个tmux命令，命令和参数分别进行传递，例如：

    bind-key F1 set-window-option force-width 81

Or if using sh(1):
或者，如果使用sh的话：

    $ tmux bind-key F1 set-window-option force-width 81

Multiple commands may be specified together as part of a command sequence. Each command should be separated by spaces and a semicolon; commands are executed sequentially from left to right and lines ending with a backslash continue on to the next line, except when escaped by another backslash. A literal semicolon may be included by escaping it with a backslash (for example, when specifying a command sequence to bind-key).
多个命令可以作为命令序列的一部分一起指定，每个命令需要使用空格和分号来分隔；命令按照从左至右的顺序执行，并且以反斜线结束的行会继续到下一行，除非被另外一个反斜线转义。
一个字面量分号可以通过一个反斜线进行转义包含进来（例如，当需要制定一个命令行序列给键绑定时）
Example tmux commands include:
tmux命令包含样例：

    refresh-client -t/dev/ttyp2 
 
    rename-session -tfirst newname 
 
    set-window-option -t:0 monitor-activity on 
 
    new-window ; split-window -d 
 
    bind-key R source-file ~/.tmux.conf \; \ 
	    display-message "source-file done"

Or from sh(1):
或者从sh中：

    $ tmux kill-window -t :1 
 
    $ tmux new-window \; split-window -d 
 
    $ tmux new-session -d 'vi /etc/passwd' \; split-window -d \; attach

CLIENTS AND SESSIONS
客户端和会话：

The tmux server manages clients, sessions, windows and panes. Clients are attached to sessions to interact with them, either when they are created with the new-session command, or later with the attach-session command. Each session has one or more windows linked into it. Windows may be linked to multiple sessions and are made up of one or more panes, each of which contains a pseudo terminal. Commands for creating, linking and otherwise manipulating windows are covered in the WINDOWS AND PANES section.
tmux服务器管理客户端，会话，窗口和面板。客户端是附着在会话上来与他们交互的，不论他们是通过new-session命令或者之后的attach-session命令创建的。
每个会话具有一个或多个窗口与其链接。
窗口可以连接到多个会话上，窗口又是有一个或多个面板组成的，每个面板包含了一个伪终端。
对于创建、链接或其他窗口操作的命令会在WINDOWS AND PANES部分详解。

The following commands are available to manage clients and sessions:
下面的命令可以用来管理客户端和会话：
attach-session [-dr] [-c working-directory] [-t target-session]
(alias: attach)
(别名： attach)

If run from outside tmux, create a new client in the current terminal and attach it to target-session. If used from inside, switch the current client. If -d is specified, any other clients attached to the session are detached. -r signifies the client is read-only (only keys bound to the detach-client or switch-client commands have any effect)
如果从tmux之外来运行，会在当前终端中创建一个新的客户端并且将其附着在一个目标会话上。如果这个命令是在tmux中运行的，就会切换当前的客户端。
如果指定了 -d 选项， 附着在这个会话上的其他客户端就会脱离，
-r表示这个客户端时只读的（只有键绑定到detach-client或switch-client命令时具有效果）。

If no server is started, attach-session will attempt to start it; this will fail unless sessions are created in the configuration file.
如果没有启动服务器， attach-session 会尝试启动服务器；除非在配置文件中创建了会话否则就会失败。

The target-session rules for attach-session are slightly adjusted: if tmux needs to select the most recently used session, it will prefer the most recently used unattached session.
对于attach-session命令的目标会话规则稍微有一点调整：如果tmux需要选择最近使用的会话，会偏好选择最近使用的脱离附着的会话。

-c will set the session working directory (used for new windows) to working-directory.
-c 为设置会话的工作目录（为新窗口所使用）为working-directory

detach-client [-P] [-a] [-s target-session] [-t target-client]
(alias: detach)
(别名： detach)

Detach the current client if bound to a key, the client specified with -t, or all clients currently attached to the session specified by -s. The -a option kills all but the client given with -t. If -P is given, send SIGHUP to the parent process of the client, typically causing it to exit.
如果绑定了一个键时会脱离当前客户端，由-t来指定客户端，或者所有附着在由-s指定的会话中的客户端。
-a选项会终止除-t指定的目标客户端之外的所有客户端。
如果给定了-P，会发送一个SIGHUP信号给当前客户端的父进程，一般时导致其触发退出动作。

has-session [-t target-session]
(alias: has)
(别名: has)

Report an error and exit with 1 if the specified session does not exist. If it does exist, exit with 0.
如果指定的会话不存在的话，就会报告一个错误并且退出，返回值为1. 如果存在的话，就会退出，返回值为0.

kill-server

Kill the tmux server and clients and destroy all sessions.
终止tmux服务器和客户端并且销毁所有的会话。

kill-session [-a] [-t target-session]
Destroy the given session, closing any windows linked to it and no other sessions, and detaching all clients attached to it. If -a is given, all sessions but the specified one is killed.
销毁指定的会话，关闭连接到会话的任何窗口，并且让所有附着在其上的客户端脱离。
如果给定了-a选项的话，除了指定的会话之外的会话都被终止。

list-clients [-F format] [-t target-session]
(alias: lsc)
(别名： lsc)

List all clients attached to the server. For the meaning of the -F flag, see the FORMATS section. If target-session is specified, list only clients connected to that session.
列出附着在服务器上的所有客户端。 对于-F标记，可以参考 FORMATS 部分。
如果给定了目标会话的话，只列出连接到该会话上的客户端。  

list-commands
(alias: lscm)
(别名： lscm)

List the syntax of all commands supported by tmux.
列出所有tmux支持的所有命令语法。

list-sessions [-F format]
(alias: ls)
(别名： ls)

List all sessions managed by the server. For the meaning of the -F flag, see the FORMATS section.
列出服务器管理的所有会话，对于-F标记，参考 FORMATS部分。

lock-client [-t target-client]
(alias: lockc)
(别名: lockc)

Lock target-client, see the lock-server command.
锁定目标客户端， 可以参考 lock-server 命令。

lock-session [-t target-session]
(alias: locks)
(别名: locks)
Lock all clients attached to target-session.
锁定附着在目标会话上的所有客户端。

new-session [-AdDP] [-c start-directory] [-F format] [-n window-name] [-s session-name] [-t target-session] [-x width] [-y height] [shell-command]
(alias: new)
(别名:new)
Create a new session with name session-name.
使用session-name 来创建一个新的会话。

The new session is attached to the current terminal unless -d is given. window-name and shell-command are the name of and shell command to execute in the initial window. If -d is used, -x and -y specify the size of the initial window (80 by 24 if not given).
除非给定-d选项，否则新的会话就会附着在当前的终端上。 window-name和shell-comand
是在初始化窗口中执行的窗口和shell命令你该名称。
如果使用了-d选项， -x和-y用来指定初始窗口的大小（默认为80x24）。

If run from a terminal, any termios(4) special characters are saved and used for new windows in the new session.
如果从终端中运行的恶化，任何的termios特殊字符都被保存并且在新会话中的新窗口中使用。

The -A flag makes new-session behave like attach-session if session-name already exists; in this case, -D behaves like -d to attach-session.
-A
标记使得新会话与一个附着会话具有相同的行为，如果会话名称已经存在的话；这种情况下，对于attach-session来说-D具有与-d相同的行为。

If -t is given, the new session is grouped with target-session. This means they share the same set of windows - all windows from target-session are linked to the new session and any subsequent new windows or windows being closed are applied to both sessions. The current and previous window and any session options remain independent and either session may be killed without affecting the other. Giving -n or shell-command are invalid if -t is used.
如果给定了-t选项，新的会话被分组到目标会话中。
这就意味着他们共享相同的窗口集合--目标会话中的所有窗口都会连接到新的会话上，并且任何后续的新建窗口或关闭窗口都会被应用在两个会话上。
当前的窗口和之前的窗口以及任何会话选项保持独立，并且每个会话都会在不影响其他会话的情况下被终止。 -n或
shell-command只有在使用-t选项时合法。


The -P option prints information about the new session after it has been created. By default, it uses the format ‘#{session_name}:’ but a different format may be specified with -F.
-P选项会在新会话创建之后来打印新会话相关信息。默认情况下，会使用'#{session_name}:'格式，但是可以通过-F来指定一个不同的格式。

refresh-client [-S] [-t target-client]
(alias: refresh)
(别名:refresh)

Refresh the current client if bound to a key, or a single client if one is given with -t. If -S is specified, only update the client's status bar.
如果绑定了一个键的话会刷新当前客户端，如果使用-t指定了一个客户端的话会刷新单独的客户端。如果指定-S，只会更新客户端的状态条。

rename-session [-t target-session] new-name
(alias: rename)
(别名：rename)

Rename the session to new-name.
重命名会话为一个新名称。

show-messages [-IJT] [-t target-client]
(alias: showmsgs)
(别名：showmsgs)
Show client messages or server information. Any messages displayed on the status line are saved in a per-client message log, up to a maximum of the limit set by the message-limit server option. With -t, display the log for target-client. -I, -J and -T show debugging information about the running server, jobs and terminals.
显示客户端消息或服务器信息。所有显示在状态行的消息都存储在一个客户端独立的消息日志中，具有一个由message-limit选项设置的最大限制。使用-t会显示目标客户端的日志。
-I，-J 和-T分别显示运行服务器，任务和终端的调试信息。

source-file path
(alias: source)
(别名：source)
Execute commands from path.
从路径中来执行命令

start-server
(alias: start)
(别名：start)
Start the tmux server, if not already running, without creating any sessions.
开启tmux服务器，如果还没有运行，不会创建任何会话。

suspend-client [-t target-client]
(alias: suspendc)
(别名：suspendc)
Suspend a client by sending SIGTSTP (tty stop).
通过发送一个SIGTSTP（tty stop）信号来挂起一个客户端。

switch-client [-lnpr] [-c target-client] [-t target-session] [-T key-table]
(alias: switchc)
(别名：switchc)
Switch the current session for client target-client to target-session. If -l, -n or -p is used, the client is moved to the last, next or previous session respectively. -r toggles whether a client is read-only (see the attach-session command).
将目标客户端所在的当前会话切换到目标会话中， 如果-l, -n或者-p被使用的话，客户端会被分别移动到最后，下一个或上一个会话中。-r 转换一个客户端的只读（可以参考attach-session命令）

-T sets the client's key table; the next key from the client will be interpreted from key-table. This may be used to configure multiple prefix keys, or to bind commands to sequences of keys. For example, to make typing ‘abc’ run the list-keys command:
-T
设置客户端的简表；来自客户端的下一个键会被解释为来自键表。这可能会被用在配置多个前缀键时或者绑定命令到一序列键值时使用。例如，让键入'abc'来运行 list-keys命令：
bind-key -Ttable2 c list-keys 
bind-key -Ttable1 b switch-client -Ttable2 
bind-key -Troot   a switch-client -Ttable1

WINDOWS AND PANES
窗口和面板

A tmux window may be in one of several modes. The default permits direct access to the terminal attached to the window. The other is copy mode, which permits a section of a window or its history to be copied to a paste buffer for later insertion into another window. This mode is entered with the copy-mode command, bound to ‘\[’ by default. It is also entered when a command that produces output, such as list-keys, is executed from a key binding.
一个tmux窗口可能会在处在多个模式中的某一个模式。默认的模式时直接访问附着在窗口上的终端。另外一个是复制模式，允许一个窗口的一部分或者其历史能够被复制到一个粘贴缓存中，以便稍候插入到另外的窗口中。
这个模式时使用 copy-mode命令来进入的，默认绑定到'\['上。也会在一个命令产生输出时进入，例如通过键绑定执行的list-keys。

The keys available depend on whether emacs or vi mode is selected (see the mode-keys option). The following keys are supported as appropriate for the mode:
可用的键依赖于是选择emacs还是vi模式（参考 mode-keys 选项）。 下面的键对于不同的模式具有合适的支持。

Function	                vi	        emacs
函数                        vi模式      emacs模式
Append selection	        A	
Back to indentation	        ^	        M-m
Bottom of history	        G	        M-<
Clear selection	Escape	    C-g
Copy selection	Enter	    M-w
Copy to named buffer    	"   	
Cursor down	                j       	Down
Cursor left	                h	        Left
Cursor right	            l	        Right
Cursor to bottom line	    L	
Cursor to middle line	    M	        M-r
Cursor to top line	        H	        M-R
Cursor up	                k	        Up
Delete entire line	        d	        C-u
Delete/Copy to end of line	D	        C-k
End of line	                $	        C-e
Go to line              	:           g
Half page down	            C-d	        M-Down
Half page up	            C-u	        M-Up
Jump again	                ;	        ;
Jump again in reverse   	,	        ,
Jump backward	            F	        F
Jump forward	            f       	f
Jump to backward	        T	
Jump to forward	            t	
Next page	                C-f	        Page down
Next space	                W	
Next space, end of word	    E	
Next word	                w	
Next word end	            e	        M-f
Other end of selection	    o	
Paste buffer	            p	        C-y
Previous page	            C-b	        Page up
Previous space	            B	
Previous word	            b	        M-b
Quit mode	                q	        Escape
Rectangle toggle	        v	        R
Scroll down	                C-Down/C-e	C-Down
Scroll up	                C-Up/C-y	C-Up
Search again	            n	        n
Search again in reverse	    N	        N
Search backward	            ?	        C-r
Search forward	            /	        C-s
Select line	                V	
Start of line	            0	        C-a
Start selection	            Space	    C-Space
Top of history	            g	        M->
Transpose characters		C-t

The next and previous word keys use space and the ‘-’, ‘_’ and ‘@’ characters as word delimiters by default, but this can be adjusted by setting the word-separators session option. Next word moves to the start of the next word, next word end to the end of the next word and previous word to the start of the previous word. The three next and previous space keys work similarly but use a space alone as the word separator.
下一个和上一个单词简默认使用空格和'-','_'以及'@'字符作为单词分隔符，但是可以通过设置会话的word-separators选项进行调整。下一个单词会移动到下一个单词的开始位置，下一个单词的末尾会移动到下一个单词的末尾位置，
前一个单词移动到前一个单词的开始位置。 三个下一个和前一个空格键具有相似的作用但是单独使用一个空国作为单词分隔符。

The jump commands enable quick movement within a line. For instance, typing ‘f’ followed by ‘/’ will move the cursor to the next ‘/’ character on the current line. A ‘;’ will then jump to the next occurrence.
跳转命令允许在一个行中快速移动，例如，输入'f'跟随一个'/'会将光标移动到当前行的下一个'/'字符处。
一个';'之后会移动到字符下一次出现的地方。

Commands in copy mode may be prefaced by an optional repeat count. With vi key bindings, a prefix is entered using the number keys; with emacs, the Alt (meta) key and a number begins prefix entry. For example, to move the cursor forward by ten words, use ‘M-1 0 M-f’ in emacs mode, and ‘10w’ in vi.
复制模式中的命令可能由一个可选的重复计数器作为前导，在vi键绑定下，通过数字键来输入前导；使用emacs时，使用Alt(meta)+数字作为前导实体。例如，为了将光标向前移动10个单词使用'M-1 0 M-f'-对于emacs模式，'10w'-对于vi模式。

Mode key bindings are defined in a set of named tables: vi-edit and emacs-edit for keys used when line editing at the command prompt; vi-choice and emacs-choice for keys used when choosing from lists (such as produced by the choose-window command); and vi-copy and emacs-copy used in copy mode. The tables may be viewed with the list-keys command and keys modified or removed with bind-key and unbind-key. If append-selection, copy-selection, or start-named-buffer are given the -x flag, tmux will not exit copy mode after copying. copy-pipe copies the selection and pipes it to a command. For example the following will bind ‘C-w’ not to exit after copying and ‘C-q’ to copy the selection into /tmp as well as the paste buffer:
模式键绑定是通过一个命名表集合定义的：在命令提示的行编辑时使用vi-edit和emacs-edit键，当从列表中选择时使用vi-choice和emacs-coice键，在复制模式中时使用vi-copy和emacs-copy键。这些表可以通过list-keys命令来查看，另外可以通过bind-key和unbund-key命令来修改或移除键。如果append-selection,copy-selection或者start-named-buffer给定-x标记，tmux将不会在复制之后退出复制模式。
copy-pipe复制所选内容并且将其管道到一个命令。例如下面的命令会绑定'C-w'在复制之后不会退出，
'C-q'将所选内容复制到/tmp和粘贴缓冲中。

bind-key -temacs-copy C-w copy-selection -x 
bind-key -temacs-copy C-q copy-pipe "cat >/tmp/out"

The paste buffer key pastes the first line from the top paste buffer on the stack.
粘贴缓存键会从栈中顶端的粘贴缓存中粘贴第一行。

The synopsis for the copy-mode command is:
copy-mode命令的简介为：

copy-mode [-Mu] [-t target-pane]
Enter copy mode. The -u option scrolls one page up. -M begins a mouse drag (only valid if bound to a mouse key binding, see MOUSE SUPPORT).
进入复制模式。-u选项向上滚动一页。 -M 开始一个鼠标拖拽（只有在绑定鼠标键绑定时有效，参考MOUSE SUPPORT）

Each window displayed by tmux may be split into one or more panes; each pane takes up a certain area of the display and is a separate terminal. A window may be split into panes using the split-window command. Windows may be split horizontally (with the -h flag) or vertically. Panes may be resized with the resize-pane command (bound to ‘C-up’, ‘C-down’ ‘C-left’ and ‘C-right’ by default), the current pane may be changed with the select-pane command and the rotate-window and swap-pane commands may be used to swap panes without changing their position. Panes are numbered beginning from zero in the order they are created.
tmux显示的每个窗口可能会被分割为一个或多个面板；每个面板占用一个特定的区域进行显示并且具有一个单独的终端。一个窗口可以通过split-window名令分割为多个面板。窗口可以被水平分割（使用-h标记）或者垂直分割。面板可以通过resize-pane命令改变大小（默认绑定为'C-up','C-down','C-left','C-right'）, 当前的面板可能会通过select-panel命令改变，而rotate-window和swap-panel命令可以在不改变面板位置的情况下切换面板。 面板被从0开始的数字按顺序计数。

A number of preset layouts are available. These may be selected with the select-layout command or cycled with next-layout (bound to ‘Space’ by default); once a layout is chosen, panes within it may be moved and resized as normal.
有一些默认的预设布局可用，这可以通过select-layout命令来选择或者使用next-layout命令循环选择（默认绑定为'Space'布局）；一旦布局被选定，其中的面板会被移动以及重新改变大小。

The following layouts are supported:
支持以下的布局：

even-horizontal:
Panes are spread out evenly from left to right across the window.
面板按照偶数地从左到右来分布在窗口中。

even-vertical:
Panes are spread evenly from top to bottom.
面板按照偶数地从上到下来分布在窗口中

main-horizontal
A large (main) pane is shown at the top of the window and the remaining panes are spread from left to right in the leftover space at the bottom. Use the main-pane-height window option to specify the height of the top pane.
在窗口的顶端会显示一个大的面板，其余的面板按照从左到右的方式在底部左端的空间分布，可以使用main-pane-height窗口选项来指定顶部面板的高度。

main-vertical
Similar to main-horizontal but the large pane is placed on the left and the others spread from top to bottom along the right. See the main-pane-width window option.
类似于main-horizontal，但是最大的面板会放置在窗口左边而其他的面板按照从上往下的方式在右边进行分布。
可以参考main-pane-width窗口选项。

tiled
Panes are spread out as evenly as possible over the window in both rows and columns.
面板会尽量将面板在窗口中在行列上以偶数地方式分布。

In addition, select-layout may be used to apply a previously used layout - the list-windows command displays the layout of each window in a form suitable for use with select-layout. For example:
此外，select-layout可以用来应用一个之前使用的布局，list-windows命令会以一个合适的格式显示每个窗口的布局来于select-layout命令结合使用，例如：

    $ tmux list-windows 

0: ksh [159x48] 
    layout: bb62,159x48,0,0{79x48,0,0,79x48,80,0} 
$ tmux select-layout bb62,159x48,0,0{79x48,0,0,79x48,80,0}

tmux automatically adjusts the size of the layout for the current window size. Note that a layout cannot be applied to a window with more panes than that from which the layout was originally defined.
tmux自动地调整当前窗口大小中的布局大小。 注意，一个布局不能应用在多于布局默认定义的面板数量。

Commands related to windows and panes are as follows:
与窗口和面板相关的命令如下：

break-pane [-dP] [-F format] [-t target-pane]
(alias: breakp)
(别名：breakp)
Break target-pane off from its containing window to make it the only pane in a new window. If -d is given, the new window does not become the current window. The -P option prints information about the new window after it has been created. By default, it uses the format ‘#{session_name}:#{window_index}’ but a different format may be specified with -F.
将目标面板从其所在的窗口中终止，并将其作为一个新窗口中的唯一的面板。 如果指定-d,新的窗口不会称为当前的窗口。
-P选项会在新窗口创建之后显示其信息。 默认会使用
'#{session_name}:#{window_index}'的显示格式，但是可以通过-f来指定一个不同的格式。

capture-pane [-aepPq] [-b buffer-name] [-E end-line] [-S start-line] [-t target-pane]
(alias: capturep)
(别名：capturep)
Capture the contents of a pane. If -p is given, the output goes to stdout, otherwise to the buffer specified with -b or a new buffer if omitted. If -a is given, the alternate screen is used, and the history is not accessible. If no alternate screen exists, an error will be returned unless -q is given. If -e is given, the output includes escape sequences for text and background attributes. -C also escapes non-printable characters as octal \xxx. -J joins wrapped lines and preserves trailing spaces at each line's end. -P captures only any output that the pane has received that is the beginning of an as-yet incomplete escape sequence.
捕获一个面板的内容，如果指定-p，那么输出会到达stdou，否则会到达有-b指定的缓冲区（如果没有指定-b缓冲区的话就会指定一个新的缓冲区）。
如果指定-a, 会使用备用屏幕，并且历史是不可以访问的。如果没有备用的屏幕，在没有指定-q的情况下会返回一个错误。
如果指定-e,那么输出会包含文本转义序列和后台属性。 -C 也会转义非打印字符为八进制 \\xxx。 -J
会链接包裹的多行并且保留每行末尾尾随的空格。 -P 只会面板接受到的捕获开头是一个非完整转义序列的任意输出。

-S and -E specify the starting and ending line numbers, zero is the first line of the visible pane and negative numbers are lines in the history. ‘-’ to -S is the start of the history and to -E the end of the visible pane. The default is to capture only the visible contents of the pane.
-S 和 -E 指定开始和结束行的行数，0是可视面板的第一行，而负数时历史行。 '-'到 -S是历史的开始，而
'-'到-E是可视面板的结尾。 默认情况下只会捕获面板的可视内容。

choose-client [-F format] [-t target-window] [template]
Put a window into client choice mode, allowing a client to be selected interactively from a list. After a client is chosen, ‘%%’ is replaced by the client pty(4) path in template and the result executed as a command. If template is not given, "detach-client -t '%%'" is used. For the meaning of the -F flag, see the FORMATS section. This command works only if at least one client is attached.
将一个窗口置于客户端选择模式，允许从一个列表中交互地选择一个客户端。
在一个客户端被选择之后'%%'会由模板中的客户端pty路径替换，之后的结果会作为一个命令被执行。如果模板没有给定，会使用"detach-client
-t '%%'"。 对于-F标记，可以参考FORMATS部分。 这个命令只有在至少一个客户端被附着之后才工作。
choose-session [-F format] [-t target-window] [template]
Put a window into session choice mode, where a session may be selected interactively from a list. When one is chosen, ‘%%’ is replaced by the session name in template and the result executed as a command. If template is not given, "switch-client -t '%%'" is used. For the meaning of the -F flag, see the FORMATS section. This command works only if at least one client is attached.
将一个窗口置于会话选择模式中，可以从一个列表中交互式地选择一个会话。当一个会话被选择时，'%%'会由模板中的会话名称替换，之后的结果会作为一个命令被执行。如果模板没有给定，会使用"switch-client -t '%%'"。对于-F标记，可以参考FORMATS部分.这个命令只有在至少有一个客户端附着时工作。

choose-tree [-suw] [-b session-template] [-c window-template] [-S format] [-W format] [-t target-window]
Put a window into tree choice mode, where either sessions or windows may be selected interactively from a list. By default, windows belonging to a session are indented to show their relationship to a session.
将窗口置于一个树选择模式，其中的会话或窗口可能是从一个列表中交互地选择的。
默认情况下，窗口属于一个会话主要为了显示他们与一个会话的关系。

Note that the choose-window and choose-session commands are wrappers around choose-tree.
注意choose-window和choose-session命令被包裹在choose-tree中。

If -s is given, will show sessions. If -w is given, will show windows.
如果给定-s会显示会话，如果给定-w会显示窗口。

By default, the tree is collapsed and sessions must be expanded to windows with the right arrow key. The -u option will start with all sessions expanded instead.
默认情形下，树是被折叠起来的，会话必须通过右箭头简将其展开为窗口。 -u选项会将所有的会话展开。

If -b is given, will override the default session command. Note that ‘%%’ can be used and will be replaced with the session name. The default option if not specified is "switch-client -t '%%'". If -c is given, will override the default window command. Like -b, ‘%%’ can be used and will be replaced with the session name and window index. When a window is chosen from the list, the session command is run before the window command.
如果给定-b，会重载默认的会话命令。 注意 '%%'可以被使用而且会被会话名称替换。如果没有指定的话，默认为"switch-client -t
'%%'"。 如果给定-c，会重载默认的窗口命令，与-b类似，'%%'可以被使用而且会被会话名与窗口索引替换。
当一个窗口从列表中被选择时，会话命令会在窗口命令运行之前运行。

If -S is given will display the specified format instead of the default session format. If -W is given will display the specified format instead of the default window format. For the meaning of the -s and -w options, see the FORMATS section.
如果给定-S，会显示指定的格式而不是默认的会话格式。如果给定-W，会显示指定的格式而不是默认的窗口格式。
对于-s和-w选项的含义可以参考FORMATS部分。

This command works only if at least one client is attached.
这个命令只有当至少有一个客户端附着时工作。

choose-window [-F format] [-t target-window] [template]
Put a window into window choice mode, where a window may be chosen interactively from a list. After a window is selected, ‘%%’ is replaced by the session name and window index in template and the result executed as a command. If template is not given, "select-window -t '%%'" is used. For the meaning of the -F flag, see the FORMATS section. This command works only if at least one client is attached.
将一个窗口置于一个选择模式，其中的窗口可以从一个列表中交互地选择。当选择一个窗口之后，'%%'会被模板中的会话名称和窗括索引替换，之后的结果作为一个命令被执行。如果没有给定模板，"select-window -t '%%'"被使用。 对于-F的含义可以参考FORMATS部分。 这个命令只有在至少一个客户端附着之后才会工作。

display-panes [-t target-client]
(alias: displayp)
(别名：displayp)
Display a visible indicator of each pane shown by target-client. See the display-panes-time, display-panes-colour, and display-panes-active-colour session options. While the indicator is on screen, a pane may be selected with the ‘0’ to ‘9’ keys.
由一个客户端来显示每个面板的可视化指示器，可以参考 display-panes-time, display-panes-colour和
display-panes-active-colour会话选项。由于指示器在屏幕上，一个面板可以通过'0-9'键来选择。

find-window [-CNT] [-F format] [-t target-window] match-string
(alias: findw)
(别名:findw)
Search for the fnmatch(3) pattern match-string in window names, titles, and visible content (but not history). The flags control matching behavior: -C matches only visible window contents, -N matches only the window name and -T matches only the window title. The default is -CNT. If only one window is matched, it'll be automatically selected, otherwise a choice list is shown. For the meaning of the -F flag, see the FORMATS section. This command works only if at least one client is attached.
在窗口名称，标题和可见的内容中搜索fnmatch模式的匹配字符串。标记被用来控制匹配行为： -C只匹配可见窗口内容，
-N只匹配窗口名称，-T匹配窗口标题。
默认为-CNT。如果只有一个窗口匹配，就会被自动选择，否则就会显示一个选项列表。对于-F标记可以参考FORMATS部分。这个命令只有在至少一个客户端被附着时会工作。

join-pane [-bdhv] [-l size | -p percentage] [-s src-pane] [-t dst-pane]
(alias: joinp)
(别名：joinp)
Like split-window, but instead of splitting dst-pane and creating a new pane, split it and move src-pane into the space. This can be used to reverse break-pane. The -b option causes src-pane to be joined to left of or above dst-pane.
与split-window相似，但是取代分割dst-panel并创建一个新面板而代之的是，将其分割并将src-panel移动到空间中。
这个可以用来逆转break-pane动作。-b选项使得src-pane被联接到dst-pane的左边或上边。

kill-pane [-a] [-t target-pane]
(alias: killp)
(别名：killp)
Destroy the given pane. If no panes remain in the containing window, it is also destroyed. The -a option kills all but the pane given with -t.
销毁给定的pane。如果所在窗口中没有剩余的面板，该窗口也会被销毁。 -a选项会销毁除由-t指定面板之外的所有面板。

kill-window [-a] [-t target-window]
(alias: killw)
(别名：killw)
Kill the current window or the window at target-window, removing it from any sessions to which it is linked. The -a option kills all but the window given with -t.
终止当前窗口或目标窗口，将其从所链接的任意会话中移除。 -a选项终止除-t指定窗口之外的所有窗口。

last-pane [-de] [-t target-window]
(alias: lastp)
(别名：lastp)
Select the last (previously selected) pane. -e enables or -d disables input to the pane.
选择最后一个面板，-e 使得输入到面板生效，-d使得输入到面板失效。

last-window [-t target-session]
(alias: last)
(别名:last)
Select the last (previously selected) window. If no target-session is specified, select the last window of the current session.
选择最后一个窗口，如果没有目标窗口指定，选择当前会话中的最后一个窗口。

link-window [-dk] [-s src-window] [-t dst-window]
(alias: linkw)
(别名:linkw)
Link the window at src-window to the specified dst-window. If dst-window is specified and no such window exists, the src-window is linked there. If -k is given and dst-window exists, it is killed, otherwise an error is generated. If -d is given, the newly linked window is not selected.
将在src-window的窗口链接到指定的dst-window。如果dst-window被指定但是不存在的话，那么src-window会被链接导那儿。
如果给定-k并且dst-window存在，那么就会将其终止，否则就会生成一个错误。如果给定-d，新链接的窗口不会被选择。

list-panes [-as] [-F format] [-t target]
(alias: lsp)
(别名：lsp)
If -a is given, target is ignored and all panes on the server are listed. If -s is given, target is a session (or the current session). If neither is given, target is a window (or the current window). For the meaning of the -F flag, see the FORMATS section.
如果给定-a, 会湖绿target并且会列出服务器上的所有面板。
如果给定-s，target就是一个会话（或者当前会话）。如果都没有指定，target就是一个窗口（或者当前窗口）。对于-F标记可以参考FORMATS部分。

list-windows [-a] [-F format] [-t target-session]
(alias: lsw)
(别名：lsw)
If -a is given, list all windows on the server. Otherwise, list windows in the current session or in target-session. For the meaning of the -F flag, see the FORMATS section.
如果给定-a,会列出服务器上的所有窗口。 否则会列出当前会话或target-session中的窗口。对于-F标记可以参考FORMATS部分。

move-pane [-bdhv] [-l size | -p percentage] [-s src-pane] [-t dst-pane]
(alias: movep)
(别名：movep)
Like join-pane, but src-pane and dst-pane may belong to the same window.
与join-pane类似，但是src-pane和dst-pane可以属于相同的窗口。

move-window [-rdk] [-s src-window] [-t dst-window]
(alias: movew)
(别名: movew)
This is similar to link-window, except the window at src-window is moved to dst-window. With -r, all windows in the session are renumbered in sequential order, respecting the base-index option.
这个于link-window相似，除了src-window中的窗口被移动到dst-window。给定-r会话中的所有窗口都会在遵照base-index选项下按照序列顺序重新编号。

new-window [-adkP] [-c start-directory] [-F format] [-n window-name] [-t target-window] [shell-command]
(alias: neww)
(别名：neww)
Create a new window. With -a, the new window is inserted at the next index up from the specified target-window, moving windows up if necessary, otherwise target-window is the new window location.
创建一个新的窗口，给定-a，新建的窗口会被插入到指定target-window的下一个索引上，必要的话会将窗口向上移，否则target-window就是这个新建的窗口。

If -d is given, the session does not make the new window the current window. target-window represents the window to be created; if the target already exists an error is shown, unless the -k flag is used, in which case it is destroyed. shell-command is the command to execute. If shell-command is not specified, the value of the default-command option is used. -c specifies the working directory in which the new window is created.
如果给定-d,
会话不会将新建窗口作为当前窗口。target-window表示将会创建的窗口；如果目标窗口已经存在会显示一个错误，如果使用-k标记就会销毁。
shell-command是将要执行的命令。如果没有指定shell-command,
default-command选项的值被默认使用。-c选项指定了新窗口创建的工作目录。

When the shell command completes, the window closes. See the remain-on-exit option to change this behaviour.
当shell命令完成时，窗口关闭。 参考remain-on-exit选项来改变这个行为。

The TERM environment variable must be set to “screen” for all programs running inside tmux. New windows will automatically have “TERM=screen” added to their environment, but care must be taken not to reset this in shell start-up files.
对于运行在tmux中的所有程序需要将TERM环境变量设置为"screen"。新的窗口会自动将"TERM=screen"加到他们的环境中，但是必须注意不要在shell启动文件中重置这个变量。

The -P option prints information about the new window after it has been created. By default, it uses the format ‘#{session_name}:#{window_index}’ but a different format may be specified with -F.
-P
选项在新窗口创建后会打印与之相关的信息。默认情况下，使用'#{session_name}:#{window_index}'的格式，但是可以通过使用-F来指定一个不同的格式。

next-layout [-t target-window]
(alias: nextl)
(别名：nextl)
Move a window to the next layout and rearrange the panes to fit.
将窗口移动到下一个布局模式并且重新安排面板来使之适应。

next-window [-a] [-t target-session]
(alias: next)
(别名：next)
Move to the next window in the session. If -a is used, move to the next window with an alert.
移动到会话中的下一个窗口，如果-a指定，在移动到下一个窗口时带有警告。

pipe-pane [-o] [-t target-pane] [shell-command]
(alias: pipep)
(别名：pipep)
Pipe any output sent by the program in target-pane to a shell command. A pane may only be piped to one command at a time, any existing pipe is closed before shell-command is executed. The shell-command string may contain the special character sequences supported by the status-left option. If no shell-command is given, the current pipe (if any) is closed.
将target-pane中程序的输出通过管道传递给一个shell命令。一个面板可能一次只能管道给一个命令，在shell-command命令执行之前任何存在的管道都会关闭。
shell-command字符串可能会包含status-left选项所支持的特殊字符序列。 如果没有指定shell-command,那么当前的管道就会被关闭。

The -o option only opens a new pipe if no previous pipe exists, allowing a pipe to be toggled with a single key, for example:
-o选项只有在没有之前的管道存在时打开一个新管道，允许一个管道通过一个单键进行切换，例如：
    bind-key C-p pipe-pane -o 'cat >>~/output.#I-#P'


previous-layout [-t target-window]
(alias: prevl)
(别名：prevl )
Move to the previous layout in the session.
移动到会话之前的布局。

previous-window [-a] [-t target-session]
(alias: prev)
(别名:prev)
Move to the previous window in the session. With -a, move to the previous window with an alert.
移动到会话之前的窗口，使用-a选项会带有一个警告。

rename-window [-t target-window] new-name
(alias: renamew)
(别名: renamew)
Rename the current window, or the window at target-window if specified, to new-name.
重命名当前窗口或者由-t指定的target-window窗口为new-name

resize-pane [-DLMRUZ] [-t target-pane] [-x width] [-y height] [adjustment]
(alias: resizep)
(别名：resizep)
Resize a pane, up, down, left or right by adjustment with -U, -D, -L or -R, or to an absolute size with -x or -y. The adjustment is given in lines or cells (the default is 1).
重新定义面板的大小，通过-U, -D, -L或-R来调整上下左右，或者通过-x/-y指定绝对值大小。
调整是通过行或单元格来给定的（默认为1）。

With -Z, the active pane is toggled between zoomed (occupying the whole of the window) and unzoomed (its normal position in the layout).
使用-Z时，活动面板会在放大（占用整个窗口）或未放大（在布局中的正常位置）之间进行切换。

-M begins mouse resizing (only valid if bound to a mouse key binding, see MOUSE SUPPORT).
-M 开始鼠标重定义大小（只有在鼠标键绑定时有效，参考MOUSE SUPPORT部分）

respawn-pane [-k] [-t target-pane] [shell-command]
(alias: respawnp)
(别名：respawnp)
Reactivate a pane in which the command has exited (see the remain-on-exit window option). If shell-command is not given, the command used when the pane was created is executed. The pane must be already inactive, unless -k is given, in which case any existing command is killed.
在shell-comman退出之后重新激活面板（可以参考remain-on-exit 窗口选项）。如果没有给定shell-comman,
那么面板创建时所使用的命令会被执行。面板必须是已经激活的状态，如果给定-k,任何存在的命令都会被终止。

respawn-window [-k] [-t target-window] [shell-command]
(alias: respawnw)
(别名：respawnw)
Reactivate a window in which the command has exited (see the remain-on-exit window option). If shell-command is not given, the command used when the window was created is executed. The window must be already inactive, unless -k is given, in which case any existing command is killed.
在shell-command退出之后重新激活窗口（可以参考remain-on-exit窗口选项）。 如果没有指定shell-comman,
那么窗口创建时所使用的命令会被执行。 窗口必须是已经激活的状态，如果指定-k任何存在的命令都会被终止。

rotate-window [-DU] [-t target-window]
(alias: rotatew)
(别名：rotatew)
Rotate the positions of the panes within a window, either upward (numerically lower) with -U or downward (numerically higher).
轮换窗口中面板的位置，或者通过-U向前或者向后。

select-layout [-nop] [-t target-window] [layout-name]
(alias: selectl)
(别名：selectl)
Choose a specific layout for a window. If layout-name is not given, the last preset layout used (if any) is reapplied. -n and -p are equivalent to the next-layout and previous-layout commands. -o applies the last set layout if possible (undoes the most recent layout change).
为窗口选择一个特定的布局，如果没有指定布局名称，就会使用最后使用的预设布局并且重新布局。
select-pane [-DdegLlRU] [-P style] [-t target-pane]
(alias: selectp)
(别名：selectp)
Make pane target-pane the active pane in window target-window, or set its style (with -P). If one of -D, -L, -R, or -U is used, respectively the pane below, to the left, to the right, or above the target pane is used. -l is the same as using the last-pane command. -e enables or -d disables input to the pane.
将target-pane面板作为target-window窗口中的活动面板，或者设置其风格（使用-P）。如果使用了-D,-L,-R或者-U的话，就会分别使用target-pane面板的下面，左边，右边或上面的面板。-l与使用last-pane命令效果一样。
-e使得输入到面板生效，-d使得输入到面板失效。

Each pane has a style: by default the window-style and window-active-style options are used, select-pane -P sets the style for a single pane. For example, to set the pane 1 background to red:
每个面板具有一个风格：默认使用window-style和window-active-style选项。select-pane -P 为单个面板设置风格。
例如将第一个面板的北京设置为红色red:
    select-pane -t:.1 -P 'bg=red'

-g shows the current pane style.
-g 显示当前面板的风格。

select-window [-lnpT] [-t target-window]
(alias: selectw)
(别名：selectw)
Select the window at target-window. -l, -n and -p are equivalent to the last-window, next-window and previous-window commands. If -T is given and the selected window is already the current window, the command behaves like last-window.
选择目标窗口。-l,-n和-p等价于last-window,next-window和previous-window命令。
如果给定-T,而且选择的窗口已经是当前窗口，那么命令表现为last-windown命令。

split-window [-bdhvP] [-c start-directory] [-l size | -p percentage] [-t target-pane] [shell-command] [-F format]
(alias: splitw)
(别名：splitw)
Create a new pane by splitting target-pane: -h does a horizontal split and -v a vertical split; if neither is specified, -v is assumed. The -l and -p options specify the size of the new pane in lines (for vertical split) or in cells (for horizontal split), or as a percentage, respectively. The -b option causes the new pane to be created to the left of or above target-pane. All other options have the same meaning as for the new-window command.
通过分割target-pane窗口来创建一个新面板: -h为水平分割，-v为垂直分割; 默认为垂直分割。
-l和-p选项指定新面板的行（对于垂直分割）或单元格（水平分割）,或者作为百分比。-b选项使得新建面板在target-pane的左边或上边。其他的任何选项的含义于new-window命令一样。

swap-pane [-dDU] [-s src-pane] [-t dst-pane]
(alias: swapp)
(别名：swapp)
Swap two panes. If -U is used and no source pane is specified with -s, dst-pane is swapped with the previous pane (before it numerically); -D swaps with the next pane (after it numerically). -d instructs tmux not to change the active pane.
转换两个面板。如果使用-U并且没有通过-s来指定源面板的话，dst-pane会与之前的面板进行转换;-D与下一个面板进行转换。
-d用来指示tmux不要改变活动面板。

swap-window [-d] [-s src-window] [-t dst-window]
(alias: swapw)
(别名：swapw)
This is similar to link-window, except the source and destination windows are swapped. It is an error if no window exists at src-window.
这个与link-window类似，除了源面板与目标面板相互转换之外。如果在src-window没有窗口的话会出错。

unlink-window [-k] [-t target-window]
(alias: unlinkw)
(别名：unlinkw)
Unlink target-window. Unless -k is given, a window may be unlinked only if it is linked to multiple sessions - windows may not be linked to no sessions; if -k is specified and the window is linked to only one session, it is unlinked and destroyed.
取消target-window的链接。除非给定-k选项，否则只有当一个窗口链接到多个会话时才会被取消链接-窗口不能链接到空会话;如果指定-k选项，而且窗口只有与一个会话相关联，那么窗口会被取消该链接并且被销毁。

KEY BINDINGS
键绑定：

tmux allows a command to be bound to most keys, with or without a prefix key. When specifying keys, most represent themselves (for example ‘A’ to ‘Z’). Ctrl keys may be prefixed with ‘C-’ or ‘^’, and Alt (meta) with ‘M-’. In addition, the following special key names are accepted: Up, Down, Left, Right, BSpace, BTab, DC (Delete), End, Enter, Escape, F1 to F12, Home, IC (Insert), NPage/PageDown/PgDn, PPage/PageUp/PgUp, Space, and Tab. Note that to bind the ‘"’ or ‘'’ keys, quotation marks are necessary, for example:
tmux允许一个命令被绑定到大多数键上-无论时候带有前导键。当指定键时，大部分键代表其本身字面量含义（例如'A-Z'）。
Control键可能表示为'C-'或'^'前导，而Alt(meta)表示为'M-'.此外，特殊的键名称也是被接受的：Up,Down,Left,Right,BSpace,BTab,DC(delete),End,Enter,Escape,F1-F12,Home,IC(insert), NPage/PageDown/PgDn, PPage/PageUp/PgUp,Space,和Tab。注意为了绑定双引号或单引号键，引号标记是必需的，例如：
    bind-key '"' split-window 
    bind-key "'" new-window

Commands related to key bindings are as follows:
与键绑定相关的命令如下：

bind-key [-cnr] [-t mode-table] [-T key-table] key command [arguments]
(alias: bind)
(别名:bind)
Bind key key to command. Keys are bound in a key table. By default (without -T), the key is bound in the prefix key table. This table is used for keys pressed after the prefix key (for example, by default ‘c’ is bound to new-window in the prefix table, so ‘C-b c’ creates a new window). The root table is used for keys pressed without the prefix key: binding ‘c’ to new-window in the root table (not recommended) means a plain ‘c’ will create a new window. -n is an alias for -T root. Keys may also be bound in custom key tables and the switch-client -T command used to switch to them from a key binding. The -r flag indicates this key may repeat, see the repeat-time option.
将键绑定到命令。键被绑定到一个键表中。
默认情况（没有-T)时，键被绑定到前导键表中。这个表被用在前导键键入之后再键入的键（例如，'c'默认绑定为前导键表中的新建窗口命令，所以'C-b c'会创建一个新窗口）。根键表被用在不需要前导键的键输入：将'c'在根键表中绑定为新建窗口命令时意味着一个普通的'c'键就会创建一个新窗口。-n 是 -T root的别名。键同样可以绑定到客制化的键表中，switch-client -T command 用来从一个键绑定中切换到相应的键。 -r标记指定这个键可以重复，参考repeat-time选项。

If -t is present, key is bound in mode-table: the binding for command mode with -c or for normal mode without. See the WINDOWS AND PANES section and the list-keys command for information on mode key bindings.
如果出现-t选项，那么键会绑定到一个mode-table(模式表): 使用-c来绑定到命令模式，缺省时绑定为普通模式。 可以参考WINDOWS AND
PANES部分，并且 list-keys命令可以查看模式键绑定。

To view the default bindings and possible commands, see the list-keys command.
为了查看默认的绑定和可用的命令可以参考list-keys 命令：

list-keys [-t mode-table] [-T key-table]
(alias: lsk)
(别名：lsk)
List all key bindings. Without -T all key tables are printed. With -T only key-table.
列出所有的键绑定。 没有指定-T时会打印所有的键表，指定-T时只会打印key-table.

With -t, the key bindings in mode-table are listed; this may be one of: vi-edit, emacs-edit, vi-choice, emacs-choice, vi-copy or emacs-copy.
带-t时，在模式表中的键绑定会被列出来; 模式可能为以下之一：vi-edit,emacs-edit,vi-choice,emacs-choice,
vi-copy或emcas-copy。

send-keys [-lMR] [-t target-pane] key ...
(alias: send)
(别名:send)
Send a key or keys to a window. Each argument key is the name of the key (such as ‘C-a’ or ‘npage’ ) to send; if the string is not recognised as a key, it is sent as a series of characters. The -l flag disables key name lookup and sends the keys literally. All arguments are sent sequentially from first to last. The -R flag causes the terminal state to be reset.
发送一个或多个键到一个窗口中，其中的每个参数key是发送的键的名称（例如'C-a'或者'npage'）；如果字符串不能作为键来识别，就会作为一系列字符串发送。
-l标记让键名查找失效并且发送键的字面量值。所有的参数被按照先后顺序序列地发送。 -R标记导致终端状态被重置。

-M passes through a mouse event (only valid if bound to a mouse key binding, see MOUSE SUPPORT).
-M 通过一个鼠标时间传递（只有当绑定一个鼠标键绑定时有效，参考MOUSE SUPPORT）。

send-prefix [-2] [-t target-pane]
Send the prefix key, or with -2 the secondary prefix key, to a window as if it was pressed.
发送前导键-或者发送二级前导键如果与-2一起使用时-到一个窗口就像该键被按下一样。

unbind-key [-acn] [-t mode-table] [-T key-table] key
(alias: unbind)
(别名：unbind)
Unbind the command bound to key. -c, -n, -T and -t are the same as for bind-key. If -a is present, all key bindings are removed.
取消命令到键的绑定，-c,-n,-T 和-t与bind-key命令的含义一样。 如果-a存在，所有的键绑定都会被移除。

OPTIONS
选项
The appearance and behaviour of tmux may be modified by changing the value of various options. There are three types of option: server options, session options and window options.
tmux的外观和行为可以通过修改各个选项的值来进行改变，具有三种选项：服务器选项，会话选项和窗口选项。

The tmux server has a set of global options which do not apply to any particular window or session. These are altered with the set-option -s command, or displayed with the show-options -s command.
tmux服务器具有一个全局选项集合-这个选项集合不会应用到任何特定的窗口或会话中。 这些选项通过set-option -s
命令进行修改，或者通过show-options -s 命令进行显示。

In addition, each individual session may have a set of session options, and there is a separate set of global session options. Sessions which do not have a particular option configured inherit the value from the global session options. Session options are set or unset with the set-option command and may be listed with the show-options command. The available server and session options are listed under the set-option command.
此外，每个单独的会话可能具有一个会话选项集合，同时具有一个分开全局会话选项集合。
没有特定选项配置的会话会从全局会话选项中继承其值。会话选项可以通过set-option命令进行设置或重置，
可以通过show-options命令来列出会话选项。可用的服务器和会话选项会在set-option命令下列出。

Similarly, a set of window options is attached to each window, and there is a set of global window options from which any unset options are inherited. Window options are altered with the set-window-option command and can be listed with the show-window-options command. All window options are documented with the set-window-option command.
类似地，每个窗口都附着了一个窗口选项集合，并且具有一个全局窗口集合来继承所有的重置选项。
窗口选项使用set-window-option命令来进行修改，并且可以通过show-window-options命令来列出。
所有的窗口选项使用set-window-option命令归档。

tmux also supports user options which are prefixed with a ‘@’. User options may have any name, so long as they are prefixed with ‘@’, and be set to any string. For example:
tmux也支持以'@'作为前导的用户选项， 用户选项可以具有任何名称，可以被设置为任何字符串，只要具有'@'作为前导，例如：

    $ tmux setw -q @foo "abc123" 
    $ tmux showw -v @foo 
    abc123

Commands which set options are as follows:
设置选项的命令如下：

set-option [-agoqsuw] [-t target-session | target-window] option value
(alias: set)
(别名：set)
Set a window option with -w (equivalent to the set-window-option command), a server option with -s, otherwise a session option.
通过-w来设置窗口选项(与set-window-option命令等价)，-s设置服务器选项，否则设置会话选项。

If -g is specified, the global session or window option is set. The -u flag unsets an option, so a session inherits the option from the global options. It is not possible to unset a global option.
如果指定-g，那么全局会话或窗口选项就会被设置。 -u标记用来重置选项，所以一个会话从全局选项中来继承。
不能重置一个全局选项。

The -o flag prevents setting an option that is already set.
-o标记阻止设置一个已经存在的选项。

The -q flag suppresses errors about unknown options.
-q标记会取消位置选项发生的错误。

With -a, and if the option expects a string or a style, value is appended to the existing setting. For example:
带有-a时，如果选项期待一个字符串或者一个样式，那么只会被附加在已经设置的值后面，例如：

    set -g status-left "foo" 
    set -ag status-left "bar"

Will result in ‘foobar’. And:
会得到'foobar'结果，而：

    set -g status-style "bg=red" 
    set -ag status-style "fg=blue"

Will result in a red background and blue foreground. Without -a, the result would be the default background and a blue foreground.
会得到一个红所背景和蓝色前景的结果。 没有-a时，这个值会使用默认的背景和一个蓝色前景。

Available window options are listed under set-window-option.
可用的窗口选项在set-window-option下列出。

value depends on the option and may be a number, a string, or a flag (on, off, or omitted to toggle).
选项的值依赖于选项，可以为数字，字符串，或者一个标记（on/off/省略）。

Available server options are:
可选的服务器选项有：

buffer-limit number
Set the number of buffers; as new buffers are added to the top of the stack, old ones are removed from the bottom if necessary to maintain this maximum length.
设置缓冲器数量; 新的缓冲器被放置在栈顶端，旧的缓冲器在维护最大长度时有必要时从栈底端移除。

default-terminal terminal
Set the default terminal for new windows created in this session - the default value of the TERM environment variable. For tmux to work correctly, this must be set to ‘screen’, ‘tmux’ or a derivative of them.
为会话中新创键的窗口设置一个默认的终端-默认值为TERM环境变量。为了让tmux正确工作，这个值必须设置为'screen','tmux'或者来自他们的一个派生值。

escape-time time
Set the time in milliseconds for which tmux waits after an escape is input to determine if it is part of a function or meta key sequences. The default is 500 milliseconds.
设置一个tmux用来在接受到一个转义字符输入时的等待毫秒数，以此让tmux来判断该字符时函数的一部分还是一个meta键序列的一部分。
默认为500毫秒。
设置一个tmux用来在接受到一个转义字符输入时的等待毫秒数，以此让tmux来判断该字符时函数的一部分还是一个meta键序列的一部分。
默认为500毫秒。

exit-unattached [on | off]
If enabled, the server will exit when there are no attached clients.
如果生效，服务器会在没有任何附着的客户端时退出。


focus-events [on | off]
When enabled, focus events are requested from the terminal if supported and passed through to applications running in tmux. Attached clients should be detached and attached again after changing this option.
如果生效，在终端支持的情况下会从终端获取聚焦事件请求，然后通过tmux中运行的应用来传递。
附着的客户端应该被脱离附着状态然后在选项修改之后重新进行附着。

message-limit number
Set the number of error or information messages to save in the message log for each client. The default is 100.
设置每个客户端保存到消息日志中的错误或信息消息的数量，默认为100。

set-clipboard [on | off]
Attempt to set the terminal clipboard content using the \e]52;...\007 xterm(1) escape sequences. This option is on by default if there is an Ms entry in the terminfo(5) description for the client terminal. Note that this feature needs to be enabled in xterm(1) by setting the resource:
尝试使用\e]52;...\007 xterm转义序列来设置终端的剪切版。
如果在客户终端的terminfo描述中存在一个Ms实体，那么这个选项默认为on. 注意在xterm中需要设置以下资源来让这个特性生效：
    disallowedWindowOps: 20,21,SetXprop

Or changing this property from the xterm(1) interactive menu when required.
或者在需要的时候从xterm交互中改变这个属性值

terminal-overrides string
Contains a list of entries which override terminal descriptions read using terminfo(5). string is a comma-separated list of items each a colon-separated string made up of a terminal type pattern (matched using fnmatch(3)) and a set of name=value entries.
For example, to set the ‘clear’ terminfo(5) entry to ‘\e[H\e[2J’ for all terminal types and the ‘dch1’ entry to ‘\e[P’ for the ‘rxvt’ terminal type, the option could be set to the string:
"*:clear=\e[H\e[2J,rxvt:dch1=\e[P"
The terminal entry value is passed through strunvis(3) before interpretation. The default value forcibly corrects the ‘colors’ entry for terminals which support 256 colours:
"*256col*:colors=256,xterm*:XT"
Available session options are:
assume-paste-time milliseconds
If keys are entered faster than one in milliseconds, they are assumed to have been pasted rather than typed and tmux key bindings are not processed. The default is one millisecond and zero disables.
base-index index
Set the base index from which an unused index should be searched when a new window is created. The default is zero.
bell-action [any | none | current]
Set action on window bell. any means a bell in any window linked to a session causes a bell in the current window of that session, none means all bells are ignored and current means only bells in windows other than the current window are ignored.
bell-on-alert [on | off]
If on, ring the terminal bell when an alert occurs.
default-command shell-command
Set the command used for new windows (if not specified when the window is created) to shell-command, which may be any sh(1) command. The default is an empty string, which instructs tmux to create a login shell using the value of the default-shell option.
default-shell path
Specify the default shell. This is used as the login shell for new windows when the default-command option is set to empty, and must be the full path of the executable. When started tmux tries to set a default value from the first suitable of the SHELL environment variable, the shell returned by getpwuid(3), or /bin/sh. This option should be configured when tmux is used as a login shell.
destroy-unattached [on | off]
If enabled and the session is no longer attached to any clients, it is destroyed.
detach-on-destroy [on | off]
If on (the default), the client is detached when the session it is attached to is destroyed. If off, the client is switched to the most recently active of the remaining sessions.
display-panes-active-colour colour
Set the colour used by the display-panes command to show the indicator for the active pane.
display-panes-colour colour
Set the colour used by the display-panes command to show the indicators for inactive panes.
display-panes-time time
Set the time in milliseconds for which the indicators shown by the display-panes command appear.
display-time time
Set the amount of time for which status line messages and other on-screen indicators are displayed. time is in milliseconds.
history-limit lines
Set the maximum number of lines held in window history. This setting applies only to new windows - existing window histories are not resized and retain the limit at the point they were created.
lock-after-time number
Lock the session (like the lock-session command) after number seconds of inactivity, or the entire server (all sessions) if the lock-server option is set. The default is not to lock (set to 0).
lock-command shell-command
Command to run when locking each client. The default is to run lock(1) with -np.
lock-server [on | off]
If this option is on (the default), instead of each session locking individually as each has been idle for lock-after-time, the entire server will lock after all sessions would have locked. This has no effect as a session option; it must be set as a global option.
message-command-style style
Set status line message command style, where style is a comma-separated list of characteristics to be specified.
These may be ‘bg=colour’ to set the background colour, ‘fg=colour’ to set the foreground colour, and a list of attributes as specified below.
The colour is one of: black, red, green, yellow, blue, magenta, cyan, white, aixterm bright variants (if supported: brightred, brightgreen, and so on), colour0 to colour255 from the 256-colour set, default, or a hexadecimal RGB string such as ‘#ffffff’, which chooses the closest match from the default 256-colour set.
The attributes is either none or a comma-delimited list of one or more of: bright (or bold), dim, underscore, blink, reverse, hidden, or italics, to turn an attribute on, or an attribute prefixed with ‘no’ to turn one off.
Examples are:
fg=yellow,bold,underscore,blink 
bg=black,fg=default,noreverse
With the -a flag to the set-option command the new style is added otherwise the existing style is replaced.
message-style style
Set status line message style. For how to specify style, see the message-command-style option.
mouse [on | off]
If on, tmux captures the mouse and allows mouse events to be bound as key bindings. See the MOUSE SUPPORT section for details.
mouse-utf8 [on | off]
If enabled, request mouse input as UTF-8 on UTF-8 terminals.
prefix key
Set the key accepted as a prefix key.
prefix2 key
Set a secondary key accepted as a prefix key.
renumber-windows [on | off]
If on, when a window is closed in a session, automatically renumber the other windows in numerical order. This respects the base-index option if it has been set. If off, do not renumber the windows.
repeat-time time
Allow multiple commands to be entered without pressing the prefix-key again in the specified time milliseconds (the default is 500). Whether a key repeats may be set when it is bound using the -r flag to bind-key. Repeat is enabled for the default keys bound to the resize-pane command.
set-remain-on-exit [on | off]
Set the remain-on-exit window option for any windows first created in this session. When this option is true, windows in which the running program has exited do not close, instead remaining open but inactivate. Use the respawn-window command to reactivate such a window, or the kill-window command to destroy it.
set-titles [on | off]
Attempt to set the client terminal title using the tsl and fsl terminfo(5) entries if they exist. tmux automatically sets these to the \e]2;...\007 sequence if the terminal appears to be an xterm. This option is off by default. Note that elinks will only attempt to set the window title if the STY environment variable is set.
set-titles-string string
String used to set the window title if set-titles is on. Formats are expanded, see the FORMATS section.
status [on | off]
Show or hide the status line.
status-interval interval
Update the status bar every interval seconds. By default, updates will occur every 15 seconds. A setting of zero disables redrawing at interval.
status-justify [left | centre | right]
Set the position of the window list component of the status line: left, centre or right justified.
status-keys [vi | emacs]
Use vi or emacs-style key bindings in the status line, for example at the command prompt. The default is emacs, unless the VISUAL or EDITOR environment variables are set and contain the string ‘vi’.
status-left string
Display string (by default the session name) to the left of the status bar. string will be passed through strftime(3) and formats (see FORMATS) will be expanded. It may also contain any of the following special character sequences:
Character pair	Replaced with
#(shell-command)	First line of the command's output
#[attributes]	Colour or attribute change
##	A literal ‘#’
The #(shell-command) form executes ‘shell-command’ and inserts the first line of its output. Note that shell commands are only executed once at the interval specified by the status-interval option: if the status line is redrawn in the meantime, the previous result is used. Shell commands are executed with the tmux global environment set (see the ENVIRONMENT section).
For details on how the names and titles can be set see the NAMES AND TITLES section. For a list of allowed attributes see the message-command-style option.
Examples are:
#(sysctl vm.loadavg) 
#[fg=yellow,bold]#(apm -l)%%#[default] [#S]
By default, UTF-8 in string is not interpreted, to enable UTF-8, use the status-utf8 option.
The default is ‘[#S] ’.
status-left-length length
Set the maximum length of the left component of the status bar. The default is 10.
status-left-style style
Set the style of the left part of the status line. For how to specify style, see the message-command-style option.
status-position [top | bottom]
Set the position of the status line.
status-right string
Display string to the right of the status bar. By default, the current window title in double quotes, the date and the time are shown. As with status-left, string will be passed to strftime(3), character pairs are replaced, and UTF-8 is dependent on the status-utf8 option.
status-right-length length
Set the maximum length of the right component of the status bar. The default is 40.
status-right-style style
Set the style of the right part of the status line. For how to specify style, see the message-command-style option.
status-style style
Set status line style. For how to specify style, see the message-command-style option.
status-utf8 [on | off]
Instruct tmux to treat top-bit-set characters in the status-left and status-right strings as UTF-8; notably, this is important for wide characters. This option defaults to off.
update-environment variables
Set a space-separated string containing a list of environment variables to be copied into the session environment when a new session is created or an existing session is attached. Any variables that do not exist in the source environment are set to be removed from the session environment (as if -r was given to the set-environment command). The default is "DISPLAY SSH_ASKPASS SSH_AUTH_SOCK SSH_AGENT_PID SSH_CONNECTION WINDOWID XAUTHORITY".
visual-activity [on | off]
If on, display a status line message when activity occurs in a window for which the monitor-activity window option is enabled.
visual-bell [on | off]
If this option is on, a message is shown on a bell instead of it being passed through to the terminal (which normally makes a sound). Also see the bell-action option.
visual-silence [on | off]
If monitor-silence is enabled, prints a message after the interval has expired on a given window.
word-separators string
Sets the session's conception of what characters are considered word separators, for the purposes of the next and previous word commands in copy mode. The default is ‘ -_@’.
set-window-option [-agoqu] [-t target-window] option value
(alias: setw)
Set a window option. The -a, -g, -o, -q and -u flags work similarly to the set-option command.
Supported window options are:
aggressive-resize [on | off]
Aggressively resize the chosen window. This means that tmux will resize the window to the size of the smallest session for which it is the current window, rather than the smallest session to which it is attached. The window may resize when the current window is changed on another sessions; this option is good for full-screen programs which support SIGWINCH and poor for interactive programs such as shells.
allow-rename [on | off]
Allow programs to change the window name using a terminal escape sequence (\033k...\033\\). The default is on.
alternate-screen [on | off]
This option configures whether programs running inside tmux may use the terminal alternate screen feature, which allows the smcup and rmcup terminfo(5) capabilities. The alternate screen feature preserves the contents of the window when an interactive application starts and restores it on exit, so that any output visible before the application starts reappears unchanged after it exits. The default is on.
automatic-rename [on | off]
Control automatic window renaming. When this setting is enabled, tmux will rename the window automatically using the format specified by automatic-rename-format. This flag is automatically disabled for an individual window when a name is specified at creation with new-window or new-session, or later with rename-window, or with a terminal escape sequence. It may be switched off globally with:
set-window-option -g automatic-rename off
automatic-rename-format format
The format (see FORMATS) used when the automatic-rename option is enabled.
c0-change-interval interval
c0-change-trigger trigger
These two options configure a simple form of rate limiting for a pane. If tmux sees more than trigger C0 sequences that modify the screen (for example, carriage returns, linefeeds or backspaces) in one millisecond, it will stop updating the pane immediately and instead redraw it entirely every interval milliseconds. This helps to prevent fast output (such as yes(1)) overwhelming the terminal. The default is a trigger of 250 and an interval of 100. A trigger of zero disables the rate limiting.
clock-mode-colour colour
Set clock colour.
clock-mode-style [12 | 24]
Set clock hour format.
force-height height
force-width width
Prevent tmux from resizing a window to greater than width or height. A value of zero restores the default unlimited setting.
main-pane-height height
main-pane-width width
Set the width or height of the main (left or top) pane in the main-horizontal or main-vertical layouts.
mode-keys [vi | emacs]
Use vi or emacs-style key bindings in copy and choice modes. As with the status-keys option, the default is emacs, unless VISUAL or EDITOR contains ‘vi’.
mode-style style
Set window modes style. For how to specify style, see the message-command-style option.
monitor-activity [on | off]
Monitor for activity in the window. Windows with activity are highlighted in the status line.
monitor-silence [interval]
Monitor for silence (no activity) in the window within interval seconds. Windows that have been silent for the interval are highlighted in the status line. An interval of zero disables the monitoring.
other-pane-height height
Set the height of the other panes (not the main pane) in the main-horizontal layout. If this option is set to 0 (the default), it will have no effect. If both the main-pane-height and other-pane-height options are set, the main pane will grow taller to make the other panes the specified height, but will never shrink to do so.
other-pane-width width
Like other-pane-height, but set the width of other panes in the main-vertical layout.
pane-active-border-style style
Set the pane border style for the currently active pane. For how to specify style, see the message-command-style option. Attributes are ignored.
pane-base-index index
Like base-index, but set the starting index for pane numbers.
pane-border-style style
Set the pane border style for panes aside from the active pane. For how to specify style, see the message-command-style option. Attributes are ignored.
remain-on-exit [on | off]
A window with this flag set is not destroyed when the program running in it exits. The window may be reactivated with the respawn-window command.
synchronize-panes [on | off]
Duplicate input to any pane to all other panes in the same window (only for panes that are not in any special mode).
utf8 [on | off]
Instructs tmux to expect UTF-8 sequences to appear in this window.
window-active-style style
Set the style for the window's active pane. For how to specify style, see the message-command-style option.
window-status-activity-style style
Set status line style for windows with an activity alert. For how to specify style, see the message-command-style option.
window-status-bell-style style
Set status line style for windows with a bell alert. For how to specify style, see the message-command-style option.
window-status-current-format string
Like window-status-format, but is the format used when the window is the current window.
window-status-current-style style
Set status line style for the currently active window. For how to specify style, see the message-command-style option.
window-status-format string
Set the format in which the window is displayed in the status line window list. See the status-left option for details of special character sequences available. The default is ‘#I:#W#F’.
window-status-last-style style
Set status line style for the last active window. For how to specify style, see the message-command-style option.
window-status-separator string
Sets the separator drawn between windows in the status line. The default is a single space character.
window-status-style style
Set status line style for a single window. For how to specify style, see the message-command-style option.
window-style style
Set the default window style. For how to specify style, see the message-command-style option.
xterm-keys [on | off]
If this option is set, tmux will generate xterm(1) -style function key sequences; these have a number included to indicate modifiers such as Shift, Alt or Ctrl. The default is off.
wrap-search [on | off]
If this option is set, searches will wrap around the end of the pane contents. The default is on.
show-options [-gqsvw] [-t target-session | target-window] [option]
(alias: show)
Show the window options (or a single window option if given) with -w (equivalent to show-window-options), the server options with -s, otherwise the session options for target session. Global session or window options are listed if -g is used. -v shows only the option value, not the name. If -q is set, no error will be returned if option is unset.
show-window-options [-gv] [-t target-window] [option]
(alias: showw)
List the window options or a single option for target-window, or the global window options if -g is used. -v shows only the option value, not the name.
MOUSE SUPPORT
If the mouse option is on (the default is off), tmux allows mouse events to be bound as keys. The name of each key is made up of a mouse event (such as ‘MouseUp1’) and a location suffix (one of ‘Pane’ for the contents of a pane, ‘Border’ for a pane border or ‘Status’ for the status line). The following mouse events are available:
MouseDown1	MouseUp1	MouseDrag1
MouseDown2	MouseUp2	MouseDrag2
MouseDown3	MouseUp3	MouseDrag3
WheelUp	WheelDown	
Each should be suffixed with a location, for example ‘MouseDown1Status’.
The special token ‘{mouse}’ or ‘=’ may be used as target-window or target-pane in commands bound to mouse key bindings. It resolves to the window or pane over which the mouse event took place (for example, the window in the status line over which button 1 was released for a ‘MouseUp1Status’ binding, or the pane over which the wheel was scrolled for a ‘WheelDownPane’ binding).
The send-keys -M flag may be used to forward a mouse event to a pane.
The default key bindings allow the mouse to be used to select and resize panes, to copy text and to change window using the status line. These take effect if the mouse option is turned on.
FORMATS
Certain commands accept the -F flag with a format argument. This is a string which controls the output format of the command. Replacement variables are enclosed in ‘#{’ and ‘}’, for example ‘#{session_name}’. The possible variables are listed in the table below, or the name of a tmux option may be used for an option's value. Some variables have a shorter alias such as ‘#S’, and ‘##’ is replaced by a single ‘#’.
Conditionals are available by prefixing with ‘?’ and separating two alternatives with a comma; if the specified variable exists and is not zero, the first alternative is chosen, otherwise the second is used. For example ‘#{?session_attached,attached,not attached}’ will include the string ‘attached’ if the session is attached and the string ‘not attached’ if it is unattached, or ‘#{?automatic-rename,yes,no}’ will include ‘yes’ if automatic-rename is enabled, or ‘no’ if not. A limit may be placed on the length of the resultant string by prefixing it by an ‘=’, a number and a colon, so ‘#{=10:pane_title}’ will include at most the first 10 characters of the pane title.
The following variables are available, where appropriate:
Variable name	Alias	Replaced with
alternate_on		If pane is in alternate screen
alternate_saved_x		Saved cursor X in alternate screen
alternate_saved_y		Saved cursor Y in alternate screen
buffer_sample		Sample of start of buffer
buffer_size		Size of the specified buffer in bytes
client_activity		Integer time client last had activity
client_activity_string		String time client last had activity
client_created		Integer time client created
client_created_string		String time client created
client_height		Height of client
client_last_session		Name of the client's last session
client_prefix		1 if prefix key has been pressed
client_readonly		1 if client is readonly
client_session		Name of the client's session
client_termname		Terminal name of client
client_tty		Pseudo terminal of client
client_utf8		1 if client supports utf8
client_width		Width of client
cursor_flag		Pane cursor flag
cursor_x		Cursor X position in pane
cursor_y		Cursor Y position in pane
history_bytes		Number of bytes in window history
history_limit		Maximum window history lines
history_size		Size of history in bytes
host	#H	Hostname of local host
host_short	#h	Hostname of local host (no domain name)
insert_flag		Pane insert flag
keypad_cursor_flag		Pane keypad cursor flag
keypad_flag		Pane keypad flag
line		Line number in the list
mouse_any_flag		Pane mouse any flag
mouse_button_flag		Pane mouse button flag
mouse_standard_flag		Pane mouse standard flag
mouse_utf8_flag		Pane mouse UTF-8 flag
pane_active		1 if active pane
pane_bottom		Bottom of pane
pane_current_command		Current command if available
pane_dead		1 if pane is dead
pane_dead_status		Exit status of process in dead pane
pane_height		Height of pane
pane_id	#D	Unique pane ID
pane_in_mode		If pane is in a mode
pane_input_off		If input to pane is disabled
pane_index	#P	Index of pane
pane_left		Left of pane
pane_pid		PID of first process in pane
pane_right		Right of pane
pane_start_command		Command pane started with
pane_synchronized		If pane is synchronized
pane_tabs		Pane tab positions
pane_title	#T	Title of pane
pane_top		Top of pane
pane_tty		Pseudo terminal of pane
pane_width		Width of pane
saved_cursor_x		Saved cursor X in pane
saved_cursor_y		Saved cursor Y in pane
scroll_region_lower		Bottom of scroll region in pane
scroll_region_upper		Top of scroll region in pane
session_attached		Number of clients session is attached to
session_activity		Integer time of session last activity
session_activity_string		String time of session last activity
session_created		Integer time session created
session_created_string		String time session created
session_group		Number of session group
session_grouped		1 if session in a group
session_height		Height of session
session_id		Unique session ID
session_many_attached		1 if multiple clients attached
session_name	#S	Name of session
session_width		Width of session
session_windows		Number of windows in session
window_active		1 if window active
window_activity_flag		1 if window has activity alert
window_bell_flag		1 if window has bell
window_find_matches		Matched data from the find-window
window_flags	#F	Window flags
window_height		Height of window
window_id		Unique window ID
window_index	#I	Index of window
window_last_flag		1 if window is the last used
window_layout		Window layout description
window_name	#W	Name of window
window_panes		Number of panes in window
window_silence_flag		1 if window has silence alert
window_width		Width of window
window_zoomed_flag		1 if window is zoomed
wrap_flag		Pane wrap flag
NAMES AND TITLES
tmux distinguishes between names and titles. Windows and sessions have names, which may be used to specify them in targets and are displayed in the status line and various lists: the name is the tmux identifier for a window or session. Only panes have titles. A pane's title is typically set by the program running inside the pane and is not modified by tmux. It is the same mechanism used to set for example the xterm(1) window title in an X(7) window manager. Windows themselves do not have titles - a window's title is the title of its active pane. tmux itself may set the title of the terminal in which the client is running, see the set-titles option.
A session's name is set with the new-session and rename-session commands. A window's name is set with one of:
A command argument (such as -n for new-window or new-session).
An escape sequence:
$ printf '\033kWINDOW_NAME\033\\'
Automatic renaming, which sets the name to the active command in the window's active pane. See the automatic-rename option.
When a pane is first created, its title is the hostname. A pane's title can be set via the OSC title setting sequence, for example:
$ printf '\033]2;My Title\033\\'
ENVIRONMENT
When the server is started, tmux copies the environment into the global environment; in addition, each session has a session environment. When a window is created, the session and global environments are merged. If a variable exists in both, the value from the session environment is used. The result is the initial environment passed to the new process.
The update-environment session option may be used to update the session environment from the client when a new session is created or an old reattached. tmux also initialises the TMUX variable with some internal information to allow commands to be executed from inside, and the TERM variable with the correct terminal setting of ‘screen’.
Commands to alter and view the environment are:
set-environment [-gru] [-t target-session] name [value]
(alias: setenv)
Set or unset an environment variable. If -g is used, the change is made in the global environment; otherwise, it is applied to the session environment for target-session. The -u flag unsets a variable. -r indicates the variable is to be removed from the environment before starting a new process.
show-environment [-g] [-t target-session] [variable]
(alias: showenv)
Display the environment for target-session or the global environment with -g. If variable is omitted, all variables are shown. Variables removed from the environment are prefixed with ‘-’.
STATUS LINE
tmux includes an optional status line which is displayed in the bottom line of each terminal. By default, the status line is enabled (it may be disabled with the status session option) and contains, from left-to-right: the name of the current session in square brackets; the window list; the title of the active pane in double quotes; and the time and date.
The status line is made of three parts: configurable left and right sections (which may contain dynamic content such as the time or output from a shell command, see the status-left, status-left-length, status-right, and status-right-length options below), and a central window list. By default, the window list shows the index, name and (if any) flag of the windows present in the current session in ascending numerical order. It may be customised with the window-status-format and window-status-current-format options. The flag is one of the following symbols appended to the window name:
Symbol	Meaning
*	Denotes the current window.
-	Marks the last window (previously selected).
#	Window is monitored and activity has been detected.
!	A bell has occurred in the window.
~	The window has been silent for the monitor-silence interval.
Z	The window's active pane is zoomed.
The # symbol relates to the monitor-activity window option. The window name is printed in inverted colours if an alert (bell, activity or silence) is present.
The colour and attributes of the status line may be configured, the entire status line using the status-style session option and individual windows using the window-status-style window option.
The status line is automatically refreshed at interval if it has changed, the interval may be controlled with the status-interval session option.
Commands related to the status line are as follows:
command-prompt [-I inputs] [-p prompts] [-t target-client] [template]
Open the command prompt in a client. This may be used from inside tmux to execute commands interactively.
If template is specified, it is used as the command. If present, -I is a comma-separated list of the initial text for each prompt. If -p is given, prompts is a comma-separated list of prompts which are displayed in order; otherwise a single prompt is displayed, constructed from template if it is present, or ‘:’ if not.
Both inputs and prompts may contain the special character sequences supported by the status-left option.
Before the command is executed, the first occurrence of the string ‘%%’ and all occurrences of ‘%1’ are replaced by the response to the first prompt, the second ‘%%’ and all ‘%2’ are replaced with the response to the second prompt, and so on for further prompts. Up to nine prompt responses may be replaced (‘%1’ to ‘%9’).
confirm-before [-p prompt] [-t target-client] command
(alias: confirm)
Ask for confirmation before executing command. If -p is given, prompt is the prompt to display; otherwise a prompt is constructed from command. It may contain the special character sequences supported by the status-left option.
This command works only from inside tmux.
display-message [-p] [-c target-client] [-t target-pane] [message]
(alias: display)
Display a message. If -p is given, the output is printed to stdout, otherwise it is displayed in the target-client status line. The format of message is described in the FORMATS section; information is taken from target-pane if -t is given, otherwise the active pane for the session attached to target-client.
BUFFERS
tmux maintains a set of named paste buffers. Each buffer may be either explicitly or automatically named. Explicitly named buffers are named when created with the set-buffer or load-buffer commands, or by renaming an automatically named buffer with set-buffer -n. Automatically named buffers are given a name such as ‘buffer0001’, ‘buffer0002’ and so on. When the buffer-limit option is reached, the oldest automatically named buffer is deleted. Explicitly named are not subject to buffer-limit and may be deleted with delete-buffer command.
Buffers may be added using copy-mode or the set-buffer and load-buffer commands, and pasted into a window using the paste-buffer command. If a buffer command is used and no buffer is specified, the most recently added automatically named buffer is assumed.
A configurable history buffer is also maintained for each window. By default, up to 2000 lines are kept; this can be altered with the history-limit option (see the set-option command above).
The buffer commands are as follows:
choose-buffer [-F format] [-t target-window] [template]
Put a window into buffer choice mode, where a buffer may be chosen interactively from a list. After a buffer is selected, ‘%%’ is replaced by the buffer name in template and the result executed as a command. If template is not given, "paste-buffer -b '%%'" is used. For the meaning of the -F flag, see the FORMATS section. This command works only if at least one client is attached.
clear-history [-t target-pane]
(alias: clearhist)
Remove and free the history for the specified pane.
delete-buffer [-b buffer-name]
(alias: deleteb)
Delete the buffer named buffer-name, or the most recently added automatically named buffer if not specified.
list-buffers [-F format]
(alias: lsb)
List the global buffers. For the meaning of the -F flag, see the FORMATS section.
load-buffer [-b buffer-name] path
(alias: loadb)
Load the contents of the specified paste buffer from path.
paste-buffer [-dpr] [-b buffer-name] [-s separator] [-t target-pane]
(alias: pasteb)
Insert the contents of a paste buffer into the specified pane. If not specified, paste into the current one. With -d, also delete the paste buffer. When output, any linefeed (LF) characters in the paste buffer are replaced with a separator, by default carriage return (CR). A custom separator may be specified using the -s flag. The -r flag means to do no replacement (equivalent to a separator of LF). If -p is specified, paste bracket control codes are inserted around the buffer if the application has requested bracketed paste mode.
save-buffer [-a] [-b buffer-name] path
(alias: saveb)
Save the contents of the specified paste buffer to path. The -a option appends to rather than overwriting the file.
set-buffer [-a] [-b buffer-name] [-n new-buffer-name] data
(alias: setb)
Set the contents of the specified buffer to data. The -a option appends to rather than overwriting the buffer. The -n option renames the buffer to new-buffer-name.
show-buffer [-b buffer-name]
(alias: showb)
Display the contents of the specified buffer.
MISCELLANEOUS
Miscellaneous commands are as follows:
clock-mode [-t target-pane]
Display a large clock.
if-shell [-bF] [-t target-pane] shell-command command [command]
(alias: if)
Execute the first command if shell-command returns success or the second command otherwise. Before being executed, shell-command is expanded using the rules specified in the FORMATS section, including those relevant to target-pane. With -b, shell-command is run in the background.
If -F is given, shell-command is not executed but considered success if neither empty nor zero (after formats are expanded).
lock-server
(alias: lock)
Lock each client individually by running the command specified by the lock-command option.
run-shell [-b] [-t target-pane] shell-command
(alias: run)
Execute shell-command in the background without creating a window. Before being executed, shell-command is expanded using the rules specified in the FORMATS section. With -b, the command is run in the background. After it finishes, any output to stdout is displayed in copy mode (in the pane specified by -t or the current pane if omitted). If the command doesn't return success, the exit status is also displayed.
wait-for [-L | -S | -U] channel
(alias: wait)
When used without options, prevents the client from exiting until woken using wait-for -S with the same channel. When -L is used, the channel is locked and any clients that try to lock the same channel are made to wait until the channel is unlocked with wait-for -U. This command only works from outside tmux.
TERMINFO EXTENSIONS
tmux understands some extensions to terminfo(5):
Cs, Cr
Set the cursor colour. The first takes a single string argument and is used to set the colour; the second takes no arguments and restores the default cursor colour. If set, a sequence such as this may be used to change the cursor colour from inside tmux:
$ printf '\033]12;red\033\\'
Ss, Se
Set or reset the cursor style. If set, a sequence such as this may be used to change the cursor to an underline:
$ printf '\033[4 q'
If Se is not set, Ss with argument 0 will be used to reset the cursor style instead.
Ms
This sequence can be used by tmux to store the current buffer in the host terminal's selection (clipboard). See the set-clipboard option above and the xterm(1) man page.
CONTROL MODE
tmux offers a textual interface called control mode. This allows applications to communicate with tmux using a simple text-only protocol.
In control mode, a client sends tmux commands or command sequences terminated by newlines on standard input. Each command will produce one block of output on standard output. An output block consists of a %begin line followed by the output (which may be empty). The output block ends with a %end or %error. %begin and matching %end or %error have two arguments: an integer time (as seconds from epoch) and command number. For example:
%begin 1363006971 2 
0: ksh* (1 panes) [80x24] [layout b25f,80x24,0,0,2] @2 (active) 
%end 1363006971 2
In control mode, tmux outputs notifications. A notification will never occur inside an output block.
The following notifications are defined:
%exit [reason]
The tmux client is exiting immediately, either because it is not attached to any session or an error occurred. If present, reason describes why the client exited.
%layout-change window-id window-layout
The layout of a window with ID window-id changed. The new layout is window-layout.
%output pane-id value
A window pane produced output. value escapes non-printable characters and backslash as octal \xxx.
%session-changed session-id name
The client is now attached to the session with ID session-id, which is named name.
%session-renamed name
The current session was renamed to name.
%sessions-changed
A session was created or destroyed.
%unlinked-window-add window-id
The window with ID window-id was created but is not linked to the current session.
%window-add window-id
The window with ID window-id was linked to the current session.
%window-close window-id
The window with ID window-id closed.
%window-renamed window-id name
The window with ID window-id was renamed to name.
FILES
~/.tmux.conf
Default tmux configuration file.
/etc/tmux.conf
System-wide configuration file.
EXAMPLES
To create a new tmux session running vi(1):
$ tmux new-session vi
Most commands have a shorter form, known as an alias. For new-session, this is new:
$ tmux new vi
Alternatively, the shortest unambiguous form of a command is accepted. If there are several options, they are listed:
$ tmux n 
ambiguous command: n, could be: new-session, new-window, next-window
Within an active session, a new window may be created by typing ‘C-b c’ (Ctrl followed by the ‘b’ key followed by the ‘c’ key).
Windows may be navigated with: ‘C-b 0’ (to select window 0), ‘C-b 1’ (to select window 1), and so on; ‘C-b n’ to select the next window; and ‘C-b p’ to select the previous window.
A session may be detached using ‘C-b d’ (or by an external event such as ssh(1) disconnection) and reattached with:
$ tmux attach-session
Typing ‘C-b ?’ lists the current key bindings in the current window; up and down may be used to navigate the list or ‘q’ to exit from it.
Commands to be run when the tmux server is started may be placed in the ~/.tmux.conf configuration file. Common examples include:
Changing the default prefix key:
set-option -g prefix C-a 
unbind-key C-b 
bind-key C-a send-prefix
Turning the status line off, or changing its colour:
set-option -g status off 
set-option -g status-style bg=blue
Setting other options, such as the default command, or locking after 30 minutes of inactivity:
set-option -g default-command "exec /bin/ksh" 
set-option -g lock-after-time 1800
Creating new key bindings:
bind-key b set-option status 
bind-key / command-prompt "split-window 'exec man %%'" 
bind-key S command-prompt "new-window -n %1 'ssh %1'"
SEE ALSO
pty(4)
AUTHORS
Nicholas Marriott <nicm@users.sourceforge.net>
