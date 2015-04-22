最近一直被页面过滤器编写的工作占用了, 在整个解决问题的过程中出现了许多波的转折不断出现.
在此对这个问题的解决过程做一下小小的总结.

#### 1. 相关数据关系以及特性分析 ####
   在这个阶段首先分析了过滤器属性与过滤目标之间具有什么关系, 以及锁采用的过滤主要条件.
   过滤条件数据的类型以及数据量的大小
   过滤条件数据之间的关系, 包括相对关系以及决定性关系
   离散类型与连续类型的数据处理.


#### 2. web页面数据想关的页面组件的选择
    对页面组件的选择, 这个选择与数据量的多少,所选取过滤条件的多少等关系相关联.
    例如对于<源>类别的数据量较少,前后考虑过下拉列表<h:selectOneMenu>,<h:dataTable>
    由于后期对系统UI设计的一致性,选择了使用richfaces组件<rich:dataTable>.
    相应的交互设计也采用了richfaces本身提供的ajax功能来实现.

    而对于多个过滤条件的类别条件进行选择时,一开始是采用的<h:dataTable> + <h:selectedBooleanCheckBox>复选框,
    但是存在 数据量大小差别很明显的问题,因此对于某些对应的数据页面会下拉的很长,而其他又很短. 因此就页面设计
    就上有点不适宜.  因此设想过采用二级索引的方式来控制数据的显示,这样可以将数据显示页面控制在一定的长度范围
    内,但是增加了程序代码的复杂性. 然后就考虑使用 div + scrollbar的方式来实现,但是本人的web设计功底有限, 并且
    对于系统的整个的CSS的特性不了解,在实现之后,有人提出系统设计的一致性,因此参考了使用系统中原有的richfaces的
    <rich:panelModal>组件来控制数据的显示,但是这只是满足了在样式上的一致性,没有达到用户需求,因为按钮的显示在此
    不能以很好的方式呈现, 后来又有人提出好的建议,于是又采用richfaces的<rich:scrollableDataTable>来实现数据的展
    现,而由于对该组件的陌生, 花费许多的时间来对组件的显示进行调整,最后完成了比较好一点的效果, 但是之后又遇到了
    同样的布局,在不同的浏览器中的效果又会出现差别,我想真他妈的操蛋. 这又是闹哪样嘛, 没办法,需要调整各个显示布局
    参数来包容浏览器中会出现的异常情况. 此时真是需要一个一个的小参数调整,恼火哦. 最后总算是功夫不负有心人,算是
    比较不错的吧.

    其他的组件选择在这个过程中也逐渐开始熟悉其套路, 最终调整到了一定的满意度. 要说的是,这个过程中的web设计真的
    快把人弄疯了,但是也在这个磨练中开始不再对web设计感到那种未知的恐惧了.

#### 3. web页面布局设计的选择
   web设计页面的在第二部分也探讨了这个问题,主要经历了3个主要的阶段:
   	
	1. 最原始的没有任何修饰的web设计,此时仅仅将web组件填充到页面上,能够满足相应的功能,但是并不是用户友好的,自己也不觉得.
	2. 开始尝试使用原始的html代码来构建页面布局,此时改善了页面布局,而且在外观上也还可以, 但是整体而言缺乏了系统一致性
	3. 使用richfaces包中提供的组件来构建页面布局,此时既能使用组件来满足功能上的需求,同时其自身的布局也改善了页面可用性. 

#### 4. web页面动作处理的过度
	web页面的动作响应与web页面布局设计相呼应,因此也经历了3个相关阶段:

	1. 直接使用JSF组件及其提供的action接口来实现, 但是不能满足复杂的用户交互需求
	2. 自己尝试编写Javascript脚本代码来实现页面之间显隐交互等, 但是这样的代码还是不能满足系统的一致性
	3. 使用richfaces包本身提供的内建ajax功能,这样就简化了页面组件之间的交互功能的编写,但是参数调整花费事件长


#### 5. 代码的重构和优化过程
	在完成这个问题的过程中,后台的Java代码也经历了4次较大的重构和优化:

	1. 将所有的过滤器代码与原来的搜索逻辑代码耦合在一起,并且所有的交互控制代码也糅合在一起
	2. 在队友的建议下,开始了代码的大的重构,把所有的过滤器代码分离出来, 将不同的过滤条件封装到不同的类中
	3. 又一次的受到系统一致性问题困扰,于是又将2中分离出来的代码重构为 数据类和动作类 相当于前/后端逻辑的分离
	4. 最后又经历了异常代码效率的问题刁难,因此在这个过程中又需要自己证明某些方法的高效性,以及其他取舍,使用了相对麻烦一点的
	数据结构来换取程序逻辑的复杂性.


#### 6. 工作流程的认识加深过程
	团队工作中,相互协作需要有一个明确的规矩来约束,这样才能保证项目的良性发展,
	因此团队中具有了一系列的工作流程规范来帮助各个功能之间顺利运行, 但是在该
	过程中自己适应了快速迭代开发的过程,因此经常跳过某系环节,或者忽略了某些环节,
	这样造成了其他队友在审查过程中的噪音,不过从刚开始到现在,已经在不断碰壁过程中
	慢慢熟悉了并慢慢了解到了各种规范的重要性.


到现在为止,工作处于commit阶段,在经历这段时间的煎熬之后,写一下小结来回顾一下 :).