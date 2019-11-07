package br.com.fsma.projeto_web.validador;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fsma.projeto_web.modelo.dao.UfDao;
import br.com.fsma.projeto_web.modelo.negocio.Uf;

@Named
@RequestScoped
public class UfValidador {

	@Inject
	private UfDao ufDao;
	private String mensagem;
	
	public boolean naoPodeIncluir(Uf uf) {

		if (uf.getSigla().length() != 2) {
			mensagem = "Sigla com menos de 2 caracteres.";
			return true;
		}
		
		if (uf.getNome() == null || uf.getNome().length() == 0) {
			mensagem = "Nome não informado.";
			return true;
		}
		
		Uf sigla = ufDao.buscaPorSigla(uf.getSigla());
		
		if (sigla != null) {
			mensagem = "Essa uf já foi cadastrada anteriormente.";
			return true;
		}

		return false;

	}
	
	public boolean naoPodeAlterar(Uf uf) {

		if (uf.getSigla().length() != 2) {
			mensagem = "Sigla com menos de 2 caracteres.";
			return true;
		}
		
		if (uf.getNome() == null || uf.getNome().length() == 0) {
			mensagem = "Nome não informado.";
			return true;
		}
		
		Uf sigla = ufDao.buscaPorSigla(uf.getSigla());
		
		if (sigla != null) {
			mensagem = "Essa uf já foi cadastrada anteriormente.";
			return true;
		}

		return false;

	}

	public String getMensagem() {
		return mensagem;
	}

	
	
	
	
	
	
}
