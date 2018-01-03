/**Clase para manejar cuantas de email
 * 
 * tanto para AgregarUsuario como para la recuperacion de claves**/
package registrar;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class Email {
	private String mailUser = "nic3chile";
	private String endMail = "@gmail.com";
	private String mailPass = "testaccount";
	private int generator = 0;
	
	public String getMainMail(){
		return mailUser + endMail;
	}
	
	public String getPass(){
		return mailPass;
	}
	
	public String getNewMail(){
		generator++;
		return mailUser + "+" + generator + endMail;
	}
	
	public int mailUsed(){
		return generator;
	}
	
	private HtmlPage loginGmail(String mail) throws Exception{
		try (final WebClient webClient = new WebClient()) {
			System.out.println("Openging gmail(dot)com");
			String url ="http://gmail.com";
	        HtmlPage page = webClient.getPage(url);
	        assertTrue(page.isHtmlPage());
	        
	        System.out.println("Setting account");
	        HtmlForm form = page.getForms().get(0);
	        final HtmlTextInput inputMail = form.getInputByName("identifier");
	        inputMail.setValueAttribute(mail);
	        HtmlElement buttonNext = (HtmlElement) page.getElementById("identifierNext");
	        //System.out.println(buttonNext.asText());
	        
	        
	        HtmlPage page2 = buttonNext.click();
	        assertTrue(page2.isHtmlPage());
	        System.out.println("Setting password");
	        HtmlForm form2 = page.getForms().get(0);
	        final HtmlTextInput inputPass = form2.getInputByName("password");
	        inputPass.setValueAttribute(mailPass);
	        HtmlElement buttonNext2 = (HtmlElement) page.getElementById("passwordNext");
	        System.out.println(buttonNext2.asText());
	        
	        HtmlPage page3 = buttonNext2.click();
	        assertTrue(page3.isHtmlPage());
	        System.out.println("Getting mails");
	        List<HtmlTable> bandejas = page3.getByXPath("//table[@id=':2v']");
	        assertTrue(!bandejas.isEmpty());
	        HtmlTable bandeja = bandejas.get(0);
	        System.out.println(bandeja.asText());
	        
	        return page3;
	        
	    }
	}
	
	@Test
	public void login(){
		try {loginGmail(this.getMainMail());}
		catch (Exception e){
			System.out.print(e + "\n");
		}
	}
}

