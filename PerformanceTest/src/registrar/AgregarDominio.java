package registrar;

import static org.junit.Assert.assertTrue;

import java.net.URL;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
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
			return !pageLink.contains("listarDominio.do");
		}
}
