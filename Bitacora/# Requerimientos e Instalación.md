# Requerimientos e Instalación

## JMeter
**Verficación de Java**
El número en negrita debe ser mayor a 1.7
```
 $ java -version
 openjdk version "**1.8**.0_151"
 OpenJDK Runtime Environment (build 1.8.0_151-8u151-b12-0ubuntu0.16.04.2-b12)
 OpenJDK 64-Bit Server VM (build 25.151-b12, mixed mode)
```

Si no, podemos actualizar Java con los siguientes comandos
``` 
$ sudo apt-get update
$ sudo apt-get install default-jdk
```

**Instalación de JMeter**
Buscar la última versión de *Binario* de JMeter comprimido como .tgz [aquí](http://jmeter.apache.org/download_jmeter.cgi) 

Luego, en terminal descargamos el binario

``` $ wget http://www-eu.apache.org/dist//jmeter/binaries/apache-jmeter-3.3.tgz ```

Descomprimimos el directorio

``` $ tar -xf apache-jmeter-3.3.tgz ```

Entramos a la carpeta ``apache-jmeter-x.x/bin/`` y abrimos JMeter

```
 $ cd apache-jmeter-3.3/bin/
 $ ./jmeter
```

