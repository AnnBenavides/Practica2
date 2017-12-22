package registry;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;

public class Ultimos {

	/**Validar URLs**/
	@Test
	public void openHour() throws Exception{
		try (final WebClient webClient = new WebClient()) {
	        webClient.getPage("http://www.nic.cl/registry/Ultimos.do?t=1h");
	    }
	}
	
	@Test
	public void openDay() throws Exception{
		try (final WebClient webClient = new WebClient()) {
	        webClient.getPage("http://www.nic.cl/registry/Ultimos.do?t=1d");
	    }
	}
	
	@Test
	public void openWeek() throws Exception{
		try (final WebClient webClient = new WebClient()) {
	        webClient.getPage("http://www.nic.cl/registry/Ultimos.do?t=1w");
	    }
	}
	
	@Test
	public void openMonth() throws Exception{
		try (final WebClient webClient = new WebClient()) {
	        webClient.getPage("http://www.nic.cl/registry/Ultimos.do?t=1m");
	    }
	}
	
	/**TODO
	 * validar resultados
	 * **/
	

}
