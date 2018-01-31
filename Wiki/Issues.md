# Issues
***

# Lectura de Issues

Al trabajarse en un repositorio _git_, se utilizaron las herramientas entregadas por _GitHub_ para la documentación del desarrollo y avance del proyecto. En esta sección se transcriben las issues (o tareas) que se abrieron a lo largo del desarrollo. A continuación se explican los diferentes elementos que puede poseer una issue, junto con lo que ello significa

### Etiquetas
- **Prioridades:** En el orden de mayor a menos prioridad están urgentes, necesario y deseable.
- **Categorías:** 
  - info : _documentación rápida, información del sistema y toma de decisiones_
  - pregunta/ayuda : _para issues en donde se necesite discusión o una segunda opinión antes de tomar una decisión_
  - pendiente : _issues que aun no son revisados, o que no se lograron desarrollar en el periodo de práctica_
  - invalido : _issues que ya no son necesarias_
  - bug : _problemas o casos especiales que rompen el programa_

### Milestones
- **Rr: Cuentas de Usuario:** issues relacionadas con la documentación e información recolectada del sistema Registrar
- **RegistryPublic: NIC.cl:** issues relacionadas con la documentación e información recolectada del sistema Registry
- **Sistema EPP:** issues relacionadas con la documentación e información recolectada de la conexión EPP
- **Test de Funcionalidad**
- **Test de Stress**
- **Test de Seguridad**

### Proyectos
Canvas de progreso para los distintos proyectos o etapas
- **Investigación:** Buenas prácticas y herramientas para: performance testing, security testing & stress testing
- **Modelo de Test:** Identificar qué iremos a testear en detalle: con que prioridad, qué herramienta, de que forma, definir ambientes y datos.
- **Test Registry:** nic.cl/registry || Identificar qué iremos a testear en detalle: con que prioridad, qué herramienta, de que forma, definir ambientes y datos.
- **Test Registrar:** clientes.nic.cl/registrar/ || Identificar qué iremos a testear en detalle: con que prioridad, qué herramienta, de que forma, definir ambientes y datos.

# Información de registryPublic

## rP001 Conocer el sistema Registry #1 (CLOSED)
| _Etiquetas: info_ | _Milestones: RegistryPublic_ | _Proyectos: Investigación_ |
|:--:|:--:|:--:|

Primero hay que saber con qué estoy tratando, por lo que hay que conocerlo mejor posible la aplicación registrer de NIC

- [x] Consultar al Área de Desarrollo (of. 17)
- [x] Navegar en el sitio

**Objetivos**
Lo ideal será documentar la aplicación, si es que no lo está ya, para ello se pueden usar los siguientes modelos:
- Diagrama de Usabilidad
- Diagrama de flujo

Una vez entendido el comportamiento, podremos identificar con mayor facilidad los puntos críticos o peligrosos del sistema (tanto en funcionalidad, seguridad y aguante de carga)

***Comentarios issue***
Desde Dominios y Whois se puede acceder a otras componentes de registry:

- Desde Dominios
	- Whois
	- Ultimos dominios registrados (hora, dia, semana, mes)
	- Ultimos dominios eliminados (hora, dia, semana, mes)

- Desde Whois (html)
	- Buscar dominio

***

## rP002 Diagrama Registry #5 (CLOSED)
| _Etiquetas: info_ | _Milestones: RegistryPublic_ | _Proyectos: Investigación_ |
|:--:|:--:|:--:|
### Mapa de navegación registry
#### ...desde inicio de  NIC.CL

**Slideadds en inicio**
- Eliminados :  registry/Eliminados.do?t=1d
- [x] Whois : registry/Whois.do

**Formularios**
- [x] Whois : registry/Whois.do?d=ejemplo.cl

#### Desde Dominios

- Whois : registry/Whois.do
- [x] Ultimos dominios registrados (hora, dia, semana, mes)
  - registry/Ultimos.do?t=1h
  - registry/Ultimos.do?t=1d
  - registry/Ultimos.do?t=1w
  - registry/Ultimos.do?t=1m

- [x] Ultimos dominios eliminados (dia, semana)
  - registry/Eliminados.do?t=1d
  - registry/Eliminados.do?t=1w

#### Desde Whois (html)
	
- [x] Buscar dominio : registry/BuscarDominio.do

#### Desde Dominios

- Whois : registry/Whois.do
- [x] Ultimos dominios registrados (hora, dia, semana, mes)
  - registry/Ultimos.do?t=1h
  - registry/Ultimos.do?t=1d
  - registry/Ultimos.do?t=1w
  - registry/Ultimos.do?t=1m

- [x] Ultimos dominios eliminados (dia, semana)
  - registry/Eliminados.do?t=1d
  - registry/Eliminados.do?t=1w

#### Desde Whois (html)
	
- [x] Buscar dominio : registry/BuscarDominio.do

***Resultados en `Practica2/Diagramas/`***

***

## rP003 scraper.py: Scraper para mapeo registry #12 (CLOSED)
| _Etiquetas: info_ | _Milestones: RegistryPublic_ | _Proyectos: Investigación_ |
|:--:|:--:|:--:|

