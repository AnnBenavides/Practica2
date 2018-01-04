package registrar;

import static org.junit.Assert.assertTrue;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class login {
	private Email mail = new Email();
	
	private HtmlPage openPage() throws Exception{
		try (final WebClient webClient = new WebClient()) {
			String url ="https://clientes.nic.cl/";
	        HtmlPage page = webClient.getPage(url);
	        assertTrue(page.isHtmlPage());
	        return page;
	    }
	}
	private String getUser(){
		return mail.getMainMail();
	}
	private String getPass(){
		return mail.getNicPass();
	}
	
	private HtmlPage fillFormAndPost(HtmlPage page){
		try{
			final HtmlForm form = page.getFormByName("buscarDominioForm");
	        
	        final HtmlSubmitInput button = form.getInputByValue("Ingresar");
	        final HtmlTextInput user = form.getInputByName("j_username");
	        final HtmlTextInput pass = form.getInputByName("j_password");
	
	        user.setValueAttribute(this.getUser());
	        pass.setValueAttribute(this.getPass());
	        return button.click();
		} catch (Exception e){
			System.out.println(e);
			assertTrue(false);
		}
		return page;
	}

}
