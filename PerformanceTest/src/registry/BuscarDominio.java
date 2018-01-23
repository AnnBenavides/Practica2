package registry;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;


public class BuscarDominio {
	private DomainsFile file = new DomainsFile();
	private String[] filterValues = {"exacta",
			"contenga","comience","termine"};
	
	/**Metodo generalizado de testeo
	 * 
	 * @param testCase		palabra a buscar
	 * @param methodValue	filtro de busqueda, dados
	 * 						por la lista filterValues
	 * 
	 * @return				pagina luego de la busqueda
	 * */
	private HtmlPage submitForm(String testCase,String methodValue) throws Exception{
		//System.out.println("Abriendo URL https://nic.cl/registry/BuscarDominio.do");
		try (final WebClient webClient = new WebClient()) {
			final HtmlPage page = webClient.getPage("https://nic.cl/registry/BuscarDominio.do");
	        final HtmlForm form = page.getFormByName("buscarDominioForm");
	        
	        final HtmlSubmitInput button = form.getInputByName("buscar");
	        final HtmlTextInput textField = form.getInputByName("patron");
	        final HtmlRadioButtonInput radioInput = form.getInputByValue(methodValue);
			
	        radioInput.setChecked(true);
	        textField.setValueAttribute(testCase);
	        //System.out.println("Success");
	        return button.click();
		}
	}
	
	/**Respuesta a un dominio no inscrito
	 * 
	 * Aparece un boton para inscribir el dominio
	 * redireccionando a /registrar/agregarDominio.do?...
	 * con una llave al final
	 * 
	 * @param page	contenido de la pagina luego de la busqueda
	 * **/
	private void verifyDomNotFound(HtmlPage page){
		//System.out.print("Opción de inscripción? ");
		final DomElement button = page.getElementById("submitButton");
		String btn = button.getTextContent();
		if (btn.contains("Inscribir")){
			String href = button.getAttribute("onclick");
			String nextUrl = "https://clientes.nic.cl/registrar/agregarDominio.do?";
			assertTrue(href.contains(nextUrl));
			//System.out.println(href);
		} else { 
			assertTrue(false);
			//System.out.println("No");
		}
	}
	
	/** Verifica por contenido
	 * si se ha encontrado el dominio o no
	 * 
	 * @param page	contenido de la pagina luego de la busqueda
	 * @return		true si no hay resultados, false los hay
	 * **/
	private boolean domNotFound(HtmlPage page){
		final List<HtmlElement> inTable= page.getByXPath("//table[@class='tablabusqueda']/tbody/tr/td[1]");
		final HtmlElement elem = inTable.get(0);
		String response = elem.asText();
		if (response.contains("no arrojó resultados")){
			System.out.println("No hay dominios resultantes de la busqueda");
			return true;
		} else {
			return false;
		}
	}
	
//////////////// TEST con filtro "exacta" ////////////////////
	/**Metodo para verificar el resultado de una busqueda
	 * con filtro 'exacta'
	 * 
	 * @param page	pagina generada post submit
	 * @param word	palabra buscada
	 * **/
	private void verifyExactaResults(HtmlPage page, String word){
		//System.out.println("> Verificando datos de busqueda para "+word+" con criterio 'exacta'");
		if (!domNotFound(page)){
			//verificar dato mostrado con word.cl
			final List<HtmlElement> results= page.getByXPath("//table[@class='tablabusqueda']/tbody/tr[2]/td[1]");
			final HtmlElement row = results.get(0);
			String rowText = row.asText();
			String[] columns = rowText.split("\n"); //[0]=dom, [1]=owner
			String dom = word+".cl";
			assertTrue(dom.equals(columns[0]));
			assertTrue(!columns[1].isEmpty());
			//System.out.println("\n\t | Dominio: "+columns[0]);
			//System.out.println("\t | Dueño: "+columns[1]);
			HtmlElement ahref = row.getElementsByTagName("a").get(0);
			String href = "Whois.do?d="+columns[0];
			boolean hasLink = ahref.getAttribute("href").contains(href);
			//assertTrue(hasLink);
			//System.out.println("\t | Redirección a "+ahref.getAttribute("href"));			
		} else {
			// dominio no encontrado
			verifyDomNotFound(page);
		}
	}
	
	@Test
	public void exactaSimpleTest(){
		System.out.println("<< STARTING BuscarDominio.exactaSimple test");
		HtmlPage page;
		for (String word : file.getSimple()){
			try {
				page = submitForm(word,filterValues[0]);
				//System.out.println("Verificando contenido...");
				verifyExactaResults(page, word);
			} catch (Exception e) {
				assertTrue(false);
				e.printStackTrace();
			}
		}		
	}
	
