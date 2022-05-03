package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Mensagens implements Serializable {

	private static final long serialVersionUID = 1L;
	private String de;
	private String para;
	private String assunto;
	private String Mensagem;

	public Mensagens() {

	}

	public Mensagens(String de, String para, String assunto, String mensagem) {
		this.de = de;
		this.para = para;
		this.assunto = assunto;
		Mensagem = mensagem;
	}

	public String getDe() {
		return de;
	}

	public void setDe(String de) {
		this.de = de;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return Mensagem;
	}

	public void setMensagem(String mensagem) {
		Mensagem = mensagem;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Mensagem, assunto, de, para);
	}

	@Override
	public String toString() {
		return "Mensagens [de=" + de + ", para=" + para + ", assunto=" + assunto + ", Mensagem=" + Mensagem + "]";
	}


}
