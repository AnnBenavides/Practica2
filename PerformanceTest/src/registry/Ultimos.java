package registry;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

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
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss"); 
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
	
	private Date verifyRow(HtmlElement element){
		List<HtmlElement> divs = element.getElementsByTagName("div");
		assertTrue(!divs.isEmpty());
		
		String dom = divs.get(0).asText();
		assertTrue(dom.endsWith(".cl"));
		
		List<HtmlElement> a = divs.get(0).getElementsByTagName("a");
		assertTrue(!a.isEmpty());
		
		String url = "Whois.do?d=";
		String href = a.get(0).getAttribute("href");
		assertTrue(href.contains(url) && href.endsWith(".cl"));
		
		
		String strDate = divs.get(1).asText();
		assertTrue(!strDate.isEmpty());
		
		Date date = this.parseDate(strDate);
		assertTrue(date.before(new Date()));
		
		return date;
		
	}
	
	private void verifyResults(HtmlPage page){
		try {
			final List<HtmlTable> table = page.getByXPath("//table[@class='tablabusqueda']");
			assertTrue(!table.isEmpty());
			List <HtmlTableRow> rows = table.get(0).getRows();
			Date thisDate = new Date();
			Date lastDate = new Date();
			for (int i = 1; i < rows.size() ; i++){
				thisDate = verifyRow(rows.get(i));	
				int diff = thisDate.compareTo(lastDate);
				assertTrue(diff <= 0);
				lastDate = thisDate;
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
	@Test
	public void openHour() throws Exception{
		try (final WebClient webClient = new WebClient()) {
	        HtmlPage page = this.openPage("h");
	        verifyResults(page);
	    }
	}
	
	@Test
	public void openDay() throws Exception{
		try (final WebClient webClient = new WebClient()) {
			HtmlPage page = this.openPage("d");
			verifyResults(page);
	    }
	}
	
	@Test
	public void openWeek() throws Exception{
		try (final WebClient webClient = new WebClient()) {
			HtmlPage page = this.openPage("w");
			verifyResults(page);
	    }
	}
	
	@Test
	 /** Por alguna razon este test demora mucho**/
	public void openMonth() throws Exception{
		try (final WebClient webClient = new WebClient()) {
			HtmlPage page = this.openPage("m");
			verifyResults(page);
	    }
	}
	
	

}
