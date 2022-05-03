package model.dao;

public interface MensagensDao {

	void criarMensagens(String meuEmail, String para, String assunto, String mensagens);
	void consultarMinhasMensagens(String meuEmail);
	void consultarMinhasMensagensEnviadas(String meuEmail);
	
	
	
}
