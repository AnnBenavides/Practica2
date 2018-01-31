# Investigación
***

# Sistemas

## Mapa de navegación nic.cl
**Header**
- Inicio
- *Dominios* http://www.nic.cl/dominios/index.html
- *Whois (html)* http://www.nic.cl/whois/
- normativa http://www.nic.cl/normativa/index.html
- controversias http://www.nic.cl/controversias/index.html
- acerca http://www.nic.cl/acerca/index.html
- ayuda http://www.nic.cl/ayuda/index.html
- *clientes* /registry/Whois.do


- contador http://www.nic.cl/contador.html
- tecnologia http://www.nic.cl/tecnologia/index.html
- estadísitica http://www.nic.cl/estadisticas/index.html
- anuncios http://www.nic.cl/anuncios/

**Footer**
- buscar http://www.nic.cl/buscar/index.html
- contacto http://www.nic.cl/contacto/index.html

**Formularios**
- *autoatencion* : https://www.nic.cl/autoatencion
- *registry* : /registry/Whois.do


## Mapa de navegación registry
- Desde NIC.CL

	**Slideadds en inicio**
	- Eliminados día : https://www.nic.cl/registry/Eliminados.do?t=1d
	- Whois :http://www.nic.cl/registry/Whois.do

	**Formularios**
	- Whois: /registry/Whois.do

- Desde Dominios http://www.nic.cl/dominios/index.html
	- Whois http://nic.cl/registry/Whois.do
	- [x] Ultimos dominios registrados (hora, dia, semana, mes) http://nic.cl/registry/Ultimos.do?t=1h
	- [x] Ultimos dominios eliminados (hora, dia, semana, mes)http://nic.cl/registry/Eliminados.do?t=1d 

- Desde Whois (html)
	- Buscar dominio /registry/BuscarDominio.do;jsessionid=9DE834BB6C612BF2E8848C9C086A2D06.worker1

## Mapa de navegación *clientes*.nic.cl (registrar)
- Vista visita (clientes)
	- Logon (Inicio clientes)
	- Login
	  - Home Sesion
	- Recuperación de clave https://clientes.nic.cl/registrar/recuperaPassword.do
	  - Cambiar password
	- Nuevos usuarios https://clientes.nic.cl/registrar/agregarUsuario.do
	  - Crear usuario
	  - Activar cuenta

- Vista Usuario (Home Sesion)
	- Salir
	- Editar usuario
		- Grabar Usuario
	- Agregar Dominio
		- Crear Dominio
	- Mis Dominios
	- Comprobantes
	- Avisos
	- Carro

# Herramientas
Se desea que el desarrollo sea compatible con Linux, desarrollado en Java, que no dependa de una GUI, y además sea modular y escalable.

## Test de Funcionalidad
- [Katalon](https://www.katalon.com/): puedes importar projectos de Selenium a Katalon, tiene una UI amigable y menos tediosa (enfocada para gente no especializada). Object Spy integrado y simple; entrega plugins de navegador (Chrome o Firefox) para grabar test desde la web.
- [Selenium](http://www.seleniumhq.org/) : también es un UI testing, pero permite testear en paralelo, se usa para automatizar los comportamientos de browsers. Con la actualización, se integró WebDriver, que permite manejar un HtmlUnit que logra un high load.
- [HtmlUnit](http://htmlunit.sourceforge.net/) : algo como una GUI de navegador, posee una API que permite invocar, rellenar y clickear en las paginas, basado en JavaScript (es usado en Spring MVC) 

## Test de Carga
- [Apache JMeter](http://jmeter.apache.org/): simula heavy loads y mide la funcionalidad en servers,networks,u objetos como archivos, bases de datos y servicios web, ademas de identificar cuellos de botella y desempeño bajo stress. Test dinámicos y estáticos. 
	- Multiplataforma (Java)
	- Escalable
	- Soporte multiprotocolo (HTTP, SMTP, POP3, LDAP, JDBC, FTP, JMS, SOAP, TCP)
	- Varias implementaciones pre y post procesos sobre la prueba
	- Varias evaluaciones para definir criterios
	- Multiples listeners integrados y externos para visualizar y analizar resultados de los performance test
- [AB (Apache Brench)para servidores](http://httpd.apache.org/docs/2.2/programs/ab.html): enfocado en HTTP, desarrollado para Apache HTTP server
- **WCAT** (Gratuito): herramienta para Windows, Web Capacity Analysis Tool, libiana, simula usuarios en 1+ sitiois, performance del server en env controlados, API para facilitar su extensión. _(!) Hay que escribir un script para cada request._
- **Load Storm** (Suscripción): cloudbased, genera escenarios realistas, al ser un agente externo el que bombardee la aplicación. _(!) Suscripción_
- **WatiN**(MUERTO):no es un stress tester, más bien un UI tester en C#, usa una API para operar y correr en Firefox y IE. _(!)Pesado de correr suficientes instancias como para simular un heavy load_
- **Speed Tracer** (Gratuito): credo por Google, como una extensión de Chrome, basado en Java posee plug-ins para Eclipse

## Test de Seguridad
- [OpenVAS](http://www.openvas.org/) : herramienta para evaluar vulnerabilidades, al parecer el administrados y scanner de vulnerabilidad OpenSource más avanzado (fork de Nessus) Actualizado diaramente con nuevos NVT's
- [Nexpose Community](http://www.rapid7.com/products/nexpose/compare-downloads.jsp) : otra herramienta para evaluar vulnerabilidad. Si bien es gratis está limitado a 32 direcciones IP y un usuario. Carece de escaneo para aplicaciones web.
- [Retina CS Community](http://info.beyondtrust.com/cscommunity) : otra herramienta para evaluar vulnerabilidad, es una consola Web que simplifica y centralizael manejo de vulnerabilidades para más de 256 evaluaciones sin costo incluyendo evaluacion automatica de vulnerabildades en servers, estaciones de trabajo (workstations), dispositivos móviles, bases de datos, aplicaciones y aplicaciones web. Soporte completo para entornos VMware, icluyendo escane do imagen virtual online y offline, escaneo  de aplicaciones virtuales, e integración con vCenter.
- [Nikto](https://cirt.net/Nikto2) : escaner de servidores Web que usa "comprhensive" test contra multiples elementos del servidor, incluyendo sobre 6700 archivos/programas potencialmente peligroso, tambien chequea servidores con versiones desactualizadas y problemas especificos para esas veersiones (para 270 servidores).
- [Metasploit Framework](https://www.metasploit.com/) : framework para testeo de penetraciones que trabaja con Nexpose, recien posee una WebUI, se puede usar para validar vulnerbilidades encontradas por Nexpose
- [ZAP (Zed Attack Proxy) OWASP](https://www.owasp.org/index.php/OWASP_Zed_Attack_Proxy_Project) : herramienta integrada para encontrar vulnerabilidades en aplicaciones web. Fork de Paros Proxy, provee esceneres automatizados así como un set de herramientas para encontrar vulnerabilidades de seguridad manualmente. En continuo desarrollo a manos de OWASP, Microsoft y Google.