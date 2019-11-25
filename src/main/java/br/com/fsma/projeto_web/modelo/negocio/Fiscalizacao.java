package br.com.fsma.projeto_web.modelo.negocio;

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
@Table(name = "fiscalizacao", indexes = {
		@Index(name="buscafiscalizacao_cnpj", columnList = "cnpj, dataultimafiscalizacao")
})
public class Fiscalizacao {

	@Transient
	private static final long serialVersionUID = 1L;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long id;
	@Column(name = "razaosocial", nullable = false, length = 100)
	private String razaoSocial;
	@Column(name = "logradouro", nullable = false, length = 100)
	private String logradouro;
	@Column(name = "cnpj", nullable = false, length = 20)
	private String cnpj;
	@Column(name = "dataultimafiscalizacao", nullable = false)
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
	@JoinColumn(name = "empresa_fk")
	@OneToOne
	private Empresa empresa;
	
	public Fiscalizacao() {}
	
	public Fiscalizacao(String razaoSocial, String logradouro, String cnpj, Bairro bairro, Cidade cidade, Uf uf, LocalDate dataUltimaFiscalizacao, Empresa empresa) {
		this.razaoSocial = razaoSocial;
		this.logradouro = logradouro;
		this.cnpj = cnpj;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
		this.dataUltimaFiscalizacao = dataUltimaFiscalizacao;
		this.empresa = empresa;
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
		this.razaoSocial = razaoSocial;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
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

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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
		Fiscalizacao other = (Fiscalizacao) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Fiscalizacao [empresa=" + empresa + "]";
	}
}
