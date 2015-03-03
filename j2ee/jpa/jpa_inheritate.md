###JPA中的实体之间的继承###
>1. 指定子类实体继承类型的方式:@Inheritance(strategy = InheritanceType.*)
>2. 子类实体继承方式的种类:
	InheritanceType.TABLE_PER_CLASS
	InheritanceType.JOINED
	InheritanceType.SINGLE_TABLE

>3. 3种继承方式的解释:
	TABLE_PER_CLASS:分别为每个子类各自创建一个单独的表,各个表之间相互独立,每个表中都包含了从父类继承的字段
	JOINED:为每个子类和父类分别创建各自的表,但是子类对应的表中不会包含从父类继承来的字段,所有子类共有的字段都保存在父类对应的表中
	SINGLE_TABLE:所有的子类都被存储在同一个表中,该表中通过包含一个discriminator数据列来区分各个子类数据.
