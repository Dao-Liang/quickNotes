###连接并使用MySQL ###

<p>
<code>
install python-mysqldb <br/>
import MySQLdb <br/>
db=MySQLdb.connect(host,username,password,dbname); <br/>
#the handler to execute sql statement <br/>
cursor=db.cursor() <br/>
#normal sql statement <br/>
query=”sql statement” <br/>
#execute a query statement <br/>
cursor.execute(query,[parameters]) <br/>
for result in cursor: <br/>
do something with result <br/>
cursor.close() <br/>
db.close() <br/>
</code>
</p>
