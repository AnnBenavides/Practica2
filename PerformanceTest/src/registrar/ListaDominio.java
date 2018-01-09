package registrar;

import java.net.URL;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class ListaDominio {
	//index : 0.1.5 -> todos || 2-4 -> contactos || 6-11 -> dominio
	private String[] filterId = {"filterBy.all","filterBy.contacto.all",
	        "filterBy.contacto.comercial","filterBy.contacto.tecnico",
	        "filterBy.contacto.administrativo","filterBy.dominio.all",
	        "filterBy.dominio.asignado","filterBy.dominio.noPagado",
	        "filterBy.dominio.enConflicto","filterBy.dominio.enRestauracion",
	        "filterBy.dominio.bloqueado","filterBy.dominio.eliminado"};
	private String[] filterValue = {"Todos los dominios","Cualquiera",
			"Comercial","Técnico","Administrativo","Todos","Inscritos",
			"No pagados","En conflito","En restauración","Bloqueados",
			"Eliminados"};
	
	private HtmlPage login(){
		HtmlPage page;
		System.out.println("Signing in ...");
		
		UserAndPass up = new UserAndPass();
		up.getTuple(0);
		String username = up.getUser();
		String password = up.getPass();
		
		System.out.println("Starting login with "+username+" account");
		
		String url ="https://clientes.nic.cl/registrar/logon.do";
	    String usernameInputName = "j_username";
	    String passwordInputName = "j_password";
	    
	    // create the HTMLUnit WebClient instance
	    @SuppressWarnings("resource")
		WebClient wclient = new WebClient();
	    
	    // configure WebClient based on your desired
		wclient.getOptions().setPrintContentOnFailingStatusCode(false);
		wclient.getOptions().setCssEnabled(false);
		wclient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		wclient.getOptions().setThrowExceptionOnScriptError(false);
	    
	    try {
    	
	      final HtmlPage loginPage = wclient.getPage(url);
	      assertTrue(loginPage.isHtmlPage());
	      System.out.print("Log in state : ");     
	      final HtmlForm loginForm = loginPage.getForms().get(0);
	      
	      // get the text input field by the name and set the value
	      final HtmlTextInput txtUser = loginForm.getInputByName(usernameInputName);
	      txtUser.setValueAttribute(username);
	    		
	      // get the password input field by the name and set the value
	      final HtmlPasswordInput txtpass = loginForm.getInputByName(passwordInputName);
	      txtpass.setValueAttribute(password);
	      
	      // get the submit button by the text value
	      final HtmlButton submitLogin = (HtmlButton) loginForm.getElementsByTagName("button").get(0);
	      final HtmlPage returnPage = submitLogin.click();  
	      assertTrue(true);
	      page = returnPage; 
	      
		  URL lPage = page.getUrl();
		  String pageLink = lPage.toString();
		  if (pageLink.contains("listarDominio.do") || pageLink.contains("agregarDominio.do")){
		  	System.out.println("Success");
			assertTrue(true);
			return page;
		  } else {
			System.out.println("Failure");
			assertTrue(false);
			return null;
		  }
	    } catch(FailingHttpStatusCodeException e) {
	      e.printStackTrace();
	      assertTrue(false);
	      return null;
	    } catch(Exception e) {
	      e.printStackTrace();
	      assertTrue(false);
	      return null;
	    }
	}
	
	private int getIndexId(String id){
		int index = 0;
		for (String str : filterId){
			if (str.equals(id)){
				return index;
			}
			index++;
		}
		System.out.println("Filter not found");
		return -1;
	}
	
	private int getIndexValue(String value){
		int index = 0;
		for (String str : filterValue){
			if (str.equals(value)){
				return index;
			}
			index++;
		}
		System.out.println("Filter not found");
		return -1;
	}
	
	private HtmlPage selectFilter(HtmlPage page, int fIndex){
		try{
			System.out.println("Selecting filter :"+filterValue[fIndex]);
			DomElement menu = page.getElementById("menu-filtros");
			List<HtmlElement> options = menu.getElementsByTagName("li");
			
			//fIndex : 0.1.5 -> todos || 2-4 -> contactos || 6-11 -> dominio
			assertTrue(fIndex >= 0);
			
			HtmlElement filter = null;
			for (HtmlElement option : options){
				String id = option.getAttribute("id");
				if (id.equals(filterId[fIndex])){
					filter = option;
					System.out.println("Applying filter");
				}
			}
			assertNotEquals(null,filter);
			//System.out.println("Click");
			return filter.click();
		} catch (Exception e) {
			System.out.println("Problems selecting the filter");
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param 
	 * contactos : es una lista de divs hijos de "fecha_mini"
	 * id : es un elemento de filterId
	 * **/
	private boolean contactExist(List<HtmlElement> contactos, String id){
		String[] filter = id.split(".");
		assertEquals("contacto",filter[1]);
		String contacto = filter[2];
		
		for (HtmlElement div : contactos){
			String title = div.getAttribute("title");
			if (title.equals(contacto)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param 
	 * contactos : es una lista de divs hijos de "contenedor_datos"
	 * id : es un elemento de filterId
	 * **/
	private boolean hasTheState(List<HtmlElement> estados, String id){
		String[] filter = id.split(".");
		assertEquals("dominio",filter[1]);
		String estado = filter[2];
		
		String icon = "ico_mini_" + estado;
		System.out.println(icon);
		for (HtmlElement div : estados){
			String clas = div.getAttribute("class");
			System.out.println(clas);
			if (clas.equals("icon")){
				return true;
			}
		}
		return false;
	}
	/** TODO ...
	 * verificar que existan elementos en las columnas
	 * checkbox, nombre dominio, el titular, expira el, y el carrito**/
	private void verifyAllColumns(HtmlPage page){
		try {
			List<DomElement> data = page.getByXPath("//div[@class='contenedor_pdc2']//div[@id='listaDatos']");
			//List<HtmlElement> rows = data.getElementsByTagName("div");
			System.out.println(data.get(0).asText());
			//TODO
		} catch (Exception e){
			System.out.println("Can't find the data table");
			assertTrue(false);
			e.printStackTrace();
		}
		
	}
	
	private void verifyContacts(HtmlPage page, int filterIndex){
		/**TODO**/
	}
	
	private void verifyState(HtmlPage page, int filterIndex){
		/**TODO**/
	}
	
	private boolean verifyNoElementsBeforeFilter(HtmlPage page){
		try {
			DomElement box = page.getElementById("listaDatosVacia");
			String tBox = box.asText();
			//System.out.println(tBox);
			return tBox.contains("No hay dominios que coincidan con sus criterios de búsqueda.");
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("There is no domains in this selection");
			return false;
		}
		
	}
	private boolean allFilter(int index){
		if (index == 0 || index == 1 || index == 5){
			System.out.println("Showing all domains");
			return true;
		} else {
			return false;
		}
	}
	private boolean contactFilter(int index){
		if ( index > 1 && index < 5){
			System.out.println("Showing by 'Contacto' filter");
			return true;
		} else {
			return false;
		}
	}	
	private boolean stateFilter(int index){
		if ( index > 5 && index < 12){
			System.out.println("Showing by 'Estado' filter");
			return true;
		} else {
			return false;
		}
	}
	private boolean verifyNoDomains(HtmlPage page){
		// si no tiene dominios inscritos, le redirige a
		String inscripcion = "https://clientes.nic.cl/registrar/agregarDominio.do";
		URL loginPage = page.getUrl();
		String pageLink = loginPage.toString();
		//System.out.println(pageLink);
		return inscripcion.equals(pageLink);
	}
	
	/**
	 * TODO
	 * @param page : pagina retornada del login
	 * 		fId : algun elemento de filterId o null
	 * 		fValue : algun elemento de filterValue o null
	 * fId o fValue debe ser !null, en caso de ambos tener valores
	 * se prioriza fId **/
	private void verifyElements(HtmlPage page, String fId, String fValue){
		//fIndex : 0.1.5 -> todos || 2-4 -> contactos || 6-11 -> dominio
		int fIndex;
		if (fValue !=  null){
			fIndex = this.getIndexValue(fValue);
		}
		else if (fId != null){
			fIndex = this.getIndexId(fId);
		} else {
			assertTrue(false);
			System.out.println("Ingrese algun valor de filtro, ya sea la id (fId) o su valor (fValue)");
			fIndex = -1;
		}
		HtmlPage filter = this.selectFilter(page, fIndex);
		//System.out.println(filter.asText());
		if (!verifyNoElementsBeforeFilter(page)){
			this.verifyAllColumns(page);
			/**TODO ... **/
		}
		
		
	}
	
	@Test
	public void misDominios(){
		HtmlPage page = this.login();
		if (!verifyNoDomains(page)){
			System.out.println("User has domains");
			/**for (String filter : filterId){
				this.verifyElements(page, filter, null);
			}**/
			this.verifyElements(page, filterId[1], null);
		} else {
			System.out.println("User has no domains");
		}
	}
}
