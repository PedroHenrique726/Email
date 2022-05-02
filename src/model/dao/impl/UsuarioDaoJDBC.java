package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import db.DB;
import db.DbException;
import model.dao.UsuarioDao;
import model.entities.Usuario;

public class UsuarioDaoJDBC implements UsuarioDao {

	private Connection conn;

	public UsuarioDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	public void CriarUsuario(String name, String senha, String email) {
		if (isValidEmailAddressRegex(email)) {

			int id = nextId();

			PreparedStatement st = null;
			ResultSet rs = null;

			try {
				st = conn.prepareStatement("INSERT INTO Usuario (Email, Senha, ID, Nome ) " + "VALUES (?, ?, ?, ?)");
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
		}else {
			System.out.println("Email digitado errado");
		}
	

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
		} else {
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
	public String findById(int id) {
		String email = null;

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT Email " + "FROM Usuario " + "WHERE ID = ?");

			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				email = rs.getString("Email");

				return email;
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatament(st);
		}
		return email;
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

	public boolean isValidEmailAddressRegex(String email) {
		boolean isEmailIdValid = false;

		if (email != null && email.length() > 0 && isEmailUsed(email) == false) {
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			if (matcher.matches()) {
				isEmailIdValid = true;
			}
		}
		return isEmailIdValid;
	}

	@Override
	public Usuario updateContatos(String contatos) {

		return null;
	}

	@Override
	public void adicionarContatos(String meuEmail, String emailAdicionado) {
		String contatos;
		if(consultarContatos(meuEmail) == null) {
		contatos = emailAdicionado;
		}else {
			contatos = consultarContatos(meuEmail) + ", " + emailAdicionado;
		}
		 
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("update usuario set contatos = ? where email = ?");
			st.setString(2, meuEmail);
			st.setString(1, contatos);

			st.executeUpdate();
			System.out.println("Contato adicionado");

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatament(st);
		}
	}

	public String consultarContatos(String email) {
		String lista = "";
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT Contatos " + "FROM Usuario " + "WHERE email = ?");

			st.setString(1, email);

			rs = st.executeQuery();
			rs.next();
			lista = rs.getString("Contatos");
			return lista;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatament(st);
		}
	}

	@Override
	public String consulta(String oque, String aonde, String parametro) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT ? " + "FROM Usuario " + "WHERE ? = ?");

			st.setString(1, oque);
			st.setString(2, aonde);
			st.setString(3, parametro);
			rs = st.executeQuery();
			rs.next();
			oque = rs.getString(aonde);
			return oque;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatament(st);
		}

	}

	public boolean isEmailUsed(String email) {
		int count = 0;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT email FROM Usuario");
			rs = st.executeQuery();
			while (rs.next()) {	
				if (email.equals(rs.getString("Email"))) {
					count++;

				}
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatament(st);
		}

		if (count > 0) {
			System.out.println("Email esta sendo utilizado");
			return true;
		} else {
			return false;
		}

	}

}
