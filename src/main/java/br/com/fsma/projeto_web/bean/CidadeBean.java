package br.com.fsma.projeto_web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fsma.projeto_web.modelo.dao.CidadeDao;
import br.com.fsma.projeto_web.modelo.negocio.Cidade;

@Named
@ViewScoped
public class CidadeBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	private CidadeDao cidadeDao;
	private Cidade cidade;
	private String nome;
	private List<Cidade> cidades = new ArrayList<Cidade>();
	
	public Cidade getCidade() {
		return cidade;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setUfs(List<Cidade> cidades) {
		this.cidades = cidades;
	}
	
	public String novaCidade() {
		cidade = new Cidade();
		System.out.println("NOVA Cidade");
		return "/view/cidade/cadastro.xhtml";
	}
	
	public String cadastrarCidade() {
		cidadeDao.adiciona(cidade);
		return "/view/cidade/listaCidade.xhtml";		
	}
	
	public String acessoCidade() {
		cidades = cidadeDao.buscaTodasCidades();
		return "/view/cidade/listaUf.xhtml";		
	}

	


	

}