	@Test
	public void exactaNumberTest(){
		System.out.println("<< STARTING BuscarDominio.exactaNumber test");
		HtmlPage page;
		for (String word : file.getNumber()){
			try {
				page = submitForm(word,filterValues[0]);
				//System.out.println("Verificando contenido...");
				verifyExactaResults(page, word);
			} catch (Exception e) {
				assertTrue(false);
				e.printStackTrace();
			}
		}		
	}
	// TODO convertir caracteres especiales @Test
	public void exactaSpecialTest(){
		System.out.println("<< STARTING BuscarDominio.exactaSpecial test");
		HtmlPage page;
		for (String word : file.getSpecial()){
			try {
				page = submitForm(word,filterValues[0]);
				//System.out.println("Verificando contenido...");
				verifyExactaResults(page, word);
			} catch (Exception e) {
				assertTrue(false);
				e.printStackTrace();
			}
		}		
	}
		
	////////////////TEST con filtro "contenga" ////////////////////
	/**Metodo para verificar el resultado de una busqueda
	 * con filtro 'contenga'
	 * 
	 * @param page	pagina generada post submit
	 * @param word	palabra buscada
	 * **/
	private void verifyContengaResults(HtmlPage page, String word){
		System.out.println("> Verificando datos de busqueda para "+word+" con criterio 'contenga'");
		if (!domNotFound(page)){
			//verificar dato mostrado con word.cl
			final HtmlTable table = (HtmlTable) page.getByXPath("//table[@class='tablabusqueda']").get(0);
			final List<HtmlTableRow> rows = table.getRows();
			for (int i = 1 ; i< table.getRowCount()-1 ; i++){
				HtmlElement row = rows.get(i);
				String rowText = row.asText();
				//System.out.println(rowText);
				String[] columns = rowText.split("\n"); //[0]=dom, [1]=owner
				assertTrue(columns[0].contains(word));
				assertTrue(!columns[1].isEmpty());
				//System.out.println("\n\t | Dominio: "+columns[0]);
				//System.out.println("\t | Dueño: "+columns[1]);
				HtmlElement ahref = row.getElementsByTagName("a").get(0);
				String href = "Whois.do?d="+columns[0];
				boolean hasLink = ahref.getAttribute("href").contains(href);
				//assertTrue(hasLink);
				//System.out.println("\t | Redirección a "+ahref.getAttribute("href"));
			}
		} else {
		// dominio no encontrado
		verifyDomNotFound(page);
		}
	}
	
	/***/
	@Test
	public void contengaSimpleTest(){
		System.out.println("<< STARTING BuscarDominio.contengaSimple test");
		HtmlPage page;
		for (String word : file.getSimple()){
			try {
				page = submitForm(word,filterValues[1]);
				verifyContengaResults(page, word);
			} catch (Exception e) {
				assertTrue(false);
				e.printStackTrace();
			}
		}		
	}

	@Test
	public void contegaNumberTest(){
		System.out.println("<< STARTING BuscarDominio.contengaNumber test");
		HtmlPage page;
		for (String word : file.getNumber()){
			try {
				page = submitForm(word,filterValues[1]);
				verifyContengaResults(page, word);
			} catch (Exception e) {
				assertTrue(false);
				e.printStackTrace();
			}
		}	
	}
	
	// TODO convertir caracteres especiales @Test
	public void contengaSpecialTest(){
		System.out.println("<< STARTING BuscarDominio.contengaSpecial test");
		HtmlPage page;
		for (String word : file.getSpecial()){
			try {
				page = submitForm(word,filterValues[1]);
				verifyContengaResults(page, word);
			} catch (Exception e) {
				assertTrue(false);
				e.printStackTrace();
			}
		}	
	}
	
	////////////////TEST con filtro "comience" ////////////////////
	/**Metodo para verificar el resultado de una busqueda
	 * con filtro 'comience'
	 * 
	 * @param page	pagina generada post submit
	 * @param word	palabra buscada
	 * **/
	private void verifyComienceResults(HtmlPage page, String word){
		if (!domNotFound(page)){
			System.out.println("> Verificando datos de busqueda para "+word+" con criterio 'comience'");
			//verificar dato mostrado con word.cl
			final HtmlTable table = (HtmlTable) page.getByXPath("//table[@class='tablabusqueda']").get(0);
			final List<HtmlTableRow> rows = table.getRows();
			for (int i = 1 ; i< table.getRowCount()-1 ; i++){
				HtmlElement row = rows.get(i);
				String rowText = row.asText();
				//System.out.println(rowText);
				String[] columns = rowText.split("\n"); //[0]=dom, [1]=owner
				assertTrue(columns[0].startsWith(word));
				assertTrue(!columns[1].isEmpty());
				System.out.println("\n\t | Dominio: "+columns[0]);
				System.out.println("\t | Dueño: "+columns[1]);
				HtmlElement ahref = row.getElementsByTagName("a").get(0);
				String href = "Whois.do?d="+columns[0];
				boolean hasLink = ahref.getAttribute("href").contains(href);
				//assertTrue(hasLink);
				System.out.println("\t | Redirección a "+ahref.getAttribute("href"));
			}
		} else {
			// dominio no encontrado
			verifyDomNotFound(page);
		}
	}
	
