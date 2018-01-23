package registry;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class Eliminados {
	/** Cargar la pagina de la forma
	 * "http://www.nic.cl/registry/Eliminados.do?t=1"+filter
	 * 
	 * @param filter	filtro publico: hora o dia
	 * @return 			contenido de página Eliminados.do
	 * **/
	private HtmlPage openPage(String filter) throws Exception{
		try (final WebClient webClient = new WebClient()) {
			String url ="http://www.nic.cl/registry/Eliminados.do?t=1"+filter;
			//System.out.println("Abriendo URL "+url);
	        HtmlPage page = webClient.getPage(url);
	        assertTrue(page.isHtmlPage());
	        //System.out.println("> Success");
	        return page;
	    }
	}
	
	/**Verifica que todos los elementos de la fila sean correctos
	 * 
	 * @param element	div de la fila
	 * 
	 * **/
	private void verifyRow(HtmlElement element){
		List<HtmlElement> divs = element.getElementsByTagName("div");
		assertTrue(!divs.isEmpty());
		
		String text = divs.get(0).asText();
		assertTrue(text.endsWith(".cl"));
		//System.out.println("\n\t | Dominio: "+text);
						
		List<HtmlElement> buttons = element.getElementsByTagName("button");
		assertTrue(!buttons.isEmpty());
		
		//System.out.print("\t | Botón de agregar dominio? ");
		String url = "https://clientes.nic.cl/registrar/agregarDominio.do?d=";
		String href = buttons.get(0).getAttribute("onclick");
		assertTrue(href.contains(url));
		//System.out.println(href.contains(url));
		
	}
	
	/** Verifica que orden y forma de todos los resultados
	 * 
	 * @param page	contenido de la página
	 * 
	 * @see	openPage(filter)
	 * **/
	private void verifyResults(HtmlPage page){
		try {
			//System.out.println("Cargado resultados...");
			final List<HtmlTable> table = page.getByXPath("//table[@class='tablabusqueda']");
			assertTrue(!table.isEmpty());
			List <HtmlTableRow> rows = table.get(0).getRows();
			for (int i = 1; i < rows.size() ; i++){
				verifyRow(rows.get(i));				
			}
			//System.out.println("> Mostrando "+rows.size()+" dominios eliminados");
			HtmlElement title = rows.get(0);
			String deletedSites = ""+(rows.size()-1);
			String[] count = title.asText().split(" ");
			assertTrue(count[0].equals(deletedSites));
			
		} catch (Exception e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	/**Test para Eliminados 
	 * del ultimo dia
	 * 
	 * @see		openPage(page)
	 * 			verifyResults(page)*/
	@Test
	public void lastDayTest() throws Exception{
		System.out.println("<< STARTING Eliminados.lastDay test");
		try (final WebClient webClient = new WebClient()) {
			HtmlPage page = this.openPage("d");
			this.verifyResults(page);
			System.out.println("> Correcto ");
	    }
	}
	
	/**Test para Eliminados 
	 * de la ultima semana
	 * 
	 * @see		openPage(page)
	 * 			verifyResults(page)*/
	@Test
	public void lastWeekTest() throws Exception{
		System.out.println("<< STARTING Eliminados.lastWeek test");
		try (final WebClient webClient = new WebClient()) {
			HtmlPage page = this.openPage("w");
			this.verifyResults(page);
			System.out.println("> Correcto ");
	    }
	}

}
