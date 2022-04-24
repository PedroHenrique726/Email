package model.dao;

import java.util.List;

import model.entities.Usuario;

public interface UsuarioDao {

	Usuario insert(String name, String senha, String email);
	void update(Usuario obj);
	void deleteByid(Integer id);
	Usuario findById(Integer id);
	List<Usuario> findAll();
	
}
