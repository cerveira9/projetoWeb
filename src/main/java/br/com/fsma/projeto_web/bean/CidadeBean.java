package br.com.fsma.projeto_web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.fsma.projeto_web.filtros.CidadeFiltro;
import br.com.fsma.projeto_web.modelo.dao.CidadeDao;
import br.com.fsma.projeto_web.modelo.dao.UfDao;
import br.com.fsma.projeto_web.modelo.negocio.Cidade;
import br.com.fsma.projeto_web.modelo.negocio.Uf;
import br.com.fsma.projeto_web.tx.Transacional;
import br.com.fsma.projeto_web.validador.CidadeValidador;

@Named
@ViewScoped
public class CidadeBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	private CidadeDao cidadeDao;
	@Inject
	private UfDao ufDao;
	@Inject
	private CidadeValidador validador;
	private CidadeFiltro filtro;
	
	private Cidade cidade;
	private List<Cidade> cidades = new ArrayList<Cidade>();
	private Uf uf;
	private List<Uf> ufs;
	private Long idUfSelecionada;

	private enum Status {LISTANDO, ALTERANDO, INCLUINDO};
	private Status status;
	
	@PostConstruct
	public void init() {
		filtro = new CidadeFiltro();
		status = Status.LISTANDO;
	}
	
	public boolean isEditando() {
		return  (status == Status.INCLUINDO) || (status == Status.ALTERANDO);
	}

	public boolean isAlterando() {
		return status == Status.ALTERANDO;
	}

	public boolean isListando() {
		return status == Status.LISTANDO;
	}

	public boolean isIncluindo() {
		return status == Status.INCLUINDO;
	}
	
	public List<Cidade> getCidades() {
		return cidades;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public Uf getUf() {
		return uf;
	}

	public List<Uf> getUfs() {
		return ufs;
	}
	
	public List<Uf> listaUfs() {
        ufs = ufDao.buscaTodasUfs();
        return ufs;
    }

	public Long getIdUfSelecionada() {
		return idUfSelecionada;
	}
	
	public void setIdUfSelecionada(Long idUfSelecionada) {
		this.idUfSelecionada = idUfSelecionada;
	}
	
	public CidadeFiltro getFiltro() {
		return filtro;
	}

	public void solicitaIncluir() {
		cidade = new Cidade();
		status = Status.INCLUINDO;
	}
	
	@Transacional
	public void confirmaInclusao() {
		
		Uf uf = ufDao.buscaPorId(idUfSelecionada);
		cidade.setUf(uf);
		
		if (validador.naoPodeIncluir(cidade)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", validador.getMensagem()));
			return;
		}
		
		cidadeDao.adiciona(cidade);
		cidades = cidadeDao.buscaTodasCidades();
		status = Status.LISTANDO;
	}
	
	public void cancelarInclusao() {
		status = Status.LISTANDO;
		cidade = null;
	}
	
	public void solicitaAlterar(Long cidadeId) {
		cidade = cidadeDao.buscaPorId(cidadeId);
		status = Status.ALTERANDO;
	}
	
	@Transacional
	public void confirmaAlteracao() {
		
		Uf uf = ufDao.buscaPorId(idUfSelecionada);
		cidade.setUf(uf);
		
		if (validador.naoPodeAlterar(cidade)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", validador.getMensagem()));
			return;
		}
		
		cidadeDao.atualiza(cidade);
		cidades.remove(cidade);
		cidades.add(cidade);
		status = Status.LISTANDO;
	}
	
	@Transacional
	public void confirmaExclusao(Long cidadeId) {
		cidade = cidadeDao.buscaPorId(cidadeId);
		cidadeDao.remove(cidade);
		cidades.remove(cidade);
		status = Status.LISTANDO;
	}
	
	public void cancelarAlteracao() {
		status = Status.LISTANDO;
		cidade = null;
	}

	public void cancelarBusca() {
		status = Status.LISTANDO;
		
	}
	
	public void buscaCidade() {
		cidades = cidadeDao.busca(filtro);
		status = Status.LISTANDO;
	}
	
}
