import request
from lxml import html

payload = {
		"username": "<USER NAME>"
		"password": "<PASSWORD>"
}

session_requests = requests.session()
login_url = "https://clientes.nic.cl"
result session_request.get(login_url)

tree = html. fromstring(result.text)