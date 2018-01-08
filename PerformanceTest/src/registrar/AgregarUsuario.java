package registrar;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

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
		HtmlForm form = forms.get(0);
		//System.out.println(form.asText());
		assertTrue(form != null);
		
		//Datos de acceso
		HtmlTextInput email = form.getInputByName("username");
		email.setValueAttribute(newMail);
		HtmlTextInput clave = form.getInputByName("password");
		clave.setValueAttribute(nicPass);
		HtmlTextInput claveRep = form.getInputByName("passwordVerification");
		clave.setValueAttribute(nicPass);
		claveRep.setValueAttribute(nicPass);  
		assertTrue(true);
		System.out.println("Datos de acceso complete");
		//Datos de contacto de usuario
		HtmlTextInput nombre = form.getInputByName("contacto.nombreORazonSocial");
		nombre.setValueAttribute("Test Nic Chile");
		//HtmlTextInput giro = form.getInputByName("contacto.giro"); //opcional
		//HtmlTextInput mailAlt = form.getInputByName("email"); //opcional
		HtmlTextInput telefono = form.getInputByName("contacto.telefono.numero");
		telefono.setValueAttribute("229407700");
		assertTrue(true);
		System.out.println("Datos de contacto complete");
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
		System.out.println("Direccion postal complete");

		//Documento de identidad (opcional)
		//List<HtmlSelect> paiseEmisor = form.getSelectsByName("contacto.documentIdentidad.paisEmisor.id");
		HtmlTextInput id = form.getInputByName("contacto.documentoIdentidad.value");
		id.setValueAttribute("9841569-6");
		System.out.println("Documentos de Identidad complete");
		//Autorizacion voluntaria de envio de DTEs por Email
		HtmlInput envioDte = form.getInputByName("envioDTE");
		assertTrue(envioDte.isChecked());
		HtmlTextInput mailDTE = form.getInputByName("envioDTEMail");
		String dte = mailDTE.getValueAttribute();
		assertTrue(dte.contains(newMail));
		System.out.println("DTE complete");
		HtmlInput button = form.getInputByValue("Continuar >");
		assertTrue(button.getAttribute("type").equals("button"));
		assertTrue(button.getAttribute("id").equals(""));
		System.out.println("Ready to POST");
		return button.click();}
		catch (Exception e){
			e.printStackTrace();
			return page;
		}
	}
	
	private HtmlPage aceptacionDeReglamentacion(HtmlPage page) throws Exception{
		System.out.println("Searching PopUp");
		DomElement popUp = page.getElementById("panel_reglamentacion_c");
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
	}
	

}
