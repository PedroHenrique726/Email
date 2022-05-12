package model.dao;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UsuarioDao extends Remote{

	//teste
	public abstract boolean teste() throws RemoteException;
	
	//usuario
	public abstract String criarUsuario(String name, String senha, String email) throws RemoteException;
	public abstract boolean login(String email, String senha) throws RemoteException;
	//contatos
	public abstract String adicionarContatos(String meuEmail, String emailAdicionado) throws RemoteException;
	public abstract String consultarContatos(String email) throws RemoteException;
	public abstract String consultarContatosCliente(String email) throws RemoteException;
	public abstract String apagarContatos(String meuEmail, String apagarContato) throws RemoteException;
	public abstract void updateContatos(String meuEmail, String alteracao) throws RemoteException;
	//consultas
	
	public abstract String findNameByEmail(String email) throws RemoteException;
	public abstract String findById(int id) throws RemoteException;
	public abstract int findByEmail(String email) throws RemoteException;

	
	//mensagens
	public abstract String criarMensagens(String meuEmail, String para, String assunto, String mensagens) throws RemoteException;
	public abstract String consultarMinhasMensagens(String meuEmail) throws RemoteException;
	public abstract String consultarMinhasMensagensEnviadas(String meuEmail) throws RemoteException;

	
	
	
	
}
