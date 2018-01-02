package registrar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;


/**Clase que permitira ingresar y tomar combinaciones
 * de usuario/clave desde un archivo en csv
 * 
 * el archivo de llama "userkeys.csv"
 * 
 * dicho archivo no se subira al repositorio por seguridad**/
public class UserAndPass {
	private String user;
	private String pass;
	private String file = "userkeys.csv";
		
	public void getTuple(int n){
		try (BufferedReader br = new BufferedReader(new FileReader(file))){
			int size=0;
			for(String line; (line = br.readLine()) != null;){ size++; }
			int index = 0;
			n = n%size;
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
		}catch (Exception e){
			System.out.println("Getting userpass problem:\n"+e);
		}	
	}
	
	public void addTuple(String usr, String pss){
		try{
			Writer output;
			output = new BufferedWriter(new FileWriter(file,true));
			output.append(usr+","+pss);
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
}
