
## 1.Create persistence.xml under src/META-INF folder ##

    <?xml version="1.0" encoding="UTF-8"?>
    <persistence xmlns="http://java.sun.com/xml/ns/persistence" version="2.0">
     
        <persistence-unit name="unit-name" transaction-type="RESOURCE_LOCAL">
            <!-- jpa implements provider-->
            <provider>org.hibernate.ejb.HibernatePersistence</provider>
            <!-- data source name-->
            <non-jta-data-source>java:comp/env/jdbc/datasourcename</non-jta-data-source>
     
            <properties>
     
                <!-- hibernate implements configurations -->
                <property name="hibernate.connection.datasource" value="java:comp/env/jdbc/datasourcename"/>
                <property name="hibernate.id.new_generator_mappings" value ="true"/>
     
                <property name="hibernate.archive.autodetection" value="class"/>
                <property name="hibernate.show_sql" value="true"/>
                <property name="hibernate.format_sql" value="true"/>
                <property name="hibernate.hbm2ddl.auto" value="update"/>
                <property name="hibernate.dialect" value="org.hibernate.dialect.MySQLDialect"/>
                <property name="connection.autocommit" value="false"/>
     
            </properties>
        </persistence-unit>
    </persistence>




## 2. Create Project independent data source configuration file context.xml in webapp/META-INF ##

    <?xml version="1.0" encoding="UTF-8"?>
     
    <Context antiJARLocking="true" path="/">
        <Resource
                name="jdbc/datasourcename"
                auth="Container"
                type="javax.sql.DataSource"
                username="your_username"
                password="your_password"
                driverClassName="com.mysql.jdbc.Driver"
                url="jdbc:mysql://localhost:3306/db_name"
                maxActive="8"
                maxIdle="4"/>
    </Context>



## 3. Create a JPA ServletContextListener ##

        package com.theo.web;
        
        import javax.persistence.EntityManagerFactory;
        import javax.persistence.Persistence;
        import javax.servlet.ServletContext;
        import javax.servlet.ServletContextEvent;
        import javax.servlet.ServletContextListener;
        
        public class PersistenceListener implements ServletContextListener {
        
        	private EntityManagerFactory	entityManagerFactory;
        
        	@Override
        	public void contextDestroyed(ServletContextEvent arg0) {
        		entityManagerFactory.close();
        
        	}
        
        	@Override
        	public void contextInitialized(ServletContextEvent arg0) {
        		ServletContext ctx = arg0.getServletContext();
        		entityManagerFactory = Persistence.createEntityManagerFactory("seam.jpa");
        
        	}
        
        }


## 4. Config listener and resource-ref in web.xml  ##

    <listener>
      <listener-class>com.theo.web.PersistenceListener</listener-class>
     </listener>

    <resource-ref>
      <description>DB connection</description>
      <res-ref-name>jdbc/seam</res-ref-name>
      <res-type>javax.sql.DataSource</res-type>
      <res-auth>Container</res-auth>
     </resource-ref>
