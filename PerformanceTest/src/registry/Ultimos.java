package registry;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Ultimos {
	
	private HtmlPage openPage(String filter) throws Exception{
		try (final WebClient webClient = new WebClient()) {
			String url ="http://www.nic.cl/registry/Ultimos.do?t=1"+filter;
	        HtmlPage page = webClient.getPage(url);
	        assertTrue(page.isHtmlPage());
	        return page;
	    }
	}
	private Date parseDate(String stringDate){
		SimpleDateFormat formatter = new SimpleDateFormat(""); 
		Date date = null;
		try {
			date = formatter.parse(stringDate);
			assertTrue(true);
		} catch (ParseException e){
			assertTrue(false);
			e.printStackTrace();
		}
		return date;
	}
	
	/**Validar URLs**/
	@Test
	public void openHour() throws Exception{
		try (final WebClient webClient = new WebClient()) {
	        HtmlPage page = this.openPage("h");
	    }
	}
	
	@Test
	public void openDay() throws Exception{
		try (final WebClient webClient = new WebClient()) {
			HtmlPage page = this.openPage("d");
	    }
	}
	
	@Test
	public void openWeek() throws Exception{
		try (final WebClient webClient = new WebClient()) {
			HtmlPage page = this.openPage("w");
	    }
	}
	
	/**@Test
	 * Por alguna razon este test demora mucho**/
	public void openMonth() throws Exception{
		try (final WebClient webClient = new WebClient()) {
			HtmlPage page = this.openPage("m");
	    }
	}
	
	/**TODO
	 * validar resultados
	 * **/
	

}
