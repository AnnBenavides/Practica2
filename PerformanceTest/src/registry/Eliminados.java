package registry;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

/*Test unitario para /registry/Eliminados.do*/
public class Eliminados {
	/**Validar URL**/
	@Test
	public void openLastDay() throws Exception{
		try (final WebClient webClient = new WebClient()) {
	        final HtmlPage page = webClient.getPage("http://www.nic.cl/registry/Eliminados.do?t=1d");
	        assertEquals("NIC Chile", page.getTitleText());
	    }
	}
	
	/**Verificar el contenido**/
	@Test
	public void lastDay() throws Exception{
		try (final WebClient webClient = new WebClient()) {
	        final HtmlPage page = webClient.getPage("http://www.nic.cl/registry/Eliminados.do?t=1d");
	        final List<HtmlTable> table= page.getByXPath("//table[@class='tablabusqueda']");
	        assertFalse(table.isEmpty()); // existe tabla de eliminados class="tablabusqueda"
	        HtmlTable theTable = table.get(0);
	        List<HtmlTableRow> rows = theTable.getRows();
	        int numberOfRows = rows.size();
	        assertTrue(numberOfRows > 1); // contiene al menos un elemento
	    }
	}
	
	/**Validar URL**/
	@Test
	public void openlastWeek() throws Exception{
		try (final WebClient webClient = new WebClient()) {
	        final HtmlPage page = webClient.getPage("http://www.nic.cl/registry/Eliminados.do?t=1w");
	        assertEquals("NIC Chile", page.getTitleText());
	    }
	}

}
