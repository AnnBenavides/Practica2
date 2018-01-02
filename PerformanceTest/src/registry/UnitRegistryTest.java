package registry;

import org.junit.Test;

public class UnitRegistryTest {
	
	@Test
	public void buscarDomino(){
		System.out.println("Starting registry/BuscarDominio tests");
		BuscarDominio test = new BuscarDominio();
		
		test.exactaSimpleTest();
		test.exactaNumberTest();
		//test.exactaSpecialTest();
		
		test.contengaSimpleTest();
		test.contegaNumberTest();
		//test.contengaSpecialTest();
		
		test.comienceSimpleTest();
		test.comienceNumberTest();
		//test.comienceSpecialTest();
		
		test.termineSimpleTest();
		test.termineNumberTest();
		//test.termineSpecialTest();
        
        System.out.println("Success");
	}
	
	@Test
	public void eliminados() throws Exception{
		System.out.println("Starting registry/Eliminados tests");

		Eliminados test = new Eliminados();
		
		test.lastDayTest();
		test.lastWeekTest();        
        System.out.println("Success");
	}
	
	@Test
	public void ultimos() throws Exception{
		System.out.println("Starting registry/Ultimos tests");
		Ultimos test = new Ultimos();
		
		test.hourTest();
		test.dayTest();
		test.weekTest();
		//test.monthTest();
        
        System.out.println("Success");
	}
	
	@Test
	public void whoIs() throws Exception{
		System.out.println("Starting registry/Whois tests");
		WhoIs test = new WhoIs();
		
		test.simpleTest();
		test.numberTest();
		//test.specialTest();
    
		System.out.println("Success");
	}
}