	@Test
	public void comienceSimpleTest(){
		System.out.println("<< STARTING BuscarDominio.comienceSimple test");
		HtmlPage page;
		for (String word : file.getSimple()){
			try {
				page = submitForm(word,filterValues[2]);
				verifyComienceResults(page, word);
			} catch (Exception e) {
				assertTrue(false);
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void comienceNumberTest(){
		System.out.println("<< STARTING BuscarDominio.comienceNumber test");
		HtmlPage page;
		for (String word : file.getNumber()){
			try {
				page = submitForm(word, filterValues[2]);
				verifyComienceResults(page, word);
			} catch (Exception e) {
				assertTrue(false);
				e.printStackTrace();
			}
		}
	}
	
	// TODO convertir caracteres especiales @Test
	public void comienceSpecialTest(){
		System.out.println("<< STARTING BuscarDominio.comienceSpecial test");
		HtmlPage page;
		for (String word: file.getSpecial()){
			try {
				page = submitForm(word,filterValues[2]);
				verifyComienceResults(page, word);
			} catch (Exception e) {
				assertTrue(false);
				e.printStackTrace();
			}
		}
	}
	
	////////////////TEST con filtro "termine" ////////////////////
	/**Metodo para verificar el resultado de una busqueda
	 * con filtro 'termine'
	 * 
	 * @param page	pagina generada post submit
	 * @param word	palabra buscada
	 * **/
	private void verifyTermineResults(HtmlPage page, String word){
		if (!domNotFound(page)){
			//verificar dato mostrado con word.cl
			System.out.println("> Verificando datos de busqueda para "+word+" con criterio 'termine'");
			final HtmlTable table = (HtmlTable) page.getByXPath("//table[@class='tablabusqueda']").get(0);
			final List<HtmlTableRow> rows = table.getRows();
			for (int i = 1 ; i< table.getRowCount()-1 ; i++){
				HtmlElement row = rows.get(i);
				String rowText = row.asText();
				//System.out.println(rowText);
				String[] columns = rowText.split("\n"); //[0]=dom, [1]=owner
				assertTrue(columns[0].endsWith(word+".cl"));
				assertTrue(!columns[1].isEmpty());
				System.out.println("\n\t | Dominio: "+columns[0]);
				System.out.println("\t | Dueño: "+columns[1]);
				HtmlElement ahref = row.getElementsByTagName("a").get(0);
				String href = "Whois.do?d="+columns[0];
				boolean hasLink = ahref.getAttribute("href").contains(href);
				//assertTrue(hasLink);
				System.out.println("\t | Redirección a "+ahref.getAttribute("href"));
			}
		} else {
			// dominio no encontrado
			verifyDomNotFound(page);
		}
	}
	
	@Test
	public void termineSimpleTest(){
		System.out.println("<< STARTING BuscarDominio.termineSimple test");
		HtmlPage page;
		for (String word: file.getSimple()){
			try {
				page = submitForm(word,filterValues[3]);
				verifyTermineResults(page, word);
			} catch (Exception e) {
				assertTrue(false);
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void termineNumberTest(){
		System.out.println("<< STARTING BuscarDominio.termineNumber test");
		HtmlPage page;
		for (String word : file.getNumber()){
			try {
				page = submitForm(word,filterValues[3]);
				verifyTermineResults(page, word);
			} catch (Exception e) {
				assertTrue(false);
				e.printStackTrace();
			}
		}
	}
	
	// TODO convertir caracteres especiales @Test
	public void termineSpecialTest(){
		System.out.println("<< STARTING BuscarDominio.termineSpecial test");
		HtmlPage page;
		for (String word : file.getSpecial()){
			try {
				page = submitForm(word,filterValues[3]);
				verifyTermineResults(page, word);
			} catch (Exception e) {
				assertTrue(false);
				e.printStackTrace();
			}
		}
	}
}