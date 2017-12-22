# Practica2
Bitácora de mi Práctica II  @NICChile

**Comienzo:** Lunes 4 de Diciembre 2017

## Performance Testing (```../PerformanceTest```)
### Herramientas
Se utiliza la librería [HtmlUnit](http://htmlunit.sourceforge.net/gettingStarted.html) (versión 2.8), y se desarrolla en Eclipse

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