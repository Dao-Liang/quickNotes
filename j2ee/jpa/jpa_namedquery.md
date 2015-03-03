###编写JPA中的命名查询的语句###
@NamedQueries(
	value={
		@NamedQuery(name="findInvokersByMethod",query="select m.invokers from ParsedMethod m where m=:method"),
		@NamedQuery(name="findMethodBySignature",query="select m from ParsedMethod m where m.methodSignature=:signature"),
	}
)
