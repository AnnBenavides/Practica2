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
	private List<String> simpleSites;
	private int simpleLimit;
	private List<String> numberSites;
	private int numberLimit;
	private List<String> specialSites;
	private int specialLimit;
	private boolean nic = false;
	
	
	/**Carga de alfabetos de prueba
	 * 
	 * @see DomainsFile.java
	 * **/
	private void getAlphabets(){
		DomainsFile file = new DomainsFile();
		simpleSites = file.getSimple();
		numberSites = file.getNumber();
		specialSites = file.getSpecial();
		simpleLimit = file.limitSimple();
		numberLimit = file.limitNumber();
		specialLimit = file.limitSpecial();		
	}
	
	/** Cargar la pagina de la forma
	 * nic.cl/regisstry/Whois.do?d=word.cl
	 * 
	 * @param word		nombre del dominio buscado
	 * @return 			página BusquedaDominio.do
	 * **/
	private HtmlPage getPage(String word) throws Exception{
		try (final WebClient webClient = new WebClient()) {
			System.out.print("\n Procesando "+word+".cl \n");
			String url = "http://www.nic.cl/registry/Whois.do?d="+word+".cl";
			System.out.println("Abriendo URL "+url);
			return webClient.getPage(url);
		}
	}
	
	/**Respuesta a un dominio no inscrito
	 * 
	 * Aparece un boton para inscribir el dominio
	 * redireccionando a /registrar/agregarDominio.do?...
	 * con una llave al final
	 * 
	 * @param page	pagina cargada
	 * @see 		getPage(String word)
	 * **/
	private void verifyDomNotFound(HtmlPage page){
		final DomElement button = page.getElementById("submitButton");
		String btn = button.getTextContent();
		if (btn.contains("Inscribir")){
			String href = button.getAttribute("onclick");
			String nextUrl = "https://clientes.nic.cl/registrar/agregarDominio.do?";
			assertTrue(href.contains(nextUrl));
			System.out.println("> Link de inscripcion? "+href.contains(nextUrl));
		} else { 
			System.out.println("> Link de inscripcion? "+false);
			assertTrue(false);
		}
	}
	
	/** Verifica si el dominio buscado esta inscrito
	 * 
	 * @param page	página de resultado tras la busqueda
	 * @return 		true si hay dominios, false sino
	 * **/
	private boolean domFound(HtmlPage page){
		final HtmlTable table = (HtmlTable) page.getByXPath("//table[@class='tablabusqueda']").get(0);
		final List<HtmlTableRow> rows = table.getRows();
		final HtmlElement elem = rows.get(0);
		String response = elem.asText();
		if (response.contains("dominio no existe")){
			System.out.println("> Failure: Dominio no inscrito");
			return false;
		} else {return true;}
	}
	
	/**Verifica la forma del contenido del elemento indicado
	 * Si el elemento de 'Agente registrador' es 'NIC Chile' se cae
	 * en un caso especial de respuesta y usa el valor global nic
	 * para indicar este caso
	 * 
	 * @param rows			lista con las filas de resultado
	 * @param elementName	nombre de la primera columna del resultado (identificador del campo)
	 * 
	 * @return				true si el formato esta correcto, false sino**/
	private boolean verifyRowElement(List<HtmlTableRow> rows, String elementName){
		try{
			System.out.print("\t | "+elementName+" ");
			boolean matches = false;
			boolean hasData = false;
			for (HtmlElement row : rows){
				String allRow = row.asText();
				String[] columns = allRow.split("\n"); //elementName = [0], data = [1]
				if(columns[0].equals(elementName)){
					matches = true;
					hasData = !columns[1].isEmpty();
					System.out.println(columns[1]);
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
				System.out.println("(!) No se encontró el campo");
				return true;
			} else {
				assertTrue(hasData);
				return matches && hasData;
			}
			
		} catch (Exception e){
			e.printStackTrace();
			return true;
		}
	}
	
	/**Verifica forma del contenido en cada fila
	 * 
	 * @param rows		lista con las filas de resultado
	 * @param word		dominio buscado	
	 * 
	 * @see 			verifyRowElement(rows, elementName)**/
	private void verifyBasic(List<HtmlTableRow> rows, String word){
		String tittle = rows.get(0).asText();
		assertTrue(tittle.equals(word+".cl"));
		System.out.println("\t | "+tittle);
		
		//Titular
		assertTrue(this.verifyRowElement(rows, "Titular:"));
		
		//Agente Registrador
		assertTrue(this.verifyRowElement(rows,"Agente Registrador:"));
		
		//Fecha de creacion
		assertTrue(this.verifyRowElement(rows,"Fecha de creación:"));
		
		//Fecha de expiracion
		assertTrue(this.verifyRowElement(rows,"Fecha de expiración:"));
	}
	
	/** Verifica el formato de los datos de contacto
	 * Si el agente administrados es NIC Chile
	 * esta información no existe
	 * 
	 * @param rows		lista con las filas de resultado
	 * @param word		dominio buscado	
	 * 
	 * @see 			verifyRowElement(rows, elementName)**/
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
			e.printStackTrace();
		}
	}
	
	private void verifyServers(List<HtmlTableRow> rows, String word){ 
		try{
			boolean servMatches = false;
			boolean servNotEmpty = false;
			for (HtmlElement row : rows){
				String serv = row.asText();
				String[] cServ = serv.split("\n");
				//System.out.println(cServ[0]+" , "+cServ[1]);
				if (cServ[0].equals("Servidor de Nombre:")){
					servMatches = true;
					servNotEmpty = !cServ[1].isEmpty();
					System.out.println("\t | Servidor: "+cServ[1]+" ");
				} else {
					servMatches = false || servMatches;
				}
			}
			if (!servMatches){
				System.out.println("\t | Servidor: (!) No servers info");
			} else {
				assertTrue(servMatches);
				assertTrue(servNotEmpty);
				assertTrue(servMatches && servNotEmpty);
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void verifyResults(HtmlPage page, String word){
		System.out.println("Verificando resultados... ");
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
		System.out.println("\t | Redirección dominio? "+hasLink);
		
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
		System.out.println("\t | Renovar? "+hasRenovar);

	}
	
	private void verify(String word) throws Exception{
		this.getAlphabets();
		HtmlPage page = this.getPage(word);
		if (domFound(page)){
			System.out.println("> Success");
			verifyResults(page,word);
			System.out.println("> Finished");
		} else {
			verifyDomNotFound(page);
		}
	}
	
	@Test
	public void simpleTest() throws Exception{
		System.out.println("<< STARTING WhoIs.simple test");
		for (String word : simpleSites){
			try{
				verify(word);
			} catch (Exception e){
				assertTrue(false);
				e.printStackTrace();
			}
		}
	}
	
	// TODO convertir caracteres especiales @Test
	public void specialTest() throws Exception{
		System.out.println("<< STARTING WhoIs.special test");
		for (String word : specialSites){
			try{
				verify(word);
			} catch (Exception e){
				assertTrue(false);
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void numberTest() throws Exception{
		System.out.println("<< STARTING WhoIs.number test");
		for (String word : numberSites){
			try{
				verify(word);
			} catch (Exception e){
				assertTrue(false);
				e.printStackTrace();			
			}
		}
	}
}