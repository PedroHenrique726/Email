package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Usuario implements Serializable{


	private static final long serialVersionUID = 1L;
	private String userName;
	private Integer id;
	private String senha;
	private String email;
	
	
	public Usuario() {
		
	}


	public Usuario(String userName, Integer id, String senha, String email) {
		this.userName = userName;
		this.id = id;
		this.senha = senha;
		this.email = email;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}


	@Override
	public String toString() {
		return "Usuario [userName=" + userName + ", id=" + id + ", senha=" + senha + ", email=" + email + "]";
	}
	
	
	
	
	
}
