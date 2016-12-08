import psycopg2

def insertSong(name, artist, url):
  connection = psycopg2.connect("dbname='adbname' user='dbuser' host='localhost' password='adbpassword'")
  
  cur = connection.cursor() 
  cur.execute("SELECT COUNT(*) FROM songs;")
  identifer = cur.fetchone()[0]
  cur.execute("SELECT COUNT(*) FROM songs WHERE url = %s;", (url,))
  sqldata = cur.fetchone()
  count = sqldata[0]
  if count == 0:
    print "Adding " + url
    cur.execute("INSERT INTO songs VALUES (%s, %s, %s, %s);", (identifer, name, artist, url))

  connection.commit()
  cur.close()
  connection.close()