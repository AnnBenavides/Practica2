#library used to query a website
import urllib2
#specify the url
nic = "https://nic.cl"
print "SCRAPPING ON : ["+nic+"]"
#Query the website and return the html to the variable 'page'
page = urllib2.urlopen(nic)
#import the Beautiful soup functions to parse the data returned from the website
from bs4 import BeautifulSoup
#Parse the html in the 'page' variable, and store it in Beautiful Soup format
soup = BeautifulSoup(page, "lxml")

all_links = soup.find_all("a")
#print "------- LINKS --------"
#for link in all_links:
#	print link.get("href")


print "------- REGISTRY LINKS --------"
for links in all_links:
	if "registry" in links:
		print link.get("href")

print "------- REGISTRAR LINKS --------"
for links in all_links:
	if "registrar" in links:
		print link.get("href")

all_forms = soup.find_all("form")
print "-------- FORMS ---------"
for form in all_forms:
	print form.get("action")