package registry;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

/*Test unitario para /registry/Eliminados.do*/
public class Eliminados {
	
	private HtmlPage openPage(String filter) throws Exception{
		try (final WebClient webClient = new WebClient()) {
			String url ="http://www.nic.cl/registry/Eliminados.do?t=1"+filter;
	        HtmlPage page = webClient.getPage(url);
	        assertTrue(page.isHtmlPage());
	        return page;
	    }
	}
	
	private void verifyRow(HtmlElement element){
		List<HtmlElement> divs = element.getElementsByTagName("div");
		assertTrue(!divs.isEmpty());
		
		String text = divs.get(0).asText();
		//System.out.println(text);
		assertTrue(text.endsWith(".cl"));
						
		List<HtmlElement> buttons = element.getElementsByTagName("button");
		assertTrue(!buttons.isEmpty());
		
		String url = "https://clientes.nic.cl/registrar/agregarDominio.do?d=";
		String href = buttons.get(0).getAttribute("onclick");
		assertTrue(href.contains(url));
	}
		
	private void verifyResults(HtmlPage page){
		try {
			final List<HtmlTable> table = page.getByXPath("//table[@class='tablabusqueda']");
			assertTrue(!table.isEmpty());
			List <HtmlTableRow> rows = table.get(0).getRows();
			for (int i = 1; i < rows.size() ; i++){
				verifyRow(rows.get(i));				
			}
			HtmlElement title = rows.get(0);
			String deletedSites = ""+(rows.size()-1);
			String[] count = title.asText().split(" ");
			assertTrue(count[0].equals(deletedSites));			
		} catch (Exception e){
			System.out.println(e); 
			assertTrue(false);
		}
	}
	
	/**Validar URL**/
	@Test
	public void lastDay() throws Exception{
		try (final WebClient webClient = new WebClient()) {
			HtmlPage page = this.openPage("d");
			this.verifyResults(page);
	    }
	}
	
	/**Validar URL**/
	@Test
	public void lastWeek() throws Exception{
		try (final WebClient webClient = new WebClient()) {
			HtmlPage page = this.openPage("w");
			this.verifyResults(page);
	    }
	}

}
