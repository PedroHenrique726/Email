package model.dao.impl;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import db.DB;
import db.DbException;
import model.dao.UsuarioDao;

public class UsuarioDaoJDBC implements UsuarioDao {

	private Connection conn;

	public UsuarioDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public String criarUsuario(String name, String senha, String email) throws RemoteException {
		if (isValidEmailAddressRegex(email)) {
			email = email.toLowerCase();
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

				return "Usuário registrado com sucesso";

			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			} finally {
				DB.closeResultSet(rs);
				DB.closeStatament(st);
			}
		} else {
			return "Email digitado já existe ou email digitado errado!";
		}

	}

	@Override
	public boolean login(String email, String senha) throws RemoteException {

		String senhaBanco;
		if (isEmailUsed(email)) {
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

		} else {
			return false;
		}
	}

	@Override
	public String findNameByEmail(String email) throws RemoteException {
		String nome = "";
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT Nome " + "FROM Usuario " + "WHERE Email = ?");

			st.setString(1, email);
			rs = st.executeQuery();
			if (rs.next()) {
				nome = rs.getString("Nome");

				return nome;
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatament(st);
		}

		return nome;
	}

	@Override
	public int findByEmail(String email) throws RemoteException {
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
	public String findById(int id) throws RemoteException {
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

	public int nextId() throws RemoteException {
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

	public boolean isValidEmailAddressRegex(String email) throws RemoteException {
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
	public String apagarContatos(String meuEmail, String apagarContato) throws RemoteException {
		String lista = consultarContatos(meuEmail);
		String[] listaSeparada = lista.split(", ");
		String listaAtualizada = "";

		for (int i = 0; i < listaSeparada.length; i++) {
			if (consultarContatos(meuEmail) != null && listaSeparada[i].equals(apagarContato)) {

			} else if (i == listaSeparada.length - 1) {
				listaAtualizada += listaSeparada[i];
			} else if (i < listaSeparada.length) {
				listaAtualizada += listaSeparada[i] + ", ";
			}
		}
		updateContatos(meuEmail, listaAtualizada);
		return "Contato apagado com sucesso";
	}

	@Override
	public String adicionarContatos(String meuEmail, String emailAdicionado) {
		String contatos;
		if (consultarContatos(meuEmail) == null) {
			contatos = emailAdicionado.toLowerCase();
		} else {
			contatos = consultarContatos(meuEmail) + ", " + emailAdicionado.toLowerCase();
		}

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("update usuario set contatos = ? where email = ?");

			st.setString(2, meuEmail);
			st.setString(1, contatos);
			st.executeUpdate();
			return "Contato adicionado";
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatament(st);
		}
	}

	@Override
	public String consultarContatosCliente(String email) {
		String lista = "Não há Contatos";
		String resposta = "Contatos:\n";
		String[] listaSeparada;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT Contatos " + "FROM Usuario " + "WHERE email = ?");

			st.setString(1, email);
			rs = st.executeQuery();
			rs.next();
			lista = rs.getString("Contatos");
			if (!lista.equals("")) {
				
			listaSeparada = lista.split(", ");
			
			for(int i = 0; i < listaSeparada.length; i++) {
				resposta += i+1 + "# " + listaSeparada[i] + " \n";
			}
			return resposta;
			}else {
				
				return lista;
			}
			
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
	public void updateContatos(String meuEmail, String alteracao) throws RemoteException {
		PreparedStatement st = null;
		int id = findByEmail(meuEmail);

		try {
			st = conn.prepareStatement("update usuario set contatos = ? where id = ?");

			st.setString(1, alteracao);
			st.setInt(2, id);
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatament(st);
		}
	}

	public boolean isEmailUsed(String email) throws RemoteException {
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

			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean teste() throws RemoteException {

		criarUsuario("Teste", "teste123", "teste123@teste.com");
		adicionarContatos("teste123@teste.com", "teste1@hotmail.com");
		adicionarContatos("teste123@teste.com", "teste2@hotmail.com");
		adicionarContatos("teste123@teste.com", "teste3@hotmail.com");
		apagarContatos("teste123@teste.com", "teste2@hotmail.com");
		if (!login("teste123@teste.com", "teste123")) {
			return false;
		} else if (!consultarContatos("teste123@teste.com").equals("teste1@hotmail.com, teste3@hotmail.com")) {

			return false;
		} else {
			PreparedStatement st = null;

			try {
				st = conn.prepareStatement("delete from usuario where id = ?");

				st.setInt(1, findByEmail("teste123@teste.com"));
				st.executeUpdate();

			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			} finally {

				DB.closeStatament(st);
			}
			return true;
		}

	}

	@Override
	public void criarMensagens(String meuEmail, String para, String assunto, String mensagens) throws RemoteException {

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
	public String consultarMinhasMensagens(String meuEmail) throws RemoteException {
		String emails = "";
		String[] mensagemSeparada;
		String mensagemAjustada = "";

		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * " + "FROM Mensagens " + "WHERE Para = ?");

			st.setString(1, meuEmail);
			rs = st.executeQuery();
			while (rs.next()) {
				mensagemSeparada = rs.getString("mensagem").split(" ");
				for (int i = 0; i < mensagemSeparada.length; i++) {
					mensagemAjustada += mensagemSeparada[i] + " ";
					if (i % 10 == 0 && i> 1) {
						mensagemAjustada += " \n";
					}
				}

				emails += "De: " + rs.getString("De") + "\nPara: " + rs.getString("para") + "\nAssunto: "
						+ rs.getString("Assunto") + "\nMensagem: " + mensagemAjustada + "\n\n\n";

				mensagemAjustada = "";
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatament(st);
		}

		return emails;

	}

	public String consultarMinhasMensagensEnviadas(String meuEmail) throws RemoteException {
		String emails = "";
		String[] mensagemSeparada;
		String mensagemAjustada = "";
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * " + "FROM Mensagens " + "WHERE De = ?");

			st.setString(1, meuEmail);
			rs = st.executeQuery();
			while (rs.next()) {
				mensagemSeparada = rs.getString("mensagem").split(" ");
				for (int i = 0; i < mensagemSeparada.length; i++) {
					mensagemAjustada += mensagemSeparada[i] + " ";
					if (i % 10 == 0 && i> 1) {
						mensagemAjustada += " \n";
					}
				}

				emails += "De: " + rs.getString("De") + "\nPara: " + rs.getString("para") + "\nAssunto: "
						+ rs.getString("Assunto") + "\nMensagem: " + mensagemAjustada + "\n\n\n";
				
				mensagemAjustada = "";
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatament(st);
		}

		return emails;
	}
}
