package model.dao;

import java.util.List;

import model.entities.Usuario;

public interface UsuarioDao {

	void CriarUsuario(String name, String senha, String email);
	void update(Usuario obj);
	
	
	//contatos
	void adicionarContatos(String meuEmail, String emailAdicionado);
	String consultarContatos(String email);
	void apagarContatos(String meuEmail, String apagarContato);
	void updateContatos(String meuEmail, String alteracao);
	
	String consulta(String oque, String aonde, String parametro);
	String findById(int id);
	void deleteByid(Integer id);
	boolean login(String email, String senha);
	int findByEmail(String email);
	List<Usuario> findAll();
	
	
	
}
