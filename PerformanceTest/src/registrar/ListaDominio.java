package registrar;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
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
	//iconIndex = fIndex-6
	private String[] iconState = {"asignado","no_pagado","en_conflito","en_restauracion",
			"bloqueado","eliminado"};
	private HtmlPage login(int userNumber){
		HtmlPage page;
		System.out.println("Signing in ...");
		
		UserAndPass up = new UserAndPass();
		up.getTuple(userNumber);
		String username = up.getUser();
		String password = up.getPass();
		
		System.out.println("Starting login with "+username+" account");
		
		String url ="https://clientes.nic.cl/registrar/logon.do";
	    String usernameInputName = "j_username";
	    String passwordInputName = "j_password";
	    
	    // create the HTMLUnit WebClient instance
	    @SuppressWarnings("resource")
		WebClient wclient = new WebClient(BrowserVersion.FIREFOX_45);
	    
	    
	    // configure WebClient based on your desired
		wclient.getOptions().setPrintContentOnFailingStatusCode(false);
		wclient.getOptions().setCssEnabled(false);
		wclient.getOptions().setJavaScriptEnabled(true);
        wclient.setAjaxController(new NicelyResynchronizingAjaxController());
		wclient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		wclient.getOptions().setThrowExceptionOnScriptError(false);		
	    
	    try {
	      WebRequest request = new WebRequest(new URL(url));
	      final HtmlPage loginPage = wclient.getPage(request);
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
			System.out.println("! Selecting filter : "+filterValue[fIndex]);
			DomElement menu = page.getElementById("menu-filtros");
			List<HtmlElement> options = menu.getElementsByTagName("li");
			
			//fIndex : 0.1.5 -> todos || 2-4 -> contactos || 6-11 -> dominio
			assertTrue(fIndex >= 0);
			
			if (!allFilter(fIndex)){
				HtmlElement filter = null;
				for (HtmlElement option : options){
					String id = option.getAttribute("id");
					if (id.equals(filterId[fIndex])){
						filter = option;
						System.out.print("Getting domains... ");
					}
				}
				assertNotEquals(null,filter);
				//System.out.println("Click");
				HtmlPage refresh = filter.click();
				synchronized (refresh) {
		            refresh.wait(2000); //wait
		        }
				return refresh;
			}
			System.out.println("Applying no filter");
			synchronized (page) {
	            page.wait(2000); //wait
	        }
			return page;
		} catch (Exception e) {
			System.out.println("Problems selecting the filter");
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param 
	 * contactos : es una lista de divs hijos de "fecha_mini"
	 * fIndex : es el índice de un elemento de filterId
	 * **/
	private boolean contactExist(List<HtmlElement> contactos, int fIndex){
		String contacto = filterValue[fIndex];
		for (HtmlElement div : contactos){
			String title = div.getAttribute("title");
			if (title.equals(contacto)){
				//System.out.println(title + " found");
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
	private boolean hasTheState(HtmlElement estados,  int fIndex){
		int iconIndex = fIndex-6;
		String icon = "ico_mini_" + iconState[iconIndex];
		String clas = estados.getAttribute("class");
		assertEquals(icon,clas);
		if (clas.equals(icon)){
			return true;
		}
		return false;
	}
	
	private List<HtmlElement> getDataDiv(HtmlPage page){
		List<DomElement> boxes = page.getElementsById("listaDatos");
		DomElement box = boxes.get(0);
		List<HtmlElement> divs = box.getElementsByTagName("div");
		List<HtmlElement> ret = new ArrayList<HtmlElement>();
		for (HtmlElement div: divs){
			if(div.hasAttribute("class")){
				String cont = div.getAttribute("class");
				if (cont.equals("contenedor_datos")){
					ret.add(div);
				}	
			}
		}
		System.out.println("Number of domains: "+ret.size());
		return ret;
	}
	/** 
	 * verificar que existan elementos en las columnas 
	 * **/
	private void verifyAllColumns(HtmlPage page, int fIndex){
		try {
			List<HtmlElement> rows = this.getDataDiv(page);
			for (HtmlElement row : rows){
				List<HtmlElement> divs = row.getElementsByTagName("div");
				for (HtmlElement div : divs){
					if (div.hasAttribute("class")){
						String classAttr = div.getAttribute("class");
						
						if (classAttr.equals("fecha_mini")){
							//CHECKBOX
							if(div.hasAttribute("style")){
								List<HtmlElement> inputs = div.getElementsByTagName("input");
								for (HtmlElement input : inputs){
									String type = input.getAttribute("type");
									String name = input.getAttribute("name");
									if (type.equals("checkbox")){
										assertEquals("check",name);
										//System.out.println("\t [ ]");
									}
								}
							} else {
								List<HtmlElement> p = div.getElementsByTagName("p");
								if (p.size()>0){
							//EXPIRA EN
									String title = p.get(0).getAttribute("title");
									assertTrue(title.contains("expira en:"));
									String pText = p.get(0).asText();
									System.out.println("\t | Expira en : "+pText);
									String[] date = pText.split("/");
									assertTrue(date.length == 3 || date.length == 1);
								} else {
							//CONTACTO	
									List<HtmlElement> divCont = div.getElementsByTagName("div");
									List<HtmlElement> contactos = new ArrayList<HtmlElement>();
									for (HtmlElement cont : divCont){
										if(cont.hasAttribute("title")){
											contactos.add(cont);
										}
									}
									System.out.print("\t | Tipos de contacto : "+contactos.size());
									if (this.contactFilter(fIndex)){
										boolean c = this.contactExist(contactos, fIndex);
										System.out.println(" | Filtro correcto? "+c);
										assertTrue(c);
									} else {
										System.out.println(" | Filtro correcto? "+true);
										assertTrue(contactos.size()>0);
									}
								}
							}
						}
						//ESTADO
						if (classAttr.startsWith("ico_mini_")){
							if (this.stateFilter(fIndex)){
								boolean e = this.hasTheState(div, fIndex);
								System.out.println("\t | Estado correcto? : "+e);
								assertTrue(e);
							} else {
								System.out.println("\t | Estado correcto? : "+true);
								assertTrue(true);
							}
						}
						//NOMBRE DOMINIO
						if (classAttr.equals("dominio_mini")){
							String text = div.asText();
							System.out.println("\t | Dominio : "+text);
							assertTrue(text.endsWith(".cl"));
							HtmlElement a = div.getElementsByTagName("a").get(0);
							String href = a.getAttribute("href");
							assertTrue(href.contains("registrar/editarDominio.do?id="));
						}
						//TITULAR
						if (classAttr.equals("titular_mini")){
							String text = div.asText();
							System.out.println("\t | Titular : "+text);
							assertTrue(!text.isEmpty());
						}
						//CARRITO
						if (classAttr.startsWith("botones_")){
							HtmlElement a = div.getElementsByTagName("a").get(0);
							String href = a.getAttribute("href");
							boolean pagar = href.contains("/registrar/agregarDominioCarro.do?id=");
							System.out.println("\t | Carrito? : "+pagar+"\n");
							assertTrue(pagar);
						}
					}
				}
			}
		} catch (Exception e){
			System.out.println("Can't find the data table");
			e.printStackTrace();
			assertTrue(false);
			
		}
		
	}
	
	private boolean isEmptyBeforeFilter(HtmlPage page){
		try {
			DomElement box = page.getElementById("listaDatosVacia");
			String style = box.getAttribute("style");
			boolean isEmpty = style.contains("display: block;");
			//System.out.println("Style of 'listaDatosVacia' : "+ style);
			//System.out.println("Is there any elements? "+ !isEmpty);
			return isEmpty;
		} catch (Exception e){
			System.out.println("Problem finding domains");
			e.printStackTrace();
			return false;
		}
		
	}
	private boolean allFilter(int index){
		if (index == 0 || index == 1 || index == 5){
			//System.out.println("\n ! Showing all domains");
			return true;
		} else {
			return false;
		}
	}
	private boolean contactFilter(int index){
		if ( index > 1 && index < 5){
			//System.out.println("! Showing by 'Contacto' filter");
			return true;
		} else {
			return false;
		}
	}	
	private boolean stateFilter(int index){
		if ( index > 5 && index < 12){
			//System.out.println("! Showing by 'Estado' filter");
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
	 * @param page : pagina del login
	 * 		fId : algun elemento de filterId o null
	 * 		fValue : algun elemento de filterValue o null
	 * fId o fValue debe ser !null, en caso de ambos tener valores
	 * se prioriza fId **/
	private void verifyElements(HtmlPage page, String fId, String fValue){
		try {
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
			if (!isEmptyBeforeFilter(filter)){
				System.out.println("Checking domains \n");
				this.verifyAllColumns(filter, fIndex);
			} else {
				System.out.println("No domains in this selection \n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void misDominios(){
		System.out.println("<< STARTING ListaDominio.misDominios test");
		int topUser = new UserAndPass().numberOfAccounts();
		for (int user = 0 ; user < topUser ; user++){
			HtmlPage page = this.login(user);
			if (!verifyNoDomains(page)){
				System.out.println("User has domains");
				for (String filter : filterId){
					this.verifyElements(page, filter, null);
				}
			} else {
				System.out.println("User has no domains");
			}
		}
		System.out.println("ListaDominio.misDominios FINISHED >>");
	}
}
