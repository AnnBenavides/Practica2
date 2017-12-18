# This Python file uses the following encoding: utf-8
#library used to query a website
import urllib2
import os, sys
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
	if len(pages)>0:
		for links in pages:
			urls = links.get("href")
			if "registrar" in urls:
				if "clientes.nic.cl" not in urls:
					urls = cliente+urls

			if "nic.cl" not in urls:
				if "http" not in urls:
					urls = "http://www.nic.cl"+urls

			if "nic.cl" in urls:
				all_urls.append(urls)
	return all_urls

def getRegistry(list_of_links):
	print "------- REGISTRY LINKS --------"
	registry_urls = []
	if len(list_of_links)>0:
		for links in list_of_links:
			urls = links.get("href")
			if "registry" in urls:
				if "nic.cl" not in urls:
					urls = nic+urls
				#print urls
				registry_urls.append(urls)
	return registry_urls

def getRegistrar(list_of_links):
	print "------- REGISTRAR LINKS --------"
	registrar_urls = []
	if len(list_of_links)>0:
		for links in list_of_links:
			urls = links.get("href")
			if "registrar" in urls:
				if "clientes.nic.cl" not in urls:
					urls = cliente+urls
				#print urls
				registrar_urls.append(urls)
	return registrar_urls

def getForms(list_of_links):
	print "-------- FORMS ---------"
	form_urls = []
	if len(list_of_links)>0:
		for form in list_of_links:
			#print form.get("action")
			form_urls.append(form.get("action"))
	return form_urls

def uniqueURLS(list_of_urls):
	diff_links = []
	if len(list_of_urls)>0:
		for links in list_of_urls:
			if links not in diff_links:
				diff_links.append(links)
		for delUrl in already_scrapped:
			if delUrl in diff_links:
				diff_links.remove(delUrl)	
	return diff_links

def scraper(main_url):
	already_scrapped.append(main_url)
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

	all_urls = getAllLinks(all_links)
	uniUrls = uniqueURLS(all_urls)
	if len(uniUrls)>0:
		if yesOrNo("¿Seguir por este thread?"):
			stepIn(main_url)

def yesOrNo(question):
	reply = str(input(question+" (s/n): ")).lower().strip()
	if reply[0] =='s':
		return True
	else:
		return False


def stepIn(main_url):
	print "\n --------------------------- \n"
	print "\t >> ["+main_url+"]"
	print "\n ---------------------------"
	#print "Obteniendo links..."
	all_links = getURLs(main_url, "a")
	all_urls = getAllLinks(all_links)
	uniUrls = uniqueURLS(all_urls)
	print "Se abrirán los siguientes "+str(len(uniUrls))+" threads no explorados:"
	printList(uniUrls)
	print "\n --------------------------- \n"

	if len(uniUrls)>0:
		for links in uniUrls:
			scraper(links)
	else:
		print "(!) No hay links nuevos para explorar"
	
already_scrapped = []
main_url = input("URL:") #al input hay que escribirlo entre ""
nic = "http://nic.cl"
cliente = "https://clientes.nic.cl"
scraper(main_url)







