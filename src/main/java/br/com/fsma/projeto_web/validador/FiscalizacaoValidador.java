package br.com.fsma.projeto_web.validador;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fsma.projeto_web.modelo.dao.FiscalizacaoDao;
import br.com.fsma.projeto_web.modelo.negocio.Fiscalizacao;

@Named
@RequestScoped
public class FiscalizacaoValidador {

	@Inject
	private FiscalizacaoDao fiscalizacaoDao;
	private String mensagem;
	
	public String getMensagem() {
		return mensagem;
	}
	
	public boolean naoPodeIncluir(Fiscalizacao fiscalizacao) {

		if (fiscalizacao.getRazaoSocial().length() == 0 || fiscalizacao.getRazaoSocial() == null) {
			mensagem = "Razão social da empresa não informada.";
			return true;
		}
		
		if (fiscalizacao.getLogradouro().length() == 0 || fiscalizacao.getLogradouro() == null) {
			mensagem = "Logradouro da empresa não informado.";
			return true;
		}
		
		if (fiscalizacao.getCnpj().length() == 0 || fiscalizacao.getCnpj() == null) {
			mensagem = "CNPJ da empresa não informado.";
			return true;
		}
		
		if (fiscalizacao.getCidade() == null) {
			mensagem = "Cidade não informada.";
			return true;
		}
		
		if (fiscalizacao.getBairro() == null) {
			mensagem = "Bairro não informado.";
			return true;
		}
		
		if (fiscalizacao.getCidade() == null) {
			mensagem = "Cidade não informada.";
			return true;
		}
		
		if (fiscalizacao.getUf() == null) {
			mensagem = "Unidade federativa não informada.";
			return true;
		}
		
		Fiscalizacao cnpj = fiscalizacaoDao.buscaEmpresaPorCnpj(fiscalizacao.getCnpj());
		
		if (cnpj != null) {
			mensagem = "Essa empresa já foi cadastrada anteriormente.";
			return true;
		}

		return false;

	}
	
	public boolean naoPodeAlterar(Fiscalizacao fiscalizacao) {

		if (fiscalizacao.getRazaoSocial().length() == 0 || fiscalizacao.getRazaoSocial() == null) {
			mensagem = "Razão social da empresa não informada.";
			return true;
		}
		
		if (fiscalizacao.getLogradouro().length() == 0 || fiscalizacao.getLogradouro() == null) {
			mensagem = "Logradouro da empresa não informado.";
			return true;
		}
		
		if (fiscalizacao.getCnpj().length() == 0 || fiscalizacao.getCnpj() == null) {
			mensagem = "CNPJ da empresa não informado.";
			return true;
		}
		
		if (fiscalizacao.getCidade() == null) {
			mensagem = "Cidade não informada.";
			return true;
		}
		
		if (fiscalizacao.getBairro() == null) {
			mensagem = "Bairro não informado.";
			return true;
		}
		
		if (fiscalizacao.getCidade() == null) {
			mensagem = "Cidade não informada.";
			return true;
		}
		
		if (fiscalizacao.getUf() == null) {
			mensagem = "Unidade federativa não informada.";
			return true;
		}
		
		Fiscalizacao cnpj = fiscalizacaoDao.buscaEmpresaPorCnpj(fiscalizacao.getCnpj());
		
		if (cnpj != null) {
			mensagem = "Essa empresa já foi cadastrada anteriormente.";
			return true;
		}

		return false;

	}
}
