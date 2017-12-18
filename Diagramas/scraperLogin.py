

#######
import urllib
import urllib2
import cookielib
import datetime

cj = cookielib.CookieJar()
browser = urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))

def open(url, postdata=None):
    if postdata is not None:
        postdata = urllib.urlencode(postdata)
    return browser.open(url, postdata).read()

USER = 'andrea.benavidesj@gmail.com'
PASS = str(input("password: "))
URL = 'https://clientes.nic.cl/registrar/logon.do;jsessionid=FF260AC82C2C4F8D84D1A1DEA2940DA5.worker2'
now = datetime.datetime.now().strftime('%Y-%m-%d %H:%M')

open(URL)

POST = {'j_username': USER,
        'password': PASS
        }

loggedIn = open(URL, POST)

print(loggedIn)
