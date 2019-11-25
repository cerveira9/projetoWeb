package br.com.fsma.projeto_web.modelo.negocio;

import java.io.Serializable;
import java.time.LocalDate;

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
@Table(name = "empresa", indexes = {
		@Index(name="buscaempresa_cnpj", columnList = "cnpj")
})
public class Empresa implements Serializable{

	@Transient
	private static final long serialVersionUID = 1L;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long id;
	@Column(name = "razaosocial", nullable = false, length = 100)
	private String razaoSocial;
	@Column(name = "logradouro", nullable = false, length = 100)
	private String logradouro;
	@Column(name = "cep", nullable = false, length = 9)
	private String cep;
	@Column(name = "cnpj", nullable = false, length = 20)
	private String cnpj;
	@Column(name = "dataUltimaFiscalizacao", nullable = false)
	private LocalDate dataUltimaFiscalizacao;
	@JoinColumn(name = "uf_fk")
	@OneToOne
	private Uf uf;
	@JoinColumn(name = "cidade_fk")
	@OneToOne
	private Cidade cidade;
	@JoinColumn(name = "bairro_fk")
	@OneToOne
	private Bairro bairro;
	
	public Empresa() {}
	
	public Empresa(String razaoSocial, String logradouro, String cep, String cnpj, Bairro bairro, Cidade cidade, Uf uf, LocalDate dataUltimaFiscalizacao) {
		this.razaoSocial = razaoSocial;
		this.logradouro = logradouro;
		this.cep = cep;
		this.cnpj = cnpj;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
		this.dataUltimaFiscalizacao = dataUltimaFiscalizacao;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial.toUpperCase();
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro.toUpperCase();
	}
	
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	

	public LocalDate getDataUltimaFiscalizacao() {
		return dataUltimaFiscalizacao;
	}

	public void setDataUltimaFiscalizacao(LocalDate dataUltimaFiscalizacao) {
		this.dataUltimaFiscalizacao = dataUltimaFiscalizacao;
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

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
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
		Empresa other = (Empresa) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Empresa [razaoSocial=" + razaoSocial + "]";
	}
	
}