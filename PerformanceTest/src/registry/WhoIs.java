package registry;

import java.util.List;

import static org.junit.Assert.*;

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

public class WhoIs {
	private String[] testSimpleSites = {"test","horrible","defensa","emision",
			"antigua","farmacia","azucar","abajo","bolso",
			"cama","casa","vendedor","raton","fruta",
			"triangulo","flor","bota","ruta","jardin"};
	private int simpleLimit = 1;
	private String[] testNumberSites = {"1","2","34","5","01"};
	private int numberLimit = 1;
	private String[] testSpecialSites = {"ñandú", "jardín", 
			"corazón", "sillón", "rápido", "teléfono"};
	private int specialLimit = 1;
	private String[] filterValues = {"exacta",
			"contenga","comience","termine"};
	
	/**Metodo generalizado de testeo
	 * 
	 * testCase es la palabra a buscar,
	 * sacada de las listas :testSimpleSites, testNumberSites y testSpecialSites
	 * 
	 * methodValue es el filtro de busqueda, 
	 * dados por la lista filterValues
	 * 
	 * retorna la pagina luego de la busqueda**/
	private HtmlPage submitForm(String testCase,String methodValue) throws Exception{
		try (final WebClient webClient = new WebClient()) {
			final HtmlPage page = webClient.getPage("http://www.nic.cl/registry/Whois.do");
	        final HtmlForm form = page.getFormByName("buscarDominioForm");
	        
	        final HtmlSubmitInput button = form.getInputByName("buscar");
	        final HtmlTextInput textField = form.getInputByName("patron");
	        final HtmlRadioButtonInput radioInput = form.getInputByValue(methodValue);
			
	        radioInput.setChecked(true);
	        textField.setValueAttribute(testCase);
	        return button.click();
		}
	}
	
	/**Respuesta a un dominio no inscrito
	 * 
	 * Aparece un boton para inscribir el dominio
	 * redireccionando a /registrar/agregarDominio.do?...
	 * con una llave al final
	 * **/
	private void verifyDomNotFound(HtmlPage page){
		final DomElement button = page.getElementById("submitButton");
		String btn = button.getTextContent();
		if (btn.contains("Inscribir")){
			String href = button.getAttribute("onclick");
			String nextUrl = "https://clientes.nic.cl/registrar/agregarDominio.do?";
			assertTrue(href.contains(nextUrl));
		} else { 
			assertTrue(false);
		}
	}
	
	/** Verifica por contenido
	 * si se ha encontrado el dominio o no
	 * **/
	private boolean domNotFound(HtmlPage page){
		final List<HtmlElement> inTable= page.getByXPath("//table[@class='tablabusqueda']/tbody/tr/td[1]");
		final HtmlElement elem = inTable.get(0);
		String response = elem.asText();
		if (response.contains("no arrojó resultados")){
			return true;
		} else {return false;}
	}
	
//////////////// TEST con filtro "exacta" ////////////////////
	/**Metodo para verificar el resultado de una busqueda
	 * 
	 * page es la pagina generada post submit
	 * 
	 * word es la palabra buscada
	 * **/
	private void verifyExactaResults(HtmlPage page, String word){
		if (!domNotFound(page)){
			//verificar dato mostrado con word.cl
			final List<HtmlElement> results= page.getByXPath("//table[@class='tablabusqueda']/tbody/tr[2]/td[1]");
			final HtmlElement row = results.get(0);
			String rowText = row.asText();
			String[] columns = rowText.split("\n"); //[0]=dom, [1]=owner
			String dom = word+".cl";
			boolean matches = dom.startsWith(columns[0]);
			
			final HtmlElement link =  row.getElementsByTagName("a").get(0);
			final String href = link.getAttribute("href");
			//System.out.println(href);
			String url = "Whois.do?=";
			if (matches && !columns[1].isEmpty()){
				assertTrue(true);
			} else {
				assertTrue(false);
			}		
		} else {
			// dominio no encontrado
			verifyDomNotFound(page);
		}
	}
	
