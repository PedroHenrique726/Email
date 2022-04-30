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

		int id = nextId();

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("INSERT INTO Usuario (Email, Senha, ID, Username) " + "VALUES (?, ?, ?, ?)");
			st.setString(1, email);
			st.setString(2, senha);
			st.setInt(3, id);
			st.setString(4, name);

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatament(st);
		}
		return null;

	}

	@Override
	public boolean login(String email, String senha) {
		
		String senhaBanco;
		
			PreparedStatement st = null;
			ResultSet rs = null;
			try {
				st = conn.prepareStatement("SELECT Senha " + "FROM Usuario " + "WHERE Email = ?");

				st.setString(1, email);
				rs = st.executeQuery();
				 rs.next(); 
				 senhaBanco = (String) rs.getString("Senha");
				
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			} finally {
				DB.closeResultSet(rs);
				DB.closeStatament(st);
			}		
			if (senha.equals(senhaBanco)) {
				return true;
			}
			else {
				return false;
			}
		
	}

	@Override
	public void update(Usuario obj) {

	}

	@Override
	public void deleteByid(Integer id) {
		// nao alterado ainda

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM Usuario WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatament(st);
		}

	}

	@Override
	public int findByEmail(String email) {
		int id = 0;

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT ID " + "FROM Usuario " + "WHERE Email = ?");

			st.setString(1, email);
			rs = st.executeQuery();
			if (rs.next()) {
				id = rs.getInt("ID");

				return id;
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatament(st);
		}
		return id;

	}

	@Override
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public void validacao() {
		int id = 0;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement("SELECT MAX(id) as maxId FROM Usuario");

			rs = st.executeQuery();
			rs.next();
			id = rs.getInt("maxId");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatament(st);
		}

	}

	public int nextId() {
		int id = 0;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement("SELECT MAX(id) as maxId FROM Usuario");

			rs = st.executeQuery();
			rs.next();
			id = rs.getInt("maxId");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatament(st);
		}

		return id + 1;
	}

}
