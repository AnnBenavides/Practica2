package registrar;

import static org.junit.Assert.assertTrue;

import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class AgregarUsuario {
	private Email mailObj = new Email();
	private String newMail = mailObj.getNewMail();
	private String nicPass = mailObj.getNicPass();
	
	private HtmlPage openPage() throws Exception{
		try (final WebClient webClient = new WebClient()) {
			String url ="https://clientes.nic.cl/registrar/agregarUsuario.do";
	        HtmlPage page = webClient.getPage(url);
	        assertTrue(page.isHtmlPage());
	        return page;
	    }
	}
	
	private HtmlPage fillFormAndPost(HtmlPage page){
		try{
			List<HtmlForm> forms = page.getForms();
			HtmlForm form = null;
			for (HtmlForm f : forms){
				String action = f.getAttribute("action");
				if (action.contains("registrar/crearUsario.do")){
					form = f;
				}
			}
			assertTrue(form != null);
			
			//Datos de acceso
			HtmlTextInput email = form.getInputByName("username");
			email.setValueAttribute(newMail);
			HtmlTextInput clave = form.getInputByName("password");
			clave.setValueAttribute(nicPass);
			HtmlTextInput claveRep = form.getInputByName("passwordVerification");
			clave.setValueAttribute(nicPass);
			assertTrue(true);
			
			//Datos de contacto de usuario
			HtmlTextInput nombre = form.getInputByName("contacto.nombreORazonSocial");
			nombre.setValueAttribute("Test Nic Chile");
			//HtmlTextInput giro = form.getInputByName("contacto.giro"); //opcional
			//HtmlTextInput mailAlt = form.getInputByName("email"); //opcional
			HtmlTextInput telefono = form.getInputByName("contacto.telefono.numero");
			telefono.setValueAttribute("229407700");
			assertTrue(true);
			
			//Direccion postal
			//List<HtmlSelect> paises = form.getSelectsByName("contacto.direccion.pais.id");
			//List<HtmlSelect> regiones = form.getSelectsByName("contacto.direccion.regionEstadoProvincia");
			HtmlTextInput ciudad = form.getInputByName("contacto.direccion.ciudad");
			ciudad.setValueAttribute("Santiago");
			List<HtmlSelect> comunas = form.getSelectsByName("contacto.direccion.communa.id");
			for (HtmlSelect comuna : comunas){
				if (comuna.asText().contains("Santiago")){
					comuna.click();
					assertTrue(true);
				}
			}
			HtmlTextInput calle = form.getInputByName("contacto.direccion.calleYNumero");
			calle.setValueAttribute("Miraflores 222");
			assertTrue(true);
			
	
			//Documento de identidad (opcional)
			//List<HtmlSelect> paiseEmisor = form.getSelectsByName("contacto.documentIdentidad.paisEmisor.id");
			//HtmlTextInput run = form.getInputByName("contacto.documentoIdentidad.value");
			
			//Autorizacion voluntaria de envio de DTEs por Email
			HtmlInput envioDte = form.getInputByName("envioDTE");
			assertTrue(envioDte.isChecked());
			HtmlTextInput mailDTE = form.getInputByName("envioDTEMail");
			String dte = mailDTE.getValueAttribute();
			assertTrue(dte.contains(newMail));
			
			HtmlInput button = form.getInputByValue("Continuar >");
			assertTrue(button.getAttribute("type").equals("button"));
			assertTrue(button.getAttribute("id").equals(""));
			
			return button.click();
		
		} catch (Exception e){
			System.out.println(e);
		}
		return page;
	}
	
	private HtmlPage aceptacionDeReglamentacion(HtmlPage page){
		try{
			DomElement popUp = page.getElementById("panel_reglamentacion_c");
			List<HtmlElement> inputs = popUp.getElementsByTagName("input");
			for (HtmlElement input : inputs){
				if (input.getAttribute("id").equals("chkAceptado")){
					input.click();
				} else if (input.getAttribute("id").equals("reglamentacionDialogSubmit")){
					return input.click();
				}
			}
			return page;
		} catch (Exception e){
			System.out.println(e);
		}
		return page;
	} 
	
	

}