Tarde se me ocurrió, pero... hacer un scraper web de nic.cl categorizando los links encontrados por registry, registrar y formularios
Se hizo un svript en Python ```\Diagramas\script.py``` usando las librerías _urllib2_ y _BeautifulSoup_
### Modo de uso
```sh
/Diagramas$ python scraper.py
#consultará URL a navergar, debe escribirse entre comillas, como a continuación
URL:"http://nic.cl"

 --------------------------- 

	 SCRAPPING ON : [http://nic.cl]

 ---------------------------
------- REGISTRY LINKS --------
https://www.nic.cl/registry/Eliminados.do?t=1d
http://www.nic.cl/registry/Whois.do
http://nic.cl/registry/Whois.do
------- REGISTRAR LINKS --------
https://clientes.nic.cl/registrar/agregarUsuario.do
-------- FORMS ---------
/registry/Whois.do
https://www.nic.cl/autoatencion
-------ALL LINKS --------
¿Seguir por este thread? (s/n): 
```
Si se desea seguir con el scraper en los enlaces que alcanza la página, se tiene la consulta _¿Seguir por este thread? (s/n):_ . Si la respuesta es **s** se muestran todos los enlaces alcanzados por la página que pertenencen al sistema de nic, sin categorizar, y se procede a "scrapear" en dicho orden.

```sh
¿Seguir por este thread? (s/n): "s"

 --------------------------- 

	 >> [http://nic.cl]

 ---------------------------
-------ALL LINKS --------
Se abrirán los siguientes 29 threads no explorados:
https://clientes.nic.cl
https://clientes.nic.cl/registrar/agregarUsuario.do
http://www.nic.cl/dominios/index.html
http://www.nic.cl/dominios/tarifas.html
http://www.nic.cl/dominios/pago.html
http://www.nic.cl/whois/
http://www.nic.cl/normativa/index.html
http://www.nic.cl/normativa/reglamentacion.html
http://www.nic.cl/normativa/procedimientos.html
http://www.nic.cl/controversias/index.html
http://www.nic.cl/acerca/index.html
http://www.nic.cl/acerca/oficina.html
http://www.nic.cl/ayuda/index.html
http://www.nic.cl/ayuda/faq/index.html
http://www.nic.cl/ayuda/tutoriales/index.html
https://www.nic.cl/registry/Eliminados.do?t=1d
http://www.nic.cl/registry/Whois.do
http://www.nic.cl/anuncios/20160328-500k.html
http://www.nic.cl/
http://www.nic.cl/contador.html
http://www.nic.cl/tecnologia/index.html
http://www.nic.cl/estadisticas/index.html
http://www.nic.cl/anuncios/
http://www.nic.clanuncios/20171212-mantencion.html
http://www.nic.clanuncios/20171129-simposio-lavits.html
http://www.nic.cl/ayuda/lista-anuncios.html
http://www.nic.cl/anuncios/anuncios.rdf
http://www.nic.cl/buscar/index.html
http://www.nic.cl/contacto/index.html

 --------------------------- 


 --------------------------- 

	 SCRAPPING ON : [https://clientes.nic.cl]

 ---------------------------
------- REGISTRY LINKS --------
------- REGISTRAR LINKS --------
https://clientes.nic.cl/registrar/recuperaPassword.do
-------- FORMS ---------
j_spring_security_check
-------ALL LINKS --------
¿Seguir por este thread? (s/n):
```
Si la respuesta es negativa, seguirá su ejecución normal, ya sea analizando el siguiente link o terminando el programa (si no quedan links que analizar)

***

## rP004 Documentación test Registry #32 (CLOSED)
| _Etiquetas: info, necesario_ | _Milestones: RegistryPublic_ | _Proyectos: Test Registry_ |
|:--:|:--:|:--:|
### Documentación JavaDoc
- [x] BuscarDominio
- [x] Eliminados
- [x] Ultimos
- [x] Whois

# Información de registrar
## Rr001 Conocer el sistema Registrar #3 (CLOSED)
| _Etiquetas: info_ | _Milestones: Rr Cuentas de Usuario_ | _Proyectos: Investigación_ |
|:--:|:--:|:--:|

**Objetivos**
Lo ideal será documentar la aplicación, si es que no lo está ya, para ello se pueden usar los siguientes modelos:
- Diagrama de Usabilidad
- Diagrama de flujo

Una vez entendido el comportamiento, podremos identificar con mayor facilidad los puntos críticos o peligrosos del sistema (tanto en funcionalidad, seguridad y aguante de carga)

***Comentarios issue***
- Logon y Nuevos Usuarios pueden accederse desde nic.cl
- Login, Recuperar clave y Nuevos Usuarios (Logon es el home) puede accederse desde clientes.nic.cl
- Recuperar clave da acceso a Cambiar password por email, que redirige a Logon
- Nuevo Usuario da acceso a  creaUsuario, para redirigir a Logon, además envia un email para accesar a Activacion de Cuenta que también termina redirigiendo a Logon
- Un Login exitosa redirige a https://clientes.nic.cl/registrar/listarDominio.do

***
## Rr002 Diagrama Registrar #4 (CLOSED)
| _Etiquetas: info, invalido_ | _Milestones: Rr Cuentas de Usuario_ | _Proyectos: Investigación_ |
|:--:|:--:|:--:|
### Mapa de navegación Registrar (https://clientes.nic.cl/)
#### Vista visita
- [x] Logon (Inicio clientes) : https://clientes.nic.cl/registrar/
- [x] Login
  - [x] **Home Session**: registrar/listarDominio.do
- [x] Recuperación de clave : /registrar/recuperaPassword.do
  - [x] Cambiar password : /registar/cambiarPassword.do?u=...
- [x] Nuevos usuarios : /registrar/agregarUsuario.do
  - [x] Crear usuario : /registar/crearUsuario.do
  - [x] Activar cuenta : /registar/confirmarRegistro.do?u=...
