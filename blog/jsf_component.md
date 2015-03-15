 ## 1.用户接口组件模型
 ---
jsf 组件构建了一个JSF视图各个小块，通常一个组件可以是用户接口组建或非用户接口组件。
JSF组件是JSF应用中组成用户接口的可配置的、可重用的元素，可以是简单的按钮，也可以是复合的表元素组件。

JSF技术提供了一系列丰富的、灵活的组件架构：
>1. 一系列javax.faces.component.UIComponnet 类集合来指定UI组件的状态和行为
>2. 一个渲染模型用来定义如何使用多种方法来对组件进行渲染
>3. 一个数据转换模型用来定义如何将数据转换器配置给一个组件
>4. 一个事件/监听器模型用来定义组件如何处理相关事件
>5. 一个验证模型用来定义如何将数据验证器配置给一个组件

---
### 1.1 用户接口组件类集合
---
用户接口组件类定义了组件的所有功能，如状态持有、对象引用维护，事件驱动处理以及渲染。
简单描述为： 用户组件={状态，行为}

JSF中定义的所有的用户接口组件及其关系如下：
<pre>
<code>
javax.faces.component.UIComponent
--javax.faces.component.UIComponentBase
----UIColumn：表示了一个UIData组件中的单列数据
----UICommand：表示在激活时触发某一行为的控制器
----UIData：用来绑定一个javax.faces.model.DataModel实例表示的数据集合
----UIForm：表示一个展现给用户的输入表单，其子组件表示在表单提交时包含的输入字段
----UIGraphic：用来显示一个图片
----UIInput：用来获取用户的输入
----UIMessage：用来显示本地化的错误消息
----UIMessages：用来显示一组本地化的错误消息
----UIOutcomeTarget：显示表单中的一个链接或按钮的链接信息
----UIOutput：在页面显示数据输出
----UIPanel：管理子组件的布局
----UIParameter：表示参数替换
----UISelectBoolean：允许用户通过选择或取消来设置一个boolean值
----UISelectItem：表示一个项目集合中的单个项目
----UISelectItems：表示一个项目集合实体
----UISelectMany：允许用户从一组选项集合中选取多个选项
----UISelectOne：允许用户从一组选项集合中选取单个选项
----UIViewParameter：表示一个请求中的查询参数
----UIViewRoot：表示组件树的根

</code>
</pre>

除了上述的用户接口类之外，组件类同时实现了一些行为接口来定义组件的特定行为。
通用的行为接口除了以下几个之外，都在javax.faces.component包中进行了定义：
<pre>
<code>
ActionSource:用来说明该组件可以出发一个动作事件，该接口在JSF2中已经过期
ActionSource2：是对ActionSource的扩展，提供了相同的功能，但是允许组件使用表达式
EditableValueHolder：对ValueHolder的扩展，并为可编辑组件制定了额外的特性
NamingContainer:制定当前组件的每个子组件都具有一个唯一的ID值
StateHolder: 指示一个组件具有必须在不同请求之间要保存的状态。
ValueHolder: 指示组件在模型层级同时维护一个局部值和访问数据选项
javax.faces.event.SystemEventListenerHolder： 为类中定义的每种javax.faces.event.Systemevent维护一个javax.faces.event.SystemEventListener实例。
javax.faces.component.behavior.ClientBehaviorHolder:添加附加javax.facescomponent.behavior.ClientBehavior实例的能力，例如一个重用的脚本。

</code>
</pre>

* UICommand 实现了ActionSource2和StateHolder
* UIOutput 及子类实现了StateHolder和ValueHolder
* UIInput 及子类实现了EditableValueHolder，StateHolder和ValueHolder。
* UIComponentBase实现了StateHolder
---

#### 1.2 组件渲染模型
---

JSF组件架构被设计模式为：组件的功能通过组件类自定义，但是组件的渲染可以使用不同的渲染器类来进行定义。该设计具有以下几个优点：
>1 组件作者可以一次性定义组件的行为，但是可以创建多个渲染器，每个渲染器都定义了一个不同的方法来渲染组件，给相同的客户端或不同的客户端

>2 网页作者和应用开发人员可以通过选择标签（具有合适的组件和渲染器的结合）来修改组件的外观

渲染工具定义了如何将组件类一合适的方式为特定的用户映射到组件标签。JSF实现了标准的HTML渲染工具来为HTML客户端进行渲染。
渲染工具集为其支持的每个组件定义了一个javax.faces.render.Renderer类集合。每个渲染器类定义了不同的方法来渲染特定的组件进行输出。

