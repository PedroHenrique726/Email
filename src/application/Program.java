package application;

import model.dao.DaoFactory;
import model.dao.UsuarioDao;

public class Program {

	
	public static void main(String [] args) {
		UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
		
		//criar usuario
		
		//usuarioDao.CriarUsuario("Bruno", "Gallo", "Brunogallo@hotmail.com");
		
		//Usuario digita e-mail e senha 
		
		
		//teste login
		/*if (usuarioDao.login("bigodegrosso@yahoo.com", "Motoqueiro01") == true) {
			System.out.println("login granted");
		}
		else {
			System.out.println("login fail");
		}*/
		//System.out.println(usuarioDao.consulta("Contatos", "Email", "Brunogallo@hotmail.com"));
		System.out.println(usuarioDao.consultarContatos("pedro@email.com"));
		
		usuarioDao.adicionarContatos("pedro@email.com", "Thamires@hotmail.com");
		//usuarioDao.adicionarContatos("Brunogallo@hotmail.com", "thallys@email.com");
		
	}
	
}