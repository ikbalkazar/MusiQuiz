import psycopg2

connection = psycopg2.connect("dbname='adbname' user='dbuser' host='localhost' password='adbpassword'")
  
cur = connection.cursor() 

#remove previous tables
cur.execute("DROP TABLE users;")
cur.execute("DROP TABLE songs;")
cur.execute("DROP TABLE friends;")

#create brandnew tables
cur.execute("CREATE TABLE users (username text, password text, secquestion text, secanswer text);")
cur.execute("CREATE TABLE songs (id text, name text, artist text, url text);")
cur.execute("CREATE TABLE friends (user1 text, user2 text);")

connection.commit()
cur.close()
connection.close()