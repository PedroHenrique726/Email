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
import model.dao.MensagensDao;
import model.entities.Usuario;

public class MensagensDaoJDBC implements MensagensDao {

	private Connection conn;

	public MensagensDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	public void criarMensagens(String meuEmail, String para, String assunto, String mensagens) {

		meuEmail = meuEmail.toLowerCase();
		para = para.toLowerCase();

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("INSERT INTO Mensagens (De, Para, Assunto, Mensagem) " + "VALUES (?, ?, ?, ?)");
			st.setString(1, meuEmail);
			st.setString(2, para);
			st.setString(3, assunto);
			st.setString(4, mensagens);
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatament(st);
		}
	}

	@Override
	public void consultarMinhasMensagens(String meuEmail) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * " + "FROM Mensagens " + "WHERE Para = ?");

			st.setString(1, meuEmail);
			rs = st.executeQuery();
			while (rs.next()) {
				System.out.println("De: " + rs.getString("De") + " Assunto: " + rs.getString("Assunto")
						+ " Mensagem: \n" + rs.getString("Mensagem") + "\n\n");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatament(st);
		}

	}

	public void consultarMinhasMensagensEnviadas(String meuEmail) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * " + "FROM Mensagens " + "WHERE De = ?");

			st.setString(1, meuEmail);
			rs = st.executeQuery();
			while (rs.next()) {
				System.out.println("Para: " + rs.getString("Para") + " Assunto: " + rs.getString("Assunto")
						+ " Mensagem: \n" + rs.getString("Mensagem") + "\n\n");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatament(st);
		}
	}

	

}