例如，一个UISelectOne组件具有三种不同的渲染器，其中的一个将其作为选项组渲染，另外的一个将其作为复合框进行渲染，另外的一个将其作为列表框进行渲染。
类似地，UICommand组件可以使用h:commandLink和h:commandButton标签将其渲染为一个链接或者是一个按钮。其中标签的command部分对应了UICommand类，指定了其功能--触发一个动作；其Button/Link部分对应了不同的渲染器，用来定义这个组件如何在页面中进行显示。

标准HTML渲染工具集中定义的客制化标签由“组件功能（UIComponent定义）”和“渲染属性（有渲染器类定义）”


--- 
#### 1.3 转换模型
---
一个JSF应用可以选择地将一个组件与服务器端的对象数据进行关联，这个对象可以是一个JavaBean组件，例如一个ManagedBean。应用会通过调用合适的对象数据属性来为获取或设置组件的值。

当一个组件与一个对象进行绑定之后，应用具有该组件数据的两种视图：
>1 模型视图，在此数据表示为数据类型
>2 展示视图，数据表示为用户可以读写的方式。

JSF 实现了两种视图之间组件数据的自动转换，当与组件关联的bean的属性是组件数据支持的类型之一时。

但是有时候你可能想要转换的数据类型并不是标准的数据类型，又或者你想要转换数据的格式。为了让这个工作变得简便，JSF允许用户注册一个javax.faces.convert.Conterter的实现到UIOutput组件以及其子组件上。如果你将这个转换器应用到一个组件上时，那么这个转换器就可以在两种视图中对组件数据进行转换。

你可使用JSF提供的标准转换器，也可以创建客制化转换器。

---
#### 1.4 事件/监听器模型
---
JSF 的事件/监听器模型类似于JavaBean事件模型，具有强类型的事件类型和监听器接口，似的应用可以处理组件生成的事件。

JSF规范定义了三种类型的事件：应用事件，系统事件，数据模型事件。

应用事件与特定的应用绑定，是由UIComponent生成的。他们表示了JSF之前版本中可用的标准事件。


一个事件对象标识生成事件的组件并将与事件相关的信息存储。为了通知一个事件，应用必须提供一个监听器的实现，并且将其注册到一个生成该事件的组件。当用户激活一个组件，事件就会被触发，这就使得JSF来调用监听器来处理这个事件。

JSF支持两种类型的应用事件：**动作事件** 和 **值改变事件**。

**动作事件** 会在用户激活实现了ActionSource的组件时出现，这些组件包括**按钮**和**链接**

**值改变事件** 会在用户更改UIInput及其子类所代表的组件的值时出现。例如选择一个复选框。能够生成该事件的组件类型有UIIput、UISelectOne、UISelectMany和UISelectBoolean等组件。值改变事件只有在没有验证错误出现时被触发。

根据组件的**immediate**属性值触发的事件，**动作事件**可以在**应用调用**或**应用请求值**阶段进行处理，**值改变事件**可以在**验证处理**和**应用请求值**阶段进行处理。

**系统事件**是由一个Object产生的，而不是有UIComponent产生的。他们实在应用执行时生成的，他们可以在整个应用中使用而不只是特定的组件。

**数据模型事件** 会在一个UIData组件中的一个新行被选择时出现。

具有两种方法来来让系统对标准组件生成的**动作事件**和**值改变事件**进行反应：

*  实现一个事件监听器类来处理事件，通过f:valueChangeListener或f:actionListener标签为组件进行注册

* 实现一个managedBean的方法来处理事件，然后通过组件的合适属性通过方法表达式来进行引用

***TODO***

***动作事件***/***值改变事件*** 的两种处理方法的实现稍后详解。
---
### 1.5 验证模型 ###
---
JSF支持一种用来验证可编辑组件局部数据的机制，验证过程会在模型数据更新之前处理。

与转换模型相似，验证模型定义了一组标准类集合来实现通用数据的验证。JSF的核心标签库中也包涵了与标准验证器（javax.face.validator.Validator）实现类相关的标签集合。

大部分标签具有一组用来配置验证器的属性，可以通过内置标签为组件配置验证器。也可以为应用中的所有UIInput组件声明默认的验证器。

验证器模型允许用户克制化验证器和标签来使用客制化验证,具有两种实现方法：

* 实现Validator接口来执行验证
* 实现managedBean方法来执行验证
***如果使用Validator***接口，还需要进行以下处理：
>1 通过应用注册Validator实现
>2 创建一个客制化标签或者使用f:validator标签在组件中注册验证器。

***TODO***
Bean验证器

























