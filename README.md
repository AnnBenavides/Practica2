# Practica2
Bitácora de mi Práctica II  @NICChile

**Comienzo:** Lunes 4 de Diciembre 2017
**Finalización:** Miércoles 31 de Enero 2018

# Alcance del Proyecto

Se desean realizar, sobre los sistemas _registryPublic_, _registrar_ y la conexión _EPP_, tres tipos de pruebas que puedan integrarse de manera individual a cada módulo: test de Funcionalidad, test de Carga y test de Seguridad. 

Se prioriza el desarrollo de test de funcionalidad para los sistemas _registryPublic_ y _registrar_, para luego seguir con el test de carga en los mismos. Dejamos de lado tanto la conexión EPP como el test de seguridad por falta de tiempo. Adelantamos que el test de carga tampoco alcanzó a desarrollarse.

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

# Lectura de Issues
Segun nombre de issue se categorizan en:
- **rPXXX:** de _registryPublic_ son las issues relacionadas puramente con ese sistema
- **RrXXX:** de _registrar_ son las issues relacionadas puramente con ese sistema
- **TFXXX:** issues relacionadas con Test de Funcionalidad
- **TSXXX:** issues relacionadas con Test de Stress
- **TTXXX:** issues relacionadas con Test de Seguridad

## Labels
***Prioridades** (de mayor a menor)*
- [urgente](https://github.com/AnnBenavides/Practica2/labels/urgente)
- [necesario](https://github.com/AnnBenavides/Practica2/labels/necesario)
- [deseable](https://github.com/AnnBenavides/Practica2/labels/deseable)

***Categorías***
- [info](https://github.com/AnnBenavides/Practica2/labels/info) : documentación rápida, información del sistema y toma de decisiones
- [issue padre](https://github.com/AnnBenavides/Practica2/labels/issue%20padre) 
- [pregunta/ayuda](https://github.com/AnnBenavides/Practica2/labels/pregunta%2Fayuda) : para issues en donde se necesite discusión o una segunda opinion antes de tomar una decisión
- [pendiente](https://github.com/AnnBenavides/Practica2/labels/pendiente) : issues que aun no son revisados, o que no se lograron desarrollar en el periodo de práctica
- [invalido](https://github.com/AnnBenavides/Practica2/labels/invalido) : issues que ya no son necesarias
- [bug](https://github.com/AnnBenavides/Practica2/labels/bug) : problemas o casos especiales que rompen el programa

## Milestones
- **[Rr: Cuentas de Usuario](https://github.com/AnnBenavides/Practica2/milestone/6):** issues relacionadas con la documentación e información recolectada del sistema Registrar
- **[RegistryPublic: NIC.cl](https://github.com/AnnBenavides/Practica2/milestone/4):** issues relacionadas con la documentación e información recolectada del sistema Registry
- **[Sistema EPP](https://github.com/AnnBenavides/Practica2/milestone/5):** issues relacionadas con la documentación e información recolectada de la conexión EPP
- **[Test de Funcionalidad](https://github.com/AnnBenavides/Practica2/milestone/2)**
- **[Test de Stress](https://github.com/AnnBenavides/Practica2/milestone/1)**
- **[Test de Seguridad](https://github.com/AnnBenavides/Practica2/milestone/3)** 

## Projectos
Canvas de progreso para los distintos proyectos o etapas
- [Investigación](https://github.com/AnnBenavides/Practica2/projects/1): _Buenas prácticas y herramientas para: performance testing, security testing & stress testing_
- [Modelo de Test](https://github.com/AnnBenavides/Practica2/projects/3): _Identificar qué iremos a testear en detalle: con que prioridad, qué herramienta, de que forma, definir ambientes y datos._
- [Test Registry](https://github.com/AnnBenavides/Practica2/projects/4): _nic.cl/registry || Identificar qué iremos a testear en detalle: con que prioridad, qué herramienta, de que forma, definir ambientes y datos._
- [Test Registrar](https://github.com/AnnBenavides/Practica2/projects/5): _https://clientes.nic.cl/registrar/ || Identificar qué iremos a testear en detalle: con que prioridad, qué herramienta, de que forma, definir ambientes y datos._