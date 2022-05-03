package model.dao;



import model.entities.Usuario;

public interface UsuarioDao {

	//usuario
	void CriarUsuario(String name, String senha, String email);
	boolean login(String email, String senha);
	//contatos
	void adicionarContatos(String meuEmail, String emailAdicionado);
	String consultarContatos(String email);
	void apagarContatos(String meuEmail, String apagarContato);
	void updateContatos(String meuEmail, String alteracao);
	//consultas
	String consulta(String oque, String aonde, String parametro);
	String findById(int id);
	int findByEmail(String email);


	
	
	
	
}