#### Vista usuario (*Home Session*)
- [x] Salir : /registrar/ ... #
- [ ] Editar usuario : /registrar/editarUsuario.do
	- [ ] Grabar Usuario : /registrar/grabarUsuario.do
- [ ] Mis Dominios : /registrar/listaDominio.do
- [ ] Comprobantes : /registrar/dte.do
- [ ] Avisos : /registrar/listaMensajes.do
- [ ] Carro : /registrar/verCarro.do
- [ ] Crear Dominio : /registrar/agregarDominio.do

***Resultados parciales en `Practica2/Diagramas/`***

***

## Rr003 Scraper para mapeo #13 (CLOSED)
| _Etiquetas: deseable, invalido_ | _Milestones: Rr Cuentas de Usuario_ | _Proyectos: Investigación_ |
|:--:|:--:|:--:|

Tarde se me ocurrió, pero... hacer un scraper web de nic.cl categorizando los links encontrados por registry, registrar y formularios
***

## Rr004 Documentación test Registrar #31 (OPEN)
| _Etiquetas: info, necesario, pendiente_ | _Milestones: Rr Cuentas de Usuario_ | _Proyectos: Test Registrar_ |
|:--:|:--:|:--:|

### Documentación JavaDoc
- [x] ListaDominio
- [ ] RecuperarClave
- [x] AgregarUsuario
- [ ] EditarUsuario
- [x] DTE
- [ ] ListaMensajes
- [x] VerCarro
- [ ] AgregarDominio
- [x] Email
- [x] Logon
- [x] UserAndPass

***

# Información de EPP
## SE001 - Conocer el sistema #2 #4 (CLOSED)
| _Etiquetas: pendiente_ | _Milestones: Sistema EPP_ | _Proyectos: Investigación_ |
|:--:|:--:|:--:|

Primero hay que saber con qué estoy tratando, por lo que hay que conocerlo mejor posible el sistema de conexion

- [ ] Consultar a expertos (?)
- [ ] Meter mano

**Objetivos**
Lo ideal será documentar la aplicación, si es que no lo está ya, para ello se pueden usar los siguientes modelos:
- Diagrama de Usabilidad
- Diagrama de flujo
- Diagrama de clases
- Casos de uso

Una vez entendido el comportamiento, podremos identificar con mayor facilidad los puntos críticos o peligrosos del sistema (tanto en funcionalidad, seguridad y aguante de carga)

***

# Test de Funcionalidad
## TF001 [HtmlUnit] Herramientas de testeo funcional #6 (CLOSED)
| _Etiquetas: necesario, pregunta/ayuda_ | _Milestones: Test Funcional_ | _Proyectos: Modelo Test_ |
|:--:|:--:|:--:|

