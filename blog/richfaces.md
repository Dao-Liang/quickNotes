### Richfaces 基本概念 ###

####如何使用RichFaces####

####如何发送一个Ajax请求####
从JSF页面发送Ajax请求的方法有几种方式:例如可以使用<a4j:commandButton>,<a4j:commandLink>,<a4j:poll>,<a4j:support>等标签以及其他的标签.
所有的这些标签隐藏了通常需要创建XMLTHHPRequest对象和发送Ajax请求的Javascript活动.同时,它们允许你来决定让JSF页面中的那些组件在Ajax响应结果中重新渲染--通过使用reRender属性.

>1. <a4j:commandButton> <a4j:commandLink>标签用来在"onclick"事件时发送一个Ajax请求.
>2. <a4j:poll> 用来通过计时器来周期地发送一个Ajax请求.
>3. <a4j:support> 允许你讲Ajax功能添加到标准的JSF组件上,并发送Ajax请求到一个选择的Javascript事件上,如"onkeyup"等.

####确定发送什么####
你可能想要将页面上描述的一个区域发送到服务器,这样就可以在你想要发送Ajax请求时控制视图的编码区域.

最简单的在JSF页面上描述Ajax区域的方式就是什么都不做,因为在<f:view>和</f:view>之间的内容会当做默认的Ajax区域.
你也可以通过<a4j:region>标签来在JSF页面中定义多个Ajax区域.

如果你想要渲染一个在活动区域之外的Ajax响应内容,可以讲renderRegionOnly属性设置为"false",否则你的Ajax更新操作会被限制在当前活动区域的元素中.

####确定改变什么####
通过"reRender"属性来更新动作的组件IDs.
但是当你的页面包含例如<f:verbatim>标签时,当你想要在Ajax响应阶段更新其内容,这种方法就不可行.
以上这个问题与JSF组件的transient标志相关,如果这个标志为true,这个组件就不会参与到状态保存或恢复的过程中.

为了能够为这类问题提供解决方案,,RichFaces使用通过<a4j:outputPanel>标签定义的输出面板的概念.
如果你将一个<f:verbatim>标签放置在这个输出面板中,那么该标签的呢荣以及面板的其他子标签的内容就可以在Ajax响应时更新.具有两种方法来进行控制:

>1. 设置 "ajaxRendered"=true
>2. 设置 reRender属性指向 输出面板的ID


####确定处理什么####
"process"属性允许定义需要与标志为ajaxSingle或者包装到区域中的组件一起进行处理的组件IDs.
在当你仅需要处理视图不同区域的两个组件时,可以利用"process"属性.

想象一下,在同一个区域中存在多个组件,但是你只需要对其中的两个组件进行关联,即通过一组件的动作
控制另外一个组件的相应动作,但是不触发其他组件的动作时,既可以使用**process**属性


####richfaces 限制与规则####

>1. 不应该附加或删除任何Ajax框架,只需要在页面进行元素的替换即可.为了保证更新成功,
在响应页面必须存在与更新ID对应的元素.如果想要在页面中附加任何的代码,
使用占位符--任何空元素. 类似地,推荐将消息放到AjaxOutput组件中.
>2. 不要使用<f:verbatim>作为自我渲染的容器,因为该组件是暂时的,并且没有保存在树中.
>3. Ajax请求是以XML格式通过XMLHTTPRequest函数产生的,但是这个XML绕开了大部分的验证,
相应的更正工作可能会在浏览器中进行.所以创建一个与标准严格兼容的HTML/XHTML,
不要忽略任何必须的元素和属性.任何必须的XML修正会在服务器端通过XML过滤器自动实现,
是许多不可预料的情况可能会通过不正确的html代码生成.
>4. RichFaces ViewHandler将其本身放置在Facelets ViewHandlers 链之前.
>5. RichFaces组件使用它们本身的渲染器. 在渲染响应阶段, RichFaces框架会对组件树进行一个遍历,调用其本身的渲染器并将其结果放入到Faces 响应中.
