package registry;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;


public class BuscarDominio {

	/**Validar URL**/
	@Test
	public void openPage() throws Exception{
		try (final WebClient webClient = new WebClient()) {
	        webClient.getPage("http://www.nic.cl/whois");
	    }
	}
	
	/**TODO
	 * Hacer busqueda
	 * validar resultados
	 * **/

}
