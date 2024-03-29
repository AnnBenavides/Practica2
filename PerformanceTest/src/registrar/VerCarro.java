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
		/**Método para iniciar sesion
		 * login exitoso lleva a listarDominio.do
		 * o a agregarDominio.do si el usuario no tiene dominios
		 * 
		 * @param userNumber	indice de usuario en el csv 'userkeys'
		 * @return				contenido de la pagina si el inicio fue exitoso
		 * 						null si hubo algun problema
		 * 
		 * @see		UserAndPass.java
		 * */
		private HtmlPage login(int userNumber){
			//System.out.println("\n\n\t Signing in ...");
			
			UserAndPass up = new UserAndPass();
			up.getTuple(userNumber);
			String username = up.getUser();
			String password = up.getPass();
			
			//System.out.println("Starting login with "+username+" account");
			
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
		      //System.out.print("Log in state : ");     
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
		      return returnPage; 		  
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
		
		/** En caso de que el usuario no tengo dominios
		 * 
		 * @param page	contenido de la pagina luego de iniciar sesion
		 * @return		true si el usuario no tiene dominios, false si los tiene
		 * */
		private boolean verifyNoDomains(HtmlPage page){
			URL loginPage = page.getUrl();
			String pageLink = loginPage.toString();
			//System.out.println(pageLink);
			return !pageLink.contains("listarDominio.do");
		}
	
	/**Si al iniciar sesion se retorno a listarDominio.do
	 * vamos a la seccion de 'Ver Carro' sin cargarlo previamente
	 * 
	 * @param page		contenido de la pagina listarDominio.do
	 * @return			contenido de la pagina VerCarro.do
	 * 					o null si ocurrio algun problema	
	 * */
	private HtmlPage goToCarro(HtmlPage page){
		try {
			//System.out.print("Verify response of an empty 'Carro de Compra'... ");
			HtmlElement div = (HtmlElement) page.getElementById("menu_pago");
			HtmlElement a = div.getElementsByTagName("a").get(0);
			String href = a.getAttribute("href");
			boolean checkLink = href.contains("verCarro.do");
			assertTrue(checkLink);
			if (checkLink){
				//System.out.println("Success");
				HtmlPage refresh = a.click();
				synchronized (refresh) {
		            refresh.wait(2000); //wait
		        }
				return refresh;
			} else {
				//System.out.println("Failure");
				return null;
			}
		} catch (Exception e){
			//System.out.println("! Link not accesible");
			assertTrue(false);
			e.printStackTrace();
			return null;
		}
	}

	/**Desde listarDominio.do carga un elemento al carro
	 * y redirije a VerCarro.do
	 * 
	 * @param page 		contenido de la pagina listarDominio.do
	 * @return			contenido de la pagina VerCarro.do,
	 * 					con un elemento cargado. o null si no
	 * 					se encuentra el boton del carro
	 * */
	private HtmlPage addToCarro(HtmlPage page){
		try {
			//System.out.println("Adding products to 'Carro'");
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
						//System.out.println("\t Going to 'Ver Carro' with 1 product");
						assertTrue(true);
						return refresh;
					}
				}
			}
			assertTrue(true);
			//System.out.println("There is no selectable domain");
			return null;
		} catch (Exception e){ 
			//System.out.println("! Problems in VerCarro.addToCarro");
			e.printStackTrace();
			assertTrue(false);
			return page;
		}
	}
	
	/** Evalua el contenido de la pagina en caso de no
	 * haber elementos cargados al carro
	 * 
	 * @param page		contenido de VerCarro.do
	 * @return			true si el carro esta vacio, false sino
	 * */
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
			//System.out.println("! Problems in VerCarro.hasNoProducts");
			e.printStackTrace();
			assertTrue(false);
			return true;
		}
	}
	
	/** Verifica el contenido de una fila
	 * 
	 * @param row	div que contiene una elemento
	 * 				class = 'contenedor_datos'
	 * */
	private void verifyColumns(HtmlElement row){
		try {
			List<HtmlElement> columns = row.getElementsByTagName("div");
			for (HtmlElement div : columns){
				if (div.hasAttribute("class")){
					String classAttr = div.getAttribute("class");
			//	QUITAR
					if (classAttr.equals("input_mis_pagos")){
						HtmlElement a = div.getElementsByTagName("a").get(0);
						String href = a.getAttribute("href");
						assertTrue(href.contains("eliminarDominioCarro.do?id="));
					}
			//	NOMBRE DE DOMINIO
					else if (classAttr.equals("dominio_pago")){
						String str = div.asText();
						assertTrue(str.contains(".cl"));
					} 
			//	TITULAR
					else if (classAttr.equals("dato_pago") && !div.hasAttribute("style")){
						String str = div.asText();
						assertTrue(!str.isEmpty());
					}
			//	TIPO DE OPERACION
					else if (classAttr.equals("dato_pago") && div.hasAttribute("style")){
						String str = div.asText();
						assertTrue(!str.isEmpty());
						HtmlElement input = div.getElementsByTagName("input").get(0);
						assertTrue(input.getAttribute("id").contains("tipoOperacion.id"));
					}
			//	PLAZO
					else if (classAttr.contains("dato_pago")){
						List<HtmlElement> options = div.getElementsByTagName("option");
						boolean selected = false;
						for (HtmlElement option : options){
							assertTrue(option.hasAttribute("value"));
							if (option.hasAttribute("selected")){
								assertTrue(option.getAttribute("selected").equals("selected"));
								selected = !selected;
							}
						}
						assertTrue(selected);
					}
			//	VALOR
					else if (classAttr.equals("total")){
						int p = div.getElementsByTagName("p").size();
						String total = div.asText();
						assertTrue((!total.isEmpty()) && (p>0));
					}
				}
			}
		} catch (Exception e){ 
			//System.out.println("! Problems in VerCarro.verify columns");
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	/**Verifica el contenido del carro y lo evalua
	 * 
	 * @param rows	contenedor de filas
	 * 
	 * @see			verifyColumns(row)*/
	private void verifyProducts(HtmlElement rows){
		try {
			//System.out.println("> Verifing Domains data");
			List<HtmlElement> divs = rows.getElementsByTagName("div");
			for (HtmlElement div: divs){
				if (div.hasAttribute("class")){
					if (div.getAttribute("class").equals("contenedor_datos")){
						this.verifyColumns(div);
					}
				}
			}
			assertTrue(true);
		} catch (Exception e){ 
			//System.out.println("! Problems in VerCarro.verifyProducts");
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	/** Asegura las opciones de cambio de moneda 
	 * y la existencia del monto total
	 * 
	 * @param div			contenedos de la seccion
	 * @param monedaValue	moneda que se ha elegido (CLP, USD)
	 * */
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
			//System.out.println("\t | Moneda : "+monedaValue);
			HtmlElement input = div.getElementsByTagName("input").get(0);
			assertEquals("codigoMonedaActual",input.getAttribute("id"));
			assertEquals(monedaValue,input.getAttribute("value"));
			//System.out.print("\t | ");
			List<HtmlElement> divs = div.getElementsByTagName("div");
			for (HtmlElement total : divs){
				if (total.hasAttribute("class")){					
					if (total.getAttribute("class").equals("carro_compras_valor_total")){
						assertTrue(total.asText().contains("Total") && total.asText().contains(monedaValue));
						//System.out.println(total.asText());
						return;
					}
				}
			}
			//System.out.println("Incorrect 'Total' output");
			assertTrue(false);
			
		} catch (Exception e){ 
			//System.out.println("! Problems in VerCarro.verifyTotalesYMoneda");
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	/** Selecciona el medio de pago segun tipo de moneda
	 * 
	 * @param div			contenedor de la seccion
	 * @param medioDePago	"webpay","khipu","servipag_online" o "servipag" para CLP
	 * 						"paypal" para USD
	 * 
	 * @return				contenido de la pagina tras seleccion, o null de haber
	 * 						algun problema
	 * */
	private HtmlPage selectMediodePago(HtmlElement div, String medioDePago){
		try {
			//System.out.println("> Select 'medio de pago' : "+medioDePago);
			List<HtmlElement> medios = div.getElementsByTagName("input");
			for (HtmlElement medio : medios){
				String id = "medio_pago_"+medioDePago;
				String medio_id = medio.getAttribute("id");
				if (medio_id.equals(id)){
					//System.out.println("> Found and clicked, charging page");
					HtmlPage refresh = medio.click();
					synchronized (refresh) {
			            refresh.wait(2000); //wait
			        }
					assertTrue(true);
					return refresh;
				}
			}
			//System.out.println("> Option not found");
			return null;
		} catch (Exception e){ 
			//System.out.println("! Problems in VerCarro.selectMediodePago");
			e.printStackTrace();
			assertTrue(false);
			return null;
		}
	}
	
	/** Verifica el contenido tras seleccionar un medio de pago
	 * 
	 * @param div			contenedor de la seccion Medio De Pago
	 * @param medioDePago	"webpay","khipu","servipag_online" o "servipag" para CLP
	 * 						"paypal" para USD
	 * 
	 * @see					selectMediodePago(div,medioDePago)
	 * */
	private void verifyMediosdePago(HtmlElement div, String medioDePago){
		try {
			//System.out.println("Seleccionar medio de pago");
			HtmlPage refresh = this.selectMediodePago(div, medioDePago);
			DomElement pago = refresh.getElementById("medios_de_pago");
			List<HtmlElement> divs = pago.getElementsByTagName("div");
			//System.out.print("\t | Medio de pago : "+medioDePago+"\t | Success? ");
			for (HtmlElement medio : divs){
				if (medio.hasAttribute("class")){
					if (medio.getAttribute("class").equals("medio_pago on")){
						HtmlElement input = medio.getElementsByTagName("input").get(0);
						//System.out.println(input.getAttribute("id").equals("medio_pago_"+medioDePago));
						assertEquals("medio_pago_"+medioDePago, input.getAttribute("id"));
					}
				}
			}
		} catch (Exception e){ 
			//System.out.println("! Problems in VerCarro.verifyMediosdePago");
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	/** Selecciona la opcion de precios en dolares
	 * 
	 * @param page		contenido de la pagina VerCarro.do
	 * @param moneyCode "CLP", "USD"
	 * @return			contenido de la pagina con precios en USD,
	 * 					null de haber algun error, o la misma pagina
	 * 					de no haber elementos en el carro
	 * 
	 * @see				hasNoProducts(page)
	 * */
	private HtmlPage getDivisaPage(HtmlPage page, String moneyCode){
		try{
			if (!this.hasNoProducts(page)){
				//System.out.print("Searching button ");
				List<DomElement> divs = page.getElementsByTagName("div");
				for (DomElement div : divs){
					if (div.hasAttribute("class")){
						String classAttr = div.getAttribute("class");
						//TOTALES y MONEDA
						if (classAttr.equals("total_final")){
							//System.out.println(". . .");	
							List<HtmlElement> buttons = div.getElementsByTagName("button");
							for (HtmlElement button : buttons){
								if(button.getAttribute("id").equals(moneyCode+"-button")){
									HtmlPage refresh = button.click();
									//System.out.println("> Getting USD page");
									synchronized (refresh) {
							            refresh.wait(2000); //wait
							        }
									assertTrue(true);
									return refresh;
								}
							}
						} 
					}
				}
				//System.out.println("Cant perform this action");
				assertTrue(false);
				return null;
			} else {
				//System.out.println("There are no products");
				assertTrue(false);
				return page;
			}
		} catch (Exception e){ 
			//System.out.println("! Problems in VerCarro.getUSDPage");
			e.printStackTrace();
			assertTrue(false);
			return null;
		}
	}
		
	/** Verifica el contenido de todo VerCarro.do en CLP:
	 * 		contenedor de datos, totales y moneda,
	 * 		medios de pago y compra
	 * 
	 * @param page	contenedor de pagina luego de elegir moneda chilena
	 * 
	 * @see			hasNoProducts(page)
	 * 				verifyProducts(div)
	 * 				verifyTotalesYMoneda(div, monedaValue)
	 * 				verifyMediosdePago(div, medioDePago)
	 * 				verifyCompra(div, medioDePago)
	 * */
	private void verifyCLP(HtmlPage defaultPage){
		try {
			HtmlPage page = this.getDivisaPage(defaultPage, "CLP");
			if (!this.hasNoProducts(page)){
				//System.out.println("Checking content...");
				List<DomElement> divs = page.getElementsByTagName("div");
				int pago = 0;
				for (DomElement div : divs){
					if (div.hasAttribute("class")){
						String classAttr = div.getAttribute("class");
						//CONTENEDOR PRODUCTOS
						if (classAttr.equals("contenedor_scroll")){
							this.verifyProducts((HtmlElement) div);	
						}
						//TOTALES y MONEDA
						else if (classAttr.equals("total_final")){	
							this.verifyTotalesYMoneda((HtmlElement) div,"CLP");
						} 
						//MEDIOS DE PAGO
						else if (classAttr.equals("medios_de_pago")){
							String[] mediosDePago = {"webpay","khipu","servipag_online","servipag"};
							this.verifyMediosdePago((HtmlElement) div, mediosDePago[pago]);
						}
						//COMPRA
						else if (classAttr.equals("contenedor_pdc2") && div.hasAttribute("style")){
							String[] mediosDePago = {"Webpay","Khipu","ServipagOnline","Servipag"};
							this.verifyCompra(div, mediosDePago[pago]);
						}
					}
				}
				
			}else{
				//System.out.println("There are no products");
				assertTrue(false);
			}
		} catch (Exception e){ 
			//System.out.println("! Problems in VerCarro.verifyCLP");
			e.printStackTrace();
			assertTrue(false);
		}
	}
		
	/** Verifica el contenido de todo VerCarro.do en USD:
	 * 		contenedor de datos, totales y moneda,
	 * 		medios de pago y compra
	 * 
	 * @param page	contenedor de pagina VerCarro.do default
	 * 
	 * @see			getUSDPage(page)
	 * 				hasNoProducts(page)
	 * 				verifyProducts(div)
	 * 				verifyTotalesYMoneda(div, monedaValue)
	 * 				verifyMediosdePago(div, medioDePago)
	 * 				verifyCompra(div, medioDePago)
	 * */
	private void verifyUSD(HtmlPage defaultPage){
		try {
			HtmlPage page = this.getDivisaPage(defaultPage, "USD");
			if (!this.hasNoProducts(page)){
				//System.out.println("Checking content...");
				List<DomElement> divs = page.getElementsByTagName("div");
				for (DomElement div : divs){
					if (div.hasAttribute("class")){
						String classAttr = div.getAttribute("class");
						//CONTENEDOR PRODUCTOS
						if (classAttr.equals("contenedor_scroll")){
							this.verifyProducts((HtmlElement) div);	
						}
						//TOTALES y MONEDA
						else if (classAttr.equals("total_final")){	
							this.verifyTotalesYMoneda((HtmlElement) div,"USD");
						} 
						//MEDIOS DE PAGO
						else if (classAttr.equals("medios_de_pago")){
							this.verifyMediosdePago((HtmlElement) div, "paypal");
						}
						//COMPRA
						else if (classAttr.equals("contenedor_pdc2") && div.hasAttribute("style")){
							//System.out.println("Compra con ");
							this.verifyCompra(div, "Paypal");
						}
					}
				}
				
			}else{
				//System.out.println("There are no products");
				assertTrue(false);
			}
		} catch (Exception e){ 
			//System.out.println("! Problems in VerCarro.verifyUSD");
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	/** Verifica el contenido de la seccion compra 
	 * (al final de VerCarro.do)
	 * 
	 * @param div			contenedor de la ultima seccion
	 * @param medioDePago	"Webpay","Khipu","ServipagOnline","Servipag" para CLP
	 * 						"Paypal" para USD+
	 * 
	 * @see					verifyOutterSale(div,url)
	 * 						getRedirectUrl(medioDePago)
	 * */
	private void verifyCompra(DomElement div, String medioDePago){
		try {
			//System.out.println("> Verify 'Compra'");
			List<HtmlElement> divs = div.getElementsByTagName("div");
			for (HtmlElement botonera : divs){
				if(botonera.hasAttribute("id") && botonera.hasAttribute("style")){
					boolean id = botonera.getAttribute("id").equals("botonera"+medioDePago);
					if (id){
						boolean style =botonera.getAttribute("style").contains("display: block");
						//System.out.println("\t | 'Comprar' por "+medioDePago+"? "+style);
						this.verifyOutterSale(div, this.getRedirectUrl(medioDePago));
						assertEquals(true,style);
						return;
					}
				}
			}
			//System.out.println("\t | 'Comprar'? "+false);
			assertTrue(false);
		} catch (Exception e){ 
			//System.out.println("! Problems in VerCarro.verifyCompra");
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	/** Dado un medio de paga, entrega la url que debería redirigir
	 * al clickear 'Comprar'
	 * 
	 * @param medioDePago	"Webpay","Khipu","ServipagOnline","Servipag","Paypal"
	 * @return				parte de la url correspondiente al medio de pago, 
	 * 						null si no lo encuentra
	 * 
	 * @see					verifyOutterSale(page,medioDePago)*/
	private String getRedirectUrl(String medioDePago){
		String[] medios = {"Webpay","Khipu","ServipagOnline","Servipag","Paypal"};
		String[] urls = {"transbank.cl","khipu.com","servipag.com","generarCupon.do?","paypal.com"};
		int i = -1;
		for (String medio: medios){
			i++;
			if (medio.equals(medioDePago)){
				assertTrue(true);
				return urls[i];
			}
		}
		assertTrue(false);
		return null;
	}
	
	/** Verifica que, una ves elegidoel medio de pago, el botón de compra
	 * redirica al sistema externo indicado para realizar la compra
	 * 
	 * @param div	contenedor de la botonera correspondiente 
	 * 				al medio de pago elegido
	 * @param url	URL a la que debería redirigir la compra
	 * 
	 * @see			verifyCompra(div,mediDePago)
	 * 				getRedirectUrl(medioDePago)
	 * */
	private void verifyOutterSale(DomElement div, String url){
		try{
			List<HtmlElement> inputs = div.getElementsByTagName("input");
			for (HtmlElement button : inputs){
				if (button.getAttribute("type").equals("submit")){
					HtmlPage page = button.click();
					synchronized (page) {
			            page.wait(5000); //wait
			        }
					URL lPage = page.getUrl();
					String pageLink = lPage.toString();
					assertTrue(!pageLink.contains("VerCarro.do"));
					return;
				}
			}
			assertTrue(false);
		} catch (Exception e){
			assertTrue(false);
			e.printStackTrace();
		}
	}
	
	/**Test de VerCarro.do con elementos cargados
	 * y precios en CLP, para todos los usuarios guardados
	 * 
	 * @see	UserAndPass.java
	 * 		login(userNumber)
	 * 		addToCarro(page)
	 * 		verifyCLP(page)
	 * */
	@Test
	public void compraCLP(){
		System.out.println("<< STARTING VerCarro.compraCLP test");
		try{
			int users = new UserAndPass().numberOfAccounts();
			for (int user = 0 ; user < users ; user++){
				HtmlPage loginPage = this.login(user);
				if (!this.verifyNoDomains(loginPage)){
					HtmlPage refresh = this.addToCarro(loginPage);
					if (refresh != null){
						synchronized (refresh) {
				            refresh.wait(2000); //wait
				        }
						this.verifyCLP(refresh);
					}
					
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		System.out.println("VerCarro.compraCLP FINISHED >> \n");
	}
	
	/**Test de VerCarro.do con elementos cargados
	 * y precios en USD, para todos los usuarios guardados
	 * 
	 * @see	UserAndPass.java
	 * 		login(userNumber)
	 * 		addToCarro(page)
	 * 		verifyUSD(page)
	 * */
	@Test
	public void compraUSD(){
		System.out.println("<< STARTING VerCarro.compraUSD test");
		try{
			int users = new UserAndPass().numberOfAccounts();
			for (int user = 0 ; user < users ; user++){
				HtmlPage loginPage = this.login(user);
				if (!this.verifyNoDomains(loginPage)){
					HtmlPage refresh = this.addToCarro(loginPage);
					if (refresh != null){
						synchronized (refresh) {
				            refresh.wait(2000); //wait
				        }
						this.verifyUSD(refresh);
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		System.out.println("VerCarro.compraUSD FINISHED >> \n");
	}
	
	/**Test de VerCarro.do sin elementos cargados,
	 * para todos los usuarios guardados
	 * 
	 * @see	UserAndPass.java
	 * 		login(userNumber)
	 * 		goToCarro(page)
	 * 		hasNoProducts(page)
	 * */
	@Test
	public void carroVacio(){
		System.out.println("<< STARTING VerCarro.carroVacio test");
		try{
			int users = new UserAndPass().numberOfAccounts();
			for (int user = 0 ; user < users ; user++){
				HtmlPage loginPage = this.login(user);
				if (!this.verifyNoDomains(loginPage)){
					assertTrue(this.hasNoProducts(this.goToCarro(loginPage)));
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		System.out.println("VerCarro.carroVacio FINISHED >> \n");
	}
}
