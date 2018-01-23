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
	
	/** Cargar la pagina de la forma
	 * "http://www.nic.cl/registry/Ultimos.do?t=1"+filter
	 * 
	 * @param filter	filtro: hora, dia, semana o mes
	 * @return 			página Ultimos.do
	 * **/
	private HtmlPage openPage(String filter) throws Exception{
		try (final WebClient webClient = new WebClient()) {
			String url ="http://www.nic.cl/registry/Ultimos.do?t=1"+filter;
			//System.out.println("Abriendo URL "+url);
	        HtmlPage page = webClient.getPage(url);
	        assertTrue(page.isHtmlPage());
	        return page;
	    }
	}
	
	/**Parsea un String para verificar que
	 * su formato sea de fecha
	 * 	YYYY-MM-DD hh:mm:ss.s
	 * 
	 * @param stringDate	palabra que debería tenr el formato
	 * @return 				palabra como objeto Date
	 * **/
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
	
	/**Verifica que todos los elementos de la fila sean correctos
	 * 
	 * @param element	div de la fila
	 * @return			fecha para posterior comparacion
	 * 
	 * @see 	parseDate(stringDate)
	 * 			verifyResults(page)
	 * **/
	private Date verifyRow(HtmlElement element){
		List<HtmlElement> divs = element.getElementsByTagName("div");
		assertTrue(!divs.isEmpty());
		
		String dom = divs.get(0).asText();
		assertTrue(dom.endsWith(".cl"));
		//System.out.println("\n\t | "+dom);
		
		List<HtmlElement> a = divs.get(0).getElementsByTagName("a");
		assertTrue(!a.isEmpty());
		
		String url = "Whois.do?d=";
		String href = a.get(0).getAttribute("href");
		assertTrue(href.contains(url) && href.endsWith(".cl"));
		//System.out.println("\t | Redirecciona a "+href);
		
		
		String strDate = divs.get(1).asText();
		assertTrue(!strDate.isEmpty());
		//System.out.println("\t | Fecha de inscripción: "+strDate);
		
		Date date = this.parseDate(strDate);
		assertTrue(date.before(new Date()));
		
		return date;
		
	}
	
	/** Verifica que orden y forma de todos los resultados
	 * para los tres filtros publicos:
	 * 	hora, dia, semana y mes
	 * 
	 * @param page	contenido de la página
	 * 
	 * @see	openPage(filter)
	 * **/
	private void verifyResults(HtmlPage page){
		try {
			//System.out.println("Verificando resultados ... ");
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
			//System.out.println("> Mostrando "+rows.size()+" resultados");
		} catch (Exception e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	/**Test que carga los Ultimos sitios
	 * en la última hora
	 * 
	 * @see	verifyResults(page)
	 * */
	@Test
	public void hourTest() throws Exception{
		System.out.println("<< STARTING Ultimos.hour test");
		try (final WebClient webClient = new WebClient()) {
	        HtmlPage page = this.openPage("h");
	        System.out.println("> Success");
	        verifyResults(page);
	    }
	}
	
	/**Test que carga los Ultimos sitios
	 * en el último día
	 * 
	 * @see	verifyResults(page)
	 * */
	@Test
	public void dayTest() throws Exception{
		System.out.println("<< STARTING Ultimos.day test");
		try (final WebClient webClient = new WebClient()) {
			HtmlPage page = this.openPage("d");
			System.out.println("> Success");
			verifyResults(page);
	    }
	}
	
	/**Test que carga los Ultimos sitios
	 * en la última semana
	 * 
	 * @see	verifyResults(page)
	 * */
	@Test
	public void weekTest() throws Exception{
		System.out.println("<< STARTING Ultimos.week test");
		try (final WebClient webClient = new WebClient()) {
			HtmlPage page = this.openPage("w");
			System.out.println("> Success");
			verifyResults(page);
	    }
	}
	
	/**Test que carga los Ultimos sitios
	 * en el último día
	 * 
	 * TODO IMPORTANTE, este test 'demora mucho'
	 * 
	 * @see	verifyResults(page)
	 * */
	//@Test
	public void monthTest() throws Exception{
		System.out.println("<< STARTING Ultimos.month test");
		try (final WebClient webClient = new WebClient()) {
			HtmlPage page = this.openPage("m");
			System.out.println("> Success");
			verifyResults(page);
	    }
	}
	
	

}
