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
			System.out.print("\n Procesando "+word+".cl \n");
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
		final List<HtmlElement> inTable= page.getByXPath("//table[@class='tablabusqueda']/tbody/tr/td");
		final HtmlElement elem = inTable.get(0);
		String response = elem.asText();
		if (response.contains("dominio no existe")){
			System.out.println("Dominio no inscrito");
			return false;
		} else {return true;}
	}
	
	private boolean verifyBasic(List<HtmlTableRow> rows, String word){
		String tittle = rows.get(0).asText();
		boolean matches = tittle.equals(word+".cl");
		
		//Titular
		String titular = rows.get(1).asText();
		String[] cTitular = titular.split("\n");
		boolean titularMatches = cTitular[0].equals("Titular:");
		boolean titularNotEmpty = !cTitular[1].isEmpty();
		
		//Agente Registrador
		String agente = rows.get(2).asText();
		String[] cAgente = agente.split("\n");
		boolean agenteMatches = cAgente[0].equals("Agente Registrador:");
		boolean agenteNotEmpty = !cAgente[1].isEmpty();
		
		//Fecha de creacion
		String creacion = rows.get(3).asText();
		String[] cCreacion = creacion.split("\n");
		boolean creacionMatches = cCreacion[0].equals("Fecha de creación:");
		boolean creacionNotEmpty = !cCreacion[1].isEmpty();
		
		//Fecha de expiracion + boton de renovar
		HtmlElement exp = rows.get(4);
		String expiracion = exp.asText();
		String[] cExpiracion = expiracion.split("\n");
		HtmlElement button = exp.getElementsByTagName("button").get(0);
		String href = button.getAttribute("onclick");
		String renovar = "https://clientes.nic.cl/registrar/renovar.do?d="+word+".cl";
		boolean hasRenovar = href.contains(renovar);
		boolean expiracionMatches = cExpiracion[0].equals("Fecha de expiración:");
		boolean expiracionNotEmpty = !cExpiracion[1].isEmpty();
		
		return matches && titularMatches && titularNotEmpty && agenteMatches && agenteNotEmpty 
				&& creacionMatches && creacionNotEmpty && expiracionMatches && expiracionNotEmpty
				&& hasRenovar;
	}
	
	private boolean verifyContact(List<HtmlTableRow> rows, String word){
		try{
			//Nombre Contacto Administrativo
			String admin = rows.get(5).asText();
			String[] cAdmin = admin.split("\n");
			boolean adminMatches = cAdmin[0].equals("Nombre Contacto Administrativo:");
			boolean adminNotEmpty = !cAdmin[1].isEmpty();
			
			//Nombre Contacto Comercial
			String comercial = rows.get(6).asText();
			String[] cComercial = comercial.split("\n");
			boolean comercialMatches = cComercial[0].equals("Nombre Contacto Comercial:");
			boolean comercialNotEmpty = !cComercial[1].isEmpty();
			
			//Nombre Contacto Técnico
			String tecnico = rows.get(7).asText();
			String[] cTecnico = tecnico.split("\n");
			boolean tecnicoMatches = cTecnico[0].equals("Nombre Contacto Técnico:");
			boolean tecnicoNotEmpty = !cTecnico[1].isEmpty();
			
			return adminMatches && adminNotEmpty && comercialMatches && comercialNotEmpty 
					&& tecnicoMatches && tecnicoNotEmpty; 
		} catch (Exception e){ 
			System.out.println(word+".cl : No contacts info");
			return true;
		}
	}
	
	private boolean verifyServers(List<HtmlTableRow> rows, String word){ //TODO
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
			return servMatches && servNotEmpty;
		} catch (Exception e){
			System.out.println(word+".cl : No servers info");
			return true;
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
		
		assertTrue(hasLink && this.verifyBasic(rows, word) && 
				this.verifyContact(rows, word));

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
			verify(simpleSites[i]);
		}
	}
	
	@Test
	public void specialTest() throws Exception{
		for (int i = 0 ; i < specialLimit; i++){
			verify(specialSites[i]);
		}
	}
	
	@Test
	public void numberTest() throws Exception{
		for (int i = 0 ; i < numberLimit; i++){
			verify(numberSites[i]);
		}
	}
}