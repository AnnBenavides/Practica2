# Documentación Performance Test

***

# Herramientas
`PerformanceTest` es un Java Project, desarrollado en Eclipse con JRE 1.5 y JDK 1.7. Además complementa la API de HtmlUnit (v 2.8) con JUnit4 (v 4.8), los archivos de la API estan en la carpeta `lib` del proyecto.

# Inicialización
Primero se debe clonar el repositorio, donde la carpeta `PerformanceTest` corresponde a los recursos del proyecto. Idealmente en Eclipse, se debe:
- Importar como _proyecto generico_ esta carpeta junto con sus subcarpetas `src` y `lib`
- Configurar el proyecto como _Java Project_
- Agregar la librería de HTMLUnit, para esto se va a _Build Path_ > _Libraries_ > _Add JAR's_ y se seleccionan todos los elementos contenidos en `PerformanceTest/lib/` y se aplican los cambios.
- Integrar JUnit4 al proyecto
- Agregar un archivo de nombre _userkeays.csv_ en `PerformanceTest/src/registrar/` que contenga usuarios y claves de usuarios de nic, para realizar las pruebas. En cada lines debe ir el correo del usuario, seguido de una coma y luego su clave, sin espacios.

## Verificar instalación correcta

Para verificar que la API de HTMLUnit esta correcta se hace correr el archivo `PerformanceTest/src/Test.java` donde si todo esta bien deberíamos tener la siguiente respuesta:

```java
Querying
//...
Success
//...
Finished
```

Por otro lado, para verificar que los usuarios son útiles para el testeo se recomienda correr, como test, la clase _Logon_ (`PerformanceTest/src/registrar/Logon.java`). Si algún usuario lanza error se recomienda removerlo de la lista ya que creará errores en el resto de los test del sistema registrar.

## Casos de prueba
Para aumentar o cambiar las cantidades de pruebas diferentes se pueden agregar usuarios en `PerformanceTest/src/registrar/userkeays.csv` para aumentar las pruebas en el sistema **registrar**, mientras que para el sistema **registryPublic** están los diccionarios de palabras en archivos .txt ubicados en `PerformanceTest/src/registry` donde _simple.txt_ contiene caracteres simples, _number.txt_ tiene sitios alfanumericos y _special.txt_ contiene palabras con caracteres latinos para sitios objetivo de la campaña ñandú.cl

# ../src/registry

# ../src/registrar
