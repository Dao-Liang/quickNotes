###连接并使用MySQL ###

$install python-mysqldb 
import MySQLdb 
db=MySQLdb.connect(host,username,password,dbname); 
#the handler to execute sql statement 
cursor=db.cursor() 
#normal sql statement 
query=”sql statement” 
#execute a query statement 
cursor.execute(query,[parameters]) 
for result in cursor: 
do something with result 
cursor.close() 
db.close()
