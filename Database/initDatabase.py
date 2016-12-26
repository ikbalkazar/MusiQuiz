import psycopg2

connection = psycopg2.connect("dbname='adbname' user='dbuser' host='localhost' password='adbpassword'")
  
cur = connection.cursor() 

#remove previous tables
for tableName in ['users', 'friends', 'challenges', 'questions']:
	try:
		cur.execute("DROP TABLE %s;" % (tableName))
	except:
		pass	
	connection.commit()

#create new empty tables
cur.execute("CREATE TABLE users (username text, password text, secquestion text, secanswer text);")
#cur.execute("CREATE TABLE songs (id text, name text, artist text, url text);")
cur.execute("CREATE TABLE friends (user1 text, user2 text);")
cur.execute("CREATE TABLE challenges (id text, sender text, receiver text, createdat text, senderscore integer, receiverscore integer, status integer);")
cur.execute("CREATE TABLE questions (id text, challengeid text, choices text[], songurl text)")

connection.commit()
cur.close()
connection.close()
