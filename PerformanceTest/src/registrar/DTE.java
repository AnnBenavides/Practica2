package registrar;

import static org.junit.Assert.*;

import java.net.URL;
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
	/**TODO**/
	private HtmlPage login(int userNumber){
		HtmlPage page;
		System.out.println("Signing in ...");
		
		UserAndPass up = new UserAndPass();
		up.getTuple(userNumber);
		String username = up.getUser();
		String password = up.getPass();
		
		System.out.println("Starting login with "+username+" account");
		
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
	      System.out.print("Log in state : ");     
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
		  	System.out.println("Success");
			assertTrue(true);
			return page;
		  } else {
			System.out.println("Failure");
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
	
	private HtmlPage goToDTE(HtmlPage page){
		try {
			System.out.print("Accessing to 'Comprobantes'... ");
			HtmlElement div = (HtmlElement) page.getElementById("menu_dte");
			HtmlElement a = div.getElementsByTagName("a").get(0);
			String href = a.getAttribute("href");
			boolean checkLink = href.contains("dte.do");
			assertTrue(checkLink);
			if (checkLink){
				System.out.println("Success");
				HtmlPage refresh = a.click();
				synchronized (refresh) {
		            refresh.wait(2000); //wait
		        }
				return refresh;
			} else {
				System.out.println("Failure");
				return null;
			}
		} catch (Exception e){
			System.out.println("! Link not accesible");
			assertTrue(false);
			e.printStackTrace();
			return null;
		}
	}
	private boolean hasNoDomains(HtmlPage page){
		try{
			List<DomElement> divs = page.getElementsByTagName("div");
			for (DomElement div : divs){
				if (div.hasAttribute("class")){
					String classAttr = div.getAttribute("class");
					assertEquals("mensaje",classAttr);
					if (classAttr.equals("mensaje")){
						String text = div.asText();
						String msg = "No tiene dominios que se encuentren facturados, por lo cual, tampoco Documentos Tributarios Electronicos asociados";
						return text.contains(msg);
					}
				}
			}
			return false;
		} catch (Exception e){
			System.out.println("! Problems at DTE.hasNoDomains(HtmlPage page)");
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	private int getDomains(HtmlPage page){
		try{
			DomElement select = page.getElementById("filtroBusqueda");
			List<HtmlElement> options = select.getElementsByTagName("option");
			assertTrue(options.size()>0);
			System.out.println("User has "+options.size()+" domains to choose");
			return options.size();
		} catch (Exception e){
			assertTrue(false);
			System.out.println("! Can't find select for domains");
			e.printStackTrace();
			return -1;
		}
	}
	
	private HtmlPage selectDomain(HtmlPage page, int dom){
		try{
			System.out.print("Filter by domain ");
			if (dom <= this.getDomains(page)){
				DomElement select = page.getElementById("filtroBusqueda");
				List<HtmlElement> options = select.getElementsByTagName("option");
				HtmlElement domain = options.get(dom);
				System.out.println(domain.asText());
				HtmlPage refresh = domain.click();
				synchronized (refresh) {
		            refresh.wait(2000); //wait
		        }
				return refresh;
			} else {
				System.out.println("! There is no such domain to select");
				assertTrue(false);
				return null;
			}
		} catch (Exception e){
			System.out.println("! Problems at DTE.selectDomain(HtmlPage page, int dom)");
			e.printStackTrace();
			assertTrue(false);
			return null;
		}
	}
	
	private verifyRows(HtmlPage page){
		//TODO
		try{
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private verifyResults(HtmlPage page){
		//TODO
		try{
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void comprobantes(){
		try{
			HtmlPage page = this.goToDTE(this.login(0));
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
