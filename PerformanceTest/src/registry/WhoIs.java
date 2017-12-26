package registry;

import java.io.IOException;
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
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class WhoIs {
	private String[] testSimpleSites = {"horrible","defensa","emision",
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
	private void verifyExactaResults(HtmlPage page, String word){// TODO
		if (!domNotFound(page)){
			// TODO verificar dato mostrado con word.cl
			
		} else {
			// dominio no encontrado
			verifyDomNotFound(page);
		}
	}
	
	@Test
	public void exactaSimpleTest(){
		HtmlPage page;
		int limit = 1;
		for (int i = 0 ; i < simpleLimit; i++){
			try {
				page = submitForm(testSimpleSites[i],filterValues[0]);
				System.out.println("Verificando contenido...");
				verifyExactaResults(page, testSimpleSites[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}
}