###查询语句中的命名参数###

*采用冒号(:)加 参数名称的方式来定义查询语句.*
<pre><code>
public List findWithName(String name) {
	return em.createQuery(
		"SELECT c FROM Customer c WHERE c.name LIKE :custName")
		.setParameter("custName", name)
		.getResultList();
}
</code></pre>

###查询语句中的位置参数###
*采用问号(?)加 参数所在索引位置的方式来定义查询语句,其中索引位置以1作为开始:*
<pre><code>
public List findWithName(String name) {
	return em.createQuery(
		“SELECT c FROM Customer c WHERE c.name LIKE ?1”)
		.setParameter(1, name)
		.getResultList();
}
</code></pre>
