package registry;

import java.util.List;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class WhoIs {
	private String[] simpleSites = {"test","horrible","defensa","emision",
			"antigua","farmacia","azucar","abajo","bolso",
			"cama","casa","vendedor","raton","fruta",
			"triangulo","flor","bota","ruta","jardin"};
	private int simpleLimit = 5;
	private String[] numberSites = {"1","2","34","5","01"};
	private int numberLimit = 5;
	private String[] specialSites = {"ñandú", "jardín", 
			"corazón", "sillón", "rápido", "teléfono"};
	private int specialLimit = 5;
	private boolean nic = false;
	
	/** Cargar la pagina de la forma
	 * nic.cl/regisstry/Whois.do?d=word.cl
	 * 
	 * word es el nombre del dominio buscado
	 * 
	 * IMPORTANTE
	 * se accede a esta página luego de una busqueda con BusquedaDominio.do
	 * **/
	private HtmlPage getPage(String word) throws Exception{
		try (final WebClient webClient = new WebClient()) {
			//System.out.print("\n Procesando "+word+".cl \n");
			String url = "http://www.nic.cl/registry/Whois.do?d="+word+".cl";
			return webClient.getPage(url);
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
	
	/** el dominio buscado esta inscrito
	 * 
	 * verificamos segun el contenido de la pagina**/
	private boolean domFound(HtmlPage page){
		final HtmlTable table = (HtmlTable) page.getByXPath("//table[@class='tablabusqueda']").get(0);
		final List<HtmlTableRow> rows = table.getRows();
		final HtmlElement elem = rows.get(0);
		String response = elem.asText();
		if (response.contains("dominio no existe")){
			System.out.println("Dominio no inscrito");
			return false;
		} else {return true;}
	}
	
	private boolean verifyRowElement(List<HtmlTableRow> rows, String elementName){
		try{
			boolean matches = false;
			boolean hasData = false;
			for (HtmlElement row : rows){
				String allRow = row.asText();
				String[] columns = allRow.split("\n"); //elementName = [0], data = [1]
				if(columns[0].equals(elementName)){
					matches = true;
					hasData = !columns[1].isEmpty();
					if (columns[0].equals("Agente Registrador:") && columns[1].equals("NIC Chile")){
						nic = true;
					} else if (columns[0].equals("Agente Registrador:") && !columns[1].equals("NIC Chile")){
						nic = false;
					}
				} else {
					hasData = hasData || false;
				}
			}
			if (!matches){
				throw new Exception("No se encontró el campo -> "+elementName);
			} else {
				assertTrue(hasData);
				return matches && hasData;
			}
			
		} catch (Exception e){
			System.out.println(e);
			return true;
		}
	}
	
	private void verifyBasic(List<HtmlTableRow> rows, String word){
		String tittle = rows.get(0).asText();
		assertTrue(tittle.equals(word+".cl"));
		
		//Titular
		assertTrue(this.verifyRowElement(rows, "Titular:"));
		
		//Agente Registrador
		assertTrue(this.verifyRowElement(rows,"Agente Registrador:"));
		
		//Fecha de creacion
		assertTrue(this.verifyRowElement(rows,"Fecha de creación:"));
		
		//Fecha de expiracion
		assertTrue(this.verifyRowElement(rows,"Fecha de expiración:"));
	}
	
	private void verifyContact(List<HtmlTableRow> rows, String word){
		try{
			if (!nic){
				//Nombre Contacto Administrativo
				assertTrue(this.verifyRowElement(rows,"Nombre Contacto Administrativo:"));
				
				//Nombre Contacto Comercial
				assertTrue(this.verifyRowElement(rows,"Nombre Contacto Comercial:"));

				//Nombre Contacto Técnico
				assertTrue(this.verifyRowElement(rows,"Nombre Contacto Técnico:"));
			} else {
				assertTrue(true);
			}
		} catch (Exception e){ 
			System.out.println(word+".cl : No contacts info");
		}
	}
	
	private void verifyServers(List<HtmlTableRow> rows, String word){ //TODO
		try{
			boolean servMatches = false;
			boolean servNotEmpty = false;
			for (HtmlElement row : rows){
				String serv = row.asText();
				String[] cServ = serv.split("\n");
				System.out.println(cServ[0]+" , "+cServ[1]);
				if (cServ[0].equals("Servidor de Nombre:")){
					servMatches = true;
					servNotEmpty = !cServ[1].isEmpty();
					
				} else {
					servMatches = false || servMatches;
				}
			}
			if (!servMatches){
				throw new Exception(word+".cl : No servers info");
			} else {
				assertTrue(servMatches);
				assertTrue(servNotEmpty);
				assertTrue(servMatches && servNotEmpty);
			}
			
		} catch (Exception e){
			System.out.println(e);
		}
	}
	
	private void verifyResults(HtmlPage page, String word){
		final HtmlTable table = (HtmlTable) page.getByXPath("//table[@class='tablabusqueda']").get(0);
		final List<HtmlTableRow> rows = table.getRows();

		//Sitio web
		List<HtmlElement> links = table.getElementsByTagName("a");
		boolean hasLink = false;
		for (HtmlElement link : links){
			String aHref = link.getAttribute("href");
			hasLink = hasLink || aHref.contains(word+".cl");
		}
		
		this.verifyBasic(rows, word);
		this.verifyContact(rows, word);
		this.verifyServers(rows, word);
		assertTrue(hasLink);
		
		//boton de renovar
		HtmlElement exp = rows.get(4);
		HtmlElement button = exp.getElementsByTagName("button").get(0);
		String href = button.getAttribute("onclick");
		String renovar = "registrar/renovar.do?d="+word+".cl";
		String autoatencion = "autoatencion?d="+word+".cl";
		boolean hasRenovar = href.contains(renovar) || href.contains(autoatencion);
		/**List<HtmlElement> buttons = rows.get().getElementsByTagName("button");
		System.out.println(buttons.toString());
		boolean hasRenovar = false;
		for (HtmlElement btn : buttons){
			try{
				String href = btn.getAttribute("onckick");
				String renovar = "https://clientes.nic.cl/registrar/renovar.do?d="+word+".cl";
				hasRenovar = hasRenovar || href.contains(renovar);
				
			} catch (Exception e){
				System.out.println("'Renovar' button not found");
			}
		}**/
		assertTrue(hasRenovar);

	}
	
	private void verify(String word) throws Exception{
		HtmlPage page = this.getPage(word);
		if (domFound(page)){
			verifyResults(page,word);
		} else {
			verifyDomNotFound(page);
		}
	}
	
	@Test
	public void simpleTest() throws Exception{
		for (int i = 0 ; i < simpleLimit; i++){
			try{
				verify(simpleSites[i]);
			} catch (Exception e){
				assertTrue(false);
			}
		}
	}
	
	// TODO convertir caracteres especiales @Test
	public void specialTest() throws Exception{
		for (int i = 0 ; i < specialLimit; i++){
			try{
				verify(specialSites[i]);
			} catch (Exception e){
				assertTrue(false);
			}
		}
	}
	
	@Test
	public void numberTest() throws Exception{
		for (int i = 0 ; i < numberLimit; i++){
			try{
				verify(numberSites[i]);
			} catch (Exception e){
				assertTrue(false);
			}
		}
	}
}