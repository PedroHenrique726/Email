package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.UsuarioDao;
import model.entities.Usuario;

public class UsuarioDaoJDBC implements UsuarioDao {

	
	private Connection conn;
	
	public UsuarioDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Usuario insert(String name, String senha, String email) {
	
	
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"INSERT INTO Usuario (Email, Senha, ID, Username) "
					+ "VALUES (?, ?, ?, ?)"
					);
			st.setString(1, email);
			st.setString(2, senha);
			st.setInt(3, nextId());
			st.setString(4, name);
			
			st.executeUpdate();
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatament(st);
		}
		return null;
		
		
	}

	@Override
	public void update(Usuario obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteByid(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Usuario findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public int nextId() {
		int id = 0;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"select ID from usuario where ID = (SELECT MAX(id) FROM Usuario)");

			rs = st.executeQuery();
			
			id = rs.getInt("Id");
			
			
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
			DB.closeResultSet(rs);
			DB.closeStatament(st);
			}
		
		return id+1;
	}
	
	
	
}
