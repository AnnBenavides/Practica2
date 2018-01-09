package registrar;

import java.net.URL;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

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
		System.out.println("Signing in ...");
		UserAndPass up = new UserAndPass();
		up.getTuple(1);
		String username = up.getUser();
		String password = up.getPass();
		Logon login = new Logon();
		HtmlPage page = login.startLogin(username, password);
		System.out.print("Log in state : ");
		URL loginPage = page.getUrl();
		String pageLink = loginPage.toString();
		if (pageLink.contains("listarDominio.do")){
			System.out.println("Success");
			assertTrue(true);
			return page;
		} else {
			System.out.println("Failure");
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
	
	private HtmlPage selectFilter(HtmlPage page, String fValue){
		try{
			System.out.println("Selecting filter :");
			DomElement menu = page.getElementById("menu-filtros");
			List<HtmlElement> options = menu.getElementsByTagName("li");
			
			int fIndex = this.getIndexValue(fValue);
			//0.1.5 -> todos || 2-4 -> contactos || 6-11 -> dominio
			assertTrue(fIndex >= 0);
			
			HtmlElement filter = null;
			for (HtmlElement option : options){
				String id = option.getAttribute("id");
				if (id.equals(filterId[fIndex])){
					filter = option;
				}
			}
			assertNotEquals(null,filter);
			
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
		//System.out.println(icon);
		for (HtmlElement div : estados){
			String clas = div.getAttribute("class");
			//System.out.println(clas);
			if (clas.equals("icon")){
				return true;
			}
		}
		return false;
	}
	
	private void verifyAllColumns(HtmlPage page){
		//TODO
	}
	
	private void verifyContacts(HtmlPage page, int filterIndex){
		//TODO
	}
	
	private void verifyState(HtmlPage paga, int filterIndex){
		//TODO
	}
	
	private void verifyNoELementsBeforeFilter(HtmlPage page){
		//TODO
	}
	
	private void verifyNoDomains(HtmlPage page){
		//TODO cuando el usuario no tiene dominios
	}
	
	private void verifyElements(HtmlPage page){
		//TODO
	}
	
	@Test
	public void misDominios(){
		this.login();
	}
}
