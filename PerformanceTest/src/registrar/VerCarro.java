package registrar;

import static org.junit.Assert.assertTrue;

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

public class VerCarro {
	/**TODO**/
	private HtmlPage login(int userNumber){
		HtmlPage page;
		System.out.println("\n\n\t Signing in ...");
		
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
		  if (pageLink.contains("listarDominio.do")){
		  	System.out.println("Success");
			assertTrue(true);
			return page;
		  } else if(pageLink.contains("agregarDominio.do")){
			System.out.println("Success, but user with no domains");
			assertTrue(true);
			return null;
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
	
	private HtmlPage goToCarro(HtmlPage page){
		try {
			System.out.print("Verify response of an empty 'Carro de Compra'... ");
			HtmlElement div = (HtmlElement) page.getElementById("menu_pago");
			HtmlElement a = div.getElementsByTagName("a").get(0);
			String href = a.getAttribute("href");
			boolean checkLink = href.contains("verCarro.do");
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

	private HtmlPage addToCarro(HtmlPage page){
		try {
			System.out.println("Adding products to 'Carro'");
			synchronized (page) {
	            page.wait(2000); //wait
	        }
			HtmlElement data = (HtmlElement) page.getElementById("listaDatos");
			List<HtmlElement> divs = data.getElementsByTagName("div");
			//System.out.println("Found "+divs.size()+" <div> elements");
			for (HtmlElement botones : divs){
				if(botones.hasAttribute("class")){
					String classAttr = botones.getAttribute("class");
					//System.out.println(classAttr);
					if (classAttr.equals("botones_dominio_mini")){
						HtmlElement a = botones.getElementsByTagName("a").get(0);
						HtmlPage refresh = a.click();
						synchronized (refresh) {
				            refresh.wait(2000); //wait
				        }
						System.out.println("\t Going to 'Ver Carro' with 1 product");
						assertTrue(true);
						return refresh;
					}
				}
			}
			assertTrue(false);
			System.out.println("There is no selectable domain");
			return null;
		} catch (Exception e){ 
			System.out.println("! Problems in VerCarro.addToCarro");
			e.printStackTrace();
			assertTrue(false);
			return page;
		}
	}
	
	private boolean hasNoProducts(HtmlPage page){
		try {
			List<DomElement> divs = page.getElementsByTagName("div");
			for (DomElement div : divs){
				if(div.hasAttribute("class")){
					String classAttr = div.getAttribute("class");
					//System.out.println(classAttr);
					if (classAttr.equals("mensaje")){
						String text = div.asText();
						if (text.contains("El carro de compras se encuentra vacío.")){
							assertTrue(true);
							return true;
						}
					}
				}
			}
			return false;
		} catch (Exception e){ 
			System.out.println("! Problems in VerCarro.hasNoProducts");
			e.printStackTrace();
			assertTrue(false);
			return true;
		}
	}
	
	private void verifyColumns(HtmlPage page){
		try {
			//TODO
		} catch (Exception e){ 
			System.out.println("! Problems in VerCarro.verify columns");
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	private void verifyProducts(HtmlPage page){
		try {
			//TODO
		} catch (Exception e){ 
			System.out.println("! Problems in VerCarro.verifyProducts");
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	private void verifyTotalesYMoneda(HtmlPage page){
		try {
			//TODO
		} catch (Exception e){ 
			System.out.println("! Problems in VerCarro.verifyTotalesYMoneda");
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	private void selectMediodePago(HtmlPage page){
		try {
			//TODO
		} catch (Exception e){ 
			System.out.println("! Problems in VerCarro.selectMediodePago");
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	private void verifyMediosdePago(HtmlPage page){
		try {
			//TODO
		} catch (Exception e){ 
			System.out.println("! Problems in VerCarro.verifyMediosdePago");
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	private void verifyCompra(HtmlPage page){
		try {
			//TODO
		} catch (Exception e){ 
			System.out.println("! Problems in VerCarro.verifyCompra");
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	private void verify(HtmlPage page){
		try {
			//TODO
			if (!this.hasNoProducts(page)){
				//TODO
			}else{
				System.out.println("There are no products");
				assertTrue(false);
			}
		} catch (Exception e){ 
			System.out.println("! Problems in VerCarro.verify");
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void compra(){
		System.out.println("<< STARTING VerCarro.compra test");
		try{
			int users = new UserAndPass().numberOfAccounts();
			for (int user = 0 ; user < users ; user++){
				HtmlPage loginPage = this.login(0);
				if (loginPage!= null){
					HtmlPage refresh = this.addToCarro(loginPage);
					synchronized (refresh) {
			            refresh.wait(2000); //wait
			        }
					this.verify(refresh);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		System.out.println("VerCarro.compra FINISHED >>");
	}
	
	@Test
	public void carroVacio(){
		System.out.println("<< STARTING VerCarro.carroVacio test");
		try{
			int users = new UserAndPass().numberOfAccounts();
			for (int user = 0 ; user < users ; user++){
				HtmlPage loginPage = this.login(0);
				if (loginPage!= null){
					assertTrue(this.hasNoProducts(this.goToCarro(loginPage)));
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		System.out.println("VerCarro.carroVacio FINISHED >>");
	}
}
