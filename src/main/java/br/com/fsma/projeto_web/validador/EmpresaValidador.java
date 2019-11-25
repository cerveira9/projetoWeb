package br.com.fsma.projeto_web.validador;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fsma.projeto_web.modelo.dao.EmpresaDao;
import br.com.fsma.projeto_web.modelo.negocio.Empresa;

@Named
@RequestScoped
public class EmpresaValidador {
	
	@Inject
	private EmpresaDao empresaDao;
	private String mensagem;
	
	public String getMensagem() {
		return mensagem;
	}
	
	public boolean naoPodeIncluir(Empresa empresa) {

		if (empresa.getRazaoSocial().length() == 0 || empresa.getRazaoSocial() == null) {
			mensagem = "Razão social da empresa não informada.";
			return true;
		}
		
		if (empresa.getLogradouro().length() == 0 || empresa.getLogradouro() == null) {
			mensagem = "Logradouro da empresa não informado.";
			return true;
		}
		
		if (empresa.getCep().length() == 0 || empresa.getCep() == null) {
			mensagem = "Cep da empresa não informado.";
			return true;
		}
		
		if (empresa.getCnpj().length() == 0 || empresa.getCnpj() == null) {
			mensagem = "CNPJ da empresa não informado.";
			return true;
		}
		
		if (empresa.getCidade() == null) {
			mensagem = "Cidade não informada.";
			return true;
		}
		
		if (empresa.getBairro() == null) {
			mensagem = "Bairro não informado.";
			return true;
		}
		
		if (empresa.getCidade() == null) {
			mensagem = "Cidade não informada.";
			return true;
		}
		
		if (empresa.getUf() == null) {
			mensagem = "Unidade federativa não informada.";
			return true;
		}
		
		Empresa cnpj = empresaDao.buscaEmpresaPorCnpj(empresa.getCnpj());
		
		if (cnpj != null) {
			mensagem = "Essa empresa já foi cadastrada anteriormente.";
			return true;
		}

		return false;

	}
	
	public boolean naoPodeAlterar(Empresa empresa) {

		if (empresa.getRazaoSocial().length() == 0 || empresa.getRazaoSocial() == null) {
			mensagem = "Razão social da empresa não informada.";
			return true;
		}
		
		if (empresa.getLogradouro().length() == 0 || empresa.getLogradouro() == null) {
			mensagem = "Logradouro da empresa não informado.";
			return true;
		}
		
		if (empresa.getCep().length() == 0 || empresa.getCep() == null) {
			mensagem = "Cep da empresa não informado.";
			return true;
		}
		
		if (empresa.getCnpj().length() == 0 || empresa.getCnpj() == null) {
			mensagem = "CNPJ da empresa não informado.";
			return true;
		}
		
		if (empresa.getCidade() == null) {
			mensagem = "Cidade não informada.";
			return true;
		}
		
		if (empresa.getBairro() == null) {
			mensagem = "Bairro não informado.";
			return true;
		}
		
		if (empresa.getCidade() == null) {
			mensagem = "Cidade não informada.";
			return true;
		}
		
		if (empresa.getUf() == null) {
			mensagem = "Unidade federativa não informada.";
			return true;
		}
		
//		Empresa cnpj = empresaDao.buscaEmpresaPorCnpj(empresa.getCnpj());
//		
//		if (cnpj != null) {
//			mensagem = "Essa empresa já foi cadastrada anteriormente.";
//			return true;
//		}

		return false;

	}

}
