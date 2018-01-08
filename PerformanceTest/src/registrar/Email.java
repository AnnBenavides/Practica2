/**Clase para manejar cuantas de email
 * 
 * tanto para AgregarUsuario como para la recuperacion de claves**/
package registrar;

public class Email {
	private String mailUser = "nic3chile";
	private String endMail = "@gmail.com";
	private String mailPass = "testaccount";
	private String nicPass = "RVUtxErU2018";
	private int generator = new UserAndPass().numberOfAccounts()-1;
	
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
		String newMail = generator + "." + mailUser + endMail;
		return newMail;
	}
	
	public int mailsUsed(){
		return generator;
	}
}

