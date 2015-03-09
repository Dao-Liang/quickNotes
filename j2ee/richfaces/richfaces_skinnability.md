###使用皮肤###
RichFaces设计了混合皮肤用法:
>1 通过RichFaces框架定义的Skin参数
>2 组件的预定义CSS
>3 用户样式类文件

组件的颜色模式可以通过任何三种样式类将其应用到元素本身:
>1 框架中默认加入的样式类
>2 skin扩展的样式类
>3 用户定义的样式类

#### RichFaces中的Skin参数表####
Default
plain
emeraldTown
wine
japanCherry
ruby
classic
deepMarine

为了应用其中的一个,需要将其中的一个名称知道给<context-param> org.richfaces.SKIN


