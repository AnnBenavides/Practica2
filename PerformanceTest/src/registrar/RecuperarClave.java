package registrar;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class RecuperarClave {
	private void findRecoveryLink(){
		//System.out.print("Searching for 'Olvidó su ocntraseña?' link ... ");
		try (final WebClient webClient = new WebClient()) {
			String url ="https://clientes.nic.cl/registrar/logon.do";
	        HtmlPage page = webClient.getPage(url);
	        assertTrue(page.isHtmlPage());
	        
	        String recoveryLink = "registrar/recuperaPassword.do";
	        List<DomElement> ahrefs = page.getElementsByTagName("a");
	        for (DomElement ahref : ahrefs){
	        	String href = ahref.getAttribute("href");
	        	if (href.contains(recoveryLink)){
	        		System.out.println(" o Link found ");
	        		assertTrue(true);
	        		return; 
	        	}
	        }
	        //System.out.println(" x Link not found");
	        assertTrue(false);
		} catch (Exception e){
			e.printStackTrace();
		}
		//System.out.println("Opening /recuperarPassword.do");
		this.checkRecoverPass();
	}
	
	private void checkRecoverPass(){// TODO
		//System.out.println("Recuperando contraseña... ");
		try (final WebClient webClient = new WebClient()) {
			String url ="https://clientes.nic.cl/registrar/recuperaPassword.do";
	        HtmlPage page = webClient.getPage(url);
	        assertTrue(page.isHtmlPage());
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void isRecoveryReachable(){
		this.findRecoveryLink();
	}
}
