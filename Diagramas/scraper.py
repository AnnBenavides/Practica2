#library used to query a website
import urllib2
import sys
#import the Beautiful soup functions to parse the data returned from the website
from bs4 import BeautifulSoup

def printList(links):
	for l in links:
		print l

def printUnique(links):
	u = uniqueURLS(links)
	printList(u)

def getURLs(main_url, tag):
	page = urllib2.urlopen(main_url)
	soup = BeautifulSoup(page, "lxml")
	return soup.find_all(tag)

def getAllLinks(pages):
	print "-------ALL LINKS --------"
	all_urls = []
	for links in pages:
		all_urls.append(links.get("href"))
	return all_urls

def getRegistry(list_of_links):
	print "------- REGISTRY LINKS --------"
	registry_urls = []
	for links in list_of_links:
		urls = links.get("href")
		if "registry" in urls:
			if "nic.cl" not in urls:
				urls = "http://www.nic.cl"+urls
			#print urls
			registry_urls.append(urls)
	return registry_urls

def getRegistrar(list_of_links):
	print "------- REGISTRAR LINKS --------"
	registrar_urls = []
	for links in list_of_links:
		urls = links.get("href")
		if "registrar" in urls:
			if "clientes.nic.cl" not in urls:
				urls = "https://clientes.nic.cl"+urls
			#print urls
			registrar_urls.append(urls)
	return registrar_urls

def getForms(list_of_links):
	print "-------- FORMS ---------"
	form_urls = []
	for form in list_of_links:
		#print form.get("action")
		form_urls.append(form.get("action"))
	return form_urls

def uniqueURLS(list_of_urls):
	diff_links = []
	for links in list_of_urls:
		if links not in diff_links:
			diff_links.append(links)
	return diff_links

def scraper(main_url):
	print "\n --------------------------- \n"
	print "\t SCRAPPING ON : ["+main_url+"]"
	print "\n ---------------------------"
	#print "Obteniendo links..."
	all_links = getURLs(main_url, "a")
	#all_urls = getAllLinks(all_links)
	#printUnique(all_urls)
	#print "Categorizando ..."
	registry = getRegistry(all_links)
	#printList(registry)
	printUnique(registry)
	registrar = getRegistrar(all_links)
	#printList(registrar)
	printUnique(registrar)
	#print "Escaneando formularios ..."
	all_forms = getURLs(main_url, "form")
	forms_in_page = getForms(all_forms)
	#printList(forms_in_page)
	printUnique(forms_in_page)


def registryScraper(main_url):
	print "\n --------------------------- \n"
	print "\t SCRAPPING ON : ["+main_url+"]"
	print "\n ---------------------------"
	#print "Obteniendo links..."
	all_links = getURLs(main_url, "a")
	#print "Categorizando ..."
	registry = getRegistry(all_links)
	#printList(registry)
	printUnique(registry)
	registrar = getRegistrar(all_links)
	#printList(registrar)
	printUnique(registrar)
	#print "Escaneando formularios ..."
	all_forms = getURLs(main_url, "form")
	forms_in_page = getForms(all_forms)
	#printList(forms_in_page)
	printUnique(forms_in_page)
	for links in registry:
		if "www.nic.cl" not in links:
			links = "https://www.nic.cl"+links
		scraper(links)

main_url = input("URL:") #al input hay que escribirlo entre ""
#main_url = "https://"+rawIn
#main_url = "https://nic.cl/"
#main_url = "https://clientes.nic.cl/"
scraper(main_url)
#registryScraper(main_url)

