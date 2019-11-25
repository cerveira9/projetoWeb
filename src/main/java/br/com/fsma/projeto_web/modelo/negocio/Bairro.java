package br.com.fsma.projeto_web.modelo.negocio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "bairro", indexes = {
		@Index(name="buscabairro_nome", columnList = "nome, uf_fk, cidade_fk")
})
public class Bairro implements Serializable{
	
	@Transient
	private static final long serialVersionUID = 1L;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long id;
	@Column(name = "nome", nullable = false, length = 50)
	private String nome;
	@JoinColumn(name = "uf_fk")
	@OneToOne
	private Uf uf;
	@JoinColumn(name = "cidade_fk")
	@OneToOne
	private Cidade cidade;
	
	public Bairro() {}
	
	public Bairro(String nome, Cidade cidade, Uf uf) {
		this.nome = nome;
		this.cidade = cidade;
		this.uf = uf;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome.toUpperCase();
	}

	public Uf getUf() {
		return uf;
	}

	public void setUf(Uf uf) {
		this.uf = uf;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bairro other = (Bairro) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bairro [nome=" + nome + "]";
	}

}

