# Requerimientos e Instalación

## Ambiente
### Resumen
- Processor: 2x Intel(R) Core(TM)2 Duo CPU E7300  @ 2.66GHz
- Memory: 2047MB (1600MB used)
- Operating System: Ubuntu 16.04.3 LTS


### Sistema Operativo
- Kernel: Linux 4.4.0-103-generic (x86_64)
- Compiled: #126-Ubuntu SMP Mon Dec 4 16:23:28 UTC 2017
- C Library: Unknown
- Default C Compiler: GNU C Compiler version 5.4.0 20160609 (Ubuntu 5.4.0-6ubuntu1~16.04.5) 
- Distribution		: Ubuntu 16.04.3 LTS
- Desktop Environment		: Unity (ubuntu)

## JMeter
### Verficación de Java
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

### Instalación de JMeter
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

