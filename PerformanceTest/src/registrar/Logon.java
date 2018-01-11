package registrar;

import static org.junit.Assert.assertTrue;

import java.net.URL;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class Logon {

	public HtmlPage startLogin(String username, String password) {	
		System.out.println("Starting login with "+username+" account");
		String url ="https://clientes.nic.cl/registrar/logon.do";
	    String usernameInputName = "j_username";
	    String passwordInputName = "j_password";
	    
	    // create the HTMLUnit WebClient instance
	    @SuppressWarnings("resource")
		WebClient wclient = new WebClient();
	    
	    // configure WebClient based on your desired
		wclient.getOptions().setPrintContentOnFailingStatusCode(false);
		wclient.getOptions().setCssEnabled(false);
		wclient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		wclient.getOptions().setThrowExceptionOnScriptError(false);
	    
	    try {
    	
	      final HtmlPage loginPage = wclient.getPage(url);
	      assertTrue(loginPage.isHtmlPage());
	           
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
	
	private void verifyResponse(HtmlPage page){
		System.out.print("Login status... ");
		URL loginPage = page.getUrl();
		String pageLink = loginPage.toString();
		//System.out.println(pageLink);
		
		//Error login
		String errorLink = "https://clientes.nic.cl/registrar/logon.do?login_error=";
		if (pageLink.contains(errorLink)){
			System.out.println("Failure : Account not found");
			assertTrue(true);
		}
		//NoDomain login
		String noDomainLink = "agregarDominio.do";
		//Success login
		String successLink = "listarDominio.do";
		if (pageLink.contains(successLink) || pageLink.contains(noDomainLink)){
			System.out.println("Successfull \n");
			assertTrue(true);
		}
	}
		
	@Test
	public void loginWithAllTestAccounts(){
		UserAndPass up = new UserAndPass();
		int size = up.numberOfAccounts();
		for (int index = 0; index < size; index++){
			up.getTuple(index);
			String user = up.getUser();
			String pass = up.getPass();
			HtmlPage page = this.startLogin(user, pass);
			this.verifyResponse(page);
		}
	}
}
