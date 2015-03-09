###Ajax请求优化###

#### 重新渲染 ####
**reRender**是一个关键属性,用来指定需要进行重新渲染的组件ID
**ajaxRendered**是一个<a4j:outputPanel>的属性,true设置让页面的该区域自动重新渲染,及时没有显示地指定相应的组件ID
**limitToList** 使得可以忽略掉<a4j:outputPanel>的`ajaxRendered`属性, 让重新渲染操作只针对reRender属性列出的组件ID


#### 队列和洪峰保护####
**eventsQueue**属性定义了一个用来为即将到来的Ajax请求排序的队列名称.
默认情况下RichFaces并没有对ajax请求排序,如果事件同时产生,那么所有的事件都会同时达到服务器. 而jsf实现并没有保证最先来到的请求会最先得到服务,因此服务器端对于同时请求的事件处理顺序是不可预料的. 使用eventsQueue属性可以用来避免可能的混乱.如果在应用中预期会有密集的ajax请求流量出现,那么就显示地定义队列名称.


**requestDelay**属性定义了一个请求在其准备发送之前需要在队列中等待的事件值,采用毫秒ms作为事件单位.当超过延迟事件时,该请求会被发送到服务器,并且在有最新的相似的请求存在队列中时会将该请求从队列之中删除.

**ignoreDupResponses**属性,忽略由该请求产生的AjaxResponse,如果在队列中已经存在最新的相似请求时. ignoreDupResponses=true并不会在其还在服务器端处理时取消该请求,指示允许在响应失去意义时避免不必要的客户端更新.


#### 队列原则 ####
>1 全局默认队列

>2 视图范围默认队列

>3 视图范围命名队列

>4 给予表单的默认队列

>5 队列的功能
	>>相似事件
	>>请求延迟时的相似请求
	>>Javascript API
	>>


#### 数据处理选项 ####

#### 动作和导航 ####
ajax组件与其他的非ajax JSF组件相似,也可以提交表单. 可以使用 "action"和"actionListener"属性来调用动作方法及定义动作事件.
"action"方法必须返回null,如果你想要局部页面更新的话.

#### Javascript交互####
Richfaces允许你在不编写任何javasc代码的情况下编写Ajax可用的JSF应用.如果你想要的话,也可以调用自己编写的javascript代码,有以下属性可以完成:

>1 "onsubmit"属性在一个Ajax请求发送之前调用Javas代码,如果"onsubmit"方法返回"false",那么Ajax请求就会被取消."onsubmit"的代码会被插入到RichfaAjax调用之前.

>2 "onclick"类似于"onsubmit",但是是为可点击的组件提供的,如果其返回"false",那么Ajax请求也会被取消.

>3 "oncomplete"用来在Ajax响应返回之后执行的Javascript来更新DOM.

>4 "onbeforedomupdate"属性定义了在Ajax响应之后以及更新DOM之前执行的Javascript代码.

>5 "data" 属性允许在Ajax调用期间从服务器端获取额外的数据,可以使用JSF EL来指定managedBean的属性以及其值将会在JSON格式中序列化,并在客户端可用.


####其他有用的属性####
>1 "status"属性指向了一个<a4j:status>组件的ID,如果你想要在不同的区域的不同的Ajax组件中分享 <a4j:status>组件,可以使用该组件.



#### 常用的Ajax属性####
>1 ajaxSingle 限制JSF树处理(解码,转换,验证和更新)只针对一个特定的组件,默认为false
>2 bypassUpdates 如果为true,在强制渲染响应时的处理验证阶段后跳过模型bean的更新.可以用在输入组件验证中.
>3 limitToList 如果为true,那么页面中的组件只有在reRender中指定的才会在Ajax渲染阶段更新.
>4 reRender 指定受到该组件在Ajax请求影响的渲染的组件ID列表.
>5 process
>6 status 请求状态组件的ID
>7 eventsQueue 请求队列的名称.
>8 requestDelay 指定请求在其准备发送之前在请求队列中等待的时间.
>9 data 用过Ajax请求传递的序列化的数据.
>10 ingoreDupResoponses 允许在最新的相似请求已经存在时忽略由该请求产生的Ajax响应.
>11 timeout 对于一个特定请求的响应等待事件,如果响应在这段时间内没有被接收到,那么该请求就会中止
>12 similarityGroupingId 如果存在任何组件请求相同的ID那么这些请求就会被分组.
>13 keepTransient 一个用来标记所有的子组件为non-transient的标志.
>14 ajaxListener 用来指定在该组件被ajax激活时会使用到的动作监听器,其值为方法表达式.
>15 selfRendered 
>16 immediate 用来指示如果组件通过ajax请求激活,所有的通知应该立即进行发送传递,而不是直到等到应用调用阶段的标志.
>17 mode 定义任务类型: Ajax 或 Server
>18 switchType 定义渲染模式:Ajax, Server或client.
