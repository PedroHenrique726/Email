package model.dao;

import java.util.List;

import model.entities.Usuario;

public interface UsuarioDao {

	void CriarUsuario(String name, String senha, String email);
	void update(Usuario obj);
	
	
	//contatos
	void adicionarContatos(String meuEmail, String emailAdicionado);
	Usuario updateContatos(String contatos);
	String consultarContatos(String email);
	
	
	
	String consulta(String oque, String aonde, String parametro);
	String findById(int id);
	void deleteByid(Integer id);
	boolean login(String email, String senha);
	int findByEmail(String email);
	List<Usuario> findAll();
	
	
	
}
