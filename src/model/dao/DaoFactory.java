package model.dao;

import db.DB;
import model.dao.impl.MensagensDaoJDBC;
import model.dao.impl.UsuarioDaoJDBC;

public class DaoFactory {

	
	public static UsuarioDao createUsuarioDao() {
		return new UsuarioDaoJDBC(DB.getConnection());
	}
	
	public static MensagensDao createMensagensDao() {
		return new MensagensDaoJDBC(DB.getConnection());
	}
	
	
}
