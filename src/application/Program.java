package application;

import model.dao.DaoFactory;
import model.dao.UsuarioDao;

public class Program {

	
	public static void main(String [] args) {
		
		
		//criar usuario
		UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();
		//usuarioDao.insert("Bruno", "Gallo", "Brunogallo@hotmail.com");
		
		//Usuario digita e-mail e senha 
		
		
		//teste login
		if (usuarioDao.login("Brunogallo@hotmail.com", "Gallo") == true) {
			System.out.println("login granted");
		}
		else {
			System.out.println("login fail");
		}
		
		
	}
	
}