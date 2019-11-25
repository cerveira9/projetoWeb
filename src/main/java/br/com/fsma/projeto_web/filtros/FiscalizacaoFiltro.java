package br.com.fsma.projeto_web.filtros;

public class FiscalizacaoFiltro {
	
	private Long empresaId;
	private Long bairroId;
	private Long ufId;
	private Long cidadeId;
	private String cnpj;
	
	public Long getEmpresaId() {
		return empresaId;
	}
	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}
	public Long getBairroId() {
		return bairroId;
	}
	public void setBairroId(Long bairroId) {
		this.bairroId = bairroId;
	}
	public Long getUfId() {
		return ufId;
	}
	public void setUfId(Long ufId) {
		this.ufId = ufId;
	}
	public Long getCidadeId() {
		return cidadeId;
	}
	public void setCidadeId(Long cidadeId) {
		this.cidadeId = cidadeId;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}	
	
}
