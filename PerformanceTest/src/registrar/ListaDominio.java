package registrar;

import java.net.URL;

import org.junit.Test;

import static org.junit.Assert.*;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ListaDominio {
	
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
	
	//TODO
	
	@Test
	public void misDominios(){
		this.login();
	}
}
