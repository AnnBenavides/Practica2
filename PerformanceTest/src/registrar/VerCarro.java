package registrar;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.List;

import org.junit.Test;

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

public class VerCarro {
	/**TODO**/
	private HtmlPage login(int userNumber){
		HtmlPage page;
		System.out.println("\n\n\t Signing in ...");
		
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
		  if (pageLink.contains("listarDominio.do")){
		  	System.out.println("Success");
			assertTrue(true);
			return page;
		  } else if(pageLink.contains("agregarDominio.do")){
			System.out.println("Success, but user with no domains");
			assertTrue(true);
			return null;
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
	
	private HtmlPage goToCarro(HtmlPage page){
		try {
			System.out.print("Verify response of an empty 'Carro de Compra'... ");
			HtmlElement div = (HtmlElement) page.getElementById("menu_pago");
			HtmlElement a = div.getElementsByTagName("a").get(0);
			String href = a.getAttribute("href");
			boolean checkLink = href.contains("verCarro.do");
			assertTrue(checkLink);
			if (checkLink){
				System.out.println("Success");
				HtmlPage refresh = a.click();
				synchronized (refresh) {
		            refresh.wait(2000); //wait
		        }
				return refresh;
			} else {
				System.out.println("Failure");
				return null;
			}
		} catch (Exception e){
			System.out.println("! Link not accesible");
			assertTrue(false);
			e.printStackTrace();
			return null;
		}
	}

	private HtmlPage addToCarro(HtmlPage page){
		try {
			System.out.println("Adding products to 'Carro'");
			synchronized (page) {
	            page.wait(2000); //wait
	        }
			HtmlElement data = (HtmlElement) page.getElementById("listaDatos");
			List<HtmlElement> divs = data.getElementsByTagName("div");
			//System.out.println("Found "+divs.size()+" <div> elements");
			for (HtmlElement botones : divs){
				if(botones.hasAttribute("class")){
					String classAttr = botones.getAttribute("class");
					//System.out.println(classAttr);
					if (classAttr.equals("botones_dominio_mini")){
						HtmlElement a = botones.getElementsByTagName("a").get(0);
						HtmlPage refresh = a.click();
						synchronized (refresh) {
				            refresh.wait(2000); //wait
				        }
						System.out.println("\t Going to 'Ver Carro' with 1 product");
						assertTrue(true);
						return refresh;
					}
				}
			}
			assertTrue(false);
			System.out.println("There is no selectable domain");
			return null;
		} catch (Exception e){ 
			System.out.println("! Problems in VerCarro.addToCarro");
			e.printStackTrace();
			assertTrue(false);
			return page;
		}
	}
	
	private boolean hasNoProducts(HtmlPage page){
		try {
			List<DomElement> divs = page.getElementsByTagName("div");
			for (DomElement div : divs){
				if(div.hasAttribute("class")){
					String classAttr = div.getAttribute("class");
					//System.out.println(classAttr);
					if (classAttr.equals("mensaje")){
						String text = div.asText();
						if (text.contains("El carro de compras se encuentra vacío.")){
							assertTrue(true);
							return true;
						}
					}
				}
			}
			return false;
		} catch (Exception e){ 
			System.out.println("! Problems in VerCarro.hasNoProducts");
			e.printStackTrace();
			assertTrue(false);
			return true;
		}
	}
	
	private void verifyColumns(HtmlElement row){
		try {
			//TODO
		} catch (Exception e){ 
			System.out.println("! Problems in VerCarro.verify columns");
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	private void verifyProducts(HtmlElement rows){
		try {
			//TODO
		} catch (Exception e){ 
			System.out.println("! Problems in VerCarro.verifyProducts");
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	private void verifyTotalesYMoneda(HtmlElement div,String monedaValue){
		try {
			List<HtmlElement> buttons = div.getElementsByTagName("button");
			int nButtons = 0;
			for (HtmlElement button : buttons){
				if(button.getAttribute("id").equals("CLP-button") || button.getAttribute("id").equals("USD-button")){
					nButtons++;
				}
			}
			assertEquals(2,nButtons);
			System.out.println("\t | Moneda : "+monedaValue);
			HtmlElement input = div.getElementsByTagName("input").get(0);
			assertEquals("codigoMonedaActual",input.getAttribute("id"));
			assertEquals(monedaValue,input.getAttribute("value"));
			System.out.print("\t | ");
			List<HtmlElement> divs = div.getElementsByTagName("div");
			for (HtmlElement total : divs){
				if (total.hasAttribute("class")){					
					if (total.getAttribute("class").equals("carro_compras_valor_total")){
						assertTrue(total.asText().contains("Total") && total.asText().contains(monedaValue));
						System.out.println(total.asText());
						return;
					}
				}
			}
			System.out.println("Incorrect 'Total' output");
			assertTrue(false);
			
		} catch (Exception e){ 
			System.out.println("! Problems in VerCarro.verifyTotalesYMoneda");
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	private HtmlPage selectMediodePagoCLP(HtmlElement div, String medioDePago){
		try {
			System.out.println("> Select 'medio de pago' : "+medioDePago);
			List<HtmlElement> medios = div.getElementsByTagName("input");
			for (HtmlElement medio : medios){
				String id = "medio_pago_"+medioDePago;
				String medio_id = medio.getAttribute("id");
				if (medio_id.equals(id)){
					System.out.println("> Found and clicked, charging page");
					HtmlPage refresh = medio.click();
					synchronized (refresh) {
			            refresh.wait(2000); //wait
			        }
					assertTrue(true);
					return refresh;
				}
			}
			System.out.println("> Option not found");
			return null;
		} catch (Exception e){ 
			System.out.println("! Problems in VerCarro.selectMediodePago");
			e.printStackTrace();
			assertTrue(false);
			return null;
		}
	}
	
	private void verifyMediosdePagoCLP(HtmlElement div, String medioDePago){
		try {
			System.out.println("Seleccionar medio de pago");
			HtmlPage refresh = this.selectMediodePagoCLP(div, medioDePago);
			DomElement pago = refresh.getElementById("medios_de_pago");
			List<HtmlElement> divs = pago.getElementsByTagName("div");
			System.out.print("\t | Medio de pago : "+medioDePago+"\t | Success? ");
			for (HtmlElement medio : divs){
				if (medio.hasAttribute("class")){
					if (medio.getAttribute("class").equals("medio_pago on")){
						HtmlElement input = medio.getElementsByTagName("input").get(0);
						System.out.println(input.getAttribute("id").equals("medio_pago_"+medioDePago));
						assertEquals("medio_pago_"+medioDePago, input.getAttribute("id"));
					}
				}
			}
		} catch (Exception e){ 
			System.out.println("! Problems in VerCarro.verifyMediosdePago");
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	private void verifyCompraCLP(DomElement div, String medioDePago){
		try {
			System.out.println("> Verify 'Compra'");
			List<HtmlElement> divs = div.getElementsByTagName("div");
			for (HtmlElement botonera : divs){
				if(botonera.hasAttribute("id") && botonera.hasAttribute("style")){
					boolean id = botonera.getAttribute("id").equals("botonera"+medioDePago);
					if (id){
						boolean style =botonera.getAttribute("style").contains("display: block");
						System.out.println("\t | 'Comprar' por "+medioDePago+"? "+style);
						assertEquals(true,style);
						return;
					}
				}
			}
			System.out.println("\t | 'Comprar'? "+false);
			assertTrue(false);
		} catch (Exception e){ 
			System.out.println("! Problems in VerCarro.verifyCompra");
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	private void verifyCLP(HtmlPage page){
		try {
			//TODO
			if (!this.hasNoProducts(page)){
				//TODO
				System.out.println("Checking content...");
				List<DomElement> divs = page.getElementsByTagName("div");
				int pago = 0;
				for (DomElement div : divs){
					if (div.hasAttribute("class")){
						String classAttr = div.getAttribute("class");
						//CONTENEDOR PRODUCTOS
						if (classAttr.equals("contenedor_scroll")){
							System.out.println("Verificando productos");	
						}
						//TOTALES y MONEDA
						else if (classAttr.equals("total_final")){
							System.out.println("Verificando total");	
							this.verifyTotalesYMoneda((HtmlElement) div,"CLP");
						} 
						//MEDIOS DE PAGO
						else if (classAttr.equals("medios_de_pago")){
							String[] mediosDePago = {"webpay","khipu","servipag_online","servipag"};
							this.verifyMediosdePagoCLP((HtmlElement) div, mediosDePago[pago]);
						}
						//COMPRA
						else if (classAttr.equals("contenedor_pdc2") && div.hasAttribute("style")){
							System.out.println("Compra con ");
							String[] mediosDePago = {"Webpay","Khipu","ServipagOnline","Servipag"};
							this.verifyCompraCLP(div, mediosDePago[pago]);
						}
					}
				}
				
			}else{
				System.out.println("There are no products");
				assertTrue(false);
			}
		} catch (Exception e){ 
			System.out.println("! Problems in VerCarro.verify");
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void compraCLP(){
		System.out.println("<< STARTING VerCarro.compraCLP test");
		try{
			int users = new UserAndPass().numberOfAccounts();
			for (int user = 0 ; user < users ; user++){
				HtmlPage loginPage = this.login(user);
				if (loginPage!= null){
					HtmlPage refresh = this.addToCarro(loginPage);
					synchronized (refresh) {
			            refresh.wait(2000); //wait
			        }
					this.verifyCLP(refresh);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		System.out.println("VerCarro.compraCLP FINISHED >> \n");
	}
	
	@Test
	public void carroVacio(){
		System.out.println("<< STARTING VerCarro.carroVacio test");
		try{
			int users = new UserAndPass().numberOfAccounts();
			for (int user = 0 ; user < users ; user++){
				HtmlPage loginPage = this.login(user);
				if (loginPage!= null){
					assertTrue(this.hasNoProducts(this.goToCarro(loginPage)));
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		System.out.println("VerCarro.carroVacio FINISHED >> \n");
	}
}