1. [Katalon](https://www.katalon.com/): puedes importar projectos de Selenium a Katalon, tiene una UI amigable y menos tediosa (enfocada para gente no especializada). Object Spy integrado y simple; entrega plugins de navegador (Chrome o Firefox) para grabar test desde la web.
2. [Selenium](http://www.seleniumhq.org/) : también es un UI testing, pero permite testear en paralelo, se usa para automatizar los comportamientos de browsers. Con la actualización, se integró WebDriver, que permite manejar un HtmlUnit que logra un high load.
3. [HtmlUnit](http://htmlunit.sourceforge.net/) : algo como una GUI de navegador, posee una API que permite invocar, rellenar y clickear en las paginas, basado en JavaScript (es usado en Spring MVC) 

**Comentarios issue**
Decantamos por HtmlUnit :)
***

## TF002 definir ambiente, test urgentes y casos correctos #9 (OPEN)
| _Etiquetas: necesario, pendiente, pregunta/ayuda_ | _Milestones: Test Funcional_ | _Proyectos: Test Registry, Test Registrar_ |
|:--:|:--:|:--:|

Registrar #4
Unitarios #20 #21 #23 #24 #25 #28 #29 #30 : La idea es, por acceso directo a la página, verificar el funcionamiento correcto de esta.
Ruteo : Test que verifican que la página es accesible para el usuario mediante navegación por el sitio

Registry #5
Unitarios #15 #17 #18 #19 : La idea es, por acceso directo a la página, verificar el funcionamiento correcto de esta.
Ruteo #16 : Test que verifican que la página es accesible para el usuario mediante navegación por el sitio

***
## TF003 Aprender lo básico de Selenium #11 (CLOSED)
| _Etiquetas: info, invalido_ | _Milestones: Test Funcional_ | _Proyectos: Modelo Test_ |
|:--:|:--:|:--:|

![Componentes de Selenium](https://www.tatvasoft.com/blog/wp-content/uploads/2016/05/Selenium-Components.jpg)
Investigar:
- [x] Requerimientos
- [x] Instalación
- [ ] Uso básico
- [ ] Dependencia a Firefox browser

**Comentarios Issue**
Artículos relacionados:
- https://www.tatvasoft.com/blog/automating-functional-testing-using-selenium/
- https://www.adictosaltrabajo.com/tutoriales/selenium-ide/


Actualmente Katalon Studio no es soportado en Linux en su totalidad, si bien su IDE Katalon recorder funciona como plugin tiene un uso muy limitado ()y casi ni existe documentación al respecto)

Por lo que he decidido probar HtmlUnit para el testeo funcional, con el apoyo de Katalon al identificar las componentes.

***

## TF004 Aprender lo básico de HtmlUnit #14 (CLOSED)
| _Etiquetas: urgente, info_ | _Milestones: Test Funcional_ | _Proyectos: Investigación, Modelo Test_ |
|:--:|:--:|:--:|

Browser sin GUI basado en Java, que permite la manipulación de paginas web, como rellenar formularios, clickear links.

Se agrega como librería en los proyectos java, por lo que se usará Eclipse en el desarrollo.

### Instalación de HtmlUnit en Eclipse
1. Crear un nuevo proyecto Jave (default settings)
2. Crear una carpeta llamada "lib" (Click derecho en proyecto: New -> Folder)
3. [Descargar](https://sourceforge.net/projects/htmlunit/files/htmlunit/) la librería de HtmlUnit (htmlunit-X.X-bin.zip)
4. Descomprimir la carpeta "/htmlunit-X.X/lib" en la carpeta del proyecto "lib"
5. Click derecho en el proyecto : Build Path -> Configure Build Path
6. En la pestaña "Libraries" y clickeamos _Add JARs..._
7. Buscamos nuestra carpeta "lib", seleccionamos todos los elementos de esta y cerramos las ventanas
8. Para verificar que todo funciona se puede probar el siguiente código:
```java
import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;

public class Test {
    public static void main(final String[] args) {
        final WebClient webClient = new WebClient();
        webClient.setTimeout(1000);
        try {
            System.out.println("Querying");
            webClient.getPage("http://www.google.com");
            System.out.println("Success");
        } catch (final FailingHttpStatusCodeException e) {
            System.out.println("One");
            e.printStackTrace();
        } catch (final MalformedURLException e) {
            System.out.println("Two");
            e.printStackTrace();
        } catch (final IOException e) {
            System.out.println("Three");
            e.printStackTrace();
        } catch (final Exception e) {
            System.out.println("Four");
            e.printStackTrace();
        }
        System.out.println("Finished");
    }
}
```
	donde si todo esta bien deberíamos tener la siguiente respuesta:
```java
Querying
Success
Finished
```

#### [Documentación de HtmlUnit](http://htmlunit.sourceforge.net/gettingStarted.html)
- ***Versión utilizada*** : 2.8

***

## TF005 Eliminados: /registry/Elimindados.do?t=... #15(CLOSED)
| _Etiquetas: urgente_ | _Milestones: Test Funcional_ | _Proyectos: Test Registry_ |
|:--:|:--:|:--:|

### Unitarios
La idea es, por acceso directo a la página, verificar el funcionamiento correcto de esta.
- Eliminados
	- ***Verfifcar contenido***
		- [x] conteo de sitios correcto
		- [x] boton de inscripción
	- ***Cargar páginas***
		- [x] Día "d"
		- [x] Semana "w"

**Comentarios issue:**
Hay tres cosas importantes:
- cargar la pagina
- que cada fila tenga escrito un dominio.cl y un botón para inscribirlo
- conteo correcto en el título

Para cargar la página, openPage recibe como argumento un string "d" o "w" para ver el sitio de eleminados del día o semana, respectivamente.

Al verificar el contenido por fila, verifyRow chequea que se muestre un dominio.cl y que tenga un botón que redirecciona a agregarDominio.do

Finalmente, verifyResults extrae la tabla de resultados, por cada fila llama a verifyRow y luego verifica que el conteo mostrado sea correcto.

Atributos de elementos HTML utilizados:
**XPath**
- ```table[@class='tablabusqueda']```

***

## TF006 AccessRegistry: Test Acceso para Registry #16(OPEN)
| _Etiquetas: deseable, pendiente_ | _Milestones: Test Funcional_ | _Proyectos: Test Registry_ |
|:--:|:--:|:--:|

### Definición de test funcionales para Registry #9 
#### Ruteo
Test que verifican que la página es accesible para el usuario mediante navegación por el sitio
- [ ] Eliminados
- [ ] Búsqueda
- [ ] Whois
- [ ] Últimos registrados
***

## TF007 BuscarDominio: registry/BuscarDominio.do #17(CLOSED)
| _Etiquetas: urgente_ | _Milestones: Test Funcional_ | _Proyectos: Test Registry_ |
|:--:|:--:|:--:|

- Búsqueda 
	- [x] *Exacta* verificar resultado de búsqueda
	- [x] *Que contenga* verificar resultado de búsqueda
	- [x] *Comience con* verificar resultado de búsqueda
	- [x] *Termine con* verificar resultado de búsqueda
	- [x] verificar respuesta de búsquedfa infructuosa
	- casos de expresiones con:
		- [x] alfabeticas americanas (Simple)
		- [ ] alfabetica latina (Special)
		- [x] numérica (number)

**Comentarios issue:**
Tomaremos tres muestras de estudio:
- Alfabetico americano (palabras sin tildes, ni caracteres especiales)
- Alfabeto latino (integración de tildes, ñ, etc) por la campaña ñandú
- Numérico 

Las palabras de prueba se guardarán en el test como variables globales, un arreglo de strings (testSimpleSites, testSpecialSites, testNumberSites)

Nos dimos cuenta que el caso de búsqueda infructifera retorna el mismo tipo de respuesta para los cuatro tipos de búsqueda, para ello se usa el mismo método varifyDomNotFound

- ESTO ES EL PROBLEMA con los caracteres especiales:
```
viñalarosawine.cl
Whois.do?d=vi%C3%B1alarosawine.cl
```

Atributos de elementos HTML utilizados:
**XPath**
- ```table[@class='tablabusqueda']```
- ```[@id='submitButton']//[@onclick='/registrar/agregarDominio.do?']```

***

## TF008 WhoIs: /registry/Whois.do #18(CLOSED)
| _Etiquetas: urgente_ | _Milestones: Test Funcional_ | _Proyectos: Test Registry_ |
|:--:|:--:|:--:|

-  Whois
	- ***Validar contenido mostrado***
		- [x] dominio no registrado
		- Dominio inscrito
			- [x] Datos básicos
			- [x] Contactos
			- [x] Servidores
	- Validar para tres tipos de sitios:
		- [x] Alfabeto americano
		- [ ] Alfabeto latino
		- [x] Numérico

**Comentarios issue:**
Al igual que en #17 se usan tres tipo de alfabetos, y se integran de la misma forma (como variables globales).

para acceder a un sitio Whois.do?d=dominio.cl , primero se tuvo que haber hecho la búsqueda en BuscarDominio.do y clickear alguno de los resultados (ej. dominio.cl), por lo que no se testeará la busqueda, sólo el resultado

Para una respuesta correcta, es decir que el dominio esté inscrito hay varias formas de presentar los resultados, con información parcial.
Los datos básicos como el sitio, titular, agente registrador, fecha de creación y fecha de expiracion, siempre estarán presentes. Mientras que la informacion de contactos (Administrativo,Técnico y Comercial) y servidores no siempre está disponible.

Para manejar esto se trataron estos bloques en tres funciones diferentes: verifyBasic, verifyContact y verifyServers.

por alguna razon al tratar la muestra de specialSites los test se caen D:
y con numberSite falla

- Whois tenia problemas con el alfabeto numerico, esto debido a que el boton de renovar no solo puede dirigir a _renovar.do_ sino tambien a _autoatencion_

- ESTO ES EL PROBLEMA con los caracteres especiales:
```
viñalarosawine.cl
Whois.do?d=vi%C3%B1alarosawine.cl
```

- NIC CHile es un Agente registrador particular que no tiene informaciónes de contacto :) (se solucionó con una varable global nic y un condicional en verifyContacts)

Atributos de elementos HTML utilizados:
**XPath**
- ```table[@class='tablabusqueda']```
- ```[@id='submitButton']```

***

## TF009 Ultimos: /registry/Ultimos-do?t=... #19 (CLOSED)
| _Etiquetas: urgente_ | _Milestones: Test Funcional_ | _Proyectos: Test Registry_ |
|:--:|:--:|:--:|

- Últimos registrados 
	- ***Verfifcar contenido***
		- [x] conteo de sitios correcto
		- [x] fecha anterior a la actual
		- [x] fechas en orden descendente
		- [x] redirección a who is
	- ***Cargar páginas*** de el/la ultim@ 
		- [x] Hora "h"
		- [x] Día "d"
		- [x] Semana "w"
		- [x] Mes "m"

**Comentarios issue:**
Tomamos el mismo razonamiento y forma de #15

OJO! que el test del ultimo mes se demora muuuucho en correr D: (370 seg aprox.)

Atributos de elementos HTML utilizados:
**XPath**
- ```table[@class='tablabusqueda']```

***

## TF010 Logon: clientes.nic.cl/registrar #20 (CLOSED)
| _Etiquetas: urgente_ | _Milestones: Test Funcional_ | _Proyectos: Test Registrar_ |
|:--:|:--:|:--:|

- Inicio de sesión
 - [x] https://clientes.nic.cl/registrar/
 - [x] ser redireccionado a registrar/listarDominio.do

**Comentarios issue:**
Por seguridad, se creo una clase que guarda y carga combinaciones de usuario y contraseña:

```/registrar/UserAndPass.java```

Se abre otro issue para la recuperación de contraseña

Atributos de elementos HTML utilizados:
**XPath**
- ```input[@name='j_username']```
- ```input[@name='j_password']```

Falta agregar el caso en que redirige a agregarDominio, pues la cuenta no tiene dominios

```java
	 /** IMPORTANTE: la primera cuenta en 'userkeys.csv' 
	 * debe ser nic3chile@gmail.com y su clave de NIC
	 * para generar esto se puede usar el siguiente test
	 * 
	 * @see		UserAndPass.checkAddTuple()
	 * */
```

***

## TF011 AgregarUsuario: /registrar/agregarUsuario.do #21(CLOSED)
| _Etiquetas: urgente_ | _Milestones: Test Funcional_ | _Proyectos: Test Registrar_ |
|:--:|:--:|:--:|

Desde https://clientes.nic.cl/registrar/
- Nuevos usuarios : /registrar/agregarUsuario.do
    - [x] Crear usuario : /registar/crearUsuario.do

**Comentarios issue:**
Para la generacion de cuentas, se creó la clase ```Email.java``` para facilitar esta tarea

Atributos de elementos HTML utilizados:
**XPath**
- ```input[@name='username']```
- ```input[@name='password']```
- ```input[@name='passwordVerification']```
- ```input[@name='contacto.nombreORazonSocial']```
- ```input[@name='contacto.giro']```
- ```input[@name='email']```
- ```input[@name='contacto.telefono.numero']```
- ```select[@name='contacto.direccion.pais.id']```
- ```select[@name='contacto.direccion.regionEstadoProvincia']```
- ```input[@name='contacto.direccion.ciudad']```
- ```select[@name='contacto.direccion.comuna.id']```
- ```input[@name='contacto.direccion.calleYNumero']```
- ```select[@name='contacto.documentIdentidad.paisEmisor.id']```
- ```input[@name='contacto.documentIdentidad.value']```
- ```input[@name='envioDTE']```
- ```input[@name='envioDTEMail']```
- ```button[@value='Continuar >']```
- ```div[@id='panel_reglamentacion_c']```
- ```input[@id='reglamentacionDialogSubmit']```

***

## TF012 EditarUsuario: /registrar/editarUsuario.do #22(OPEN)
| _Etiquetas: deseable, pendiente_ | _Milestones: Test Funcional_ | _Proyectos: Test Registrar_ |
|:--:|:--:|:--:|

Una vez hecho el login
- [ ] Editar usuario : /registrar/editarUsuario.do
- [ ] Grabar Usuario : /registrar/grabarUsuario.do

***

## TF013 ListaDominio: /registrar/listarDominio.do #23(CLOSED)
| _Etiquetas: urgente_ | _Milestones: Test Funcional_ | _Proyectos: Test Registrar_ |
|:--:|:--:|:--:|

Mis Dominios : /registrar/listaDominio.do
- Verificar listado de dominios
	- [x] checkbox
	- [x] estado
	- [x] dominio
	- [x] titular
	- [x] cargos contacto
	- [x] expiracion
	- [x] carrito
- ***Filtros***
	- [x] Todos
	- Contacto/cualquiera
	- [x] Contacto/Comercial
	- [x] Contacto/Tecnico
	- [x] Contacto/Administrativo
	- Estado/Todos
	- [x] Estado/Inscritos
	- [x] Estado/No Pagado
	- [x] Estado/En conflicto
	- [x] Estado/En restauración
	- [x] Estado/BLoqueados
	- [x] Estado/Eliminados
- ***Modificar Dominio***

**Comentarios issue:**
- [ ] Verificar listado de dominios (checkbox, estado, dominio,titular,cargos contacto,expiracion,carrito)
		- ***Filtros***
			- [ ] Todos
			- [ ] Contacto/cualquiera
			- [ ] Contacto/Comercial
			- [ ] Contacto/Tecnico
			- [ ] Contacto/Administrativo
			- [ ] Estado/Todos
			- [ ] Estado/Inscritos
			- [ ] Estado/No Pagado
			- [ ] Estado/En conflicto
			- [ ] Estado/En restauración
			- [ ] Estado/BLoqueados
			- [ ] Estado/Eliminados
		- ***Modificar Dominio***

Atributos de elementos HTML utilizados:
**XPath**
- ```div[@id={"filterBy.all","filterBy.contacto.all","filterBy.contacto.comercial","filterBy.contacto.tecnico", "filterBy.contacto.administrativo","filterBy.dominio.all","filterBy.dominio.asignado","filterBy.dominio.noPagado","filterBy.dominio.enConflicto","filterBy.dominio.enRestauracion","filterBy.dominio.bloqueado","filterBy.dominio.eliminado"}```
- ```div[@id='menu-filtros']```
- ```div[@title={'Administrativo','Técnico','Comercial'}]```
- ```div[@title={"Inscritos","No pagados","En conflito","En restauración","Bloqueados","Eliminados"}]```
- ```div[@id='listaDatosVacia']```

***

## TF014 DTE: /registrar/dte.do #24(CLOSED)
| _Etiquetas: urgente_ | _Milestones: Test Funcional_ | _Proyectos: Test Registrar_ |
|:--:|:--:|:--:|

Comprobantes : /registrar/dte.do
- [x] Iniciar sesión
- [x] Acceder a DTE
- [x] Elección de dominio
- **Verificar resultados por dominio**
	- [x] Sin dominios
	- [x] Nro Documento
	- [x] Fecha
	- [x] tipo de documento
	- [x] Verificar links de 'Descarga' (xlm y pdf)

***

## TF015 RecuperarClave: /registrar/recuperaPassword.do #25(OPEN)
| _Etiquetas: deseable, necesario_ | _Milestones: Test Funcional_ | _Proyectos: Test Registrar_ |
|:--:|:--:|:--:|

- Recuperación de contraseña
 - **/registrar/logon**
    - [x] Encontrar link a formulario
 - **/registrar/recuperaPassword.do** 
    - [ ] Rellenar formulario y enviar
    - [ ] luego al inicio de registrar: /registar/logon
 - se envia un mail para acceder a /registar/cambiarPassword.do?u=...

***

## TF016 Beautify Registry #26(CLOSED)
| _Etiquetas: deseable, info_ | _Milestones: Test Funcional_ | _Proyectos: Test Registry_ |
|:--:|:--:|:--:|

Hacer que los test informen de fases y resultados de relevancia, más verbose para que el usuario sienta que el test no está pegado :+1: 
- [x] BuscarDominio
- [x] Eliminados
- [x] Ultimos
- [x] Whois

Investiga re implementar LogBack

***

## TF017 Beautify Registrar #27(OPEN)
| _Etiquetas: deseable,info, pendiente_ | _Milestones: Test Funcional_ | _Proyectos: Test Registrar_ |
|:--:|:--:|:--:|

Hacer que los test informen de fases y resultados de relevancia, más verbose para que el usuario sienta que el test no está pegado :+1: 
- [x] ListaDominio
- [ ] RecuperarClave
- [x] AgregarUsuario
- [ ] EditarUsuario
- [x] DTE
- [ ] ListaMensajes
- [x] VerCarro
- [ ] AgregarDominio
- [x] Email
- [x] Logon
- [x] UserAndPass

***

## TF018 AgregarDominio: /registrar/agregarDominio.do #28(CLOSED)
| _Etiquetas: urgente_ | _Milestones: Test Funcional_ | _Proyectos: Test Registrar_ |
|:--:|:--:|:--:|

/registrar/agregarDominio.do
- [x] Nombre dominio
	- [x] no disponible
	- [x] disponible
- [ ] Datos del titular
- [ ] informacion de contactos
- [ ] Facturacion
- [ ] servidores DNS
- [ ] Crear Dominio

***

## TF019 VerCarro: /registrar/verCarro.do #29(CLOSED)
| _Etiquetas: urgente_ | _Milestones: Test Funcional_ | _Proyectos: Test Registrar_ |
|:--:|:--:|:--:|

Carro :  /registrar/verCarro.do
- [x] Iniciar sesión
- [x] Acceder a Carro Compra
- **Verificar resultados por dominio**
  - [x] Carro vacio
  - **Contenido**
    - [x] Por dominio
    - [x] Totales y Moneda
    - [x] Medios de Pago
    - [x] Compra

***

## TF020 ListaMensajes: /registrar/listaMensajes.do #30(OPEN)
| _Etiquetas: deseable, pendiente_ | _Milestones: Test Funcional_ | _Proyectos: Test Registrar_ |
|:--:|:--:|:--:|

Avisos : /registrar/listaMensajes.do

***
## TF021 LogBack Registry #33(OPEN)
| _Etiquetas: necesario, pendiente_ | _Milestones: Test Funcional_ | _Proyectos: Test Registry_ |
|:--:|:--:|:--:|

Hacer que los test informen de fases y resultados de relevancia, hacer selectivo el verbose y activar alarmas en caso de ERROR o FATAL
- [ ] BuscarDominio
- [ ] Eliminados
- [ ] Ultimos
- [ ] Whois

***

## TF022 LogBack Registrar #34(CLOSED)
| _Etiquetas: necesario, pendiente_ | _Milestones: Test Funcional_ | _Proyectos: Test Registrar_ |
|:--:|:--:|:--:|

Hacer que los test informen de fases y resultados de relevancia, hacer selectivo el verbose y activar alarmas en caso de ERROR o FATAL
- [ ] ListaDominio
- [ ] RecuperarClave
- [ ] AgregarUsuario
- [ ] EditarUsuario
- [ ] DTE
- [ ] ListaMensajes
- [ ] VerCarro
- [ ] AgregarDominio
- [ ] Email
- [ ] Logon
- [ ] UserAndPass

***

## TF023 Carácteres especiales #35(OPEN)
| _Etiquetas: bug, pendiente_ | _Milestones: Test Funcional_ | _Proyectos: Test Registry_ |
|:--:|:--:|:--:|

En el sistema registry, tanto en BuscarDomino #17 como en WhoIs #18, al seleccionar dominios con carácteres especiales (ñ, acentos, etc) el test se cae, esto por que los caracteres son reemplazados por una codificación.
***Ej***
```
viñalarosawine.cl
Whois.do?d=vi%C3%B1alarosawine.cl
```

Por lo que en ambas clases hay que agregar un **codificador** y **decodificador** para esto.
- [ ] BuscarDominio
- [ ] Whois

***

## TF024 Externalizar Urls de Registry y Registrar #36(OPEN)
| _Etiquetas: deseable, info, pendiente, pregunta/ayuda_ | _Milestones: Test Funcional_ | _Proyectos: Test Registrar, Test Registry_ |
|:--:|:--:|:--:|

Se desea que las URLs de los sistemas Registry #1 y Registrar #3 se encuentren en un archivo, que los test lean y tomen la url que le corresponde. Así se podrá modificar las referencias sin entrar al codigo per se.

Para lograr esto habrá que:
- [x] Crear un _handler_ de este archivo, que busque en el archivo y retorne la URL correcta para pagina a testear
- [ ] Crear estos archivos para cada sistema que tengan los elementos: ```<test>,<url>``` por linea **(?)**

### Integrar esta implementación a los test
#### ***src/registrar***
- [ ] ```src/registrar/urls.csv```
- [ ] AgregarDomino
- [ ] AgregarUsuario
- [ ] DTE
- [ ] EditarDominio
- [ ] ListarDominio
- [ ] ListarMensajes
- [ ] Logon
- [ ] RecuperarClave
- [ ] VerCarro

#### ***src/registry***
- [ ] ```src/registry/urls.csv```
- [ ] BuscarDominio
- [ ] Eliminados
- [ ] Ultimos
- [ ] WhoIs

**Comentarios issue:**
Se creo la clase ```src/UrlHandler.java``` para el primer propósito descrito

***

## TF025 Activación de Usuario #37(OPEN)
| _Etiquetas: deseable, pendiente_ | _Milestones: Test Funcional_ | _Proyectos: Test Registrar_ |
|:--:|:--:|:--:|

Tras agregar un usuario #21, se envía un correo para activar la cuenta. Es entonces necesario implementar una forma de automatizar los siguientes pasos:
- [ ] Abrir el correo del usuario agregado
- [ ] Verificar el contenido del correo de activación (Asunto : _"Activación de cuenta de usuario en NIC Chile"_ )
- [ ] Activar la cuenta desde el link en el correo
- [ ] Verificar el contenido de es este link
- [ ] Verificar la creación de la cuenta

***

# Test de Carga
## TS001 [JMeter] Herramienta de testeo de stress #7 (CLOSED)
| _Etiquetas: necesario, pregunta/ayuda_ | _Milestones: Test de Stress_ | _Proyectos: Modelo de Test_ |
|:--:|:--:|:--:|

1. [Apache JMeter](http://jmeter.apache.org/) : simula heavy loads y mide la funcionalidad en servers,networks,u objetos como archivos, bases de datos y servicios web, ademas de identificar cuellos de botella y desempeño bajo stress. Test dinámicos y estáticos. 
	- Multiplataforma (Java)
	- Escalable
	- Soporte multiprotocolo (HTTP, SMTP, POP3, LDAP, JDBC, FTP, JMS, SOAP, TCP)
	- Varias implementaciones pre y post procesos sobre la prueba
	- Varias evaluaciones para definir criterios
	- Multiples listeners integrados y externos para visualizar y analizar resultados de los performance test
2. [AB (Apache Brench)para servidores](http://httpd.apache.org/docs/2.2/programs/ab.html): enfocado en HTTP, desarrollado para Apache HTTP server
3. [Speedtracer](https://code.google.com/archive/p/speedtracer/) : creado por Google, como una extensión de Chrome, basado en Java posee plug-ins para Eclipse

**Comantarios issue**
- [x] [Apache JMeter](http://jmeter.apache.org/): simula heavy loads y mide la funcionalidad en servers,networks,u objetos como archivos, bases de datos y servicios web, ademas de identificar cuellos de botella y desempeño bajo stress. Test dinámicos y estáticos. 
	- Multiplataforma (Java)
	- Escalable
	- Soporte multiprotocolo (HTTP, SMTP, POP3, LDAP, JDBC, FTP, JMS, SOAP, TCP)
	- Varias implementaciones pre y post procesos sobre la prueba
	- Varias evaluaciones para definir criterios
	- Multiples listeners integrados y externos para visualizar y analizar resultados de los performance test
- [ ] [AB (Apache Brench)para servidores](http://httpd.apache.org/docs/2.2/programs/ab.html): enfocado en HTTP, desarrollado para Apache HTTP server
- [ ] **WCAT** (Gratuito): herramienta para Windows, Web Capacity Analysis Tool, libiana, simula usuarios en 1+ sitiois, performance del server en env controlados, API para facilitar su extensión. _(!) Hay que escribir un script para cada request._
- [ ] **Load Storm** (Suscripción): cloudbased, genera escenarios realistas, al ser un agente externo el que bombardee la aplicación. _(!) Suscripción_
- [ ] **WatiN**(MUERTO):no es un stress tester, más bien un UI tester en C#, usa una API para operar y correr en Firefox y IE. _(!)Pesado de correr suficientes instancias como para simular un heavy load_
- [ ] **Speed Tracer** (Gratuito): credo por Google, como una extensión de Chrome, basado en Java posee plug-ins para Eclipse

### Apache JMeter is the chosen one!

***
## TS002 definir ambiente, test urgentes y casos correctos #10 (OPEN)
| _Etiquetas: necesario, pendiente, pregunta/ayuda_ | _Milestones: Test de Stress_ | _Proyectos: Modelo de Test_ |
|:--:|:--:|:--:|

Usando JMeter #7 

**Registrar #4** 
- Enviroment
- Test de stress por prioridad
- Comportamiento correcto

**Registry #5**
- Enviroment
- Test de stress por prioridad
- Comportamiento correcto

***
# Test de seguridad
## TT001 herramientas de testo de seguridad #8 (OPEN)
| _Etiquetas: deseable, pendiente_ | _Milestones: Test de Seguridad_ | _Proyectos: Modelo de Test_ |
|:--:|:--:|:--:|

Investigar, rankear y elegir herramientas para testeo de seguridad

- [ ] [OpenVAS](http://www.openvas.org/) : herramienta para evaluar vulnerabilidades, al parecer el administrados y scanner de vulnerabilidad OpenSource más avanzado (fork de Nessus) Actualizado diaramente con nuevos NVT's
- [ ] [Nexpose Community](http://www.rapid7.com/products/nexpose/compare-downloads.jsp) : otra herramienta para evaluar vulnerabilidad. Si bien es gratis está limitado a 32 direcciones IP y un usuario. Carece de escaneo para aplicaciones web.
- [ ] [Retina CS Community](http://info.beyondtrust.com/cscommunity) : otra herramienta para evaluar vulnerabilidad, es una consola Web que simplifica y centralizael manejo de vulnerabilidades para más de 256 evaluaciones sin costo incluyendo evaluacion automatica de vulnerabildades en servers, estaciones de trabajo (workstations), dispositivos móviles, bases de datos, aplicaciones y aplicaciones web. Soporte completo para entornos VMware, icluyendo escane do imagen virtual online y offline, escaneo  de aplicaciones virtuales, e integración con vCenter.
- [ ] [Nikto](https://cirt.net/Nikto2) : escaner de servidores Web que usa "comprhensive" test contra multiples elementos del servidor, incluyendo sobre 6700 archivos/programas potencialmente peligroso, tambien chequea servidores con versiones desactualizadas y problemas especificos para esas veersiones (para 270 servidores).
- [ ] [Metasploit Framework](https://www.metasploit.com/) : framework para testeo de penetraciones que trabaja con Nexpose, recien posee una WebUI, se puede usar para validar vulnerbilidades encontradas por Nexpose
- [ ] [ZAP (Zed Attack Proxy) OWASP](https://www.owasp.org/index.php/OWASP_Zed_Attack_Proxy_Project) : herramienta integrada para encontrar vulnerabilidades en aplicaciones web. Fork de Paros Proxy, provee esceneres automatizados así como un set de herramientas para encontrar vulnerabilidades de seguridad manualmente. En continuo desarrollo a manos de OWASP, Microsoft y Google.

***