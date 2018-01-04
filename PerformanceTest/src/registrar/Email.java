/**Clase para manejar cuantas de email
 * 
 * tanto para AgregarUsuario como para la recuperacion de claves**/
package registrar;

import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HTMLParserListener;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;

public class Email {
	private String mailUser = "nic3chile";
	private String endMail = "@gmail.com";
	private String mailPass = "testaccount";
	private String nicPass = "RVUtxErU2018";
	private int generator = 0;
	
	public String getMainMail(){
		return mailUser + endMail;
	}
	
	public String getPass(){
		return mailPass;
	}
	
	public String getNicPass(){
		return nicPass;
	}
	
	public String getNewMail(){
		generator++;
		return mailUser + "+" + generator + endMail;
	}
	
	public int mailUsed(){
		return generator;
	}
	
	private HtmlPage loginGmail(String emailAddress) throws Exception{
		//TODO esto no funciona ni es prioritario
		try (final WebClient client = new WebClient(BrowserVersion.CHROME);) {
	        client.setHTMLParserListener(HTMLParserListener.LOG_REPORTER);
	        client.setJavaScriptEngine(new JavaScriptEngine(client));
	        client.getOptions().setJavaScriptEnabled(true);
	        client.getCookieManager().setCookiesEnabled(true);
	        client.getOptions().setThrowExceptionOnScriptError(false);
	        client.getOptions().setThrowExceptionOnFailingStatusCode(false);
	        client.setAjaxController(new NicelyResynchronizingAjaxController());
	        client.getCache().setMaxSize(0);
	        client.getOptions().setRedirectEnabled(true);

	        String url = "https://accounts.google.com/login?hl=en#identifier";
	        HtmlPage loginPage = client.getPage(url);
	        client.waitForBackgroundJavaScript(1000000);

	        HtmlForm loginForm = loginPage.getFirstByXPath("//form[@id='gaia_loginform']");
	        List<HtmlInput> buttonInputs = loginForm.getInputsByName("signIn");
	        HtmlInput nextButton = buttonInputs.get(0);
	        HtmlInput loginButton = nextButton;
	        Thread.sleep(2000);

	        //setup email
	        HtmlInput emailInput = loginForm.getInputByName("Email");
	        emailInput.setValueAttribute(emailAddress);
	        Thread.sleep(2000);

	        //click next button
	        nextButton.click();
	        client.waitForBackgroundJavaScript(1000000);
	        Thread.sleep(2000);

	        //setup password
	        HtmlInput passwordInput = loginForm.getInputByName("Passwd");
	        passwordInput.setValueAttribute(mailPass);

	        //click login button
	        loginButton.click();
	        client.waitForBackgroundJavaScript(1000000);
	        Thread.sleep(2000);

	        HtmlPage gmailPage = client.getPage("https://mail.google.com/mail/u/0/#inbox");
	        //log.info(gmailPage.asText());
	        System.out.println(gmailPage.asText());
	        return gmailPage;
	        
	    }
	}
}

