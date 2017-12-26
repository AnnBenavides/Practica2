package registry;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
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
	private String[] testNumberSites = {"1","2","34","5","01"};
	private String[] testSpecialSites = {"ñandú", "jardín", 
			"corazón", "sillón", "rápido", "teléfono"};
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
	public HtmlPage submitForm(String testCase,String methodValue) throws Exception{
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
	
	/**Metodo para verificar el resultado de una busqueda
	 * 
	 * **/
	
	private void verifyResults(HtmlPage page){// TODO
		
	}
	
	private void failure(){// TODO
	
	}
	private void success(){// TODO
		
	}
		

	@Test
	public void exactaSimpleTest(){
		HtmlPage page;
		int limit = 10;
		for (int i = 0 ; i < limit; i++){
			try {
				System.out.println("Testing "+(i+1)+" of "+limit+" sites...");
				page = submitForm(testSimpleSites[i],filterValues[0]);
				System.out.println("Verificando contenido...");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}
	
	
	
	

}
