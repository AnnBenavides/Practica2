# Practica2
Bitácora de mi Práctica II  @NICChile

**Comienzo:** Lunes 4 de Diciembre 2017

# Performance Testing (```../PerformanceTest```)
## Herramientas
Se utiliza la librería [HtmlUnit](http://htmlunit.sourceforge.net/gettingStarted.html) (versión 2.8), y se desarrolla en Eclipse

## Inicialización
### Archivos
- ***Usuarios:*** crear un archivo csv ```src\registrar\userkeys.csv``` como indica en la [issue](https://github.com/AnnBenavides/Practica2/issues/20#issuecomment-360145362)
- ***URLs:*** revisar ```src\UrlHandler.java```
- ***Alfabetos de prueba:*** revisar ```src\registry\DomainsFile.java```

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
- [pendiente](https://github.com/AnnBenavides/Practica2/labels/pendiente) : issues que aun no son revisados
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