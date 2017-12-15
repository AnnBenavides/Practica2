# Requerimientos e Instalación

## JMeter
### Entorno y requerimientos
El número en negrita debe ser mayor a 1.7
```sh
 $ java -version
 openjdk version "**1.8**.0_151"
 OpenJDK Runtime Environment (build 1.8.0_151-8u151-b12-0ubuntu0.16.04.2-b12)
 OpenJDK 64-Bit Server VM (build 25.151-b12, mixed mode)
```

Si no, podemos actualizar Java con los siguientes comandos
```sh
$ sudo apt-get update
$ sudo apt-get install default-jdk
```

### Instalación de JMeter
Buscar la última versión de *Binario* de JMeter comprimido como .tgz [aquí](http://jmeter.apache.org/download_jmeter.cgi) 

Luego, en terminal descargamos el binario

```sh
 $ wget http://www-eu.apache.org/dist//jmeter/binaries/apache-jmeter-3.3.tgz 

# Descomprimimos el directorio
 $ tar -xf apache-jmeter-3.3.tgz 
 ```

Entramos a la carpeta ``apache-jmeter-x.x/bin/`` y abrimos JMeter

```sh
 $ cd apache-jmeter-3.3/bin/
 $ ./jmeter
```

## Selenium
### Entorno y requerimientos
- ***Sistema operativo:*** Ubuntu 16.04.3 LTS (tambien está soportado Windows, OS X, Solaris, en verdad lo importante es el browser)
- ***Versión de Firefox:*** Firefox Quantum 57.0.1 (64-bit) , aunque basta con Firefox 2 o 3
- ***Lenguaje de programación*** : Java, con opciones de C#, Perl, PHP, Python y Ruby


### Instalación de Selenium HQ
en este caso se usó la [última versión](http://selenium-release.storage.googleapis.com/index.html) disponible de SeleniumHQ
 - ACTUAL: selenium-server-standalone-3.8.1.jar

```sh
$ sudo apt-get update

# Make a directory for Selenium
$ mkdir ~/selenium
$ cd ~/selenium

#Get selenium
$ sudo wget http://selenium-release.storage.googleapis.com/3.8/selenium-server-standalone-3.8.1.jar

# Install headless Java runtime
$ sudo apt-get install openjdk-8-jre-headless -y

# Install Firefox
$ sudo apt-get install firefox -y

# Install headless GUI for firefox.  'Xvfb is a display server that performs graphical operations in memory'
sudo apt-get install xvfb -y

# Finally, starting up Selenium server
#
echo "\r\nStarting up Selenium server ...\r\n"
DISPLAY=:1 xvfb-run java -jar ~/selenium/selenium-server-standalone-3.8.1.jar

``` 
