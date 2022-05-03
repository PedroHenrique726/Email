package application;

import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.MensagensDao;
import model.dao.UsuarioDao;

public class Program {

	
	public static void main(String [] args) {
		Scanner teclado = new Scanner(System.in);
		String meuEmail = "";
		UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
		MensagensDao mensagensDao = DaoFactory.createMensagensDao();
		//criar usuario
		

		//usuarioDao.CriarUsuario("Alisson1234", "18851231324", "Alisson@email.com");
		
		//Usuario digita e-mail e senha 
		
		
		//teste login
		if (usuarioDao.login("Alisson@email.com", "18851231324") == true) {
			System.out.println("login granted");
			meuEmail = "Alisson@email.com";
		}
		else {
			System.out.println("login fail");
		}
		
		//mensagensDao.CriarMensagens(meuEmail, "Pedro@email.com", "Testando coisas", "corpo do email vamos ver se vai");
		mensagensDao.consultarMinhasMensagensEnviadas(meuEmail);
		
		//System.out.println(usuarioDao.consulta("Contatos", "Email", "Brunogallo@hotmail.com"));
		//System.out.println(usuarioDao.consultarContatos("Brunogallo@hotmail.com"));
		
		//usuarioDao.adicionarContatos("Alisson@email.com", "pedro@email.com");
		//usuarioDao.adicionarContatos("Brunogallo@hotmail.com", "Alisson@email.com");
		
		//usuarioDao.updateContatos("Brunogallo@hotmail.com", "thallys@email.com, alisson@email.com, thamires@email.com");
		//usuarioDao.apagarContatos("Brunogallo@hotmail.com", "alisson@email.com");
		
		
		
		teclado.close();
	}
	
}