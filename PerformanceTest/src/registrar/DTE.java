package registrar;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class DTE {
	/**Método para iniciar sesion
	 * login exitoso lleva a listarDominio.do
	 * o a agregarDominio.do si el usuario no tiene dominios
	 * 
	 * @param userNumber	indice de usuario en el csv 'userkeys'
	 * @return				contenido de la pagina si el inicio fue exitoso
	 * 						null si hubo algun problema
	 * 
	 * @see		UserAndPass.java
	 * */
	private HtmlPage login(int userNumber){
		HtmlPage page;
		//System.out.println("\n\n\t Signing in ...");
		
		UserAndPass up = new UserAndPass();
		up.getTuple(userNumber);
		String username = up.getUser();
		String password = up.getPass();
		
		//System.out.println("Starting login with "+username+" account");
		
		String url ="https://clientes.nic.cl/registrar/logon.do";
	    String usernameInputName = "j_username";
	    String passwordInputName = "j_password";
	    
	    // create the HTMLUnit WebClient instance
	    @SuppressWarnings("resource")
		WebClient wclient = new WebClient(BrowserVersion.FIREFOX_45);
	    
	    
	    // configure WebClient based on your desired
		wclient.getOptions().setPrintContentOnFailingStatusCode(false);
		wclient.getOptions().setCssEnabled(false);
		wclient.getOptions().setJavaScriptEnabled(true);
        wclient.setAjaxController(new NicelyResynchronizingAjaxController());
		wclient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		wclient.getOptions().setThrowExceptionOnScriptError(false);		
	    
	    try {
	      WebRequest request = new WebRequest(new URL(url));
	      final HtmlPage loginPage = wclient.getPage(request);
	      assertTrue(loginPage.isHtmlPage());
	      //System.out.print("Log in state : ");     
	      final HtmlForm loginForm = loginPage.getForms().get(0);
	      
	      // get the text input field by the name and set the value
	      final HtmlTextInput txtUser = loginForm.getInputByName(usernameInputName);
	      txtUser.setValueAttribute(username);
	    		
	      // get the password input field by the name and set the value
	      final HtmlPasswordInput txtpass = loginForm.getInputByName(passwordInputName);
	      txtpass.setValueAttribute(password);
	      
	      // get the submit button by the text value
	      final HtmlButton submitLogin = (HtmlButton) loginForm.getElementsByTagName("button").get(0);
	      final HtmlPage returnPage = submitLogin.click();  
	      assertTrue(true);
	      page = returnPage; 
	      
		  URL lPage = page.getUrl();
		  String pageLink = lPage.toString();
		  if (pageLink.contains("listarDominio.do") || pageLink.contains("agregarDominio.do")){
		  	//System.out.println("Success");
			assertTrue(true);
			return page;
		  } else {
			//System.out.println("Failure");
			assertTrue(false);
			return null;
		  }
		  
	    } catch(FailingHttpStatusCodeException e) {
	      e.printStackTrace();
	      assertTrue(false);
	      return null;
	    } catch(Exception e) {
	      e.printStackTrace();
	      assertTrue(false);
	      return null;
	    }
	}
	
	/**Si al iniciar sesion se retorno a listarDominio.do
	 * vamos a la seccion de 'Comprobantes' o dte.do
	 * 
	 * @param page		contenido de la pagina listarDominio.do
	 * @return			contenido de la pagina dte.do
	 * 					o null si ocurrio algun problema	
	 *
	 * @see			login(userNumber)	
	 * */
	private HtmlPage goToDTE(HtmlPage page){
		try {
			//System.out.print("Accessing to 'Comprobantes'... ");
			HtmlElement div = (HtmlElement) page.getElementById("menu_dte");
			HtmlElement a = div.getElementsByTagName("a").get(0);
			String href = a.getAttribute("href");
			boolean checkLink = href.contains("dte.do");
			assertTrue(checkLink);
			if (checkLink){
				//System.out.println("Success");
				HtmlPage refresh = a.click();
				synchronized (refresh) {
		            refresh.wait(2000); //wait
		        }
				return refresh;
			} else {
				//System.out.println("Failure");
				return null;
			}
		} catch (Exception e){
			//System.out.println("! Link not accesible");
			assertTrue(false);
			e.printStackTrace();
			return null;
		}
	}
	
	/** Evalua si hay o no elementos facturados
	 * 
	 * @param page		contenido luego de ingresar a dte.do
	 * @return			true si no hay elementos facturados
	 * 					o false si los hay
	 * 
	 * @see			goToDTE(page)
	 * */
	private boolean hasNoDomains(HtmlPage page){
		try{
			List<DomElement> divs = page.getElementsByTagName("div");
			for (DomElement div : divs){
				if (div.hasAttribute("class")){
					String classAttr = div.getAttribute("class");
					//System.out.println("div class = "+classAttr);
					if (classAttr.equals("mensaje")){
						String text = div.asText();
						String msg = "No tiene dominios que se encuentren facturados, por lo cual, tampoco Documentos Tributarios Electronicos asociados";
						return text.contains(msg);
					}
				}
			}
			return false;
		} catch (Exception e){
			//System.out.println("! Problems at DTE.hasNoDomains(HtmlPage page)");
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	/** Entrega la cantidad de dominios con facturacion
	 * para su proxima seleccion
	 * 
	 * @param page		contenido de la pagina
	 * @return			cantidad de dominios seleccionables
	 * */
	private int getDomains(HtmlPage page){
		try{
			DomElement select = page.getElementById("filtroBusqueda");
			List<HtmlElement> options = select.getElementsByTagName("option");
			assertTrue(options.size()>0);
			//System.out.println("User has "+options.size()+" domains to choose");
			return options.size();
		} catch (Exception e){
			assertTrue(false);
			//System.out.println("! Can't find select for domains");
			e.printStackTrace();
			return -1;
		}
	}
		
	/** Verifica el contenido de cada fila
	 * o facturacion:
	 * 		nro de documento, fecha, 
	 * 		tipo de documento, descarga de pdf y xml
	 * 
	 * @param rows		lista de filas de resultados
	 * */
	private void verifyRows(List<HtmlElement> rows){
		try{
			//System.out.println("This domain has "+rows.size()+" 'comprobantes'");
			for (HtmlElement row : rows){
				 List<HtmlElement> columns = row.getElementsByTagName("div");
				 //System.out.println("Nr of columns (div): "+columns.size());
				 
				 //NRO DE DOCUMENTO
				 List<HtmlElement> col1 = columns.get(0).getElementsByTagName("p");
				 String nroDoc = col1.get(0).asText();
				 //System.out.println("\n\t | Nro. de Documento : "+nroDoc);
				 assertTrue(!nroDoc.isEmpty());
				
				 //FECHA
				 List<HtmlElement> col2 = columns.get(1).getElementsByTagName("p");
				 String fecha = col2.get(0).asText();
				// System.out.println("\t | Fecha : "+fecha);
				 String[] format = fecha.split("-");
				 assertEquals(3,format.length);
				
				 //TIPO DE DOCUMENTO
				 List<HtmlElement> col3 = columns.get(2).getElementsByTagName("p");
				 String tipoDoc = col3.get(0).asText();
				 //System.out.println("\t | Tipo de Documento : "+tipoDoc);
				 assertTrue(!tipoDoc.isEmpty());
				
				 //DESCARGAR
				 List<HtmlElement> col4 = columns.get(3).getElementsByTagName("a");
				 //System.out.println("\t | Descargas : "+col4.size());
				 String xml = "&format=XML";
				 String pdf = "&format=PDF";
				 String getDTELink = "/registrar/getDTE.do?folio="+nroDoc+"&tipoDTE=";
				 String hrefXML = col4.get(0).getAttribute("href");
				 String hrefPDF = col4.get(1).getAttribute("href");
				 boolean hasXML = hrefXML.contains(getDTELink) && hrefXML.contains("&tipoDTE=")
						 && hrefXML.contains("&idDominio=") && hrefXML.endsWith(xml);
				 boolean hasPDF = hrefPDF.contains(getDTELink) && hrefPDF.contains("&tipoDTE=")
						 && hrefPDF.contains("&idDominio=") && hrefPDF.endsWith(pdf);
				 assertEquals(true,hasXML);
				 assertEquals(true,hasPDF);
				 //System.out.println("\t | Descarga XML : "+hasXML+"\t Descarga PDF : "+hasPDF);
			}
			
		} catch (Exception e){
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	/**	Verifica los resultados de facturacion de un dominio
	 * identifica la tabla de contenidos, crea una lista con
	 * las filas de resultado y evalua
	 * 
	 * @param page	contenido de pagina luego de seleccionar un dominio
	 * 
	 * @see			verifyRows(rows)
	 * */
	private void verifyResults(HtmlPage page){
		try{
			List<DomElement> divs = page.getElementsByTagName("div");
			List<HtmlElement> rows = new ArrayList<HtmlElement>();
			for (DomElement div : divs){
				if (div.hasAttribute("class")){
					String classAttr = div.getAttribute("class");
					if (classAttr.equals("listaDatos")){
						System.out.println("Verifying data ...");
						assertTrue(true);				
					}
					String rowClass = "contenedor_datos";
					if (classAttr.equals(rowClass)){
						rows.add((HtmlElement)div);
						assertTrue(true);	
					}
				}
			}
			this.verifyRows(rows);			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/** Selecciona un dominio para cargar sus 
	 * facturaciones y evaluar su contenido,
	 * de no haber dominios facturados se evalua
	 * el aviso
	 * 
	 * @param page	contenido de cargar dte.do
	 * 
	 * @see			hasNoDomains(page)
	 * 				getDomains(page)
	 * 				verifyResults(page)
	 * */
	private void selectDomain(HtmlPage page){
		try{
			if (!this.hasNoDomains(page)){
				int domains = this.getDomains(page);
				if (domains > 0){
					//System.out.println("User has "+domains+" domains to select");
					assertTrue(true);
				}
				for (int dom = 0 ; dom < domains ; dom++){
					//System.out.print("Filter by domain ");
					if (dom <= this.getDomains(page)){
						DomElement select = page.getElementById("filtroBusqueda");
						List<HtmlElement> options = select.getElementsByTagName("option");
						HtmlElement domain = options.get(dom);
						//System.out.println("\t > Working on "+domain.asText());
						HtmlPage refresh = domain.click();
						synchronized (refresh) {
				            refresh.wait(2000); //wait
				        }
						this.verifyResults(refresh);
					} else {
						//System.out.println("! There is no such domain to select");
						assertTrue(false);
						return;
					}
				}
			} else {
				//System.out.println("User has no domains in this section");
				assertTrue(true);
			}
		} catch (Exception e){
			//System.out.println("! Problems at DTE.selectDomain(HtmlPage page, int dom)");
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/** Por cada usuario en 'userkey.csv'
	 * se evaluan los comprobantes para todos
	 * sus dominios disponibles
	 * 
	 * @see		UserAndPass class
	 * 			login(int user)
	 * 			goToDTE(page)
	 * 			selectDomain(page)
	 * */
	@Test
	public void comprobantes(){
		System.out.println("<< STARTING DTE.comprobantes test");
		try{
			int users = new UserAndPass().numberOfAccounts();
			for (int user = 0 ; user < users ; user++){
				HtmlPage page = this.goToDTE(this.login(user));
				this.selectDomain(page);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		System.out.println("DTE.comprobantes FINISHED >>");
	}
}
