###资源/Beans 处理###
该节中的组件的主要目的时用来加载资源以及在不同的请求中保持一个bean的状态.


####<a4j:loadBundle>####
该组件与JSF的<f:loadBundle>类似, 用来为当前视图的Locale加载一个本地化的资源绑定并将当前的请求的请求属性作为映射进行存储.

####< a4j:keepAlive >####
该组件允许在不同的请求之间保持一个bean的状态.




####< a4j:loadScript >####
该组件允许从一个可选的源来加载脚本,例如一个jar文件.
<a4j:loadScript src="resource:///org/mycompany/assets/script/focus.js" />

####< a4j:loadStyle >####
该组件允许从一个可选的源来加载样式文件,例如一个jar文件,它会讲样式链接插入到<head>元素中






