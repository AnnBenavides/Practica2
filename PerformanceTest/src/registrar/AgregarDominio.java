package registrar;

import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.List;

import org.junit.Test;

import registry.DomainsFile;

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

public class AgregarDominio {
	/**TODO**/
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
		      return returnPage; 		  
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
	
	/** En caso de que el usuario no tengo dominios
	 * 
	 * @param page	contenido de la pagina luego de iniciar sesion
	 * @return		true si el usuario no tiene dominios, false si los tiene
	 * */
	private boolean verifyNoDomains(HtmlPage page){
		URL loginPage = page.getUrl();
		String pageLink = loginPage.toString();
		//System.out.println(pageLink);
		return pageLink.contains("agregarDominio.do");
	}
	
	/** Luego de iniciar sesion y dirigirse a agragarDominio.do
	 * 
	 * @param page	contenido de la pagina luego de inicias sesion
	 * @return		contenido de agregarDominio o null si hay problemas
	 * 
	 * @see			verifyNoDomains(page)
	 * */
	private HtmlPage goToAgregarDominio(HtmlPage page){
		try {
			if (!this.verifyNoDomains(page)){
				List<DomElement> inputs = page.getElementsByTagName("input");
				for (DomElement input : inputs){
					if (input.hasAttribute("onclick") && input.getAttribute("onclick").contains("agregarDominio.do")){
						HtmlPage agregar = input.click();
						URL loginPage = agregar.getUrl();
						String pageLink = loginPage.toString();
						if (pageLink.contains("agregarDominio")){
							assertTrue(true);
							return agregar;
						} else {
							assertTrue(false);
							return page;
						}
						
					}
				}
				System.out.println("! No hay acceso a la pagina AgregarDominio");
				assertTrue(false);
				return page;
			} else {
				assertTrue(true);
				return page;
			}
		} catch (Exception e){
			assertTrue(false);
			e.printStackTrace();
			return null;
		}
	}
	
	/** Verifica si hay algun mensaje de error activado
	 * 
	 * @param page	contenido de la pagina AgregarDominio.do
	 * @return		true si hay mensaje de error, false sino
	 * */
	private boolean hasErrors(HtmlPage page){
		try{
			List<DomElement> divs = page.getElementsByTagName("div");
			for (DomElement div : divs){
				if (div.hasAttribute("class") && div.getAttribute("class").equals("cont_mensaje")){
					return div.asText().contains("Errores");
				}
			}
			return false;
		} catch (Exception e){
			assertTrue(false);
			e.printStackTrace();
			return false;
		}
	}
	
	/** Busca el div con cierto class e id como atributos, 
	 * dentro del form 'dominio'
	 * 
	 * @param page		contenido de la pagina AgregarDominio.do
	 * @param classAttr	valor del atributo 'class'
	 * @param idAttr	valor del atributo 'id' o null de no tenerlo
	 * @return			div con dichas caracteristicas 
	 * 					o null de no ser encontrado
	 * */
	private HtmlElement getDivInForm(HtmlPage page, String classAttr, String idAttr){
		try {
			List<HtmlForm> forms = page.getForms();
			HtmlForm form = forms.get(0);
			for (HtmlForm f : forms){
				if (f.getAttribute("id").equals("dominio")){
					form=f;
				}
			}
			assertTrue(form.getAttribute("action").contains("grabarDominio.do"));
			List<HtmlElement> divs = form.getElementsByTagName("div");
			for (HtmlElement div : divs){
				if (div.hasAttribute("class") && div.getAttribute("class").equals(classAttr)){
					if (idAttr != null){
						if (div.hasAttribute("id") && div.getAttribute("id").equals(idAttr)){
							assertTrue(true);
							return div;
						}
					} else {
						assertTrue(true);
						return div;
					}
				}
			}
			assertTrue(false);
			return null;
		} catch (Exception e){
			assertTrue(false);
			e.printStackTrace();
			return null;
		}
	}
	
	/** Encontrar un dominio que está disponible
	 * 
	 * @param page 	contenido de la pagina AgregarDominio.do
	 * @param word	palabra para dominio
	 * @return		contenido de la pagina tras ver si esta disponible
	 * 
	 * @see 		getDivInForm(page, classAttr, idAttr)
	 * */
	private HtmlPage intentoDominio(HtmlPage page, String word){
		try {
			HtmlElement seccion = this.getDivInForm(page, "cont_formulario", null);
			List<HtmlForm> forms = page.getForms();
			HtmlForm form = forms.get(0);
			for (HtmlForm f : forms){
				if (f.getAttribute("id").equals("dominio")){
					form=f;
				}
			}
			assertTrue(form.getAttribute("action").contains("grabarDominio.do"));
			HtmlTextInput input = form.getInputByName("nombre");
			input.setValueAttribute(word);
			List<HtmlElement> inputs = seccion.getElementsByTagName("input");
			for (HtmlElement ver : inputs){
				if (ver.hasAttribute("id") && ver.getAttribute("id").equals("check-domain")){
					assertTrue(true);
					ver.click();
					return page;
				}
			}
			assertTrue(false);
			return page;
		} catch (Exception e){
			assertTrue(false);
			e.printStackTrace();
			return page;
		}
	}
	
	/** Evalua si el dominio ya esta inscrito o no,
	 * a traves de alertas de la misma pagina
	 * 
	 * @param page	contenido de AgregarDominio.do 
	 * 				luego de insertar un dominio
	 * @return		true si el dominio esta disponible,
	 * 				false sino
	 * 
	 *  @see 		getDivInForm(page, classAttr, idAttr)
	 *  */
	private boolean noDisponible(HtmlPage page){
		try {
			synchronized (page) {
	            page.wait(2000); //wait
	        }
			HtmlElement seccion = this.getDivInForm(page, "cont_formulario", null);
			String result = seccion.asText();
			String no = "el dominio está inscrito";
			String rep = "Ya existe una solicitud por este nombre";
			return (result.contains(no)) || (result.contains(rep));
		} catch (Exception e){
			assertTrue(false);
			e.printStackTrace();
			return false;
		}
	}

	@Test
	public void simpleTest(){
		System.out.println("<< STARTING AgregarDominio.simple test");
		try{
			List<String> words = new DomainsFile().getSimple();
			HtmlPage login = this.login(1);
			HtmlPage page = this.goToAgregarDominio(login);
			for (String word : words){
				HtmlPage intento = this.intentoDominio(page, word);
				if (!this.noDisponible(intento)){
					System.out.println("Inscribiendo: "+word+".cl");
				}
			}
			
		} catch (Exception e){
			assertTrue(false);
			e.printStackTrace();
		}
	}
}