	@Test
	public void exactaSimpleTest(){
		HtmlPage page;
		for (int i = 0 ; i < simpleLimit; i++){
			try {
				page = submitForm(testSimpleSites[i],filterValues[0]);
				//System.out.println("Verificando contenido...");
				verifyExactaResults(page, testSimpleSites[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}
	
	@Test
	public void exactaNumberTest(){
		HtmlPage page;
		for (int i = 0 ; i < numberLimit; i++){
			try {
				page = submitForm(testNumberSites[i],filterValues[0]);
				//System.out.println("Verificando contenido...");
				verifyExactaResults(page, testNumberSites[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}
	@Test
	public void exactaSpecialTest(){
		HtmlPage page;
		for (int i = 0 ; i < specialLimit; i++){
			try {
				page = submitForm(testSpecialSites[i],filterValues[0]);
				//System.out.println("Verificando contenido...");
				verifyExactaResults(page, testSpecialSites[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}
		
	////////////////TEST con filtro "contenga" ////////////////////
	/**Metodo para verificar el resultado de una busqueda
	* 
	* page es la pagina generada post submit
	* 
	* word es la palabra buscada
	* **/
	private void verifyContengaResults(HtmlPage page, String word){
		if (!domNotFound(page)){
			//verificar dato mostrado con word.cl
			final HtmlTable table = (HtmlTable) page.getByXPath("//table[@class='tablabusqueda']").get(0);
			final List<HtmlTableRow> rows = table.getRows();
			for (int i = 1 ; i< table.getRowCount()-1 ; i++){
				HtmlElement row = rows.get(i);
				String rowText = row.asText();
				//System.out.println(rowText);
				String[] columns = rowText.split("\n"); //[0]=dom, [1]=owner
				boolean matches = columns[0].contains(word);
				
				final HtmlElement link =  row.getElementsByTagName("a").get(0);
				final String href = link.getAttribute("href");
				//System.out.println(href);
				
				String url = "Whois.do?="+columns[0];
				if (matches && !columns[1].isEmpty()){
					assertTrue(true);
				} else {
					assertTrue(false);
				}
			}
		} else {
		// dominio no encontrado
		verifyDomNotFound(page);
		}
	}
	
	@Test
	public void contengaSimpleTest(){
		HtmlPage page;
		for (int i = 0 ; i < simpleLimit; i++){
			try {
				page = submitForm(testSimpleSites[i],filterValues[1]);
				verifyContengaResults(page, testSimpleSites[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}

	@Test
	public void contegaNumberTest(){
		HtmlPage page;
		for (int i = 0 ; i < numberLimit; i++){
			try {
				page = submitForm(testNumberSites[i],filterValues[1]);
				verifyContengaResults(page, testNumberSites[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}
	
	@Test
	public void contengaSpecialTest(){
		HtmlPage page;
		for (int i = 0 ; i < specialLimit; i++){
			try {
				page = submitForm(testSpecialSites[i],filterValues[1]);
				verifyContengaResults(page, testSpecialSites[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}
	
	////////////////TEST con filtro "comience" ////////////////////
	/**Metodo para verificar el resultado de una busqueda
	* 
	* page es la pagina generada post submit
	* 
	* word es la palabra buscada
	* **/
	private void verifyComienceResults(HtmlPage page, String word){
		if (!domNotFound(page)){
			//verificar dato mostrado con word.cl
			final HtmlTable table = (HtmlTable) page.getByXPath("//table[@class='tablabusqueda']").get(0);
			final List<HtmlTableRow> rows = table.getRows();
			for (int i = 1 ; i< table.getRowCount()-1 ; i++){
				HtmlElement row = rows.get(i);
				String rowText = row.asText();
				//System.out.println(rowText);
				String[] columns = rowText.split("\n"); //[0]=dom, [1]=owner
				boolean matches = columns[0].startsWith(word);
				
				final HtmlElement link =  row.getElementsByTagName("a").get(0);
				final String href = link.getAttribute("href");
				//System.out.println(href);
				String url = "Whois.do?=";
				boolean sameLink = href.contains(url);

				if (matches && !columns[1].isEmpty()){
					assertTrue(true);
				} else {
					assertTrue(false);
				}
			}
		} else {
			// dominio no encontrado
			verifyDomNotFound(page);
		}
	}
	
	@Test
	public void comienceSimpleTest(){
		HtmlPage page;
		for (int i = 0 ; i < simpleLimit; i++){
			try {
				page = submitForm(testSimpleSites[i],filterValues[2]);
				verifyComienceResults(page, testSimpleSites[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void comienceNumberTest(){
		HtmlPage page;
		for (int i = 0 ; i < numberLimit; i++){
			try {
				page = submitForm(testNumberSites[i],filterValues[2]);
				verifyComienceResults(page, testNumberSites[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void comienceSpecialTest(){
		HtmlPage page;
		for (int i = 0 ; i < specialLimit; i++){
			try {
				page = submitForm(testSpecialSites[i],filterValues[2]);
				verifyComienceResults(page, testSpecialSites[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	////////////////TEST con filtro "termine" ////////////////////
	/**Metodo para verificar el resultado de una busqueda
	* 
	* page es la pagina generada post submit
	* 
	* word es la palabra buscada
	* **/
	private void verifyTermineResults(HtmlPage page, String word){
		if (!domNotFound(page)){
			//verificar dato mostrado con word.cl
			final HtmlTable table = (HtmlTable) page.getByXPath("//table[@class='tablabusqueda']").get(0);
			final List<HtmlTableRow> rows = table.getRows();
			for (int i = 1 ; i< table.getRowCount()-1 ; i++){
				HtmlElement row = rows.get(i);
				String rowText = row.asText();
				//System.out.println(rowText);
				String[] columns = rowText.split("\n"); //[0]=dom, [1]=owner
				boolean matches = columns[0].endsWith(word+".cl");
				
				final HtmlElement link =  row.getElementsByTagName("a").get(0);
				final String href = link.getAttribute("href");
				//System.out.println(href);
				String url = "Whois.do?=";
				boolean sameLink = href.contains(url);

				if (matches && !columns[1].isEmpty()){
					assertTrue(true);
				} else {
					assertTrue(false);
				}
			}
		} else {
			// dominio no encontrado
			verifyDomNotFound(page);
		}
	}
	
	@Test
	public void termineSimpleTest(){
		HtmlPage page;
		for (int i = 0 ; i < simpleLimit; i++){
			try {
				page = submitForm(testSimpleSites[i],filterValues[3]);
				verifyTermineResults(page, testSimpleSites[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void termineNumberTest(){
		HtmlPage page;
		for (int i = 0 ; i < numberLimit; i++){
			try {
				page = submitForm(testNumberSites[i],filterValues[3]);
				verifyTermineResults(page, testNumberSites[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void termineSpecialTest(){
		HtmlPage page;
		for (int i = 0 ; i < specialLimit; i++){
			try {
				page = submitForm(testSpecialSites[i],filterValues[3]);
				verifyTermineResults(page, testSpecialSites[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}