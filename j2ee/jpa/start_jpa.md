###Jpa的环境基本配置###
>1. 添加需要的类库到工作目录中(包括 mysql-connector,EntityManager提供商-如hibernate)
>2. 在代码工作顶层目录中添加文件夹,命名为META-INF;然后在其中添加persistence.xml文件进行基本的数据库连接配置
>3. 编写相关的数据实体类
>4. 使用EntityManager em=Persistence.createEntityManagerFactory("parser").createEntityManager();来创建EntityManager实体类对应的数据库表.
