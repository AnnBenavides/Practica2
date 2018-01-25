/**Clase para manejar cuantas de email
 * 
 * tanto para AgregarUsuario como para la recuperacion de claves**/
package registrar;

public class Email {
	private String mailUser = "nic3chile";
	private String endMail = "@gmail.com";
	private String mailPass = "testaccount"; //clave del mail
	private String nicPass = "#RVUtxErU2018"; //clave para NIC
	private int generator = new UserAndPass().numberOfAccounts()-1;
	
	/** Entrega la cuenta de correo padre
	 * 
	 * @return	mail principal nic3chile(at)gmail(dot)com
	 * */
	public String getMainMail(){
		return mailUser + endMail;
	}
	
	/** Entrega la clave de la cuenta de correo
	 * 
	 * @return	password del correo padre
	 * */
	public String getPass(){
		return mailPass;
	}
	
	/** Entrega clave de ususario para el sitio NIC.cl
	 * 
	 * @return	clave usuario
	 * */
	public String getNicPass(){
		return nicPass;
	}
	
	/** Genera una nueva cuenta de usuario
	 * de la forma:
	 * 		x.nic3chile(at)gmail(dot)com
	 * asi Registrar lo tomará como un usuario nuevo
	 * pero todas los correos llegarán a la cuenta
	 * principal
	 * 
	 * @return		nuevo mail para usuario
	 * */
	public String getNewMail(){
		generator++;
		String newMail = mailUser + "+" + generator + endMail;
		return newMail;
	}
	
	/** Cuenta cuantos usuarios hay en el archivo 'userkeays.csv'
	 * 
	 * @return		cantidad de usuarios creados testeables
	 * 
	 * @see			UsreAndPass class
	 * */
	public int mailsUsed(){
		return generator;
	}
}

