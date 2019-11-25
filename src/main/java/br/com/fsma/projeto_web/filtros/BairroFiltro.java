package br.com.fsma.projeto_web.filtros;

public class BairroFiltro {

	private String nomeBairro;
	private Long ufId;
	private Long cidadeId;
	
	public String getNomeBairro() {
		return nomeBairro;
	}
	public void setNomeBairro(String nomeBairro) {
		this.nomeBairro = nomeBairro;
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
	
	
}
