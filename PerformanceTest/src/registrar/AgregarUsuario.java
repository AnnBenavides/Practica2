package registrar;

import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class AgregarUsuario {
	private Email mailObj = new Email();
	private String username ;
	/** Cargar la pagina de la forma
	 * "https://clientes.nic.cl/registrar/agregarUsuario.do"
	 * 
	 * @return 			contenido de página agregarUsuario.do
	 * **/
	private HtmlPage openPage(){
		// create the HTMLUnit WebClient instance
	    @SuppressWarnings("resource")
		WebClient wclient = new WebClient(BrowserVersion.FIREFOX_45);
	    
	    
	    // configure WebClient based on your desired
		wclient.getOptions().setPrintContentOnFailingStatusCode(false);
		wclient.getOptions().setCssEnabled(false);
		wclient.getOptions().setJavaScriptEnabled(true); //muy importante
        wclient.setAjaxController(new NicelyResynchronizingAjaxController());
		wclient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		wclient.getOptions().setThrowExceptionOnScriptError(false);
		try {
			String url = "https://clientes.nic.cl/registrar/agregarUsuario.do";
			WebRequest request = new WebRequest(new URL(url));
		    final HtmlPage page = wclient.getPage(request);
	        assertTrue(page.isHtmlPage());
	        return page;
	    } catch (Exception e){
	    	assertTrue(false);
	    	e.printStackTrace();
	    	return null;
    	}
    }
	
	/** Rellena las diferentes secciones del formulario
	 * Sólo campos obligatorios de:
	 * 		Datos de Acceso, Datos de contacto de usuario,
	 * 		Direccion postal, Documento de identidad,
	 * 		Autorizacion voluntaria de envio de DTEs por Email
	 * Usuario y clave a registrar se generan en el momento
	 * 
	 *  @param page		contenido de agregarDominio.do
	 *  @return			pagina con el formulario completo
	 *  
	 *  @see			Email.java
	 *  				clickContinuar(page)
	 *  */
	private HtmlPage fillForm(HtmlPage page){
		username = mailObj.getNewMail();
		String password = mailObj.getNicPass();
		
		//System.out.println("Starting registrer with "+username+" account");
	    
	    try {      
	      	List<HtmlForm> forms = page.getForms();
			final HtmlForm form = forms.get(0);
			
			//Datos de acceso
			//System.out.println("> Datos de acceso");
			final HtmlTextInput email = form.getInputByName("username");
			final HtmlPasswordInput clave = form.getInputByName("password");
			final HtmlPasswordInput claveRep = form.getInputByName("passwordVerification");
			assertTrue(true);
			email.setValueAttribute(username);
			//System.out.println("\t | Username: "+username);
			clave.setValueAttribute(password);
			//System.out.println("\t | Password: "+password);
			claveRep.setValueAttribute(password);
			//System.out.println("\t | Password (again): "+password);
			synchronized (page) {
	            page.wait(2000); //wait
	        }
			
			//Datos de contacto de usuario
			//System.out.println("> Datos de contacto");
			final HtmlTextInput nombre = form.getInputByName("contacto.nombreORazonSocial");
			//HtmlTextInput giro = form.getInputByName("contacto.giro"); //opcional
			//HtmlTextInput mailAlt = form.getInputByName("email"); //opcional
			final HtmlTextInput telefono = form.getInputByName("contacto.telefono.numero");
			assertTrue(true);
			nombre.setValueAttribute("Test Nic Chile");
			//System.out.println("\t | Nombre: "+nombre.getValueAttribute());
			telefono.setValueAttribute("229407700");
			//System.out.println("\t | Telefono: (+56)"+telefono.getValueAttribute());
			synchronized (page) {
	            page.wait(2000); //wait
	        }
			
			//Direccion postal
			//System.out.println("> Direccion postal");
			//List<HtmlSelect> paises = form.getSelectsByName("contacto.direccion.pais.id");
			//List<HtmlSelect> regiones = form.getSelectsByName("contacto.direccion.regionEstadoProvincia");
			final HtmlTextInput ciudad = form.getInputByName("contacto.direccion.ciudad");
			List<HtmlSelect> comunas = form.getSelectsByName("contacto.direccion.comuna.id");
			for (HtmlSelect comuna : comunas){
				if (comuna.asText().contains("Santiago")){
					//System.out.println("\t | Comuna: "+comuna.asText());
					comuna.click();
					assertTrue(true);
				}
			}
			synchronized (page) {
	            page.wait(2000); //wait
	        }
			
			final HtmlTextInput calle = form.getInputByName("contacto.direccion.calleYNumero");
			assertTrue(true);
			ciudad.setValueAttribute("Santiago");
			//System.out.println("\t | Ciudad: "+ciudad.getValueAttribute());
			calle.setValueAttribute("Miraflores 222");
			//System.out.println("\t | Calle: "+calle.getValueAttribute());
			synchronized (page) {
	            page.wait(2000); //wait
	        }
	
			//Documento de identidad (opcional)
			//System.out.println("> Documentos de Identidad");
			//List<HtmlSelect> paiseEmisor = form.getSelectsByName("contacto.documentIdentidad.paisEmisor.id");
			final HtmlTextInput id = form.getInputByName("contacto.documentoIdentidad.value");
			id.setValueAttribute("9841569-6");
			//System.out.println("\t | RUN: "+id.getValueAttribute());
			synchronized (page) {
	            page.wait(2000); //wait
	        }
			
			//Autorizacion voluntaria de envio de DTEs por Email
			//System.out.println("> DTE complete");
			final HtmlInput envioDte = form.getInputByName("envioDTE");
			assertTrue(envioDte.isChecked());
			//System.out.println("\t | ["+envioDte.isChecked()+"]");
			final HtmlTextInput mailDTE = form.getInputByName("envioDTEMail");
			mailDTE.setValueAttribute(username);
			//System.out.println("\t | Mail: "+mailDTE.getValueAttribute());
			assertTrue(true);

			//System.out.println("Ready to POST");
			synchronized (page) {
	            page.wait(2000); //wait
	        }			
			return page;
	      
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
	
	/** Una vez relleno el form, se hace click en continuar para
	 *  que aparezca una popUp para aceptar la reglamentacion
	 *  
	 *  @param page		contenido de agregarDominio con 
	 *  				el formulario completado
	 *  @return			contenido de la pagina con popoUp de
	 *  				aceptacion de reglamento o null de 
	 *  				ocurrir algun error
	 *  
	 *  @see			aceptacionDeReglamentacion(page)
	 * */
	private HtmlPage clickContinuar(HtmlPage page){
		try{
			List<DomElement> submits = page.getElementsById("submitButton");
			for (DomElement submit : submits){
				submit.click();
				assertTrue(true);
				synchronized (page) {
		            page.wait(2000); //wait
		        }
			}
			return page;
		} catch (Exception e){
			e.printStackTrace();
		    assertTrue(false);
		    return null;
		}
	}
	
	/**	Rellena correctamente el popUp de aceptacion
	 * 	y clickea Acepto para enviar el formulario
	 * 
	 * @param page	contenido de la pagina entregada 
	 * 				tras clickear 'Continuar'
	 * @return		contenido de pagina tras enviar el 
	 * 				formulario
	 * */
	private HtmlPage aceptacionDeReglamentacion(HtmlPage page){
		try{
			//System.out.println("Searching PopUp");
			DomElement popUp = page.getElementById("panel_reglamentacion_c");
			//System.out.println(popUp.asXml());
			List<HtmlElement> inputs = popUp.getElementsByTagName("input");
			for (HtmlElement input : inputs){
				if (input.getAttribute("id").equals("chkAceptado")){
					input.click();
				} else if (input.getAttribute("id").equals("reglamentacionDialogSubmit")){
					//System.out.println("Checking complete");
					assertTrue(true);
					return input.click();
				}
			}
			assertTrue(false);
			return page;
		} catch (Exception e){
			e.printStackTrace();
			assertTrue(false);
			return null;
		}
	} 
	
	/** Verifica si la pagina de retorno confirma la creacion de cuenta
	 * de ser positivo, se guarda esta nueva cuenta en el archivo de usuarios
	 * 
	 * @param page		contenido de pagina luego de hacer POST del formulario
	 * 
	 * @see				aceptacionDeReglamentacion(page)
	 * 					UserAndPass.java
	 * */
	private void verifyPOST(HtmlPage page){
		try{
			URL lPage = page.getUrl();
			String pageLink = lPage.toString();
			if (pageLink.contains("logon.do")){
				assertTrue(true);
				//agregar usuario al archivo
				new UserAndPass().addTuple(username, mailObj.getNicPass());
			} else {
				assertTrue(false);
			}
			//System.out.println(pageLink+"\n"+page.asText());
		} catch (Exception e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	/** Test para agregar un usuario a traves de agregarUsuario.do
	 * y agregar el nuevo usuario a 'userkeys.csv'
	 *
	 * @see		UserAndPass.java
	 * */
	@Test
	public void addUser() throws Exception{
		HtmlPage init = this.openPage();
		HtmlPage form = this.fillForm(init);
		HtmlPage confirm = this.clickContinuar(form);
		HtmlPage regla = this.aceptacionDeReglamentacion(confirm);
		this.verifyPOST(regla);		
	}
}
