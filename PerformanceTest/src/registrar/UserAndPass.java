package registrar;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;

import org.junit.Test;


/**Clase que permitira ingresar y tomar combinaciones
 * de usuario/clave desde un archivo en csv
 * 
 * el archivo de llama "userkeys.csv"
 * 
 * dicho archivo no se subira al repositorio por seguridad**/
public class UserAndPass {
	private String user;
	private String pass;
	private String localPath = new File(".").getAbsolutePath();
	private String path = localPath.substring(0, localPath.length()-1);
	private String file = "src/registrar/userkeys.csv";
		
	public void getTuple(int n){
		try (BufferedReader br = new BufferedReader(new FileReader(path+file))){
			int index = 0;
			for(String line; (line = br.readLine()) != null;){
				//System.out.println(line);
				if (index == n){
					String[] tuple = line.split(",");
					//System.out.println(tuple[0]+" & "+tuple[1]);
					this.user = tuple[0];
					this.pass = tuple[1];
				}
				index++;
			}
			if (index<=n){
				this.getTuple(n-index);
			}
		}catch (Exception e){
			System.out.println("Getting userpass problem:\n");
			e.printStackTrace();
		}	
	}
	
	public int numberOfAccounts(){
		try (BufferedReader br = new BufferedReader(new FileReader(path+file))){
			int index = 0;
			for(String line; (line = br.readLine()) != null;){
				index++;
			}
			return index;
		}catch (Exception e){
			System.out.println("Reading problem : \n"+e);
			return 0;
		}	
	}
	
	public void addTuple(String usr, String pss){
		try{
			Writer output;
			output = new BufferedWriter(new FileWriter(path+file,true));
			output.append("\n"+usr+","+pss);
			output.close();
		} catch (Exception e){
			System.out.println("Problem saving tuple\n"+e);
		}
	}
	
	public String getUser(){
		return this.user;
	}
	
	public String getPass(){
		return this.pass;
	}
	
	@Test
	public void checkGetTuple(){
		System.out.println("Checking load of username and password");
		this.getTuple(0);
		assertEquals("nic3chile@gmail.com", this.getUser());
		assertEquals("RVUtxErU2018",this.getPass());
	}
	
	@Test
	public void checkAddTuple(){
		System.out.println("Checking record of username and password");
		int before = this.numberOfAccounts();
		this.addTuple("nic3chile+0@gmail.com", "RVUtxErU2018");
		int after = this.numberOfAccounts();
		assertEquals(before+1, after);
		this.getTuple(after-1);
		assertEquals("nic3chile+0@gmail.com", this.getUser());
		assertEquals("RVUtxErU2018",this.getPass());
	}
}
