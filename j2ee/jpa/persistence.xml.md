<pre><code>
<persistence version="2.0" xmlns="http://java.sun.com/xml/ns/persistence"
xmlns:xsi ="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation ="http://java.sun.com/xml/ns/persistence
http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd">
<persistence-unit name="Mapping" transaction-type="RESOURCE_LOCAL">

	<provider>org.hibernate.ejb.HibernatePersistence</provider>
	<!-- 
	<class>com.jpa.entities.Editora</class>
	  -->
	  
	<properties>
		<property name="hibernate.dialect" value="org.hibernate.dialect.MySQLDialect"/>
		<property name="hibernate.hbm2ddl.auto" value="update"/>
		<property name="hibernate.show_sql" value="true"/>
		
		<!-- Connection Configuration -->		
		<property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver"/>
		<property name="javax.persistence.jdbc.user" value="root"/>
		<property name="javax.persistence.jdbc.password" value="mysql"/>
		<property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/jpatest2"/>
	</properties>
	
</persistence-unit>


</persistence>
</pre></code>
