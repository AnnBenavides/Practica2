package registrar;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class AgregarUsuario {
	
	private HtmlPage openPage() throws Exception{
		try (final WebClient webClient = new WebClient()) {
			String url = "https://clientes.nic.cl/registrar/agregarUsuario.do";
			System.out.println("Abriendo URL "+url);
	        HtmlPage page = webClient.getPage(url);
	        assertTrue(page.isHtmlPage());
	        return page;
	    }
	}
	
	private HtmlPage fillFormAndPost(HtmlPage page){
		Email mailObj = new Email();
		String username = mailObj.getNewMail();
		String password = mailObj.getNicPass();
		
		System.out.println("Starting registrer with "+username+" account");
	    
	    try {      
	      	List<HtmlForm> forms = page.getForms();
			final HtmlForm form = forms.get(0);
			
			System.out.println("c(*.')o-- Filling form...");
			
			//Datos de acceso
			System.out.println("> Datos de acceso");
			final HtmlTextInput email = form.getInputByName("username");
			final HtmlPasswordInput clave = form.getInputByName("password");
			final HtmlPasswordInput claveRep = form.getInputByName("passwordVerification");
			assertTrue(true);
			email.setValueAttribute(username);
			System.out.println("\t | Username: "+username);
			clave.setValueAttribute(password);
			System.out.println("\t | Password: "+password);
			claveRep.setValueAttribute(password);
			System.out.println("\t | Password (again): "+password);
			synchronized (page) {
	            page.wait(2000); //wait
	        }
			
			//Datos de contacto de usuario
			System.out.println("> Datos de contacto");
			final HtmlTextInput nombre = form.getInputByName("contacto.nombreORazonSocial");
			//HtmlTextInput giro = form.getInputByName("contacto.giro"); //opcional
			//HtmlTextInput mailAlt = form.getInputByName("email"); //opcional
			final HtmlTextInput telefono = form.getInputByName("contacto.telefono.numero");
			assertTrue(true);
			nombre.setValueAttribute("Test Nic Chile");
			System.out.println("\t | Nombre: "+nombre.getValueAttribute());
			telefono.setValueAttribute("229407700");
			System.out.println("\t | Telefono: (+56)"+telefono.getValueAttribute());
			synchronized (page) {
	            page.wait(2000); //wait
	        }
			
			//Direccion postal
			//List<HtmlSelect> paises = form.getSelectsByName("contacto.direccion.pais.id");
			//List<HtmlSelect> regiones = form.getSelectsByName("contacto.direccion.regionEstadoProvincia");
			final HtmlTextInput ciudad = form.getInputByName("contacto.direccion.ciudad");
			List<HtmlSelect> comunas = form.getSelectsByName("contacto.direccion.comuna.id");
			for (HtmlSelect comuna : comunas){
				if (comuna.asText().contains("Santiago")){
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
			calle.setValueAttribute("Miraflores 222");
			System.out.println("Direccion postal complete");
			synchronized (page) {
	            page.wait(2000); //wait
	        }
	
			//Documento de identidad (opcional)
			//List<HtmlSelect> paiseEmisor = form.getSelectsByName("contacto.documentIdentidad.paisEmisor.id");
			final HtmlTextInput id = form.getInputByName("contacto.documentoIdentidad.value");
			id.setValueAttribute("9841569-6");
			System.out.println("Documentos de Identidad complete");
			synchronized (page) {
	            page.wait(2000); //wait
	        }
			
			//Autorizacion voluntaria de envio de DTEs por Email
			final HtmlInput envioDte = form.getInputByName("envioDTE");
			assertTrue(envioDte.isChecked());
			final HtmlTextInput mailDTE = form.getInputByName("envioDTEMail");
			mailDTE.setValueAttribute(username);
			assertTrue(true);
			System.out.println("DTE complete");
			synchronized (page) {
	            page.wait(2000); //wait
	        }
			
			System.out.println(form.asText());
			
			//final HtmlInput button = (HtmlInput) page.getElementById("submitButton");
			HtmlPage p = page;
			List<HtmlElement> divs = form.getElementsByTagName("div");
			for (HtmlElement div : divs){
				if (!div.hasAttribute("class") && div.hasAttribute("style")){
					System.out.println(div.asText());
					p=div.click();
				}
			}
			//p = button.click(); //TODO retorna vacío DX
			synchronized (p) {
	            p.wait(2000); //wait
	        }
			//assertTrue(button.getAttribute("type").equals("button"));
			//assertTrue(button.getAttribute("id").equals("submitButton"));
					
			System.out.println("Ready to POST");
			
			//System.out.println(p.asText());
			//new UserAndPass().addTuple(username, password);
			return p;
	      
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
	
	private HtmlPage aceptacionDeReglamentacion(HtmlPage page) throws Exception{
		System.out.println("Searching PopUp");
		DomElement popUp = page.getElementById("panel_reglamentacion_c");
		System.out.println(popUp.asXml());
		List<HtmlElement> inputs = popUp.getElementsByTagName("input");
		for (HtmlElement input : inputs){
			if (input.getAttribute("id").equals("chkAceptado")){
				input.click();
			} else if (input.getAttribute("id").equals("reglamentacionDialogSubmit")){
				System.out.println("Checking complete");
				return input.click();
			}
		}
		return page;
	} 
	 
	@Test
	public void addUser() throws Exception{
		HtmlPage post = this.fillFormAndPost(this.openPage());
		System.out.println("c(*.*)o-- Checking...");
		HtmlPage accept = this.aceptacionDeReglamentacion(post);
		System.out.println("6(*0*)9 Success!!");
		
	}
	

}
