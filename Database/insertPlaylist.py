import urllib2, psycopg2, json
from insertSong import insertSong

playlistId = raw_input()
request = urllib2.Request("https://api.spotify.com/v1/users/spotify/playlists/%s/tracks" % (playlistId))
request.add_header("Authorization", "Bearer <oauthcode>")
response = urllib2.urlopen(request)
result = response.read()
jsRes = json.loads(result)

items = jsRes["items"]
for item in items:
  track = item["track"]
  url = track["preview_url"]
  name = track["name"]
  artist = track["album"]["artists"][0]["name"]
  if artist != None and url != None:
    insertSong(name, artist, url)
