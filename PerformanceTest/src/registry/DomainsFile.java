package registry;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que maneja los rtes tipos de alfabeto
 * consisten en archivos que tiene una palabra por linea
 * y se usaran para testeos de busqueda y otros
 * 
 * los archivos son:
 * 		simple.txt
 * 		number.txt
 * 		special.txt
 * **/
public class DomainsFile {
	private String localPath = new File(".").getAbsolutePath();
	private String path = localPath.substring(0, localPath.length()-1);
	private String simpleFile = "src/registry/simple.txt";
	private String numberFile = "src/registry/number.txt";
	private String specialFile = "src/registry/special.txt";
	
	/**
	 * Lee el archivo indicado y guarda todas la palabras en una lista
	 * 
	 * @param file	ruta en el proyecto del archivo de donde
	 * 				se leeran las palabras
	 * @return		Lista de palabras
	 * **/
	private List<String> getLines(String file){
		try (BufferedReader br = new BufferedReader(new FileReader(path+file))){
			List<String> alfabeto = new ArrayList<String>();
			for(String line; (line = br.readLine()) != null;){
				System.out.println(line);
				alfabeto.add(line);
			}
			return alfabeto;
		}catch (Exception e){
			System.out.println("! Problem getting lines of file");
			e.printStackTrace();
			return null;
		}	
	}
	
	/**
	 * Lee el archivo indicado y cuenta las lineas/palabras del archivo
	 * 
	 * @param file	ruta en el proyecto del archivo de donde
	 * 				se leeran las palabras
	 * @return		numero de palabras
	 * **/
	public int numberOfWords(String file){
		try (BufferedReader br = new BufferedReader(new FileReader(path+file))){
			int index = 0;
			for(; (br.readLine()) != null;){
				index++;
			}
			return index;
		}catch (Exception e){
			System.out.println("! Reading problem");
			e.printStackTrace();
			return 0;
		}	
	}
	
	/**
	 * Lee el archivo indicado y guarda la palabra en el archivo
	 * 
	 * @param file	ruta en el proyecto del archivo de donde
	 * 				se leeran las palabras
	 * @param word	palabra que se quiere agregar al diccionario
	 * **/
	public void addWord(String file, String word){
		try{
			Writer output;
			output = new BufferedWriter(new FileWriter(path+file,true));
			output.append("\n"+word);
			output.close();
		} catch (Exception e){
			System.out.println("Problem saving word\n");
			e.printStackTrace();
		}
	}
	
	/**
	 * Retorna un alfabeto de caracreteres simples
	 *
	 * @return		Lista de palabras
	 * @see 		getLines(String file)
	 * **/
	public List<String> getSimple(){
		return this.getLines(this.simpleFile);
	}
	
	/**
	 * Retorna un alfabeto de caracreteres numericos
	 *
	 * @return		Lista de palabras
	 * @see 		getLines(String file)
	 * **/
	public List<String> getNumber(){
		return this.getLines(this.numberFile);
	}
	
	/**
	 * Retorna un alfabeto de caracreteres especiales
	 *
	 * @return		Lista de palabras
	 * @see 		getLines(String file)
	 * **/
	public List<String> getSpecial(){
		return this.getLines(this.specialFile);
	}
	
	/**
	 * Retorna el numero de palabras en 
	 * el alfabeto simple
	 *
	 * @return		numero de palabras
	 * @see 		numberOfWords(String file)
	 * **/
	public int limitSimple(){
		return this.numberOfWords(simpleFile);
	}
	
	/**
	 * Retorna el numero de palabras en 
	 * el alfabeto numerico
	 *
	 * @return		numero de palabras
	 * @see 		numberOfWords(String file)
	 * **/
	public int limitNumber(){
		return this.numberOfWords(numberFile);
	}
	
	/**
	 * Retorna el numero de palabras en 
	 * el alfabeto especial
	 *
	 * @return		numero de palabras
	 * @see 		numberOfWords(String file)
	 * **/
	public int limitSpecial(){
		return this.numberOfWords(specialFile);
	}
}
