import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;


/** Clase que maneja las URL de registry y registrar
 * consisten en archivos cuyas lineas contienen los pares
 * 
 * 	clase,url
 * 
 * y se usaran para cargar las URL que necesita cada clase
 * 
 * los archivos son:
 * 		registry/urls.csv
 * 		registrar/urls.csv
 * **/
public class UrlHandler {
	private String localPath = new File(".").getAbsolutePath();
	private String path = localPath.substring(0, localPath.length()-1);
	private String registry = "src/registry/urls.csv";
	private String registrar = "src/registrar/urls.csv";
	
	/**Lee las urls de registry y entrega la encontrada
	 * 
	 * @param className		nombre de la clase que necesita su URL
	 * @return				la URL
	 * **/
	public String searchInRegistry(String className){
		try (BufferedReader br = new BufferedReader(new FileReader(path+registry))){
			for(String line; (line = br.readLine()) != null;){
				System.out.println(line);
				String[] row = line.split(","); //[0]: className, [1]: url
				if (row[0].equals(className)){
					return row[1];
				}
			}
			System.out.println("! Class name not found");
			return null;
		}catch (Exception e){
			System.out.println("! Problem getting url from file");
			e.printStackTrace();
			return null;
		}	
	}
	
	/**Lee las urls de registrar y entrega la encontrada
	 * 
	 * @param className		nombre de la clase que necesita su URL
	 * @return				la URL
	 * **/
	public String searchInRegistrar(String className){
		try (BufferedReader br = new BufferedReader(new FileReader(path+registrar))){
			for(String line; (line = br.readLine()) != null;){
				System.out.println(line);
				String[] row = line.split(","); //[0]: className, [1]: url
				if (row[0].equals(className)){
					return row[1];
				}
			}
			System.out.println("! Class name not found");
			return null;
		}catch (Exception e){
			System.out.println("! Problem getting url from file");
			e.printStackTrace();
			return null;
		}	
	}
	
	/** Agrega a registry el nombre de una clase y su URL
	 * como el par
	 * 		clase,URL
	 * 
	 * @param className		nombre de la clase, con .java
	 * @param url			URL que debe abrir para funcionar
	 * **/
	public void addRegistryData(String className, String url){
		try{
			Writer output;
			output = new BufferedWriter(new FileWriter(path+registry,true));
			output.append("\n"+className+","+url);
			output.close();
		} catch (Exception e){
			System.out.println("Problem saving word\n");
			e.printStackTrace();
		}
	}
	
	/** Agrega a registrar el nombre de una clase y su URL
	 * como el par
	 * 		clase,URL
	 * 
	 * @param className		nombre de la clase, con .java
	 * @param url			URL que debe abrir para funcionar
	 * **/
	public void addRegistrarData(String className, String url){
		try{
			Writer output;
			output = new BufferedWriter(new FileWriter(path+registrar,true));
			output.append("\n"+className+","+url);
			output.close();
		} catch (Exception e){
			System.out.println("Problem saving word\n");
			e.printStackTrace();
		}
	}
}
