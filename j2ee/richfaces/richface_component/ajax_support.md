###支持Ajax的组件###

####<a4j:ajaxListener>####

####<a4j:actionparam>####
该组件接合了<f:param>和<f:actionListener>组件的功能,并允许通过过"assignTo"属性为managed Bean直接赋值.
> "name"属性指定参数名称
> "value"属性定义参数的初始化值
> "assignTo"定义可更新的bean属性,该属性会在父命令组件运行一个actionEvent时被更新.

####<a4j:form>####
该组件通过默认的Ajax任务提高了标准的JSF<h:form>组件的功能,并修正了在form中生成<h:commandLink>的问题.


####<a4j:region>####
该组件指定了在服务器上被处理的一部分组件,如果没有该标签,那么全部的View被视为一个区域进行工作.

###<a4j:support>####
该组件时RichFaces库中最重要的核心组件,它通过Ajax功能丰富了已有的没有Ajax功能的JSF或RichFaces组件,所有的其他RichFaces Ajax组件都是基于<a4j:support>具有的原则.

该组件具有两个关键的属性:
>1 "event"属性,用来定义Javascript事件
>2 "reRender"属性, 用来指定会被重新渲染的JSF组件.

####<a4j:commandButton>####
该组件与JSF的<h:commandButton>组件很相似,唯一的不同在于一个Ajax表单提交会在点击时生成并允许在返回响应之后动态渲染.

####<a4j:commandLink>####
该组件与JSF的<h:commandLink>具有相同的用法,不同之处在于该组件应该指定需要更新的组件.


####<a4j:jsFunction>####
该组件允许直接从Javascript代码执行Ajax请求,调用服务器端数据并将其通过JSON格式数据返回到一个客户端的javascript调用中.


####<a4j:poll>####
该组件允许周期性地发送Ajax请求到服务器,用来通过指定的时间间隔来更新页面.

> interval 属性指定时间间隔
> timeout  属性定义响应等待时间
> enabled  定义是否使用该组件发送请求

####<a4j:push>####
该组件周期性地执行Ajax请求到服务器端,来模拟'推送'数据

该组件与<a4j:poll>的主要区别在于,该组件使用最少的代码创建请求来检查队列中消息的状态.如果消息存在,那么全部的请求被执行,该组件不会询问已注册的Bean而是注册接收事件消息的EventListener.


####<a4j:queue>####
该组件讲来自客户端的ajax请求加入队列中,具有内置Ajax功能的RichFaces组件可以引用该队列来优化ajax请求.

####<a4j:status>####
该组件主要用来生成显示当前ajax请求状态的元素,具有两种status模式:处理中的或已完成的Ajax请求.





