import MySQLdb 

db=MySQLdb.connect("localhost","root","mysql","seam")

if db:
    print("not none")
else:
    print("None")

cursor=db.cursor()
query="select * from PackageInfo p where p.packageName like 'br.com.%' limit 10"

cursor.execute(query)

for re in cursor:
    print(re)
