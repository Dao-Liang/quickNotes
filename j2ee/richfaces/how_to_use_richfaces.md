###如何使用RichFaces###

####如何发送一个Ajax请求####
从JSF页面发送Ajax请求的方法有几种方式:例如可以使用<a4j:commandButton>,<a4j:commandLink>,<a4j:poll>,<a4j:support>等标签以及其他的标签.
所有的这些标签隐藏了通常需要创建XMLTHHPRequest对象和发送Ajax请求的Javascript活动.同时,它们允许你来决定让JSF页面中的那些组件在Ajax响应结果中重新渲染--通过使用reRender属性.

> <a4j:commandButton> <a4j:commandLink>标签用来在"onclick"事件时发送一个Ajax请求.
> <a4j:poll> 用来通过计时器来周期地发送一个Ajax请求.
> <a4j:support> 允许你讲Ajax功能添加到标准的JSF组件上,并发送Ajax请求到一个选择的Javascript事件上,如"onkeyup"等.

####确定发送什么####
你可能想要将页面上描述的一个区域发送到服务器,这样就可以在你想要发送Ajax请求时控制视图的编码区域.

最简单的在JSF页面上描述Ajax区域的方式就是什么都不做,因为在<f:view>和</f:view>之间的内容会当做默认的Ajax区域.
你也可以通过<a4j:region>标签来在JSF页面中定义多个Ajax区域.

如果你想要渲染一个在活动区域之外的Ajax响应内容,可以讲renderRegionOnly属性设置为"false",否则你的Ajax更新操作会被限制在当前活动区域的元素中.

####确定改变什么####
通过"reRender"属性来更新动作的组件IDs.
但是当你的页面包含例如<f:verbatim>标签时,当你想要在Ajax响应阶段更新其内容,这种方法就不可行.
以上这个问题与JSF组件的transient标志相关,如果这个标志为true,这个组件就不会参与到状态保存或恢复的过程中.

为了能够为这类问题提供解决方案,,RichFaces使用通过<a4j:outputPanel>标签定义的输出面板的概念.如果你将一个<f:verbatim>标签放置在这个输出面板中,那么该标签的呢荣以及面板的其他子标签的内容就可以在Ajax响应时更新.具有两种方法来进行控制:
>1 设置 "ajaxRendered"=true
>2 设置 reRender属性指向 输出面板的ID


####确定处理什么####
"process"属性允许定义需要与标志为ajaxSingle或者包装到区域中的组件一起进行处理的组件IDs.
在当你仅需要处理视图不同区域的两个组件时,可以利用"process"属性.

