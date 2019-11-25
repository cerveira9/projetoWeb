package br.com.fsma.projeto_web.validador;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fsma.projeto_web.modelo.dao.CidadeDao;
import br.com.fsma.projeto_web.modelo.negocio.Cidade;
import static br.com.fsma.projeto_web.util.StringUtil.*;


@Named
@RequestScoped
public class CidadeValidador {
	
	@Inject
	private CidadeDao cidadeDao;
	private String mensagem;
	
	public String getMensagem() {
		return mensagem;
	}
	
	public boolean naoPodeIncluir(Cidade cidade) {

		if (isEmpty(cidade.getNome())) {
			mensagem = "Nome da cidade não informada.";
			return true;
		}
		
		if (cidade.getUf() == null) {
			mensagem = "Unidade federativa não informado.";
			return true;
		}
		Cidade nome = cidadeDao.buscaPorNome(cidade.getNome());
		Cidade uf = cidadeDao.buscaCidadePorNomeUf(cidade.getUf().getSigla());
		
		if (nome != null && uf != null) {
			mensagem = "Essa cidade já foi cadastrada anteriormente.";
			return true;
		}

		return false;

	}
	
	public boolean naoPodeAlterar(Cidade cidade) {

		if (cidade.getNome().length() == 0) {
			mensagem = "Nome da cidade não informada.";
			return true;
		}
		
		if (cidade.getUf() == null) {
			mensagem = "Unidade federativa não informado.";
			return true;
		}
		
//		Cidade nome = cidadeDao.buscaPorNome(cidade.getNome());
//		
//		if (nome != null) {
//			mensagem = "Essa cidade já foi cadastrada anteriormente.";
//			return true;
//		}

		return false;

	}

}
