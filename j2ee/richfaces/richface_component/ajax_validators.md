###Ajax验证器###
RichFaces组件库提供了3个组件来验证用户的输入数据.这些组件使用ajax支持和使用Hibernate验证器增强了JSF的验证能力.

####< rich:ajaxValidator >####
该组件被设计来为JSF输入提供Ajax验证.

关键特性:
>忽略除验证之外的任何JSF处理动作
>同时使用标准和客制化的验证机制
>使用Hibernate验证
>基于事件验证的触发机制


####< rich:beanValidator >####
该组件被设计来使用Hibernate模型约束的方式进行验证.

关键特性:
使用Hibernate约束来进行验证


#####< rich:graphValidator >###
该组件允许为多个输入组件注册Hibernate验证器.

关键特性:
忽略除验证之外的所有JSF处理过程.



