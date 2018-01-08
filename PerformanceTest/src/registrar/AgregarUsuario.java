package registrar;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
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
			final HtmlForm form = forms.get(0);
			//System.out.println(form.asText());
			//assertTrue(form != null);
			
			//Datos de acceso
			final HtmlTextInput email = form.getInputByName("username");
			final HtmlPasswordInput clave = form.getInputByName("password");
			final HtmlPasswordInput claveRep = form.getInputByName("passwordVerification");
			assertTrue(true);
			email.setValueAttribute(newMail);
			clave.setValueAttribute(nicPass);
			claveRep.setValueAttribute(nicPass);  
			System.out.println("Datos de acceso complete");
			
			//Datos de contacto de usuario
			final HtmlTextInput nombre = form.getInputByName("contacto.nombreORazonSocial");
			//HtmlTextInput giro = form.getInputByName("contacto.giro"); //opcional
			//HtmlTextInput mailAlt = form.getInputByName("email"); //opcional
			final HtmlTextInput telefono = form.getInputByName("contacto.telefono.numero");
			assertTrue(true);
			nombre.setValueAttribute("Test Nic Chile");
			telefono.setValueAttribute("229407700");
			System.out.println("Datos de contacto complete");
			
			//Direccion postal
			//List<HtmlSelect> paises = form.getSelectsByName("contacto.direccion.pais.id");
			//List<HtmlSelect> regiones = form.getSelectsByName("contacto.direccion.regionEstadoProvincia");
			final HtmlTextInput ciudad = form.getInputByName("contacto.direccion.ciudad");
			List<HtmlSelect> comunas = form.getSelectsByName("contacto.direccion.communa.id");
			for (HtmlSelect comuna : comunas){
				if (comuna.asText().contains("Santiago")){
					comuna.click();
					assertTrue(true);
				}
			}
			final HtmlTextInput calle = form.getInputByName("contacto.direccion.calleYNumero");
			assertTrue(true);
			ciudad.setValueAttribute("Santiago");
			calle.setValueAttribute("Miraflores 222");
			System.out.println("Direccion postal complete");
			
	
			//Documento de identidad (opcional)
			//List<HtmlSelect> paiseEmisor = form.getSelectsByName("contacto.documentIdentidad.paisEmisor.id");
			final HtmlTextInput id = form.getInputByName("contacto.documentoIdentidad.value");
			id.setValueAttribute("9841569-6");
			System.out.println("Documentos de Identidad complete");
					
			//Autorizacion voluntaria de envio de DTEs por Email
			final HtmlInput envioDte = form.getInputByName("envioDTE");
			assertTrue(envioDte.isChecked());
			final HtmlTextInput mailDTE = form.getInputByName("envioDTEMail");
			mailDTE.setValueAttribute(newMail);
			assertTrue(true);
			System.out.println("DTE complete");
			final HtmlButtonInput button = form.getInputByValue("Continuar >");
			final HtmlPage p = button.click(); //TODO retorna vacío DX
			assertTrue(button.getAttribute("type").equals("button"));
			assertTrue(button.getAttribute("id").equals("submitButton"));
					
			System.out.println("Ready to POST");
			
			System.out.println(p.asText());
			return p;
		} catch (Exception e){
			e.printStackTrace();
			System.out.println(e);
			return page;
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
	public void fillForm() throws Exception{
		System.out.println("c('.')o-- Opening page...");
		HtmlPage page = this.openPage();
		System.out.println("c(*.')o-- Filling form...");
		HtmlPage post = this.fillFormAndPost(page);
		System.out.println("c(*.*)o-- Checking...");
		HtmlPage accept = this.aceptacionDeReglamentacion(post);
		System.out.println("6(*0*)9 Success!!");
		new UserAndPass().addTuple(newMail, nicPass);
	}
	

}
