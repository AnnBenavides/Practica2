# Requerimientos e Instalación
## HtmlUnit
### Instalación de HtmlUnit en Eclipse
1. Crear un nuevo proyecto Jave (default settings)
2. Crear una carpeta llamada "lib" (Click derecho en proyecto: New -> Folder)
3. [Descargar](https://sourceforge.net/projects/htmlunit/files/htmlunit/) la librería de HtmlUnit (htmlunit-X.X-bin.zip)
4. Descomprimir la carpeta "/htmlunit-X.X/lib" en la carpeta del proyecto "lib"
5. Click derecho en el proyecto : Build Path -> Configure Build Path
6. En la pestaña "Libraries" y clickeamos _Add JARs..._
7. Buscamos nuestra carpeta "lib", seleccionamos todos los elementos de esta y cerramos las ventanas
8. Para verificar que todo funciona se puede probar el siguiente código (Test.java en /src):
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
    
```sh
Querying
Four
java.lang.RuntimeException: com.gargoylesoftware.htmlunit.javascript.TimeoutError: Javascript execution takes too long (allowed: 1, already elapsed: 42)
    at com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine$HtmlUnitContextAction.run(JavaScriptEngine.java:916)
    at net.sourceforge.htmlunit.corejs.javascript.Context.call(Context.java:599)
    at net.sourceforge.htmlunit.corejs.javascript.ContextFactory.call(ContextFactory.java:527)
    at com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine.execute(JavaScriptEngine.java:790)
    at com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine.execute(JavaScriptEngine.java:766)
    at com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine.execute(JavaScriptEngine.java:757)
    at com.gargoylesoftware.htmlunit.html.HtmlPage.executeJavaScript(HtmlPage.java:920)
    at com.gargoylesoftware.htmlunit.html.HtmlScript.executeInlineScriptIfNeeded(HtmlScript.java:316)
    at com.gargoylesoftware.htmlunit.html.HtmlScript.executeScriptIfNeeded(HtmlScript.java:396)
    at com.gargoylesoftware.htmlunit.html.HtmlScript$2.execute(HtmlScript.java:246)
    at com.gargoylesoftware.htmlunit.html.HtmlScript.onAllChildrenAddedToPage(HtmlScript.java:267)
    at com.gargoylesoftware.htmlunit.html.HTMLParser$HtmlUnitDOMBuilder.endElement(HTMLParser.java:805)
    at org.apache.xerces.parsers.AbstractSAXParser.endElement(Unknown Source)
    at com.gargoylesoftware.htmlunit.html.HTMLParser$HtmlUnitDOMBuilder.endElement(HTMLParser.java:761)
    at net.sourceforge.htmlunit.cyberneko.HTMLTagBalancer.callEndElement(HTMLTagBalancer.java:1236)
    at net.sourceforge.htmlunit.cyberneko.HTMLTagBalancer.endElement(HTMLTagBalancer.java:1136)
    at net.sourceforge.htmlunit.cyberneko.filters.DefaultFilter.endElement(DefaultFilter.java:226)
    at net.sourceforge.htmlunit.cyberneko.filters.NamespaceBinder.endElement(NamespaceBinder.java:345)
    at net.sourceforge.htmlunit.cyberneko.HTMLScanner$ContentScanner.scanEndElement(HTMLScanner.java:3178)
    at net.sourceforge.htmlunit.cyberneko.HTMLScanner$ContentScanner.scan(HTMLScanner.java:2141)
    at net.sourceforge.htmlunit.cyberneko.HTMLScanner.scanDocument(HTMLScanner.java:945)
    at net.sourceforge.htmlunit.cyberneko.HTMLConfiguration.parse(HTMLConfiguration.java:521)
    at net.sourceforge.htmlunit.cyberneko.HTMLConfiguration.parse(HTMLConfiguration.java:472)
    at org.apache.xerces.parsers.XMLParser.parse(Unknown Source)
    at com.gargoylesoftware.htmlunit.html.HTMLParser$HtmlUnitDOMBuilder.parse(HTMLParser.java:1004)
    at com.gargoylesoftware.htmlunit.html.HTMLParser.parse(HTMLParser.java:253)
    at com.gargoylesoftware.htmlunit.html.HTMLParser.parseHtml(HTMLParser.java:195)
    at com.gargoylesoftware.htmlunit.DefaultPageCreator.createHtmlPage(DefaultPageCreator.java:267)
    at com.gargoylesoftware.htmlunit.DefaultPageCreator.createPage(DefaultPageCreator.java:158)
    at com.gargoylesoftware.htmlunit.WebClient.loadWebResponseInto(WebClient.java:524)
    at com.gargoylesoftware.htmlunit.WebClient.getPage(WebClient.java:398)
    at com.gargoylesoftware.htmlunit.WebClient.getPage(WebClient.java:315)
    at com.gargoylesoftware.htmlunit.WebClient.getPage(WebClient.java:463)
    at com.gargoylesoftware.htmlunit.WebClient.getPage(WebClient.java:448)
    at Test.main(Test.java:25)
Caused by: com.gargoylesoftware.htmlunit.javascript.TimeoutError: Javascript execution takes too long (allowed: 1, already elapsed: 42)
    at com.gargoylesoftware.htmlunit.javascript.HtmlUnitContextFactory$TimeoutContext.terminateScriptIfNecessary(HtmlUnitContextFactory.java:153)
    at com.gargoylesoftware.htmlunit.javascript.HtmlUnitContextFactory.observeInstructionCount(HtmlUnitContextFactory.java:312)
    at net.sourceforge.htmlunit.corejs.javascript.Context.observeInstructionCount(Context.java:2469)
    at net.sourceforge.htmlunit.corejs.javascript.Interpreter.addInstructionCount(Interpreter.java:3209)
    at net.sourceforge.htmlunit.corejs.javascript.Interpreter.interpretLoop(Interpreter.java:2002)
    at net.sourceforge.htmlunit.corejs.javascript.Interpreter.interpret(Interpreter.java:815)
    at net.sourceforge.htmlunit.corejs.javascript.InterpretedFunction.call(InterpretedFunction.java:111)
    at net.sourceforge.htmlunit.corejs.javascript.ContextFactory.doTopCall(ContextFactory.java:417)
    at com.gargoylesoftware.htmlunit.javascript.HtmlUnitContextFactory.doTopCall(HtmlUnitContextFactory.java:325)
    at net.sourceforge.htmlunit.corejs.javascript.ScriptRuntime.doTopCall(ScriptRuntime.java:3424)
    at net.sourceforge.htmlunit.corejs.javascript.InterpretedFunction.exec(InterpretedFunction.java:122)
    at com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine$3.doRun(JavaScriptEngine.java:781)
    at com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine$HtmlUnitContextAction.run(JavaScriptEngine.java:895)
    ... 34 more
Finished

```

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
